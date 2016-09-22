package skillcourt5;

import skillcourt5.interfaces.ArduinoInterface;
import skillcourt5.interfaces.GameInterface;

import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Sean Borland & Gajen Gunasegaram & Pedro Carrillo
 */
public class SkillCourt6 implements GameInterface, ArduinoInterface {

    Start_Menu start_menu;
    private GameMode currentGameMode = GameMode.BEAT_TIMER;
    int num_of_pads;
    Arduino2[] arduino_array;
    Statistics2 stats = Statistics2.getInstance();

    public enum GameMode {
        BEAT_TIMER,
        HIT_PAD
    }

    public void showFinalResults(Statistics2 stats) throws InterruptedException //***
    {
        final Object[] options  =
                {
                        "New Game",
                        "Play again", //Doesn't work.
                        "Quit game"
                };

        int finalOptions = JOptionPane.showOptionDialog(null,
                stats.printResults(),
                "Final Results",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.DEFAULT_OPTION,
                null,
                options,
                options[0]);

        if(finalOptions == 0)
        {
            newGame();
        }
        else if(finalOptions == 1)
        {
            try
            {
                //playAgain();
                stats.playAgain();
            }
            catch (Exception ex)
            {
                Logger.getLogger(SkillCourt5.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(finalOptions == 2)
        {
            System.exit(0);
        }
    }

    public void newGame() throws InterruptedException {
        start_menu = new Start_Menu();
        start_menu.setVisible(true);
        start_menu.setLocationRelativeTo(null);

        /*This loops until the Start_Menu "Submit" button is clicked.*/
        while(!start_menu.getNext())
        {
            Thread.sleep(50);
        }

        start_menu.getNumOfPads();

        num_of_pads = start_menu.getNumOfPads();

        arduino_array = new Arduino2[num_of_pads];

        /*Initialize the number of arrays entered.*/
        for(int i = 0; i < num_of_pads; i++) {
            arduino_array[i] = new Arduino2(this);
            if (arduino_array[i].initialize())
            {
                System.out.println("Program started, preparing to send colors.");
            }
            /*Wait for Arduino to establish connection before connecting to next*/
            Thread.sleep(50);
        }

        /*Best way to have starting lights blink so far..*/
        for(int i = 0; i < num_of_pads; i++)
        {
            arduino_array[i].sendData(4);
        }
        Thread.sleep(4000);//Give the lights time to blink, simulates 3 seconds.

        Statistics2.getInstance().setGameInterface(this);

        stats.setGameTimeTotal(start_menu.getTime());

        stats.startGame();
    }

    @Override
    public void onTimeObjective() {
        int rand = 0;

            /*Adjusts random if 1 pad is selected, anything more is normal.*/
        if(num_of_pads == 1)
        {
            rand = ThreadLocalRandom.current().nextInt(0, 2);
        }
        else
        {
            rand = ThreadLocalRandom.current().nextInt(0, num_of_pads);
        }

            /* Change/Send pad colors and update statitics screen.*/
        for(int i = 0; i < num_of_pads; i++) {
            if (i == rand) {
                arduino_array[i].sendData(0);
            } else {
                arduino_array[i].sendData(1);
            }
        }
    }

    @Override
    public void onFinish() {
        try {
            for (int i = 0; i < num_of_pads; i++) {
                arduino_array[i].sendData(2);
            }

            for (int i = 0; i < 5; i++) {
                stats.getCountdown().setText("00:00");//***
                Thread.sleep(500);
                stats.getCountdown().setText(" ");//***
                Thread.sleep(500);
            }
            stats.getCountdown().setText("Times Up!");
            Thread.sleep(1000);//This sleep is just to show the Times up.

            for (int i = 0; i < num_of_pads; i++) {
                arduino_array[i].sendData(3);
                arduino_array[i].close();
            }
            showFinalResults(stats);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            SkillCourt6 sc = new SkillCourt6();
            sc.newGame();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receivedData(int data) {
        if(stats.isRunning()) {
            if(data == 0) {
                stats.addGreen(1);
                stats.addPoint(1);
                stats.totalHit();
                stats.greenHit();
            }
            else
            {
                stats.addRed(1);
                stats.subtractPoint(1);
                stats.totalHit();
            }
        }
    }

}
