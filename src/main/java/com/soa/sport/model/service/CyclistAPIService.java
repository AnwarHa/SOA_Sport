package com.soa.sport.model.service;

import com.soa.sport.model.entity.Cyclist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
public class CyclistAPIService {

    @Autowired
    private final WebClient cyclistAPI;

    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(10);

    @Autowired
    public CyclistAPIService(@Qualifier("cyclistAPI") WebClient cyclistAPI){ this.cyclistAPI = cyclistAPI; }

    public Cyclist[] requestAllProCyclists(){
        return cyclistAPI
                .get()
                .uri("/pro_cyclists/")
                .retrieve()
                .bodyToMono(Cyclist[].class)
                .block(REQUEST_TIMEOUT);
    }



}
