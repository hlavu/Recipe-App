package com.example.myapplication;

import android.app.Application;

import com.example.myapplication.Database.DatabaseManager;

public class myApp extends Application {

    private final NetworkingService networkingService = new NetworkingService();

    public JsonService getJsonService() {
        return jsonService;
    }

    private final JsonService jsonService = new JsonService();

    public NetworkingService getNetworkingService() {
        return networkingService;
    }

    private final DatabaseManager databaseManager = new DatabaseManager();

    public DatabaseManager getDatabaseManager(){
        return databaseManager;
    }
}