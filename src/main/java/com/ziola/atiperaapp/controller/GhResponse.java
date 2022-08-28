package com.ziola.atiperaapp.controller;

import com.ziola.atiperaapp.ghconnect.RepoBranchAndCommit;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GhResponse {

    private String repositoryName;
    private String ownerLogin;
    private List<RepoBranchAndCommit> branches;
}
