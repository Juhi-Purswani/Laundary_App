package com.example.android.internship;

public class order {
    private String mUsername;
    private String mDate;
    private String mSlot;
    private String mService;
    private String mAddress;

    public order(){

    }

    public order(String username, String date, String slot, String service, String address){
        this.mUsername = username;
        this.mDate = date;
        this.mSlot = slot;
        this.mService = service;
        this.mAddress = address;
    }

    public String getUsername() {
        return mUsername;
    }
    public void setUsername(String username){this.mUsername = username;}

    public String getDate() {
        return mDate;
    }
    public void setDate(String date){this.mDate = date;}

    public String getSlot() {
        return mSlot;
    }
    public void setSlot(String slot){this.mSlot = slot;}

    public String getService() {
        return mService;
    }
    public void setService(String service){this.mService = service;}

    public String getAddress() {
        return mAddress;
    }
    public void setAddress(String address){this.mAddress = address;}

}
