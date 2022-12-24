package com.example.myapplication.ui.dashboard;

import java.lang.reflect.Field;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RequestBodyCreator {
    private static final String base_URL = "http://123.205.91.234:8080/db/";


    public Request findAllBody(String account){
        String url = base_URL +"findAll";
        RequestBody body =new FormBody.Builder()
                .add("account",account)
                .add("message","")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        return request;
    }
    public Request findByDateBody(String account,String date){
        String url = base_URL + "findByDate";

        RequestBody body =new FormBody.Builder()
                .add("account",account)
                .add("message",date)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        return request;
    }
    public Request newUserBody(NewUserVO vo){
        String url = base_URL + "newuser";
        FormBody.Builder builder = new FormBody.Builder();

        try {
            for (Field field : vo.getClass().getDeclaredFields()){
                builder.add(field.getName(), "" + field.get(vo));
            }
        }catch (IllegalAccessException e){}
        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        return request;
    }
    public Request loginBody(String account,String password){
        String url = base_URL + "login";
        RequestBody body =new FormBody.Builder()
                .add("account",account)
                .add("password",password)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        return request;
    }
}
