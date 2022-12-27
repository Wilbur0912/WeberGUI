package com.example.myapplication.ui.dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class watchHistory extends AppCompatActivity {

    String dateString;
    boolean result;
    TextView teh;
    TextView teg;
    TextView tep;
    TextView dataAmountText;
    int dataAmount;
    float percentage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.historyresults);
        Bundle bundle = getIntent().getExtras();

        dateString = bundle.getString("date");
        result = bundle.getBoolean("result",false);
        dataAmount = bundle.getInt("dataAmount");
        percentage= bundle.getFloat("percentage");

        teh = findViewById(R.id.textViewhave);

        teg = findViewById(R.id.textViewgo);

        tep = findViewById(R.id.textViewprobability);

        dataAmountText = findViewById(R.id.textView_show_data_amount);
        dataAmountText.setText("/"+dataAmount+"筆");

        if (!result) {
            teg.setText(getResources().getString(R.string.next_step_check_n));
            teh.setText(getResources().getString(R.string.havet_pro));
            teh.setTextColor(Color.parseColor("#59bb9b"));
            teg.setTextColor(Color.parseColor("#59bb9b"));
        } else if(result){
            teh.setText(getResources().getString(R.string.have_pro));
            teh.setTextColor(Color.parseColor("#ff8080"));
            tep.setText(percentage*100+"%");
            tep.setTextColor(Color.parseColor("#ff8080"));
        }else{
            teh.setText(getResources().getString(R.string.No_data));
            teh.setTextColor(Color.parseColor("#59bb9b"));
            teg.setText(getResources().getString(R.string.next_step_back));
            tep.setText("?");
        }

    }
    @Override
    public void onBackPressed(){
        finish();
    }
}