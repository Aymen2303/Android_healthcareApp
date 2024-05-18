package com.example.mylogin.Classes;

public class VerificationRequest {
    private String clientId;
    private String nurseId;
    private String appointmentDate;
    private String kidId;

    public VerificationRequest(String clientId, String nurseId, String appointmentDate, String kidId) {
        this.clientId = clientId;
        this.nurseId = nurseId;
        this.appointmentDate = appointmentDate;
        this.kidId = kidId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getNurseId() {
        return nurseId;
    }

    public void setNurseId(String nurseId) {
        this.nurseId = nurseId;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getKidId() {
        return kidId;
    }

    public void setKidId(String kidId) {
        this.kidId = kidId;
    }

    // Example of a toString() method for debugging purposes
    @Override
    public String toString() {
        return "VerificationRequest{" +
                "clientId='" + clientId + '\'' +
                ", nurseId='" + nurseId + '\'' +
                ", appointmentDate='" + appointmentDate + '\'' +
                ", kidId='" + kidId + '\'' +
                '}';
    }
}
