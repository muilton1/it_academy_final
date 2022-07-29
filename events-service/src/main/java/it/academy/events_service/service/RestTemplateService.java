package it.academy.events_service.service;

import it.academy.events_service.service.api.IRestTemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class RestTemplateService implements IRestTemplateService {

    public boolean checkCategoryUUID(UUID uuid, RestTemplate restTemplate) {
        ResponseEntity<UUID> entity = restTemplate.getForEntity("http://localhost:8081/api/v1/classifier/concert/category/" + uuid, UUID.class);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return true;
        } else throw new RuntimeException("Проверьте UUID!");
    }

    public boolean checkCountryUUID(UUID uuid, RestTemplate restTemplate) {
        ResponseEntity<UUID> entity = restTemplate.getForEntity("http://localhost:8081/api/v1/classifier/country" + uuid, UUID.class);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return true;
        } else throw new RuntimeException("Проверьте UUID!");
    }
}
