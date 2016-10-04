package fiu.com.skillcourt.ui.creategame;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import fiu.com.skillcourt.R;
import fiu.com.skillcourt.ui.base.BaseFragment;
import fiu.com.skillcourt.ui.custom.TimePickerFragment;

public class CreateGameFragment extends BaseFragment implements TimePickerDialog.OnTimeSetListener {

    Button btnTime;

    TimePickerFragment timePickerFragment = new TimePickerFragment();

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
        btnTime.setText(i+":"+i1);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnTime = (Button) view.findViewById(R.id.btn_time);
        timePickerFragment.setOnTimerPickerDialog(CreateGameFragment.this);

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerFragment.show(getChildFragmentManager(), "time_picker");
            }
        });


    }

}
