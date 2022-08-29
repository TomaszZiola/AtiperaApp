package com.ziola.atiperaapp.controller;

import com.ziola.atiperaapp.ghconnect.GhConnector;
import com.ziola.atiperaapp.ghconnect.GhRepository;
import com.ziola.atiperaapp.ghconnect.RepoBranch;
import com.ziola.atiperaapp.ghconnect.RepoBranchCommit;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GhServiceImpl implements GhService {

    private final GhConnector ghConnector;

    public GhServiceImpl(GhConnector ghConnector) {
        this.ghConnector = ghConnector;
    }

    @Override
    public List<ApiRespons> proceedWithUserName(String userName) {
        return ghConnector.getGhRepositoriesByUsername(userName).stream()
                .map(this::createApiRespons)
                .collect(Collectors.toList());
    }

    private ApiRespons createApiRespons(GhRepository ghRepos) {
        ApiRespons apiRespons = new ApiRespons();
        apiRespons.setRepositoryName(ghRepos.getName());
        apiRespons.setOwnerLogin(ghRepos.getOwner().getLogin());
        apiRespons.setBranches(produceAllRepoBranchAndCommit(ghRepos));
        return apiRespons;
    }

    private List<RepoBranchCommit> produceAllRepoBranchAndCommit(GhRepository ghRepo) {
        return ghConnector.getRepoBranches(ghRepo).stream()
                .map(this::createRepoBranchCommit)
                .collect(Collectors.toList());
    }

    private RepoBranchCommit createRepoBranchCommit(RepoBranch repoBranch) {
        RepoBranchCommit repoBranchCommit = new RepoBranchCommit();
        repoBranchCommit.setBranchName(repoBranch.getName());
        repoBranchCommit.setLastCommitSha(repoBranch.getCommit().getSha());
        return repoBranchCommit;
    }
}
