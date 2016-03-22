package com.xchange_place.anon.asynctask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.xchange_place.anon.R;
import com.xchange_place.anon.fragments.CreateAccountFragment;

/**
 * An asynctask looper to verify the data entered into the views of CreateAccountFragment.
 * Created by Ryan Fletcher on 4/10/2015.
 */
public class VerifyCreateAccountData extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "VerifyCreateAccountData";
    // The TextView instance passed from CreateAccountFragment for entering username
    private TextView Username;
    // The TextView instance passed from CreateAccountFragment for entering password
    private TextView Password;
    // The TextView instance passed from CreateAccountFragment for re-entering the password
    private TextView ReEnterPassword;
    // The boolean instance used to switch between the check_mark
    // image (when instance is true) and the x-mark image (when false) next to the Username
    public boolean usernameVerified = false;
    // The boolean instance used to switch between the check_mark
    // image (when instance is true) and the x-mark image (when false) next to Password
    public boolean usernameImage = false;
    /// The boolean instance used to keep track of which image is being displayed next to
    // the Username TextView. False if x_mark.png, true if check_mark.png
    public boolean passwordVerified = false;
    // The boolean instance used to switch between the check_mark
    // image (when  instance is true) and the x-mark image (when false) next to ReEnterPassword
    public boolean reEnterPasswordVerified = false;
    // Stores the state of usernameVerified from the previous loop to track if it has changed
    private boolean passwordVerifiedPreviousLoop = false;
    // Stores the state of reEnterPasswordVerified from the previous loop to track if it has changed
    private boolean reEnterPasswordVerifiedPreviousLoop = false;
    // Used to store the state of the ParseQuery (true when completed, else false)
    private boolean parseUsernameCheckComplete = false;
    // Used to store the running state of the ParseQuery (true when running, else false)
    private boolean parseUsernameCheckRunning = false;
    // The ImageView instance passed from CreateAccountFragment next to Username
    private ImageView UsernameMark;
    // The ImageView instance passed from CreateAccountFragment next to Password
    private ImageView PasswordMark;
    // The ImageView instance passed from CreateAccountFragment next to ReEnterPassword
    private ImageView ReEnterPasswordMark;


    public VerifyCreateAccountData(
            TextView username,
            TextView password,
            TextView reEnterPassword,
            ImageView usernameMark,
            ImageView passwordMark,
            ImageView reEnterPasswordMark){
        Username = username;
        Password = password;
        ReEnterPassword = reEnterPassword;
        UsernameMark = usernameMark;
        PasswordMark = passwordMark;
        ReEnterPasswordMark = reEnterPasswordMark;
    }


    protected Void doInBackground(Void... params) {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        boolean dataIncorrect = true;
        while (dataIncorrect){
            verifyUsername(Username.getText().toString());
            verifyPassword(Password.getText().toString());
            publishProgress(null);
        }
        return null;
    }

    protected void onProgressUpdate(Void... params) {
        if (usernameVerified && !usernameImage) {
            UsernameMark.setImageResource(R.drawable.check_mark);
            usernameImage = true;
        }
        if (!usernameVerified && usernameImage){
            UsernameMark.setImageResource(R.drawable.x_mark);
            usernameImage = false;
        }
        if (passwordVerified && !passwordVerifiedPreviousLoop) {
           PasswordMark.setImageResource(R.drawable.check_mark);
        }
        if (!passwordVerified && passwordVerifiedPreviousLoop){
                PasswordMark.setImageResource(R.drawable.x_mark);
        }
        passwordVerifiedPreviousLoop = passwordVerified;
        if (usernameVerified && passwordVerified){
            CreateAccountFragment.goodData = true;
        }
    }

    private void verifyUsername(String username) {
        if (username.length() >= 8) {
             // Log.e(TAG, "This point reached 1");
            if (!parseUsernameCheckComplete) {
                parseUsernameCheckComplete = false;
                parseUsernameCheckRunning = true;
               // Log.e(TAG, "This point reached 2");
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
                    query.whereEqualTo("username", username);
                    query.getFirstInBackground(new GetCallback<ParseObject>() {
                        public void done(ParseObject object, ParseException e) {
                            if (e.getMessage() != null) {
                                Log.e(TAG, "This point reached 4");
                                Log.e(TAG, e.getMessage());
                                Log.e(TAG, "here");
                                if (e.getMessage().equals("no results found for query")) {
                                    usernameVerified = true;
                                    Log.e(TAG, "username verified");
                                }
                                else{
                                    usernameVerified = false;
                                }
                            }
                            else {
                                usernameVerified = false;
                            }

                            // usernameVerifiedPreviousLoop = usernameVerified;
                            try {
                                wait(500);
                                Log.e(TAG, "waited 500ms");
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                            parseUsernameCheckComplete = true;
                            parseUsernameCheckRunning = false;
                            Log.e(TAG, "This point reached 5");
                        }
                    });
            }

        }
        else {
            parseUsernameCheckComplete = false;
            parseUsernameCheckRunning = false;
            usernameVerified = false;
        }
    }

    private void verifyPassword(String password){
        // The rules for the regex pattern are as follows:
        //  -   a digit must occur at least once
        //  -   a lower case letter must occur at least once
        //  -   an upper case letter must occur at least once
        //  -   a special character must occur at least once
        //  -   no whitespace allowed in the entire string
        //  -   must be at least eight characters in length
        String regexPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        if (password.matches(regexPattern)){
            // Log.e(TAG, "This point reached 1");
            passwordVerified = true;
        }
        else {
            passwordVerified = false;
        }
    }

    private void verifyReEnterPassword(){
//        if (CreateAccountFragment.Password.getText()
//                .equals(CreateAccountFragment.ReEnterPassword.getText())){
//            reEnterPasswordVerified = true;
//        }
    }
}
