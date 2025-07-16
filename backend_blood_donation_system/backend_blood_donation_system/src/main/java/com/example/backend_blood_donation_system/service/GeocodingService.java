package com.example.backend_blood_donation_system.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GeocodingService {

    private final RestTemplate restTemplate = new RestTemplate();

    public Coordinates getCoordinatesFromAddress(String address) {
        try {
            String url = "https://nominatim.openstreetmap.org/search?q=" + 
                         address.replace(" ", "+") + 
                         "&format=json&limit=1";

            NominatimResponse[] response = restTemplate.getForObject(url, NominatimResponse[].class);
            if (response != null && response.length > 0) {
                double lat = Double.parseDouble(response[0].getLat());
                double lon = Double.parseDouble(response[0].getLon());
                return new Coordinates(lat, lon);
            }
        } catch (Exception e) {
            log.error("Error during geocoding: {}", e.getMessage());
        }
        return null;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class NominatimResponse {
        private String lat;
        private String lon;
    }

    @Data
    @AllArgsConstructor
    public static class Coordinates {
        private double latitude;
        private double longitude;
    }
}
