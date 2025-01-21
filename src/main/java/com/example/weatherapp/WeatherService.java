package com.example.weatherapp;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WeatherService {

    private final String API_KEY = "9b6ce3f4eb12c38191501e77bc6858de";
    private final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";

    public WeatherResponse getWeather(String city) throws Exception {
        // Tworzymy URL z zapytaniem
        String url = UriComponentsBuilder.fromUriString(BASE_URL)
                .queryParam("q", city)
                .queryParam("appid", API_KEY)
                .queryParam("units", "metric") // Temperatura w stopniach Celsjusza
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        // Pobieramy dane z API w formie Stringa
        String response = restTemplate.getForObject(url, String.class);

        // Parsowanie odpowiedzi JSON
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);

        // Przetwarzamy dane do modelu WeatherResponse
        String temperature = rootNode.path("main").path("temp").asText();
        String description = rootNode.path("weather").get(0).path("description").asText();
        String humidity = rootNode.path("main").path("humidity").asText();

        return new WeatherResponse(city, temperature, description, humidity);
    }
}
