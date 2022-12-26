package com.example.myapplication.ui.dashboard;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressLint("StaticFieldLeak")
public abstract class GetData extends AsyncTask<Object, String, String> {
    ConcreteConnection connection;
    String type;

    public GetData(Connection j,String t) {
        connection = (ConcreteConnection) j;

        type = t;

    }
    @Override
    protected String doInBackground(Object... params) {
        try {
            switch (type){
                case "findall":
                    return connection.connectFindAll((String) params[0]);
                case "login":
                    return connection.connectLogin((String) params[0], (String) params[1]);
                case "newuser":
                    return connection.connectnewUser((NewUserVO) params[0]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
