package com.ziola.atiperaapp.controller;

import com.ziola.atiperaapp.ghconnect.RepoBranchAndCommit;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@AutoConfigureMockMvc
@SpringBootTest
public class GhRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GhService ghService;

    @Test
    public void should_return_apiRespons_by_username() throws Exception {

        Mockito.when(ghService.proceedWithUserName("tomaszziola")).thenReturn(createResponses());

        mockMvc.perform(MockMvcRequestBuilders.get("/tomaszziola")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void should_return_exception_when_type_xml() throws Exception {

        Mockito.when(ghService.proceedWithUserName("tomaszziola")).thenReturn(createResponses());

        mockMvc.perform(MockMvcRequestBuilders.get("/tomaszziola")
                        .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isNotAcceptable());


    }

    private List<ApiRespons> createResponses() {

        List<ApiRespons> apiRespons = new ArrayList<>();

        List<RepoBranchAndCommit> repoBranchAndCommitsList = new ArrayList<>();
        RepoBranchAndCommit repoBranchAndCommit1 = new RepoBranchAndCommit();
        repoBranchAndCommit1.setBranchName("branch1");
        repoBranchAndCommit1.setLastCommitSha("sha1");

        RepoBranchAndCommit repoBranchAndCommit2 = new RepoBranchAndCommit();
        repoBranchAndCommit2.setBranchName("branch1");
        repoBranchAndCommit2.setLastCommitSha("sha1");

        repoBranchAndCommitsList.add(repoBranchAndCommit1);
        repoBranchAndCommitsList.add(repoBranchAndCommit2);

        List<RepoBranchAndCommit> repoBranchAndCommitsList2 = new ArrayList<>();
        RepoBranchAndCommit repoBranchAndCommit3 = new RepoBranchAndCommit();
        repoBranchAndCommit3.setBranchName("branch1");
        repoBranchAndCommit3.setLastCommitSha("sha1");

        RepoBranchAndCommit repoBranchAndCommit4 = new RepoBranchAndCommit();
        repoBranchAndCommit4.setBranchName("branch1");
        repoBranchAndCommit4.setLastCommitSha("sha1");

        repoBranchAndCommitsList2.add(repoBranchAndCommit3);
        repoBranchAndCommitsList2.add(repoBranchAndCommit4);

        ApiRespons apiRespons1 = new ApiRespons();
        apiRespons1.setRepositoryName("Repo1");
        apiRespons1.setOwnerLogin("login");
        apiRespons1.setBranches(repoBranchAndCommitsList);

        ApiRespons apiRespons2 = new ApiRespons();
        apiRespons2.setRepositoryName("Repo1");
        apiRespons2.setOwnerLogin("login");
        apiRespons2.setBranches(repoBranchAndCommitsList2);

        apiRespons.add(apiRespons1);
        apiRespons.add(apiRespons2);

        return apiRespons;
    }
}
