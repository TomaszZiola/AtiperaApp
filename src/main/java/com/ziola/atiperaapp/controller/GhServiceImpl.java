package com.ziola.atiperaapp.controller;

import com.ziola.atiperaapp.ghconnect.GhConnector;
import com.ziola.atiperaapp.ghconnect.GhRepository;
import com.ziola.atiperaapp.ghconnect.RepoBranch;
import com.ziola.atiperaapp.ghconnect.RepoBranchAndCommit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GhServiceImpl implements GhService {

    private final GhConnector ghConnector;

    public GhServiceImpl(GhConnector ghConnector) {
        this.ghConnector = ghConnector;
    }

    @Override
    public List<ApiRespons> proceedWithUserName(String userName) {
        ghConnector.findUser(userName);
        List<GhRepository> allRepositories = ghConnector.getGhRepositoriesByUsername(userName);
        return produceGhResponse(allRepositories);
    }

    private List<ApiRespons> produceGhResponse(List<GhRepository> allRepositories) {
        List<ApiRespons> allResponses = new ArrayList<>();
        for (GhRepository ghRepos : allRepositories) {
            ApiRespons apiRespons = new ApiRespons();
            apiRespons.setRepositoryName(ghRepos.getName());
            apiRespons.setOwnerLogin(ghRepos.getOwner().getLogin());
            apiRespons.setBranches(produceAllRepoBranchAndCommit(ghRepos));
            allResponses.add(apiRespons);
        }
        return allResponses;
    }

    private List<RepoBranchAndCommit> produceAllRepoBranchAndCommit(GhRepository ghRepo) {
        List<RepoBranchAndCommit> allRepoBranchAndCommit = new ArrayList<>();
        List<RepoBranch> repoBranchList = ghConnector.getRepoBranches(ghRepo);
        for (RepoBranch repoBranch : repoBranchList) {
            RepoBranchAndCommit repoBranchAndCommit = new RepoBranchAndCommit();
            repoBranchAndCommit.setBranchName(repoBranch.getName());
            repoBranchAndCommit.setLastCommitSha(repoBranch.getCommit().getSha());
            allRepoBranchAndCommit.add(repoBranchAndCommit);
        }
        return allRepoBranchAndCommit;
    }
}
