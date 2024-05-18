package com.example.mylogin.Classes;

public class nurse_class {
    private String nurseId;
    private String nurseName;

    public nurse_class(String nurseId, String nurseName) {
        this.nurseId = nurseId;
        this.nurseName = nurseName;
    }

    public nurse_class() {

    }

    public String getNurseId() {
        return nurseId;
    }

    public void setNurseId(String nurseId) {
        this.nurseId = nurseId;
    }

    public String getNurseName() {
        return nurseName;
    }

    public void setNurseName(String nurseName) {
        this.nurseName = nurseName;
    }

    // Override the toString() method to return the nurse name
    @Override
    public String toString() {
        return nurseId + " - " + nurseName; // Return the ID and username for display in the spinner
    }


}
