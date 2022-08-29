package com.ziola.atiperaapp.controller;

import com.ziola.atiperaapp.errors.UserNotFoundException;
import com.ziola.atiperaapp.ghconnect.RepoBranchCommit;
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

    @Test
    public void should_return_exception_when_no_user_found() throws Exception {

        Mockito.when(ghService.proceedWithUserName("xcz")).thenThrow(new UserNotFoundException("There's no such username in Github's database!"));

        mockMvc.perform(MockMvcRequestBuilders.get("/xcz")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("There's no such username in Github's database!")));
    }

    private List<ApiRespons> createResponses() {

        List<ApiRespons> apiRespons = new ArrayList<>();

        List<RepoBranchCommit> repoBranchCommitsList = new ArrayList<>();
        RepoBranchCommit repoBranchCommit1 = new RepoBranchCommit();
        RepoBranchCommit repoBranchCommit2 = new RepoBranchCommit();
        repoBranchCommitsList.add(repoBranchCommit1);
        repoBranchCommitsList.add(repoBranchCommit2);

        List<RepoBranchCommit> repoBranchCommitsList2 = new ArrayList<>();
        RepoBranchCommit repoBranchCommit3 = new RepoBranchCommit();
        RepoBranchCommit repoBranchCommit4 = new RepoBranchCommit();
        repoBranchCommitsList2.add(repoBranchCommit3);
        repoBranchCommitsList2.add(repoBranchCommit4);

        ApiRespons apiRespons1 = new ApiRespons();
        apiRespons1.setBranches(repoBranchCommitsList);

        ApiRespons apiRespons2 = new ApiRespons();
        apiRespons2.setBranches(repoBranchCommitsList2);

        apiRespons.add(apiRespons1);
        apiRespons.add(apiRespons2);

        return apiRespons;
    }
}
