package fiu.com.skillcourt.ui.startgame;

import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

import fiu.com.skillcourt.R;
import fiu.com.skillcourt.interfaces.Constants;
import fiu.com.skillcourt.ui.base.BaseActivity;

public class StartGameActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        if (savedInstanceState == null) {
            HashMap<String, String> sequences = null;
            if (getIntent().hasExtra(Constants.TAG_SEQUENCE)) sequences = (HashMap<String, String>)getIntent().getSerializableExtra(Constants.TAG_SEQUENCE);
            replaceFragment(StartGameFragment.newInstance(sequences), false);
        }
        setNavigationToolbar();
    }

}
