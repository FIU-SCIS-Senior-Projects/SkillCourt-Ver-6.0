package fiu.com.skillcourt.ui.startgame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import fiu.com.skillcourt.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class StartGameFragment extends Fragment implements StartGameView {

    private TextView tvTimer;
    private ProgressBar progressBar;
    private TextView tvHits;
    private TextView tvGreenHits;
    private TextView tvScore;
    private TextView tvAccuracy;

    StartGamePresenter startGamePresenter;

    public static StartGameFragment newInstance() {
        return new StartGameFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startGamePresenter = new StartGamePresenter(this);
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
}
