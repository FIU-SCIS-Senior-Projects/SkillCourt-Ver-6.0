package fiu.com.skillcourt.ui.creategame;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import java.util.HashMap;

import fiu.com.skillcourt.R;
import fiu.com.skillcourt.game.SkillCourtGame;
import fiu.com.skillcourt.game.SkillCourtManager;
import fiu.com.skillcourt.interfaces.Constants;
import fiu.com.skillcourt.ui.base.ArduinosStartCommunicationFragment;
import fiu.com.skillcourt.ui.base.Utils;
import fiu.com.skillcourt.ui.custom.GameModePickerFragment;
import fiu.com.skillcourt.ui.custom.NumberPickerFragment;
import fiu.com.skillcourt.ui.custom.TimePickerFragment;
import fiu.com.skillcourt.ui.startgame.StartGameActivity;

public class CreateGameFragment extends ArduinosStartCommunicationFragment implements TimePickerDialog.OnTimeSetListener, DialogInterface.OnClickListener, NumberPickerFragment.NumericDialogListener {

    Button btnTime, btnGameMode, btnStartGame, btnTimeObjective;
    LinearLayout modeTimerContainer;
    TimePickerFragment timePickerFragment = new TimePickerFragment();
    GameModePickerFragment gameModePickerFragment = new GameModePickerFragment();
    NumberPickerFragment numberPickerFragment = new NumberPickerFragment();

    private int time = -1;
    private int frequencyTime = -1;
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
        numberPickerFragment.setupNumberPicker(time);
        btnTime.setText(String.format("%02d:%02d",i,+i1));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnTime = (Button) view.findViewById(R.id.btn_time);
        btnGameMode = (Button) view.findViewById(R.id.btn_gameMode);
        btnStartGame = (Button) view.findViewById(R.id.start_game);
        btnTimeObjective = (Button)view.findViewById(R.id.time_objective_button);
        modeTimerContainer = (LinearLayout)view.findViewById(R.id.mode_timer_container);
        timePickerFragment.setOnTimerPickerDialog(CreateGameFragment.this);
        gameModePickerFragment.setOnClickListener(this);
        numberPickerFragment.setOnClickListener(this);

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

        btnTimeObjective.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (time <= 0) {
                    Snackbar.make(getView(), "Please pick a time first", Snackbar.LENGTH_SHORT).show();
                } else {
                    if (frequencyTime != -1) numberPickerFragment.setValue(frequencyTime);
                    numberPickerFragment.show(getChildFragmentManager(), "number_picker");
                }
            }
        });

        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()) {
                    SkillCourtManager.getInstance().getGame().setGameTimeTotal(time);
                    SkillCourtManager.getInstance().getGame().setGameMode(selectedGameMode);
                    if (selectedGameMode == SkillCourtGame.GameMode.BEAT_TIMER) {
                        SkillCourtManager.getInstance().getGame().setTimeObjective(frequencyTime);
                    }
                    Intent intent = new Intent(getActivity(), StartGameActivity.class);
                    intent.putExtra(Constants.TAG_SEQUENCE, new HashMap<String, String>());
                    startActivity(intent);
                    fragmentListener.closeActivity();
                } else {
                    Utils.creatSimpleDialog(getActivity(), "Please fill all the fields").show();
                }
            }
        });

    }

    public boolean isValid() {
        if (selectedGameMode != null) {
            if (selectedGameMode == SkillCourtGame.GameMode.HIT_MODE) {
                return time > 0;
            } else if (selectedGameMode == SkillCourtGame.GameMode.BEAT_TIMER) {
                return time > 1 && frequencyTime > 1;
            } else {
                //for other game modes
                return false;
            }
        } else {
            return  false;
        }
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        SkillCourtGame.GameMode gameMode;
        if (which == 0) {
            gameMode = SkillCourtGame.GameMode.BEAT_TIMER;
            btnGameMode.setText("Beat timer");
            modeTimerContainer.setVisibility(View.VISIBLE);

        } else {
            gameMode = SkillCourtGame.GameMode.HIT_MODE;
            btnGameMode.setText("Hit mode");
            modeTimerContainer.setVisibility(View.GONE);
        }
        selectedGameMode = gameMode;
    }

    @Override
    public void onNumberSelected(int number) {
        btnTimeObjective.setText(String.valueOf(number));
        frequencyTime = number;
    }
}
