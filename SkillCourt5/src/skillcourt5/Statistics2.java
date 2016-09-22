package skillcourt5;

import skillcourt5.interfaces.GameInterface;
import skillcourt5.interfaces.TimerListener;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Sean Borland & Gajen Gunasegaram & Pedro Carrillo
 */

public class Statistics2 implements TimerListener {

    private static Statistics2 self;
    private Timer gameTimer;
    private GameTimerTask gameTimerTask;
    private GameInterface gameInterface;

    private JLabel countdown;
    private JLabel greenLabel;

    private int score;
    private int greenPad;
    private int redPad;
    private boolean isRunning = false;
    private float totalHits = 0;
    private int timeObjective = 3; // IN SECONDS
    private float greenHits;

    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    public static Statistics2 getInstance() {
        if (self == null) {
            self = new Statistics2();
        }
        return self;
    }

    private Statistics2() {
        gameTimerTask = new GameTimerTask(this);
    }

    private void restartGame() {
        isRunning = true;
        score = 0;
        greenPad = 0;
        redPad = 0;
    }

    public int getScore()
    {
        return score;
    }

    public void addPoint(int point)
    {
        score += point;
    }

    public void subtractPoint(int point)
    {
        score -= point;
    }
    public void addGreen(int point)
    {
        greenPad += point;
    }
    public void addRed(int point)
    {
        redPad += point;
    }
    public void addHit(int point)
    {
        totalHits += 1;
    }

    public int getGreen()
    {
        return greenPad;
    }

    public void setGameInterface(GameInterface gameInterface) {
        this.gameInterface = gameInterface;
    }

    public void greenHit()//++
    {
        greenHits++;
    }

    public String printResults()
    {
        return "Final score: " + score + "\n" + "Green Pad hits: " + greenPad + "\n" + "Red Pad hits: " + redPad + "\n" + "Accuracy: " + Math.round((greenHits/totalHits) * 100) + "%";
    }

    public void totalHit()
    {
        totalHits++;
    }

    public JLabel getCountdown() {
        return countdown;
    }

    public void setGameTimeTotal(int seconds) {
        gameTimerTask = new GameTimerTask(seconds, this);
    }

    public boolean isRunning()
    {
        return isRunning;
    }

    public void startGame() {
        showView();
        isRunning = true;
        gameTimer = new Timer();
        gameTimer.scheduleAtFixedRate(gameTimerTask, 0, 1000);
    }

    public void playAgain() {
        restartGame();
        startGame();
    }

    private void showView() {
        // old code

        JFrame myFrame = new JFrame("Scoreboard");
        myFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);  //FS //++
        //myFrame.setUndecorated(true);                     //FS  //++

        greenLabel = new JLabel("<html>Hits: " +
                0 + "<br>" + "Accuracy: " + "0" + "%"+"</html>");

        greenLabel.setFont(new Font("Serif", Font.BOLD, 175));//250
        myFrame.add(greenLabel, BorderLayout.NORTH);

        /*Timer for stats screen*/
        String time = String.format("%d:%02d", 0, 0);
        countdown = new JLabel(time);
        countdown.setFont(new Font("Serif", Font.BOLD, 175));//175
        myFrame.add(countdown);

        myFrame.pack();
        //myFrame.setLocationRelativeTo(null);//Centers stats screen.
        //myFrame.setSize(1650, 800);//For laptop to see stats and console.
        myFrame.setVisible(true);
    }

    public void stopTimer() {
        gameTimer.cancel();
    }

    public class GameTimerTask extends TimerTask {

        private long startTime = System.currentTimeMillis();
        private int limitInSeconds = 10;
        private int seconds = 0;
        private TimerListener timerListener;

        public GameTimerTask(int limitInSeconds, TimerListener timerListener) {
            this.limitInSeconds = limitInSeconds;
            this.timerListener = timerListener;
        }
        public GameTimerTask(TimerListener timerListener) {
            this.timerListener = timerListener;
        }

        @Override
        public void run() {
            if (System.currentTimeMillis() - startTime > limitInSeconds * 1000) {
                timerListener.onStop();
                timerListener.onSecond(seconds);
                cancel();
            } else {
                seconds++;
                timerListener.onSecond(seconds);
            }
        }
    }

    @Override
    public void onSecond(int second) {
        int minutes = second / 60;
        int seconds = second % 60;
        String time = String.format("%d:%02d", minutes, seconds);
        countdown.setText(time);

        if (second  % timeObjective == 0) {
            if (gameInterface != null) {
                gameInterface.onTimeObjective();
            }
            System.out.println("Three more seconds");
        }

        greenLabel.setText("<html>Hits: " +
                greenPad + "<br>" + "Accuracy: " + Math.round((greenHits/totalHits) * 100) + "%</html>");
    }

    @Override
    public void onStop() {
        isRunning = false;
        toolkit.beep();
        countdown.setText("TIMES UP!");
        if (gameInterface != null) {
            gameInterface.onFinish();
        }
    }

}
