package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ui.dashboard.ConcreteConnection;
import com.example.myapplication.ui.dashboard.Connection;
import com.example.myapplication.ui.dashboard.GetData;

public class LoginActivity extends AppCompatActivity {
    private EditText editText;
    private final String blockCharacterSet = "~#^|@$%&*!";
    private ConcreteConnection connection = new ConcreteConnection();
    private String account;
    private String password;

    private final InputFilter filter = (source, start, end, dest, dstart, dend) -> {

        if (source != null && blockCharacterSet.contains(("" + source))) {
            return "";///111111
        }
        return null;
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login = this.findViewById(R.id.login);
        TextView signInText = this.findViewById(R.id.log2);
        editText = (EditText) findViewById(R.id.account);
        editText.setFilters(new InputFilter[] { filter });

        login.setOnClickListener(view -> {
            EditText accountText = LoginActivity.this.findViewById(R.id.account);
            EditText passwordText = LoginActivity.this.findViewById(R.id.password);
            account =String.valueOf(accountText.getText());
            password =String.valueOf(passwordText.getText());
            try{
                new GetLoginRequest(new ConcreteConnection()).execute(account,password);
            }catch (Exception e){
                Toast.makeText(LoginActivity.this,"後端異常",Toast.LENGTH_SHORT).show();
            }


        });
        signInText.setOnClickListener(view->{
            Intent intent = new Intent(LoginActivity.this, NewUserActivity.class);
            startActivity(intent);

        });
    }
    private class GetLoginRequest extends GetData {

        public GetLoginRequest(Connection j) {
            super(j,"login");
        }
        @Override
        protected void onPostExecute(String s) {
            try {
                if(s.equals("Login Success")){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("account", account);
                    bundle.putString("password", password);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else if(s.equals("Login Error!")) {
                    Toast.makeText(LoginActivity.this,"帳號密碼錯誤",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this,"未知的錯誤",Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(LoginActivity.this,"後端異常",Toast.LENGTH_SHORT).show();
            }
        }
    }


}