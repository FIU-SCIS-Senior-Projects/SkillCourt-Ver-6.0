package fiu.com.skillcourt.ui.startgame;

import fiu.com.skillcourt.game.SkillCourtGame;
import fiu.com.skillcourt.game.SkillCourtManager;
import fiu.com.skillcourt.interfaces.SkillCourtInteractor;

/**
 * Created by pedrocarrillo on 9/22/16.
 */

public class StartGamePresenter implements SkillCourtInteractor {

    private StartGameView view;
    private SkillCourtGame skillCourtGame;

    public StartGamePresenter(StartGameView view) {
        this.view = view;
        skillCourtGame = SkillCourtManager.getInstance().getGame();
        skillCourtGame.setSkillCourtInteractor(this);
    }

    public void startGame() {
        skillCourtGame.startGame();
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
    public void onFinish() {
        view.setTimerText("TIME's up!");
    }
}
