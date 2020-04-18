package com.example.getaservice;

public class Workermodel {

    String name;
    String passwordstr;
    String email;
    String phonestr;
    String experiencestr;
    String certificationstr;
    String chargestr;
    String addressstr;
    String category;
    String status;
    String usertype;

    public Workermodel() {
    }

    public Workermodel(String name, String passwordstr, String email, String phonestr, String experiencestr, String certificationstr, String chargestr, String addressstr, String category, String status, String usertype) {
        this.name = name;
        this.passwordstr = passwordstr;
        this.email = email;
        this.phonestr = phonestr;

        this.experiencestr = experiencestr;
        this.certificationstr = certificationstr;
        this.chargestr = chargestr;
        this.addressstr = addressstr;

        this.category = category;
        this.status = status;
        this.usertype = usertype;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordstr() {
        return passwordstr;
    }

    public void setPasswordstr(String passwordstr) {
        this.passwordstr = passwordstr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonestr() {
        return phonestr;
    }

    public void setPhonestr(String phonestr) {
        this.phonestr = phonestr;
    }

    public String getExperiencestr() {
        return experiencestr;
    }

    public void setExperiencestr(String experiencestr) {
        this.experiencestr = experiencestr;
    }

    public String getCertificationstr() {
        return certificationstr;
    }

    public void setCertificationstr(String certificationstr) {
        this.certificationstr = certificationstr;
    }

    public String getChargestr() {
        return chargestr;
    }

    public void setChargestr(String chargestr) {
        this.chargestr = chargestr;
    }

    public String getAddressstr() {
        return addressstr;
    }

    public void setAddressstr(String addressstr) {
        this.addressstr = addressstr;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
}
