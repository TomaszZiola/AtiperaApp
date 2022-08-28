package com.ziola.atiperaapp.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GhRestController {

    private final GhService ghService;

    public GhRestController(GhService ghService) {
        this.ghService = ghService;
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ApiRespons> getResults(@RequestBody GhRequest ghRequest) {
        return ghService.proceedWithUserName(ghRequest.getUserName());
    }
}
