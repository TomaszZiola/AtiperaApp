package com.ziola.atiperaapp.ghconnect;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepoBranchCommit {

    private String branchName;
    private String lastCommitSha;
}
