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
import static org.hamcrest.Matchers.is;
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
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.status", is(406)))
                .andExpect(jsonPath("$.message", is("Unsupported 'Accept' header. Supported Media Types: application/json")));


    }

    private List<ApiRespons> createResponses() {

        List<ApiRespons> apiRespons = new ArrayList<>();

        List<RepoBranchAndCommit> repoBranchAndCommitsList = new ArrayList<>();
        RepoBranchAndCommit repoBranchAndCommit1 = new RepoBranchAndCommit();
        RepoBranchAndCommit repoBranchAndCommit2 = new RepoBranchAndCommit();
        repoBranchAndCommitsList.add(repoBranchAndCommit1);
        repoBranchAndCommitsList.add(repoBranchAndCommit2);

        List<RepoBranchAndCommit> repoBranchAndCommitsList2 = new ArrayList<>();
        RepoBranchAndCommit repoBranchAndCommit3 = new RepoBranchAndCommit();
        RepoBranchAndCommit repoBranchAndCommit4 = new RepoBranchAndCommit();
        repoBranchAndCommitsList2.add(repoBranchAndCommit3);
        repoBranchAndCommitsList2.add(repoBranchAndCommit4);

        ApiRespons apiRespons1 = new ApiRespons();
        apiRespons1.setBranches(repoBranchAndCommitsList);

        ApiRespons apiRespons2 = new ApiRespons();
        apiRespons2.setBranches(repoBranchAndCommitsList2);

        apiRespons.add(apiRespons1);
        apiRespons.add(apiRespons2);

        return apiRespons;
    }
}
