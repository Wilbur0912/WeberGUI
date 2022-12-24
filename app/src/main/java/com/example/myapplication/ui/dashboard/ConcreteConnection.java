package com.example.myapplication.ui.dashboard;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ConcreteConnection implements Connection {
    private static final String TAG = "MainActivity";
    private static final String JSON_URL = "http://123.205.91.234:8080/db/findAll";
    private RequestBodyCreator creator = new RequestBodyCreator();

    public String connectFindAll(String account) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        Request request = creator.findAllBody(account);
        Response response = client.newCall(request).execute();

        return response.body().source().readUtf8();
    }
    public String connectLogin(String account,String password) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        Request request = creator.loginBody(account, password);
        Response response = client.newCall(request).execute();

        return response.body().source().readUtf8();
    }
}
