package com.ziola.atiperaapp.controller;

import com.ziola.atiperaapp.ghconnect.RepoBranchCommit;

import java.util.List;

public record ApiRespons(String repositoryName, String ownerLogin, List<RepoBranchCommit> branches) {
}
