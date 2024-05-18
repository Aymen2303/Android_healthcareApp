package com.example.mylogin.Classes;

public class Appointment {

    private String ID;
    private String clientID;
    private String nurseID;
    private String appointmentDate;
    private String kidID;
    private String clientUsername;
    private String kidName;

    public Appointment(String ID, String clientID, String nurseID, String appointmentDate, String kidID, String clientUsername, String kidName) {
        this.ID = ID;
        this.clientID = clientID;
        this.nurseID = nurseID;
        this.appointmentDate = appointmentDate;
        this.kidID = kidID;
        this.clientUsername = clientUsername;
        this.kidName = kidName;
    }

    public String getClientID() {
        return clientID;
    }

    public String getNurseID() {
        return nurseID;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getKidID() {
        return kidID;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public String getKidName() {
        return kidName;
    }

    public String get_app_ID() {
        return ID;
    }


}

