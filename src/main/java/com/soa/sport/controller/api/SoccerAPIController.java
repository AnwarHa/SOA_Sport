package com.soa.sport.controller.api;

import com.soa.sport.model.dto.SoccerPlayerDTO;
import com.soa.sport.model.entity.SoccerPlayer;
import com.soa.sport.model.service.SoccerAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(path = "/sport/api/soccer")
public class SoccerAPIController {

    private final SoccerAPIService soccerAPIService;

    @Autowired
    public SoccerAPIController(WebClient soccerAPI){
        this.soccerAPIService = new SoccerAPIService(soccerAPI);
    }

    @GetMapping
    public String getOverviewSoccerPlayers(Model model){
        List<SoccerPlayer> players = Arrays.asList(this.soccerAPIService.requestAllSoccerPlayers());
        model.addAttribute("players",players);
        System.out.println(players);
        return "api-players";
    }


    @GetMapping( value = "/{id}")
    public String showPlayer(@PathVariable int id, Model model){
        model.addAttribute("player",this.soccerAPIService.readPlayer(id));
        System.out.println(this.soccerAPIService.readPlayer(id));
        return "api-player-details";
    }

    @GetMapping(value = "/new")
    public String getNewSoccerPlayer(Model model){
        model.addAttribute("player",new SoccerPlayerDTO());
        return "api-player-new";
    }

    @PostMapping(value = "/new")
    public String postNewSoccerPlayer(
            @RequestParam(name = "first_name") String first_name,
            @RequestParam(name = "last_name") String last_name,
            @RequestParam(name = "team") String team,
            @RequestParam(name = "position") String position,
            @RequestParam(name = "dob") String dob,
            @RequestParam(name = "goals") int goals,
            @RequestParam(name = "assists") int assists
    ){

        SoccerPlayerDTO soccerPlayerDTO = new SoccerPlayerDTO(first_name, last_name, team, position, dob, goals, assists);
        SoccerPlayerDTO receivedSoccerPlayer = this.soccerAPIService.create(soccerPlayerDTO);
        System.out.println("CREATED: " + receivedSoccerPlayer);
        return "redirect:/sport/api/soccer";
    }

    @GetMapping("/{id}/delete")
    public String deleteSoccerPlayer(@PathVariable int id){
        this.soccerAPIService.delete(id);
        return "redirect:/sport/api/soccer";
    }

    @GetMapping("/{id}/update")
    public String getUpdateMovie(@PathVariable int id, Model model){
        SoccerPlayer player = this.soccerAPIService.readPlayer(id);
        model.addAttribute("id",id);
        model.addAttribute("player", player);
        return "api-player-update";
    }

    @PostMapping("/{id}/update")
    public String postUpdateSoccerPlayer(
            @PathVariable int id,
            @RequestParam(name = "first_name") String first_name,
            @RequestParam(name = "last_name") String last_name,
            @RequestParam(name = "team") String team,
            @RequestParam(name = "position") String position,
            @RequestParam(name = "dob") String dob,
            @RequestParam(name = "goals") int goals,
            @RequestParam(name = "assists") int assists
    ){
        SoccerPlayerDTO soccerPlayerDTO = new SoccerPlayerDTO(first_name, last_name, team, position, dob, goals, assists);
        SoccerPlayerDTO receivedSoccerPlayer = this.soccerAPIService.update(id, soccerPlayerDTO);
        System.out.println("UPDATED: " + receivedSoccerPlayer);
        return "redirect:/sport/api/soccer/" + id;
    }


}
