package it.academy.events_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.Collections;
import java.util.List;

@Configuration
public class ControllerConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        mapper.registerModule(new JavaTimeModule());

        return mapper;
    }

    @Bean
    public MappingJackson2HttpMessageConverter jsonHttpMessageConverter(ObjectMapper mapper) {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        messageConverter.setObjectMapper(mapper);

        return messageConverter;
    }

    @Bean
    public RequestMappingHandlerAdapter requestHandlerAdapter(List<MappingJackson2HttpMessageConverter> converterList) {
        RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();

        adapter.setMessageConverters(Collections.unmodifiableList(converterList));

        return adapter;
    }
}
