package com.theah64.yts_nl.models;

/**
 * Created by theapache64 on 1/10/17.
 * email = ? , verification_code = ? , is_verified = ? , is_active = ?, updated_at = NOW()
 */
public class Subscription {

    private final String email, verificationCode;
    private boolean isVerified, isActive;

    public Subscription(String email, String verificationCode, boolean isVerified, boolean isActive) {
        this.email = email;
        this.verificationCode = verificationCode;
        this.isVerified = isVerified;
        this.isActive = isActive;
    }


    public String getEmail() {
        return email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public boolean isActive() {
        return isActive;
    }
}
