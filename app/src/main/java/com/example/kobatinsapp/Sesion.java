package com.example.kobatinsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class Sesion {
    public static final String APP = "KobatinsApp";

    public static final String IDUSER = "idUSER";
    public static final String NMUSER = "nmUSER";
    public static final String USRUSER = "usrUSER";
    public static final String PASSUSER = "passUSER";
    public static final String HPUSER = "hpUSER";

    public static final String SUDAH_LOGIN = "SudahLogin";

    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

    @SuppressLint("CommitPrefEdits")
    public Sesion(Context context){
        sp = context.getSharedPreferences(APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }
    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }
    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }
    public String getNmuser(){
        return sp.getString(NMUSER, "");
    }
    public String getHpuser(){
        return sp.getString(HPUSER, "");
    }

    public Boolean getSudahLogin(){
        return sp.getBoolean(SUDAH_LOGIN, false);
    }


}


