package com.xchange_place.anon.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xchange_place.anon.R;

/**
 * A fragment used to log into the application or create a new admin account.
 * Created by Ryan Fletcher on 2/25/2015.
 */
public class LoginFragment extends Fragment {

    public LoginFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        return rootView;
    }
}