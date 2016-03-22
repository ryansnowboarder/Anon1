package com.xchange_place.anon.activities;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.parse.Parse;
import com.parse.ParseObject;
import com.xchange_place.anon.R;
import com.xchange_place.anon.database.LocalDatabase;
import com.xchange_place.anon.dataholders.LoginData;
import com.xchange_place.anon.fragments.CreateAccountFragment;
import com.xchange_place.anon.fragments.LoginFragment;


public class MainActivity extends ActionBarActivity {

    private LocalDatabase localDatabase;
    private LoginData loginData;
    private static final int TRUE = 1;
    private static final int FALSE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        localDatabase = new LocalDatabase(this);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "7EuF9hq0IareeH7a3bnuOubGdpvrhEQ1sPsqJowD", "qxEhHqzVtqxp1Qk9fWap7FSFq97CMJvgJcqob5rc");

        // If the user has opened the application before,
        // the following try statement will fully execute
        // because the login data is stored on the local
        // database
        try {
            loginData = localDatabase.readLoginData();
            // if not logged in...
            if (loginData.isLoggedIn() == FALSE) {
                // if shortcut is saved...
                if (loginData.isShortcutted() == TRUE) {
                    // open ShortcutLoginFragment to shortcut login
                }
                // if shortcut is not saved
                else {
                    // open LoginFragment to login
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, new LoginFragment())
                            .commit();
                }
            }
        }
        // If the user has not created an account,
        // the following catch statement will fully execute
        // because the login data is not stored on the local
        // database and the localDatabase.readLoginData()
        // statement will cause the try statement to exit
        catch (Exception e){
            // add login data to the database
            LoginData newLoginData = new LoginData();
            newLoginData.setLoggedIn(FALSE);
            newLoginData.setShortcutted(FALSE);
            newLoginData.setShortcut("0");
            localDatabase.insertLoginData(newLoginData);

            // open CreateAccountFragment to create an account
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new CreateAccountFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
