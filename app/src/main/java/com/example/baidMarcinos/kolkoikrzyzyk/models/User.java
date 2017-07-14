package com.example.baidMarcinos.kolkoikrzyzyk.models;


public class User {

    private String uid;
    private String name;
    private String phoneNumber;
    private String email;
    private String password;

    private String appDisplayName;

    private String firebaseToken;

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    private String profilePictureUrl;

    public User() {    }

    public User(String uid, String name, String phoneNumber, String email, String password, String firebaseToken, String profilePictureUrl) {
        this.uid = uid;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.firebaseToken = firebaseToken;
        this.profilePictureUrl = profilePictureUrl;

        this.findAvaiableDisplayName();
    }


    private void findAvaiableDisplayName(){
        if(this.getName()!=null) this.setAppDisplayName(this.getName());
        else if(this.getEmail()!=null) this.setAppDisplayName(this.getEmail());
        else this.setAppDisplayName(this.getuid());
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public String getuid() {
        return uid;
    }

    public void setuid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAppDisplayName() {
        return appDisplayName;
    }

    public void setAppDisplayName(String appDisplayName) {
        this.appDisplayName = appDisplayName;
    }

}