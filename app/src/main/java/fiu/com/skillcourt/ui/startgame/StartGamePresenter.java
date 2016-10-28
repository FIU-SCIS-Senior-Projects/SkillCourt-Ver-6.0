package fiu.com.skillcourt.ui.startgame;


import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import fiu.com.skillcourt.connection.Arduino;
import fiu.com.skillcourt.connection.ArduinoManager;
import fiu.com.skillcourt.game.SkillCourtGame;
import fiu.com.skillcourt.game.SkillCourtManager;
import fiu.com.skillcourt.interfaces.ArduinoSkillCourtInteractor;
import fiu.com.skillcourt.interfaces.SkillCourtInteractor;

/**
 * Created by pedrocarrillo on 9/22/16.
 */

public class StartGamePresenter implements SkillCourtInteractor, ArduinoSkillCourtInteractor {

    private StartGameView view;
    private SkillCourtGame skillCourtGame;
    private ArduinoManager arduinoManager;

    private List<Integer> randomNumbers = new ArrayList<>();

    public StartGamePresenter(StartGameView view) {
        this.view = view;
        skillCourtGame = SkillCourtManager.getInstance().getGame();
        skillCourtGame.setSkillCourtInteractor(this);
        arduinoManager = ArduinoManager.getInstance();
        arduinoManager.setArduinoSkillCourtInteractor(this);
        randomNumbers.add(1);
        if (arduinoManager.getArduinos().size() > 1) {
            for (int i = 1; i < arduinoManager.getArduinos().size(); i++) {
                randomNumbers.add(0);
            }
        } else {
            randomNumbers.add(0);
        }
    }

    public void startGame() {
        skillCourtGame.startGame();
        updateArduinosStatus();
        view.setProgressTotal(skillCourtGame.getGameTimeTotal() * 1000);
    }

    @Override
    public void onSecond(String time, long seconds) {
        view.setTimerText(time);
    }

    @Override
    public void onMillisecond(long milliseconds) {
        view.changeProgressView(milliseconds);
    }

    @Override
    public void onTimeObjective() {

    }

    @Override
    public void onMessageReceived(Arduino.TYPE_LIGHT currentStatus, String message) {
        if(skillCourtGame.getGameMode() == SkillCourtGame.GameMode.HIT_MODE && skillCourtGame.isRunning()) {
            if (currentStatus == Arduino.TYPE_LIGHT.GREEN) {
                SkillCourtManager.getInstance().addGreenPoint();
            } else {
                SkillCourtManager.getInstance().addRedPoint();
            }
            Log.e("points", " greenhits " + SkillCourtManager.getInstance().getGame().getGreenHits());
            Log.e("points", " score " + SkillCourtManager.getInstance().getGame().getScore());
            Log.e("points", "acc "+ SkillCourtManager.getInstance().getGame().getAccuracy());
            SkillCourtGame sk = SkillCourtManager.getInstance().getGame();
            if (view != null) {
                view.updateResult(sk.getTotalHits(), sk.getGreenHits(), sk.getScore(), sk.getAccuracy());
            }
            updateArduinosStatus();
        }
    }

    private void updateArduinosStatus() {
        Collections.shuffle(randomNumbers);
        if (arduinoManager.getArduinos().size() > 1) {
            for (int i = 0; i < randomNumbers.size(); i++) {
                Arduino.TYPE_LIGHT type = randomNumbers.get(i) == 0 ? Arduino.TYPE_LIGHT.RED : Arduino.TYPE_LIGHT.GREEN;
                arduinoManager.getArduinos().get(i).setStatus(type);
            }
        } else {
            Arduino.TYPE_LIGHT type = randomNumbers.get(0) == 0 ? Arduino.TYPE_LIGHT.RED : Arduino.TYPE_LIGHT.GREEN;
            arduinoManager.getArduinos().get(0).setStatus(type);
        }
    }

    public void onPause() {
        view = null;
        skillCourtGame.pause();
    }

    public void onStop() {
        view = null;
        skillCourtGame.pause();
    }

    public void onResume(StartGameView view) {
        this.view = view;
        skillCourtGame.resume();
    }

    @Override
    public void onFinish() {
        for (int i = 0; i < randomNumbers.size(); i++) {
            arduinoManager.getArduinos().get(i).setStatus(Arduino.TYPE_LIGHT.FINISH);
        }
        view.setTimerText("TIME's up!");
    }

    public void cancelGame() {
        skillCourtGame.cancelGame();
    }
}
