package com.ziola.atiperaapp.controller;

import com.ziola.atiperaapp.ghconnect.GhConnector;
import com.ziola.atiperaapp.ghconnect.GhRepository;
import com.ziola.atiperaapp.ghconnect.RepoBranch;
import com.ziola.atiperaapp.ghconnect.RepoBranchCommit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GhServiceImpl implements GhService {

    private final GhConnector ghConnector;

    public GhServiceImpl(GhConnector ghConnector) {
        this.ghConnector = ghConnector;
    }

    @Override
    public List<ApiRespons> proceedWithUserName(String userName) {
        return ghConnector.getGhRepositoriesByUsername(userName).parallelStream()
                .map(this::createApiResponse)
                .toList();
    }

    private ApiRespons createApiResponse(GhRepository ghRepos) {
        return new ApiRespons(ghRepos.getName(), ghRepos.getOwner().getLogin(), produceAllRepoBranchAndCommit(ghRepos));
    }

    private List<RepoBranchCommit> produceAllRepoBranchAndCommit(GhRepository ghRepo) {
        return ghConnector.getRepoBranches(ghRepo).parallelStream()
                .map(this::createRepoBranchCommit)
                .toList();
    }

    private RepoBranchCommit createRepoBranchCommit(RepoBranch repoBranch) {
        return RepoBranchCommit.builder()
                .lastCommitSha(repoBranch.getCommit().getSha())
                .branchName(repoBranch.getName())
                .build();
    }
}
