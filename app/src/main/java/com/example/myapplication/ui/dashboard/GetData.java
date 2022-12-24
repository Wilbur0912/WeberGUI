package com.example.myapplication.ui.dashboard;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressLint("StaticFieldLeak")
public abstract class GetData extends AsyncTask<Void, String, String> {
    ConcreteConnection connection;
    String type;
    List<String> params1 = new ArrayList<>();
    public GetData(Connection j,String t,String...args) {
        connection = (ConcreteConnection) j;
        for (String p:args) {
            params1.add(p);
        }
        type = t;

    }
    @Override
    protected String doInBackground(Void... params) {
        try {
            switch (type){
                case "findall":
                    return connection.connectFindAll(params1.get(0));
                case "login":
                    return connection.connectLogin(params1.get(0), params1.get(1));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
