package com.ziola.atiperaapp.ghconnect;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RepoBranchCommit {

    private String branchName;
    private String lastCommitSha;
}
