package com.example.karim.developers;

import java.util.ArrayList;

/**
 * Created by karim on 25/09/2017.
 */

public class User {
    private ArrayList<aplication> applications;

    public ArrayList<aplication> getApplications() {
        return applications;
    }

    public void setAplications(ArrayList<aplication> applications) {
        this.applications = applications;
    }

    public User() {
    }

    public User(ArrayList<aplication> applications) {
        this.applications = applications;
    }
}
