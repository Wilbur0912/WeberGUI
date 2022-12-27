package com.example.myapplication.ui.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentDashboardBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class calendar extends AppCompatActivity {

    ResultMemento m = null;
    CalendarView calendarView;
    TextView myDate;
    Button confirm;
    String selectDate;
    static LocalDate date;
    static ResultCareTaker resultCareTaker = new ResultCareTaker();
    private String account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        FragmentDashboardBinding binding = FragmentDashboardBinding.inflate(getLayoutInflater());
        //binding.navView1.setSelectedItemId(R.id.navigation_dashboard);
        setContentView(binding.getRoot());
        Bundle accountbundle = getIntent().getExtras();
        account = accountbundle.getString("account");

        //setContentView(R.layout.fragment_dashboard);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        myDate = (TextView) findViewById(R.id.myDate);
        confirm = (Button) findViewById(R.id.button);
        Calendar calendar = Calendar.getInstance();
        if(date != null && !date.equals(calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())){
            calendar.set(date.getYear(), date.getMonthValue()-1, date.getDayOfMonth());
            calendarView.setDate(date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond()*1000);
        }

        String thisMonth = String.valueOf(calendar.get(Calendar.MONTH)+1);
        String thisYear = String.valueOf(calendar.get(Calendar.YEAR));
        String today = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        selectDate = thisYear + "/" + thisMonth + "/" + today;
        myDate.setText(selectDate);

        findViewById(R.id.navigation_dashboard).performClick();
        binding.navView1.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.navigation_home){
                //startActivity(MainActivity.mainIntent);
                setResult(Activity.RESULT_OK);
                finish();
            }
            return false;
        });

        //Log.d("OuOb", resultMementoList.toString());
        confirm.setOnClickListener(v -> {

            if(!resultCareTaker.hasMemento(selectDate)) {
                new GetResultData( new ConcreteConnection()).execute(account);
                Log.d("OuOb", "getdata");
            } else {
                ResultMemento memento = resultCareTaker.getMemento(selectDate);
                Intent intent = new Intent(calendar.this, watchHistory.class);
                Bundle dateReslutBundle = new Bundle();
                dateReslutBundle.putString("date", selectDate);
                dateReslutBundle.putBoolean("result", memento.getSavedResult());
                dateReslutBundle.putInt("dataAmount",memento.getSavedAmount());
                intent.putExtras(dateReslutBundle);
                startActivity(intent);
            }
        });

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            date = LocalDate.of(year, month+1, dayOfMonth);
            selectDate = year+"/"+(month+1)+"/"+dayOfMonth;
            myDate.setText(selectDate);
        });
    }

    private class GetResultData extends GetData {
        boolean result ;
        String date, year, month;
        int day;
        public GetResultData(Connection c) {
            super(c,"findall");
        }
        int dataAmount;

        @Override
        protected void onPostExecute(String s) {

            try {
                Log.e("e",s);
                JSONArray jsonArray  = new JSONArray(s);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    date = jsonObject.getString("date");
                    String[] time = date.split("T");
                    String[] parts = (time[0]).split("-");
                    year = parts[0];
                    month = parts[1];
                    day = Integer.parseInt(parts[2]);
                    String d = year + "/" + month + "/" + day;
                    Log.d("ricky_test",jsonObject.getString("result"));
                    if(d.equals(selectDate)){
                        dataAmount++;
                        if(jsonObject.getBoolean("result")){
                            result = true;
                        }
                    }
                }
                resultCareTaker.addMemento(selectDate, new ResultMemento(result,dataAmount));
                ResultMemento memento = resultCareTaker.getMemento(selectDate);
                Intent intent = new Intent(calendar.this, watchHistory.class);
                Bundle dateReslutBundle = new Bundle();
                dateReslutBundle.putString("date", selectDate);
                dateReslutBundle.putBoolean("result", memento.getSavedResult());
                Log.e("a", String.valueOf(dataAmount));
                Log.e("a", String.valueOf(memento.getSavedAmount()));
                dateReslutBundle.putInt("dataAmount",memento.getSavedAmount());
                intent.putExtras(dateReslutBundle);
                startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}