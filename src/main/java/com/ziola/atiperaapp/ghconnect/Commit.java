package com.ziola.atiperaapp.ghconnect;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Commit {

    private String sha;
}
