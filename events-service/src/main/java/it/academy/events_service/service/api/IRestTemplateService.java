package it.academy.events_service.service.api;

import org.springframework.web.client.RestTemplate;

import java.util.UUID;

public interface IRestTemplateService {
    boolean checkCategoryUUID(UUID uuid, RestTemplate restTemplate);

    boolean checkCountryUUID(UUID uuid, RestTemplate restTemplate);
}
