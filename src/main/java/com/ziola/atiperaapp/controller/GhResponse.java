package com.ziola.atiperaapp.controller;

import com.ziola.atiperaapp.ghconnect.RepoBranchAndCommit;

import java.util.List;

public class GhResponse {

    private String repositoryName;
    private String ownerLogin;
    private List<RepoBranchAndCommit> branches;
}
