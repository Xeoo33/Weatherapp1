package com.example.weatherapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/")
    public String getWeather(Model model, String city) {
        try {
            
            WeatherResponse weatherResponse = weatherService.getWeather(city);

            // Dodajemy dane do modelu, które będą wyświetlane w widoku
            model.addAttribute("city", weatherResponse.getCity());
            model.addAttribute("temperature", weatherResponse.getTemperature());
            model.addAttribute("description", weatherResponse.getDescription());
            model.addAttribute("humidity", weatherResponse.getHumidity());

        } catch (Exception e) {
            model.addAttribute("error", "Failed to retrieve weather data");
        }

        return "index";  
    }
}
