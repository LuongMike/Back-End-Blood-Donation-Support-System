package com.example.backend_blood_donation_system.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.backend_blood_donation_system.entity.DonationCertificate;
import com.example.backend_blood_donation_system.entity.DonationHistory;
import com.example.backend_blood_donation_system.repository.DonationCertificateRepository;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;

@Service
public class CertificateService {

        private final DonationCertificateRepository certificateRepository;
        private final Path pdfStorageDir;

        @Autowired
        public CertificateService(DonationCertificateRepository certificateRepository,
                        @Value("${certificate.storage-dir:./certificates-storage}") String storageDir) {
                this.certificateRepository = certificateRepository;
                this.pdfStorageDir = Paths.get(storageDir);
        }

        public void generateCertificateForDonation(DonationHistory donation) throws IOException {
                String certificateCode = UUID.randomUUID().toString().substring(0, 13).toUpperCase().replace("-", "");

                if (!Files.exists(this.pdfStorageDir)) {
                        Files.createDirectories(this.pdfStorageDir);
                }
                String fileName = "certificate-" + donation.getDonationId() + ".pdf";
                Path pdfPath = this.pdfStorageDir.resolve(fileName);

                createPdfFile(pdfPath.toString(), donation, certificateCode);

                DonationCertificate certificate = DonationCertificate.builder()
                                .user(donation.getUser())
                                .donationHistory(donation)
                                .certificateCode(certificateCode)
                                .issueDate(LocalDate.now())
                                .pdfStoragePath(pdfPath.toString())
                                .build();

                certificateRepository.save(certificate);
        }

