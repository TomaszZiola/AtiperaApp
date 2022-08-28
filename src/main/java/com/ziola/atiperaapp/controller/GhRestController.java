package com.ziola.atiperaapp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GhRestController {

    private final GhService ghService;

    public GhRestController(GhService ghService) {
        this.ghService = ghService;
    }

    @PostMapping("/")
    public GhResponse getResults(@RequestBody GhRequest ghRequest){

        return ghService.proceedWithUserName(ghRequest.getUserName());
    }
}
