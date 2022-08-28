package com.ziola.atiperaapp.controller;

import com.ziola.atiperaapp.ghconnect.GhConnector;
import org.springframework.stereotype.Service;

@Service
public class GhServiceImpl implements GhService{

    private final GhConnector ghConnector;

    public GhServiceImpl(GhConnector ghConnector) {
        this.ghConnector = ghConnector;
    }

    @Override
    public GhResponse proceedWithUserName(String userName) {
        ghConnector.checkUserExists(userName);
        return null;
    }
}
