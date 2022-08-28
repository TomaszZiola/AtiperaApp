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
    public List<GhResponse> proceedWithUserName(String userName) {
        ghConnector.checkUserExists(userName);
        List<GhRepository> ghRepositories = ghConnector.getGhRepositoriesByUsername(userName);
        return takeRepoProduceListResponse(ghRepositories);
    }

    private List<GhResponse> takeRepoProduceListResponse(List<GhRepository> ghRepositories) {
        List<GhResponse> responses = new ArrayList<>();
        for (GhRepository repo : ghRepositories) {
            GhResponse ghResponse = new GhResponse();
            ghResponse.setRepositoryName(repo.getName());
            ghResponse.setOwnerLogin(repo.getOwner().getLogin());
            ghResponse.setBranches(createListOfRepoBranchCommit(repo));
            responses.add(ghResponse);
        }
        return responses;
    }

    private List<RepoBranchAndCommit> createListOfRepoBranchCommit(GhRepository repo) {
        List<RepoBranchAndCommit> repoBranchAndCommitsList = new ArrayList<>();
        List<RepoBranch> repoBranchList = ghConnector.getRepoBranches(repo);
        for (RepoBranch repoBranch :
                repoBranchList) {
            RepoBranchAndCommit repoBranchAndCommit = new RepoBranchAndCommit();
            repoBranchAndCommit.setBranchName(repoBranch.getName());
            repoBranchAndCommit.setLastCommitSha(repoBranch.getCommit().getSha());
            repoBranchAndCommitsList.add(repoBranchAndCommit);
        }
        return repoBranchAndCommitsList;
    }
}
