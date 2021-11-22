package com.soa.sport.controller.api;

import com.soa.sport.model.entity.Cyclist;
import com.soa.sport.model.service.CyclistAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(path = "/sport/api/cyclists")
public class CyclistAPIController {

    private final CyclistAPIService cyclistAPIService;

    @Autowired
    public CyclistAPIController(WebClient cyclistAPI) {
        this.cyclistAPIService = new CyclistAPIService(cyclistAPI);
    }

    @GetMapping
    public String getOverviewProCyclists(Model model) {
        List<Cyclist> cyclists = Arrays.asList(this.cyclistAPIService.requestAllProCyclists());
        model.addAttribute("cyclists", cyclists);
        System.out.println(cyclists);
        return "api-cyclists";
    }
}
