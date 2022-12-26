package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ui.dashboard.ConcreteConnection;
import com.example.myapplication.ui.dashboard.Connection;
import com.example.myapplication.ui.dashboard.GetData;
import com.example.myapplication.ui.dashboard.NewUserVO;

public class NewUserActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);
        Button register = this.findViewById(R.id.button_registing);
        TextView backtext = this.findViewById(R.id.text_back_to_login);
        TextView userNameText = this.findViewById(R.id.layout_name);
        TextView accountText = this.findViewById(R.id.layout_account);
        TextView passwordText = this.findViewById(R.id.layout_password);
        RadioGroup genderRadio = this.findViewById(R.id.radio_gender);
        TextView ageText = this.findViewById(R.id.layout_age);
        TextView phoneText = this.findViewById(R.id.layout_phone);
        TextView addressNameText = this.findViewById(R.id.layout_address);
        TextView familyIDText = this.findViewById(R.id.layout_FamilyID);
        TextView familyNameText = this.findViewById(R.id.layout_familyName);
        TextView familyPhoneText = this.findViewById(R.id.layout_familyPhone);
        TextView wristbandNameText = this.findViewById(R.id.layout_wristbandName);


        register.setOnClickListener(view -> {
            int genderCheckID = genderRadio.getCheckedRadioButtonId();
            RadioButton genderCheckRadio = this.findViewById(genderCheckID);
            String username = String.valueOf(userNameText.getText());
            String account = String.valueOf(accountText.getText());
            String password = String.valueOf(passwordText.getText());
            String gender = genderCheckRadio.getText().toString();
            String age = String.valueOf(ageText.getText());
            String phone = String.valueOf(phoneText.getText());
            String address = String.valueOf(addressNameText.getText());
            String familyID = String.valueOf(familyIDText.getText());
            String familyName = String.valueOf(familyNameText.getText());
            String familyPhone = String.valueOf(familyPhoneText.getText());
            String wristBandName = String.valueOf(wristbandNameText.getText());
            NewUserVO vo = new NewUserVO(account,username,password,gender,age,phone,address,familyID,familyName,familyPhone,"sdfdsf",wristBandName);
            try {
                new NewUserActivity.PostNewUserData(new ConcreteConnection(),"newuser").execute(vo);
            }catch (Exception e){
                Toast.makeText(NewUserActivity.this,"後端異常",Toast.LENGTH_SHORT).show();
            }

        });
        backtext.setOnClickListener(view->{
            Intent intent = new Intent(NewUserActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
    private class PostNewUserData extends GetData{

        public PostNewUserData(Connection j, String t) {
            super(j, t);
        }
        @Override
        protected void onPostExecute(String s) {
            if(s.equals("create Success")){
                Toast.makeText(NewUserActivity.this,"創建成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NewUserActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }else if(s.equals("create error")){

            }else{
                Toast.makeText(NewUserActivity.this,"未知的錯誤",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
