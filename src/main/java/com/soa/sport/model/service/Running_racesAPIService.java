package com.soa.sport.model.service;

import com.soa.sport.model.dto.Running_raceDTO;
import com.soa.sport.model.entity.Running_race;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class Running_racesAPIService {
    @Autowired
    private final WebClient running_racesAPI;

    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(10);

    @Autowired
    public Running_racesAPIService(@Qualifier("running_racesAPI") WebClient running_racesAPI){ this.running_racesAPI = running_racesAPI; }

    public Running_race[] requestAllRunning_races(){
        return running_racesAPI
                .get()
                .uri("/running_races/")
                .retrieve()
                .bodyToMono(Running_race[].class)
                .block(REQUEST_TIMEOUT);
    }

    public Running_race readrunning_race(int id){ return requestRunning_race(id); }
    private Running_race requestRunning_race(int id){
        return running_racesAPI
                .get()
                .uri("/running_races/" + id + "/")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Running_race.class)
                .block(REQUEST_TIMEOUT);
    }

    public Running_raceDTO create(Running_raceDTO running_raceDTO) { return requestCreateRunning_race(running_raceDTO);}
    private Running_raceDTO requestCreateRunning_race(Running_raceDTO running_raceDTO){
        return running_racesAPI
                .post()
                .uri("/running_races/")
                .body(Mono.just(running_raceDTO), Running_raceDTO.class)
                .retrieve()
                .bodyToMono(Running_raceDTO.class)
                .block(REQUEST_TIMEOUT);
    }

    public Running_raceDTO update(int id, Running_raceDTO running_raceDTO) { return requestCreateRunning_race(id, running_raceDTO); }
    private Running_raceDTO requestCreateRunning_race(int id, Running_raceDTO running_raceDTO){
        return running_racesAPI
                .put()
                .uri("/running_races/" + id + "/")
                .body(Mono.just(running_raceDTO), Running_raceDTO.class)
                .retrieve()
                .bodyToMono(Running_raceDTO.class)
                .block(REQUEST_TIMEOUT);
    }

    public void delete(int id) { this.requestDeleteRunning_race(id); }
    private void requestDeleteRunning_race(int id){
        running_racesAPI
                .delete()
                .uri("/running_races/" + id + "/")
                .retrieve()
                .bodyToMono(Void.class)
                .block(REQUEST_TIMEOUT);
    }
}
