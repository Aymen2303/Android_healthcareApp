package com.example.mylogin.Classes;

public class Appointment_Class {
    private String appointmentID;
    private String clientID;
    private String nurseID;
    private String appointmentDate;
    private String kidID;
    private String approved;
    private String kidName;
    private String nurseName;

    public Appointment_Class(String appointmentID, String clientID, String nurseID, String appointmentDate, String kidID, String approved, String kidName, String nurseName) {
        this.appointmentID = appointmentID;
        this.clientID = clientID;
        this.nurseID = nurseID;
        this.appointmentDate = appointmentDate;
        this.kidID = kidID;
        this.approved = approved;
        this.kidName = kidName;
        this.nurseName = nurseName;
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getNurseID() {
        return nurseID;
    }

    public void setNurseID(String nurseID) {
        this.nurseID = nurseID;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getKidID() {
        return kidID;
    }

    public void setKidID(String kidID) {
        this.kidID = kidID;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getKidName() {
        return kidName;
    }

    public void setKidName(String kidName) {
        this.kidName = kidName;
    }

    public String getNurseName() {
        return nurseName;
    }

    public void setNurseName(String nurseName) {
        this.nurseName = nurseName;
    }
}
