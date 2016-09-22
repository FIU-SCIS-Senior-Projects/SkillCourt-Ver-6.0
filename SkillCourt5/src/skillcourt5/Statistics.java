package skillcourt5;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author Sean Borland & Gajen Gunasegaram
 */
public class Statistics implements Runnable 
{
    private int score;
    private int greenPad;
    private int redPad;
    private volatile boolean isRunning = true;
    private float greenHits;
    private float totalHits = 0;
    public static String msec = "00", sec = "00", min = "00";//***
    
    private static int m, s, ms;
    public static JLabel countdown;//***
    
    private Timer stats_timer;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private static boolean timerRunning = false;
    
    public Statistics() 
    {  
        stats_timer = new Timer(15, new startTimer());     
        score = 0;
        greenPad = 0;
        redPad = 0;
    }
    
    public void setTimerRunTrue()
    {
        timerRunning = true;
    }
    public void setTimerRunFalse()
    {
        timerRunning = false;
    }
    
    public boolean getTimerRun()
    {
        return timerRunning;
    }
    
    public void stopTimer()
    {
        stats_timer.stop();
    }
    
    public static void setInitTime(int mins, int secs, int msecs)
    {
        m = mins;
        s = secs;
        ms = msecs;
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
    
    public void greenHit()//++
    {
        greenHits++;
    }
    
    public void totalHit()
    {
        totalHits++;
    }
    
    public String printResults()
    {
        return "Final score: " + score + "\n" + "Green Pad hits: " + greenPad + "\n" + "Red Pad hits: " + redPad + "\n" + "Accuracy: " + Math.round((greenHits/totalHits) * 100) + "%";
    }
    
    public void kill()
    {
        isRunning = false;
    }
    
    /*Logic for the countdown timer*/
    public class startTimer implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(m > 0)
            {
                if(s == 0)
                {
                    s = 59;
                    sec = "" + s;
                    m--;
                    if(m < 10)
                    {
                        min = "0" + m;
                    }
                    else
                    {
                        min = "" + m;
                    }
                    countdown.setText(min + ":" + sec + ":" + msec);
                }
                else if(s > 0)
                {
                    System.out.println("S1 = " + s);
                    s--;
                    if(s < 10)
                    {
                        sec = "0" + s;
                    }
                    else
                    {
                        sec = "" + s;
                    }
                }
                countdown.setText(min + ":" + sec + ":" + msec);
            }
            else if(s > 0)
            {
                if(ms == 0)
                {
                    ms = 59;
                    msec = "" + ms;
                    s--;
                    if(s < 10)
                    {
                        sec = "0" + s;
                    }
                    else
                    {
                        sec = "" + s;
                    }
                    countdown.setText(min + ":" + sec + ":" + msec);
                }
                else if(ms > 0)
                {
                    ms--;
                    if(ms < 10)
                    {
                        msec = "0" + ms;
                    }
                    else
                    {
                        msec = "" + ms;
                    }
                }
                countdown.setText(min + ":" + sec + ":" + msec);
            }
            else if(m == 0 && s == 0)//I think timer thinks sec are msec
            {
                if(ms > 0)
                {
                    ms--;
                    if(ms < 10)
                    {
                        msec = "0" + ms;
                    }
                    else
                    {
                        msec = "" + ms;
                    }
                }
                countdown.setText(min + ":" + sec + ":" + msec);
                setTimerRunTrue();
                toolkit.beep();
            }
            else if(m == 0 && s == 0 && ms == 0)//This isn't really correct.
            {
                System.out.println("HERE");
                countdown.setText("TIMES UP!");
                setTimerRunTrue();
            }
        }
    }
    
    /*This is what constantly refreshes the Stats screen for instant feedback.*/
    @Override
    public void run()
    {
        JFrame myFrame = new JFrame("Scoreboard");
        myFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);  //FS //++
        //myFrame.setUndecorated(true);                     //FS  //++

        JLabel greenLabel = new JLabel("<html>Hits: " +
                greenPad + "<br>" + "Accuracy: " + "0" + "%"+"</html>");

        greenLabel.setFont(new Font("Serif", Font.BOLD, 175));//250
        myFrame.add(greenLabel, BorderLayout.NORTH);
        
        /*Timer for stats screen*/
        countdown = new JLabel(min + ":" + sec + ":" + msec);
        countdown.setFont(new Font("Serif", Font.BOLD, 175));//175
        myFrame.add(countdown);
        
        myFrame.pack();
        //myFrame.setLocationRelativeTo(null);//Centers stats screen.
        //myFrame.setSize(1650, 800);//For laptop to see stats and console.
        myFrame.setVisible(true);
        stats_timer.start();
        while(isRunning) 
        {          
            try
            {               
                Thread.sleep(500);
            } 
            catch (InterruptedException ex) 
            {
                greenLabel.setText("<html>Hits: " +
                        greenPad + "<br>" + "Accuracy: " + Math.round((greenHits/totalHits) * 100) + "%</html>");
            }           
        }
        myFrame.dispose();
    }
}