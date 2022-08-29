package com.ziola.atiperaapp.controller;

import com.ziola.atiperaapp.ghconnect.RepoBranchCommit;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApiRespons {

    private String repositoryName;
    private String ownerLogin;
    private List<RepoBranchCommit> branches;
}
