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
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        startGamePresenter.startGame();
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
}
