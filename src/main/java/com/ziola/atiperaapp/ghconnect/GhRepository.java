package com.ziola.atiperaapp.ghconnect;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GhRepository {

    private String name;
    private boolean fork;
    private String branches_url;
    private RepoOwner owner;
}
