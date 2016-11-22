package fiu.com.skillcourt.ui.startgame;

/**
 * Created by pedrocarrillo on 9/22/16.
 */

public interface StartGameView {

    void setTimerText(String time);
    void setProgressTotal(int seconds);
    void changeProgressView(long seconds);
    void updateResult(float totalHits, float greenHits, int score, int accuracy);

}
