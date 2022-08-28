package com.ziola.atiperaapp.ghconnect;

import com.ziola.atiperaapp.errors.UserNotFoundException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Component
public class GhConnector {

    private final RestTemplate restTemplate = new RestTemplate();

    public void getGhRepositoriesByUsername(String userName) {

        final RequestEntity<Void> requestRepos = RequestEntity.get(URI.create("https://api.github.com/users/" + userName + "/repos"))
                    .accept(MediaType.APPLICATION_JSON)
                    .build();

        final ResponseEntity<List<GhRepository>> response = restTemplate.exchange(requestRepos, new ParameterizedTypeReference<>() {
        });
    }

    public void checkUserExists(String userName) {
        try {
            restTemplate.getForObject("https://api.github.com/users/{USERNAME}", GhUser.class, userName);
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("There's no such username in Github's database!");
        }
    }
}
