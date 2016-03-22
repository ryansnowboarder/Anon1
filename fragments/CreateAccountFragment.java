package com.xchange_place.anon.fragments;

/**
 * Created by Ryan Fletcher on 4/10/2015.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.xchange_place.anon.R;
import com.xchange_place.anon.asynctask.VerifyCreateAccountData;

/**
 * A fragment used to create an account.
 * Created by Ryan Fletcher on 2/25/2015.
 */
public class CreateAccountFragment extends Fragment {

    public static TextView Username;
    public static TextView Password;
    public static TextView ReEnterPassword;
    public static ImageView UsernameMark;
    private static ImageView PasswordMark;
    public static ImageView ReEnterPasswordMark;
    private Button CreateAccountButton;
    private static VerifyCreateAccountData verifyDataLooper;
    public static boolean goodData = false;

    public CreateAccountFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_create_account, container, false);

        Username = (TextView) rootView.findViewById(R.id.fragment_create_account_username);
        Password = (TextView) rootView.findViewById(R.id.fragment_create_account_password);
        UsernameMark = (ImageView) rootView.findViewById(R.id.fragment_create_account_username_mark);
        PasswordMark = (ImageView) rootView.findViewById(R.id.fragment_create_account_password_mark);
        CreateAccountButton = (Button) rootView.findViewById(R.id.fragment_create_account_submit);

        verifyDataLooper =  new VerifyCreateAccountData(
                Username,
                Password,
                ReEnterPassword,
                UsernameMark,
                PasswordMark,
                ReEnterPasswordMark);

        Username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyDataLooper.execute();
            }
        });

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(goodData);
            }
        });

        return rootView;
    }

    private void createAccount(boolean goodData) {
        if (goodData) {
            ParseObject user = new ParseObject("User");
            user.put(
                    "username",
                    Username.getText().toString());
            user.put(
                    "password",
                    Password.getText().toString());
            user.saveInBackground();
        }
    }
}
