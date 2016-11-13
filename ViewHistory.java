package fiu.com.skillcourt.ui.history;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;


import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import fiu.com.skillcourt.R;
import fiu.com.skillcourt.ui.base.BaseActivity;

public class ViewHistory extends BaseActivity {

    protected FirebaseAuth mAuth;
    protected FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(user.getUid()).child("gamesPlayed");

/*
        DatabaseReference myPush = myRef.push();
        DatabaseReference newGame = myRef.child(myPush.getKey());
        HashMap<String, String> mode_and_score = new HashMap<>();

        mode_and_score.put("greenHits", "51");
        newGame.setValue(mode_and_score);
        mode_and_score.put("redHits", "8");
        newGame.setValue(mode_and_score);
        mode_and_score.put("score", "109");
        newGame.setValue(mode_and_score);
        mode_and_score.put("date", "11/12");
        newGame.setValue(mode_and_score);
  */


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                ArrayList<String> scoresList = new ArrayList<String>();
                ArrayList<String> datesList = new ArrayList<String>();
                ArrayList<String> greenList = new ArrayList<String>();
                ArrayList<String> redList = new ArrayList<String>();

                scoresList.clear();
                datesList.clear();
                greenList.clear();
                redList.clear();

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Map<String, String> map = (Map<String, String>) postSnapshot.getValue();

                    Object score = map.get((Object)"score");
                    scoresList.add((String) score);

                    Object date = map.get((Object)"date");
                    datesList.add((String)date);

                    Object red = map.get((Object)"greenHits");
                    greenList.add((String) red);

                    Object green = map.get((Object)"redHits");
                    redList.add((String) green);

                }
                System.out.println(" SCORE:" + scoresList + "  DATES:" + datesList );

                createLineGraph(scoresList, datesList);
                createHitIcons(greenList, redList);
                createBarGraph(greenList, redList, datesList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    protected  void createHitIcons (ArrayList<String> greenHits, ArrayList<String> redHits)
    {
        TextView greenTV = (TextView) findViewById(R.id.greenhits);
        TextView redTV = (TextView) findViewById(R.id.redhits);
        TextView pointsTV = (TextView) findViewById(R.id.points);

        int totalGreen = 0;
        int totalRed = 0;
        int totalPoints = 0;

        for (int i=0; i < greenHits.size(); i++) {
            totalGreen = totalGreen + Integer.parseInt(greenHits.get(i));
            totalRed = totalRed + Integer.parseInt(redHits.get(i));
        }

        totalPoints = totalGreen - totalRed;

        greenTV.setText(Long.toString(totalGreen)); //leave this line to assign a specific text
        redTV.setText(Long.toString(totalRed)); //leave this line to assign a specific text
        pointsTV.setText(Long.toString(totalPoints)); //leave this line to assign a specific text
    }

    protected void createLineGraph(ArrayList<String> scoresList, ArrayList<String> datesList)
    {
        LineChart chart = (LineChart) findViewById(R.id.chart);
        chart.setBackgroundColor(Color.TRANSPARENT);
        chart.setTouchEnabled(true);
        chart.setDrawGridBackground(false);
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(true);

        System.out.println(" SCORE:" + scoresList + "  DATES:" + datesList );

        // Entry = x axis, y axis
        ArrayList<Entry> vals = new ArrayList();
        for (int i=0; i < scoresList.size(); i++) {
            float f1 = (float) i;
            vals.add(new Entry(f1, Float.parseFloat(scoresList.get(i))));
        }

        // create data set out of these values
        LineDataSet dataSet = new LineDataSet(vals, "Points");
        LineData lineData = new LineData(dataSet);

        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        // do some styling/formatting
        dataSet.setFillAlpha(80);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setColor(Color.GRAY);
        dataSet.setFillColor(Color.GREEN);
        dataSet.setCircleColor(Color.DKGRAY);
        dataSet.setLineWidth(1f);
        dataSet.setCircleRadius(3f);
        dataSet.setDrawCircleHole(false);
        dataSet.setValueTextSize(9f);
        dataSet.setDrawFilled(true);

        // set data and create chart
        chart.setData(lineData);

        // styling of lines and grid
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0);
        xAxis.setDrawGridLines(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisRight().setEnabled(false);
        xAxis.setGranularity(1f);

        // set bottom axis values
        String[] values = new String[datesList.size()];
        for (int j=0; j < datesList.size(); j++) {
            values[j] = datesList.get(j);
        }
            xAxis.setValueFormatter(new MyXAxisValueFormatter(values));

        //  Legend l = chart.getLegend();
        //  l.setForm(Legend.LegendForm.LINE);
        chart.animateXY(3000, 3000);
        chart.invalidate(); // refresh
    }


    public class MyXAxisValueFormatter implements IAxisValueFormatter {

        private String[] mValues;

        public MyXAxisValueFormatter(String[] values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int) value];

        }

        @Override
        public int getDecimalDigits() {
            return 0;
        }
    }

    protected void createBarGraph(ArrayList<String> redList, ArrayList<String> greenList, ArrayList<String> datesList) {


        BarChart bchart = (BarChart) findViewById(R.id.bChart);
        bchart.setBackgroundColor(Color.TRANSPARENT);
        bchart.setTouchEnabled(true);

        // Entry = x axis, y axis
        ArrayList<BarEntry> vals = new ArrayList();
        ArrayList<BarEntry> vals2 = new ArrayList();
        for (int i=0; i < redList.size(); i++) {
            float f1 = (float) i;
            vals.add(new BarEntry(f1, Float.parseFloat(redList.get(i))));
            vals2.add(new BarEntry(f1, Float.parseFloat(greenList.get(i))));
        }

        BarDataSet dataSet1 = new BarDataSet(vals, "Green Hits");
        dataSet1.setColor(Color.rgb(0, 155, 0));
        BarDataSet dataSet2 = new BarDataSet(vals2, "Red Hits");
        dataSet2.setColor(Color.rgb(155, 0, 0));

        String[] values = new String[datesList.size()];
        for (int j=0; j < datesList.size(); j++) {
            values[j] = datesList.get(j);
        }
        //bchart.getXAxis().setValueFormatter(new MyXAxisValueFormatter(values));
        //bchart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        bchart.getXAxis().setAxisMinimum(0);

        BarData bData = new BarData(dataSet2, dataSet1);
        bData.setBarWidth(.4f);

        bchart.getAxisLeft().setDrawGridLines(false);
        bchart.getAxisRight().setDrawGridLines(false);

        bchart.setData(bData);
        bchart.groupBars(0, .07f, .01f);
        bchart.invalidate();

    }
}

