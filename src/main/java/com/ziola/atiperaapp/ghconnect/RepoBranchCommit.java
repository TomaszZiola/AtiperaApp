package com.ziola.atiperaapp.ghconnect;

import lombok.Builder;

@Builder
public record RepoBranchCommit(String branchName, String lastCommitSha) {
}