        private void createPdfFile(String dest, DonationHistory donation, String code) throws IOException {
                PdfWriter writer = new PdfWriter(dest);
                PdfDocument pdf = new PdfDocument(writer);
                pdf.setDefaultPageSize(PageSize.A4);
                Document document = new Document(pdf);
                document.setMargins(60, 60, 80, 60);

                // --- Tải tài nguyên một cách an toàn ---
                PdfFont fontBold = loadFontFromClasspath("/fonts/NotoSans-Bold.ttf");
                PdfFont fontRegular = loadFontFromClasspath("/fonts/NotoSans-Regular.ttf");
                byte[] logoBytes = loadImageFromClasspath("/images/logo.png");

                // --- Header ---
                Table headerTable = new Table(UnitValue.createPercentArray(new float[] { 1, 3 }))
                                .useAllAvailableWidth();
                headerTable.setBorder(null);

                // Logo
                if (logoBytes != null) {
                        // Bỏ khối try-catch ở đây
                        Image logo = new Image(ImageDataFactory.create(logoBytes));
                        logo.setHeight(60);
                        Cell logoCell = new Cell().add(logo).setBorder(null).setTextAlignment(TextAlignment.LEFT);
                        headerTable.addCell(logoCell);
                } else {
                        // Thêm cell rỗng nếu không có logo
                        headerTable.addCell(new Cell().setBorder(null));
                }

                // ... (Phần còn lại của code tạo PDF giữ nguyên)
                Paragraph nationalMotto = new Paragraph()
                                .setFont(fontBold).setFontSize(10).setTextAlignment(TextAlignment.CENTER)
                                .add("CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM\n")
                                .add(new Text("Độc lập - Tự do - Hạnh phúc").setUnderline(0.5f, -1.5f));
                Cell orgCell = new Cell().add(nationalMotto)
                                .add(new Paragraph("---o0o---").setTextAlignment(TextAlignment.CENTER)
                                                .setFont(fontRegular)
                                                .setFontSize(10))
                                .setBorder(null)
                                .setVerticalAlignment(VerticalAlignment.MIDDLE);
                headerTable.addCell(orgCell);
                document.add(headerTable);

                document.add(new Paragraph("\n"));
                Paragraph title = new Paragraph("GIẤY CHỨNG NHẬN HIẾN MÁU TÌNH NGUYỆN")
                                .setFont(fontBold).setFontSize(22)
                                .setFontColor(ColorConstants.RED)
                                .setTextAlignment(TextAlignment.CENTER)
                                .setMarginTop(20);
                document.add(title);

                document.add(new Paragraph("\n\n"));
                Paragraph body = new Paragraph()
                                .setFont(fontRegular).setFontSize(12).setFirstLineIndent(30)
                                .add("Ban Chỉ đạo vận động hiến máu tình nguyện trân trọng chứng nhận:\n\n")
                                .add(new Text("Ông/Bà: ").setFont(fontRegular))
                                .add(new Text(donation.getUser().getFullName()).setFont(fontBold))
                                .add(new Text("\n\nĐã tình nguyện hiến máu tại: ").setFont(fontRegular))
                                .add(new Text(donation.getCenter().getName()).setFont(fontBold))
                                .add(new Text("\n\nLoại máu đã hiến: ").setFont(fontRegular))
                                .add(new Text(donation.getBloodType().getType() + " ("
                                                + donation.getComponentType().getName() + ")")
                                                .setFont(fontBold))
                                .add(new Text("\n\nSố lượng: ").setFont(fontRegular))
                                .add(new Text(donation.getUnits() + " ml").setFont(fontBold))
                                .add(new Text("\n\nNgười bệnh luôn ghi ơn tấm lòng nhân ái của Ông/Bà.").setItalic());
                document.add(body);

                Table footerTable = new Table(UnitValue.createPercentArray(new float[] { 1, 1 }))
                                .useAllAvailableWidth();
                footerTable.setBorder(null);

                Cell codeCell = new Cell().add(new Paragraph("Mã tra cứu: " + code)
                                .setFont(fontRegular).setFontSize(9))
                                .setBorder(null)
                                .setTextAlignment(TextAlignment.LEFT)
                                .setVerticalAlignment(VerticalAlignment.BOTTOM);
                footerTable.addCell(codeCell);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'Ngày' dd 'tháng' MM 'năm' yyyy");
                String formattedDate = LocalDate.now().format(formatter);
                Cell signatureCell = new Cell().add(new Paragraph(formattedDate)
                                .setFont(fontRegular).setFontSize(12).setTextAlignment(TextAlignment.CENTER))
                                .add(new Paragraph("TRƯỞNG BAN CHỈ ĐẠO")
                                                .setFont(fontBold).setFontSize(12)
                                                .setTextAlignment(TextAlignment.CENTER))
                                .add(new Paragraph("\n\n\n")
                                                .setFont(fontRegular).setFontSize(12)
                                                .setTextAlignment(TextAlignment.CENTER))
                                .setBorder(null);
                footerTable.addCell(signatureCell);

                footerTable.setFixedPosition(
                                document.getLeftMargin(),
                                document.getBottomMargin() - 20,
                                document.getPdfDocument().getDefaultPageSize().getWidth() - document.getLeftMargin()
                                                - document.getRightMargin());
                document.add(footerTable);

                PdfCanvas canvas = new PdfCanvas(pdf.getFirstPage());
                canvas.setStrokeColor(ColorConstants.RED)
                                .setLineWidth(2)
                                .rectangle(40, 40, PageSize.A4.getWidth() - 80, PageSize.A4.getHeight() - 80)
                                .stroke();

                document.close();
        }

        // --- CÁC HÀM HỖ TRỢ AN TOÀN ---

        private byte[] loadImageFromClasspath(String path) throws IOException {
                try (InputStream stream = getClass().getResourceAsStream(path)) {
                        if (stream == null) {
                                // Ném ra lỗi rõ ràng nếu không tìm thấy file
                                throw new IOException("Không thể tìm thấy tài nguyên hình ảnh tại classpath: " + path);
                        }
                        return stream.readAllBytes();
                }
        }

        private PdfFont loadFontFromClasspath(String path) throws IOException {
                byte[] fontBytes = loadImageFromClasspath(path); // Dùng lại hàm trên để đọc file
                return PdfFontFactory.createFont(fontBytes, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
        }
}