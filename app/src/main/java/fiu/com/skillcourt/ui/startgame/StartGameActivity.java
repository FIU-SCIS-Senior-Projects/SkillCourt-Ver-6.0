package fiu.com.skillcourt.ui.startgame;

import android.os.Bundle;

import fiu.com.skillcourt.R;
import fiu.com.skillcourt.ui.base.BaseActivity;

public class StartGameActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        if (savedInstanceState == null) {
            replaceFragment(StartGameFragment.newInstance(), false);
        }
        setNavigationToolbar();
    }

}
