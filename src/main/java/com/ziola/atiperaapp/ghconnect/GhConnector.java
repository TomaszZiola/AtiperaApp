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
import java.util.Objects;

@Component
public class GhConnector {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<GhRepository> getGhRepositoriesByUsername(String userName) {

        findUser(userName);

        final RequestEntity<Void> requestRepos = RequestEntity.get(URI.create("https://api.github.com/users/" + userName + "/repos"))
                .accept(MediaType.APPLICATION_JSON)
                .build();

        final ResponseEntity<List<GhRepository>> response = restTemplate.exchange(requestRepos, new ParameterizedTypeReference<>() {
        });

        return Objects.requireNonNull(response.getBody()).stream()
                .filter(repo -> !repo.isFork())
                .toList();
    }

    public List<RepoBranch> getRepoBranches(GhRepository ghRepository) {
        String branchUrl = ghRepository.getBranches_url();
        String urlRemovedSerachingByBranchName = branchUrl.substring(0, branchUrl.indexOf("{"));
        final RequestEntity<Void> requestBranches = RequestEntity.get(URI.create(urlRemovedSerachingByBranchName))
                .accept(MediaType.APPLICATION_JSON)
                .build();

        final ResponseEntity<List<RepoBranch>> response = restTemplate.exchange(requestBranches, new ParameterizedTypeReference<>() {
        });
        return Objects.requireNonNull(response.getBody()).stream().toList();
    }

    public void findUser(String userName) {
        try {
            restTemplate.getForObject("https://api.github.com/users/{USERNAME}", GhUser.class, userName);
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("There's no such username in Github's database!");
        }
    }
}
