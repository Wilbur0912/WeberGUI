package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;

import com.example.myapplication.ui.dashboard.ConcreteConnection;
import com.example.myapplication.ui.dashboard.Connection;
import com.example.myapplication.ui.dashboard.GetData;
import com.example.myapplication.ui.dashboard.calendar;
import com.example.myapplication.ui.dashboard.watchHistory;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    //region getbundle Value:account、password
    private String account ;
    private String password;
    //endregion
    Intent calendarIntent;
    public static Intent mainIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle2 = getIntent().getExtras();
        account = bundle2.getString("account");
        password = bundle2.getString("password");

        super.onCreate(savedInstanceState);
        new GetChartData(new ConcreteConnection()).execute(account);

        com.example.myapplication.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard)
                .build();

        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        binding.navView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.navigation_dashboard){
                if (mainIntent == null)
                    mainIntent = MainActivity.this.getIntent();
                if (calendarIntent == null)
                    calendarIntent = new Intent(MainActivity.this, calendar.class);
                Bundle accountBundle = new Bundle();
                accountBundle.putString("account",account);
                calendarIntent.putExtras(accountBundle);
                //binding.navView.setSelectedItemId(R.id.navigation_home);
                startActivity(calendarIntent);
            }
            return false;
        });
    }
    

    public void openAboutHistory(View view) {
        Intent intent = new Intent(this, watchHistory.class);
        startActivity(intent);
    }

    @SuppressLint("StaticFieldLeak")
    private class GetChartData extends GetData {

        public GetChartData(Connection c) {
            super(c,"findall");
        }

        String date, year, month;
        boolean result;
        int day;
        int week1 = 0;
        int week2 = 0;
        int week3 = 0;
        int week4 = 0;
        int week5 = 0;



        @Override
        protected void onPostExecute(String s) {
            try {
                Calendar calendar = Calendar.getInstance();
                String thisMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
                String thisYear = String.valueOf(calendar.get(Calendar.YEAR));
                Log.e("a",s);


                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    date = jsonObject1.getString("date");
                    String[] time = date.split("T");
                    String[] parts = (time[0]).split("-");
                    year = parts[0];
                    month = parts[1];
                    day = Integer.parseInt(parts[2]);
                    result = jsonObject1.getBoolean("result");

                    // Hashmap
                    if (result && year.equals(thisYear) && month.equals(thisMonth)) {
                        if (1 <= day && day <= 7) {
                            week1 = week1 + 1;
                        } else if (8 <= day && day <= 14) {
                            week2 = week2 + 1;
                        } else if (15 <= day && day <= 21) {
                            week3 = week3 + 1;
                        } else if (22 <= day && day <= 28) {
                            week4 = week4 + 1;
                        } else if (29 <= day) {
                            week5 = week5 + 1;
                        }
                    }
                }
                ArrayList<Entry> yValues = new ArrayList<>();
                //int a = 1;
                yValues.add(new Entry(0, week1));
                yValues.add(new Entry(1, week2));
                yValues.add(new Entry(2, week3));
                yValues.add(new Entry(3, week4));
                //yValues.add(new Entry(4,week5));
                LineDataSet set1 = new LineDataSet(yValues, "Data Set 1");

                set1.setFillAlpha(110);
                set1.setColor(Color.parseColor("#59bb9b"));
                set1.setDrawFilled(true);
                set1.setLineWidth(3f);
                set1.setCircleColors(Color.parseColor("#59c1c9"));
                set1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
                set1.setValueTextSize(15);

                set1.setValueTextColor(Color.parseColor("#59c1c9"));
                set1.setCircleRadius(6);//圓點大小
                set1.setDrawCircleHole(false);//圓點為實心
                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                dataSets.add(set1);
                LineData data = new LineData(dataSets);

                LineChart mChart = findViewById(R.id.linechart);
                mChart.setDrawGridBackground(true);
                mChart.setNoDataText("點擊獲取資料");
                mChart.setDragEnabled(true);
                mChart.setScaleEnabled(false);
                mChart.setDrawBorders(false);//外框

                XAxis xAxis = mChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setGranularity(1f);
                xAxis.setAxisMinimum(0f);
                xAxis.setDrawLabels(true);
                xAxis.setDrawGridLines(false);
                xAxis.setLabelCount(4, false);
                ValueFormatter valueFormatter = new ValueFormatter() {
                    private final String[] xLableList = new String[]{"第一周","第二周","第三周","第四周"};

                    @Override
                    public String getFormattedValue(float value) {
                        if (value >= 0) {
                            return xLableList[(int) value % xLableList.length];
                        } else {
                            return "";
                        }
                    }
                };
                xAxis.setValueFormatter(valueFormatter);
                xAxis.setLabelRotationAngle(40);
                xAxis.setTextColor(Color.parseColor("#59bb9b"));
                xAxis.setTextSize(14);


                YAxis yAxis = mChart.getAxisLeft();
                YAxis rightYAxis = mChart.getAxisRight();
                yAxis.setEnabled(true);
                yAxis.setDrawGridLines(false);
                yAxis.setTextColor(Color.parseColor("#59bb9b"));
                rightYAxis.setEnabled(false);
                Legend legend = mChart.getLegend();
                legend.setEnabled(false);
                Description description = new Description();
                description.setEnabled(false);
                mChart.setDescription(description);

                mChart.setDragEnabled(true);
                mChart.setScaleEnabled(false);
                mChart.setData(data);
                mChart.invalidate();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
