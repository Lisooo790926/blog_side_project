package com.test;

import com.data.DQESuggestionAddressResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TestRestTemplate {

    public static void main(String[] args) throws JsonProcessingException {
        String searchTerm = "BERTONCOUR";
        String license = "SAMS7AY953";
        String url = "https://prod2.dqe-software.com/SINGLEV2?Pays=FRA&Licence=" + license + "&Adresse=" + searchTerm;

        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity httpEntity = new HttpEntity(headers);
        RestTemplate restTemplate = new RestTemplate();

        final HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        final HttpClient httpClient = HttpClientBuilder.create()
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build();
        factory.setHttpClient(httpClient);
        restTemplate.setRequestFactory(factory);

        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        System.out.println(exchange.getBody());

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, DQESuggestionAddressResponse> map = objectMapper.readValue(exchange.getBody(), new TypeReference<HashMap<String, DQESuggestionAddressResponse>>() {
        });

        System.out.println(map);

    }
}
