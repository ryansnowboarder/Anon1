package com.xchange_place.anon.dataholders;

/**
 * Created by Ryan Fletcher on 4/9/2015.
 */
public class LoginData {
    private int LoggedIn;
    private int shortcutted;
    private String shortcut;

    public int isLoggedIn() {
        return LoggedIn;
    }

    public void setLoggedIn(int loggedIn) {
        LoggedIn = loggedIn;
    }

    public int isShortcutted(){
        return shortcutted;
    }

    public void setShortcutted(int Shortcutted){
        shortcutted = Shortcutted;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }
}
