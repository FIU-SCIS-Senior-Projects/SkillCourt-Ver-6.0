package fiu.com.skillcourt.ui.startgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import fiu.com.skillcourt.R;
import fiu.com.skillcourt.interfaces.Constants;
import fiu.com.skillcourt.ui.base.ArduinosCommunicationFragment;
import fiu.com.skillcourt.ui.base.ArduinosStartCommunicationFragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class StartGameFragment extends ArduinosCommunicationFragment implements StartGameView, View.OnClickListener{

    private TextView tvTimer;
    private ProgressBar progressBar;
    private TextView tvHits;
    private TextView tvGreenHits;
    private TextView tvScore;
    private TextView tvAccuracy;
    private LinearLayout btnContainer, tvContainer;
    private RelativeLayout timerContainer;

    StartGamePresenter startGamePresenter;

    public static StartGameFragment newInstance(HashMap<String, String> sequences) {
        StartGameFragment startGameFragment = new StartGameFragment();
        if (sequences != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.TAG_SEQUENCE, sequences);
        }
        return new StartGameFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HashMap<String, String> sequences = new HashMap<>();
        if (savedInstanceState.containsKey(Constants.TAG_SEQUENCE)) sequences = (HashMap<String, String>) savedInstanceState.getSerializable(Constants.TAG_SEQUENCE);
        startGamePresenter = new StartGamePresenter(this, sequences);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start_game, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTimer = (TextView)view.findViewById(R.id.tvTimer);
        tvAccuracy = (TextView)view.findViewById(R.id.tv_accuracy);
        tvGreenHits = (TextView)view.findViewById(R.id.tv_green_hits);
        tvHits = (TextView)view.findViewById(R.id.tv_hits);
        tvScore = (TextView)view.findViewById(R.id.tv_score);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        btnContainer = (LinearLayout)view.findViewById(R.id.btn_containers);
        tvContainer = (LinearLayout)view.findViewById(R.id.tv_container);
        timerContainer = (RelativeLayout)view.findViewById(R.id.timer_container);
        Button btnNewGame, btnSaveAndPlay, btnPlayAgain, btnSaveAndNewGame;
        btnNewGame = (Button) view.findViewById(R.id.btn_new_game);
        btnPlayAgain = (Button) view.findViewById(R.id.btn_play_again);
        btnSaveAndPlay = (Button) view.findViewById(R.id.btn_save_play_again);
        btnSaveAndNewGame = (Button) view.findViewById(R.id.btn_save_and_new_game);
        btnNewGame.setOnClickListener(this);
        btnPlayAgain.setOnClickListener(this);
        btnSaveAndPlay.setOnClickListener(this);
        btnSaveAndNewGame.setOnClickListener(this);
        startGamePresenter.startGame();
    }

    @Override
    public void onPause() {
        super.onPause();
        startGamePresenter.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        startGamePresenter.onResume(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        startGamePresenter.cancelGame();
    }

    @Override
    public void setProgressTotal(int seconds) {
        progressBar.setMax(seconds);
    }

    @Override
    public void setTimerText(String time) {
        tvTimer.setText(time);
    }

    @Override
    public void changeProgressView(long seconds) {
        progressBar.setProgress((int)seconds);
    }

    @Override
    public void updateResult(final float totalHits,final float greenHits,final int score,final int accuracy) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvHits.setText(String.valueOf(totalHits));
                tvGreenHits.setText(String.valueOf(greenHits));
                tvScore.setText(String.valueOf(score));
                tvAccuracy.setText(String.valueOf(accuracy) + " %");
            }
        });
    }

    @Override
    public void setupInitGame() {
        timerContainer.setVisibility(View.VISIBLE);
        btnContainer.setVisibility(View.GONE);
        tvContainer.setVisibility(View.GONE);
    }

    @Override
    public void setupFinishGame() {
        timerContainer.setVisibility(View.GONE);
        btnContainer.setVisibility(View.VISIBLE);
        tvContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_new_game) {
            Intent intent = new Intent(getActivity(), StartGameActivity.class);
            startActivity(intent);
            fragmentListener.closeActivity();
        } else if (view.getId() == R.id.btn_save_and_new_game) {
            Intent intent = new Intent(getActivity(), StartGameActivity.class);
            saveFirebase();
            startActivity(intent);
            fragmentListener.closeActivity();
        } else if (view.getId() == R.id.btn_save_play_again) {
            saveFirebase();
            startGamePresenter.playAgain();
        } else if (view.getId() == R.id.btn_play_again) {
            startGamePresenter.playAgain();
        }
    }

    private void saveFirebase() {

    }

}
