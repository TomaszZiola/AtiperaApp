package com.ziola.atiperaapp.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GhRestController {

    private final GhService ghService;

    public GhRestController(GhService ghService) {
        this.ghService = ghService;
    }

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ApiRespons> getResults(@PathVariable String username) {
        return ghService.proceedWithUserName(username);
    }
}
