package fiu.com.skillcourt.ui.creategame;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import fiu.com.skillcourt.R;
import fiu.com.skillcourt.game.SkillCourtGame;
import fiu.com.skillcourt.game.SkillCourtManager;
import fiu.com.skillcourt.ui.base.ArduinosStartCommunicationFragment;
import fiu.com.skillcourt.ui.base.Utils;
import fiu.com.skillcourt.ui.custom.GameModePickerFragment;
import fiu.com.skillcourt.ui.custom.TimePickerFragment;
import fiu.com.skillcourt.ui.startgame.StartGameActivity;

public class CreateGameFragment extends ArduinosStartCommunicationFragment implements TimePickerDialog.OnTimeSetListener, DialogInterface.OnClickListener {

    Button btnTime, btnGameMode, btnStartGame;
    TimePickerFragment timePickerFragment = new TimePickerFragment();
    GameModePickerFragment gameModePickerFragment = new GameModePickerFragment();

    private int time = -1;
    private SkillCourtGame.GameMode selectedGameMode;

    public static CreateGameFragment newInstance() {
        return new CreateGameFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_game, container, false);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        time = i*60+i1;
        btnTime.setText(String.format("%02d:%02d",i,+i1));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnTime = (Button) view.findViewById(R.id.btn_time);
        btnGameMode = (Button) view.findViewById(R.id.btn_gameMode);
        btnStartGame = (Button) view.findViewById(R.id.start_game);
        timePickerFragment.setOnTimerPickerDialog(CreateGameFragment.this);
        gameModePickerFragment.setOnClickListener(this);

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerFragment.show(getChildFragmentManager(), "time_picker");
            }
        });

        btnGameMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameModePickerFragment.show(getChildFragmentManager(), "game_mode");
            }
        });

        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()) {
                    SkillCourtManager.getInstance().getGame().setGameTimeTotal(time);
                    SkillCourtManager.getInstance().getGame().setGameMode(selectedGameMode);
                    Intent intent = new Intent(getActivity(), StartGameActivity.class);
                    startActivity(intent);
                } else {
                    Utils.creatSimpleDialog(getActivity(), "Please fill all the fields").show();
                }
            }
        });

    }

    public boolean isValid() {
        return time != -1 && selectedGameMode != null;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        SkillCourtGame.GameMode gameMode;
        if (which == 0) {
            gameMode = SkillCourtGame.GameMode.BEAT_TIMER;
            btnGameMode.setText("Beat timer");
        } else {
            gameMode = SkillCourtGame.GameMode.HIT_MODE;
            btnGameMode.setText("Hit mode");
        }
        selectedGameMode = gameMode;
    }

}
