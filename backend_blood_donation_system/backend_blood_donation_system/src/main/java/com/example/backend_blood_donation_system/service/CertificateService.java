package com.example.backend_blood_donation_system.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificateService {

        private final DonationCertificateRepository certificateRepository;
        private final Path PDF_STORAGE_DIR = Paths.get(System.getProperty("user.dir"), "certificates-storage");

        // Đường dẫn đến tài nguyên trong project
        public static final String FONT_BOLD = "src/main/resources/fonts/NotoSans-Bold.ttf";
        public static final String FONT_REGULAR = "src/main/resources/fonts/NotoSans-Regular.ttf";
        public static final String LOGO_IMAGE = "src/main/resources/images/logo.png";

        public void generateCertificateForDonation(DonationHistory donation) throws IOException {
                String certificateCode = UUID.randomUUID().toString().substring(0, 13).toUpperCase().replace("-", "");

                if (!Files.exists(PDF_STORAGE_DIR)) {
                        Files.createDirectories(PDF_STORAGE_DIR);
                }
                String fileName = "certificate-" + donation.getDonationId() + ".pdf";
                Path pdfPath = PDF_STORAGE_DIR.resolve(fileName);

                createPdfFile(pdfPath.toString(), donation,
                                UUID.randomUUID().toString().substring(0, 13).toUpperCase().replace("-", ""));

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
                // Giảm bớt lề dưới để có không gian cho footer cố định
                document.setMargins(60, 60, 80, 60);

                // Tải font chữ tùy chỉnh
                PdfFont fontBold = PdfFontFactory.createFont(FONT_BOLD,
                                PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
                PdfFont fontRegular = PdfFontFactory.createFont(FONT_REGULAR,
                                PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);

                // --- Header ---
                Table headerTable = new Table(UnitValue.createPercentArray(new float[] { 1, 3 }))
                                .useAllAvailableWidth();
                headerTable.setBorder(null);

                // Logo
                try {
                        Image logo = new Image(ImageDataFactory.create(LOGO_IMAGE));
                        logo.setHeight(60);
                        Cell logoCell = new Cell().add(logo).setBorder(null).setTextAlignment(TextAlignment.LEFT);
                        headerTable.addCell(logoCell);
                } catch (MalformedURLException e) {
                        headerTable.addCell(new Cell().setBorder(null));
                        System.err.println("Không tìm thấy file logo tại: " + LOGO_IMAGE);
                }

                // Quốc hiệu và tiêu ngữ
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

                // --- Tiêu đề chính ---
                document.add(new Paragraph("\n"));
                Paragraph title = new Paragraph("GIẤY CHỨNG NHẬN HIẾN MÁU TÌNH NGUYỆN")
                                .setFont(fontBold).setFontSize(22)
                                .setFontColor(ColorConstants.RED)
                                .setTextAlignment(TextAlignment.CENTER)
                                .setMarginTop(20);
                document.add(title);

                // --- Nội dung chính ---
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

                // --- Chữ ký và ngày tháng (Footer) ---
                Table footerTable = new Table(UnitValue.createPercentArray(new float[] { 1, 1 }))
                                .useAllAvailableWidth();
                footerTable.setBorder(null);

                // Mã tra cứu
                Cell codeCell = new Cell().add(new Paragraph("Mã tra cứu: " + code)
                                .setFont(fontRegular).setFontSize(9))
                                .setBorder(null)
                                .setTextAlignment(TextAlignment.LEFT)
                                .setVerticalAlignment(VerticalAlignment.BOTTOM);
                footerTable.addCell(codeCell);

                // Ngày tháng và chữ ký
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'Ngày' dd 'tháng' MM 'năm' yyyy");
                String formattedDate = LocalDate.now().format(formatter);
                Cell signatureCell = new Cell().add(new Paragraph(formattedDate)
                                .setFont(fontRegular).setFontSize(12).setTextAlignment(TextAlignment.CENTER))
                                .add(new Paragraph("TRƯỞNG BAN CHỈ ĐẠO")
                                                .setFont(fontBold).setFontSize(12)
                                                .setTextAlignment(TextAlignment.CENTER))
                                .add(new Paragraph("\n\n\n") // Giảm bớt khoảng trống chữ ký
                                                .setFont(fontRegular).setFontSize(12)
                                                .setTextAlignment(TextAlignment.CENTER))
                                .setBorder(null);
                footerTable.addCell(signatureCell);

                // === SỬA LỖI TẠI ĐÂY: Đặt footer ở vị trí cố định ===
                footerTable.setFixedPosition(
                                document.getLeftMargin(), // Căn lề trái
                                document.getBottomMargin() - 20, // Vị trí từ dưới lên
                                document.getPdfDocument().getDefaultPageSize().getWidth() - document.getLeftMargin()
                                                - document.getRightMargin() // Chiều rộng
                );
                document.add(footerTable);

                // Vẽ viền cho trang
                PdfCanvas canvas = new PdfCanvas(pdf.getFirstPage());
                canvas.setStrokeColor(ColorConstants.RED)
                                .setLineWidth(2)
                                .rectangle(40, 40, PageSize.A4.getWidth() - 80, PageSize.A4.getHeight() - 80)
                                .stroke();

                document.close();
        }
}