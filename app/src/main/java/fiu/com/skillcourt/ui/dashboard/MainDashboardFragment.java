package fiu.com.skillcourt.ui.dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import fiu.com.skillcourt.R;
import fiu.com.skillcourt.entities.Game;
import fiu.com.skillcourt.game.Sequences;
import fiu.com.skillcourt.game.SkillCourtGame;
import fiu.com.skillcourt.ui.LauncherActivity;
import fiu.com.skillcourt.ui.base.BaseFragment;
import fiu.com.skillcourt.ui.creategame.CreateGameActivity;
import fiu.com.skillcourt.ui.dynamicsteps.DynamicStepsActivity;
import fiu.com.skillcourt.ui.startgame.StartGameActivity;

public class MainDashboardFragment extends BaseFragment  {


    LineChart mChart;
    List<Entry> entryGamesAccuracy;
    LineDataSet gamesAccuracy;

    Date oneWeekAgo;
    List<Game> gamesThisWeek;

    public static MainDashboardFragment newInstance() {
        return new MainDashboardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_main_dashboard, container, false);
        mChart = (LineChart) view.findViewById(R.id.chart_dashboard);
        setupChart();

        return view;
    }

    private void setupChart() {

//        YAxis yAxis = mChart.getAxisLeft();
//        yAxis.setAxisMaximum(100f);
//        yAxis.setAxisMinimum(0f);
//
//        LineData data = new LineData(xValuesArray);
//        mChart.setData(data);
//        mChart.invalidate();

//        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//            @Override
//            public void onValueSelected(Entry e, Highlight h) {
//
//            }
//
//            @Override
//            public void onNothingSelected() {
//
//            }
//        });
//
//        // no description text
//        mChart.getDescription().setEnabled(false);
//
//        // enable touch gestures
//        mChart.setTouchEnabled(true);
//
//        mChart.setDragDecelerationFrictionCoef(0.9f);
//
//        // enable scaling and dragging
//        mChart.setDragEnabled(true);
//        mChart.setScaleEnabled(true);
//        mChart.setDrawGridBackground(false);
//        mChart.setHighlightPerDragEnabled(false);
//
//        // if disabled, scaling can be done on x- and y-axis separately
//        mChart.setPinchZoom(true);
//
//        // set an alternative background color
//        mChart.setBackgroundColor(Color.TRANSPARENT);
//        entryGamesAccuracy = new ArrayList<>();
//        mChart.getAxisLeft().setEnabled(true);
//        mChart.getAxisLeft().setSpaceTop(40);
//        mChart.getAxisLeft().setSpaceBottom(40);
//        mChart.getAxisRight().setEnabled(false);
//        mChart.getXAxis().setEnabled(false);
//
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
//        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)-7);
//        oneWeekAgo = cal.getTime();
//        gamesAccuracy = new LineDataSet(entryGamesAccuracy, "Accuracy");
//
        gamesThisWeek = new ArrayList<>();
//
//        gamesAccuracy.disableDashedLine();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        if (email != null) {
            TextView tvWelcome = (TextView)getView().findViewById(R.id.tv_welcome);
            tvWelcome.setText("Welcome "+ email);
        }
        Button startCustomSteps = (Button) getView().findViewById(R.id.start_custom_steps);
        startCustomSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DynamicStepsActivity.class);
                startActivity(intent);
            }
        });
        Button startGame = (Button) getView().findViewById(R.id.start_game);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateGameActivity.class);
                startActivity(intent);
            }
        });

        pedrosTest();
    }


    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mUserDatabaseReference;

    private void pedrosTest() {

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUserDatabaseReference = mFirebaseDatabase.getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        DatabaseReference mGameDatabaseReference = mUserDatabaseReference
                .child("games");
        Query q = mGameDatabaseReference
                .orderByChild("date");
//                .startAt(oneWeekAgo.getTime())
//                .endAt(new Date().getTime());

        ChildEventListener mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Game game = dataSnapshot.getValue(Game.class);
                gamesThisWeek.add(game);
//                updateChart();
                Log.d("TEST", game.getScore() +" "+ game.getTimeObjective() + " " + game.getGreenPad() + "  " + game.getGameMode() + " " + game.getDate());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        q.addChildEventListener(mChildEventListener);
    }

    private void updateChart() {
        // Adding data to chart for accuracy
        for (int i = 0; i < gamesThisWeek.size(); i++) {
            Entry newEntry = new Entry(i+1, gamesThisWeek.get(i).getScore());
            if (entryGamesAccuracy.isEmpty()) {

                ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                dataSets.add(gamesAccuracy); // add the datasets

                // create a data object with the datasets
                LineData data = new LineData(dataSets);
                mChart.setData(data);
            } else {
                if (!entryGamesAccuracy.contains(newEntry)) {
                    entryGamesAccuracy.add(new Entry(i + 1, gamesThisWeek.get(i).getScore()));
                }
            }
        }
        mChart.getData().notifyDataChanged();
        mChart.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sign_out:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LauncherActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
