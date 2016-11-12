package fiu.com.skillcourt.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import fiu.com.skillcourt.R;
import fiu.com.skillcourt.connection.Arduino;
import fiu.com.skillcourt.connection.ArduinoManager;
import fiu.com.skillcourt.game.CountDownTimer;
import fiu.com.skillcourt.game.SkillCourtGame;
import fiu.com.skillcourt.interfaces.ArduinoSkillCourtInteractor;
import fiu.com.skillcourt.interfaces.CountdownInterface;
import fiu.com.skillcourt.ui.LauncherActivity;
import fiu.com.skillcourt.ui.base.BaseFragment;
import fiu.com.skillcourt.ui.creategame.CreateGameActivity;
import fiu.com.skillcourt.ui.dynamicsteps.DynamicStepsActivity;
import fiu.com.skillcourt.ui.startgame.StartGameActivity;

public class MainDashboardFragment extends BaseFragment {

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
        return inflater.inflate(R.layout.fragment_main_dashboard, container, false);
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

        CountDownTimer a = new CountDownTimer(15000, 1000, new CountdownInterface() {

            boolean newTime = true;
            long previousSecond = Long.MAX_VALUE;

            @Override
            public void onTick(long millisUntilFinished) {
                int second = Math.round((float)millisUntilFinished / 1000.0f);
                long minutes = (second / 60);
                long seconds = second % 60;

                if (seconds < previousSecond) {
                    previousSecond = seconds;
                    if (seconds % 3 == 0) {
                        Log.e("a", " "+previousSecond+" each 3 seconds");
                    }

                }
                String time = String.format("%02d:%02d", minutes, seconds);
            }

            @Override
            public void onFinish() {
                Log.e("ta", "finish");
            }
        });

        a.start();

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
