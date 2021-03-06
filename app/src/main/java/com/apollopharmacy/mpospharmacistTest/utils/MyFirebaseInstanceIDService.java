package com.apollopharmacy.mpospharmacistTest.utils;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;


public class MyFirebaseInstanceIDService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String newToken) {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
       // Log.d("TAG", "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(newToken);
    }

    public void sendRegistrationToServer(String token) {
        Log.v("FirebaseService", "Token " + token);
    }
}