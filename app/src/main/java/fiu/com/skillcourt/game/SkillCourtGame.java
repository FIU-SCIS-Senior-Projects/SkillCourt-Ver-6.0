package fiu.com.skillcourt.game;

import android.util.Log;

import fiu.com.skillcourt.interfaces.CountdownInterface;
import fiu.com.skillcourt.interfaces.SkillCourtInteractor;

/**
 * Created by pedrocarrillo on 9/22/16.
 */

public class SkillCourtGame implements CountdownInterface {

    private int score;
    private int greenPad;
    private int redPad;
    private boolean isRunning = false;
    private float totalHits = 0;
    private int timeObjective = 3; // IN SECONDS
    private float greenHits;
    private int gameTimeTotal = 10;

    private GameMode gameMode = GameMode.BEAT_TIMER;

    public enum GameMode {
        BEAT_TIMER,
        HIT_MODE
    }

    private CountDownTimer countDownTimer;
    private SkillCourtInteractor skillCourtInteractor;

    public int getScore() {
        return score;
    }

    public void addPoint(int point) {
        score += point;
    }

    public void subtractPoint(int point) {
        score -= point;
    }

    public void addGreen(int point) {
        greenPad += point;
    }

    public void addRed(int point) {
        redPad += point;
    }

    public void addHit(int point) {
        totalHits += 1;
    }

    public int getGreen() {
        return greenPad;
    }

    public void greenHit() {
        greenHits++;
    }

    public void totalHit() {
        totalHits++;
    }

    public void setGameTimeTotal(int seconds) {
        gameTimeTotal = seconds;
    }

    public int getGameTimeTotal() {
        return gameTimeTotal;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void startGame() {
        isRunning = true;
        countDownTimer = new CountDownTimer(gameTimeTotal * 1000, 1, this);
        countDownTimer.start();
    }

    private void restartGame() {
        score = 0;
        greenPad = 0;
        redPad = 0;
        totalHits = 0;
        greenHits = 0;
        startGame();
    }

    @Override
    public void onTick(long millisUntilFinished) {
        int second = Math.round((float)millisUntilFinished / 1000.0f);
        long minutes = (second / 60);
        long seconds = second % 60;
        String time = String.format("%02d:%02d", minutes, seconds);
        skillCourtInteractor.onSecond(time, second);
        skillCourtInteractor.onMillisecond(Math.round((float)millisUntilFinished));
    }

    @Override
    public void onFinish() {
        skillCourtInteractor.onFinish();
    }

    public SkillCourtGame() {

    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void setSkillCourtInteractor(SkillCourtInteractor skillCourtInteractor) {
        this.skillCourtInteractor = skillCourtInteractor;
    }

    public SkillCourtGame(SkillCourtInteractor skillCourtInteractor) {
        this.skillCourtInteractor = skillCourtInteractor;
    }

}
