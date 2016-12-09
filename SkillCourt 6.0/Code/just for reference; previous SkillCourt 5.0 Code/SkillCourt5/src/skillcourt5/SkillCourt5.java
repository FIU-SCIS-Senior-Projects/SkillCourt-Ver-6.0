package skillcourt5;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Sean Borland & Gajen Gunasegaram
 */
public class SkillCourt5
{
    static Boolean lock = false; //Set to true in Arduino class by serialEvent(...).
    static Boolean isGreen = false; //Set to t/f in Arduino class by serialEvent(...).
    Statistics stats;
    Start_Menu start_menu;
    int num_of_pads;
    Arduino[] arduino_array;
    
    public void showFinalResults(Statistics stats) throws InterruptedException //***
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
    
    /**
     * @throws InterruptedException added this for all the Thread.sleeps().
     */
    public void newGame() throws InterruptedException 
    {
        stats = new Statistics();
        stats.setTimerRunFalse();
        start_menu = new Start_Menu();
        start_menu.setVisible(true);
        start_menu.setLocationRelativeTo(null);
        
        /*This loops until the Start_Menu "Submit" button is clicked.*/
        while(!start_menu.getNext())
        {
           Thread.sleep(50);
        }
        
        num_of_pads = start_menu.getNumOfPads();
        
        arduino_array = new Arduino[num_of_pads];
        
        /*Initialize the number of arrays entered.*/
        for(int i = 0; i < num_of_pads; i++)
        {
            arduino_array[i] = new Arduino();
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
        
        if(start_menu.getTime() <= 60)
        {
            Statistics.setInitTime(0, start_menu.getTime(), 0);
            Statistics.msec = "00";
            Statistics.sec = "" + start_menu.getTime();
        }
        else
        {
            //TODO: start_menu.time > 60
        } 
 
        Thread t = new Thread(stats);
        t.start();
        
        /*Inifinite loop until the timer runs out.*/
        while(!stats.getTimerRun())
        {
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
            for(int i = 0; i < num_of_pads; i++)
            {
                if(i == rand)
                {
                    arduino_array[i].sendData(0);
                }
                else
                {
                    arduino_array[i].sendData(1);
                }
                
                /*This prevents the pads from changing until one has been hit*/
                if(i == (num_of_pads - 1))
                {                   
                    while(lock == false && !stats.getTimerRun())
                    {
                        Thread.sleep(10);
                    }
                    if(stats.getTimerRun())
                    {
                        break;
                    }
                    if(isGreen)
                    {
                        stats.addGreen(1);
                        stats.addPoint(1);
                        stats.totalHit();
                        stats.greenHit();
                        t.interrupt();
                    }
                    else
                    {
                        stats.addRed(1);
                        stats.subtractPoint(1);
                        stats.totalHit();
                        t.interrupt();
                    }   
                   lock = false;
                }
            }
        }
        
        /*Turn all the LEDs blue signaling that the session has ended.*/
        for(int i = 0; i < num_of_pads; i++)
        {
            arduino_array[i].sendData(2);
        }
        
        stats.stopTimer();//?
        
        for (int i = 0; i < 5; i++)
        {
            Statistics.countdown.setText("00:00:00");//***
            Thread.sleep(500);
            Statistics.countdown.setText(" ");//***
            Thread.sleep(500);
        }
        Statistics.countdown.setText("Times Up!");
        Thread.sleep(1000);//This sleep is just to show the Times up.
        stats.kill();
        
        for(int i = 0; i < num_of_pads; i++)
        {
            arduino_array[i].sendData(3);
            arduino_array[i].close();
        }
        showFinalResults(stats);
    }
    
    @SuppressWarnings("SleepWhileInLoop")
    public static void main(String[] args) throws InterruptedException
    {
        SkillCourt5 sc = new SkillCourt5();
        sc.newGame();
    }
}