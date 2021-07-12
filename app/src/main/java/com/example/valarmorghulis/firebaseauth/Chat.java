package com.example.valarmorghulis.firebaseauth;

import android.support.annotation.NonNull;

public class Chat {
    private String myText;
    private String emailId;

    @NonNull
    @Override
    public String toString() {
        return myText+" "+emailId+"\n";
    }

    public Chat() {
        //empty constructor needed
    }
    public Chat(String myText, String email) {
        this.myText = myText;
        this.emailId = email;
    }

    public String getMyText() {
        return myText;
    }

    public String getEmailId() {
        return emailId;
    }
}
