
/*******************************************************************************
 * Note: So, I used the JFrame form builder GUI for this and just copied and   *
 * paste all of its code into this class because when using the GUI, this code *
 * can't be edited. One user story can be to completely remake this class in a *
 * much simpler way and so it's easily extensible.                             *
 * ****************************************************************************/

package skillcourt5;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

/**
 *
 * @author Sean Borland & Gajen Gunasegaram
 */
public class Start_Menu extends JFrame
{
    private int time;
    private int numOfPads;
    private boolean next = false;
    
    private final ButtonGroup buttonGroup1;
    private final ButtonGroup buttonGroup2;
    private final JLabel errorMsg;
    private final JLabel gameTimeJL;
    private final JLabel numOfPadsJL;
    private final JButton submitJB;
    private final JRadioButton tenSecJRB;
    private final JRadioButton thirtySecJRB;
    private final JRadioButton sixtySecJRB;
    private final JRadioButton onePadJRB;
    private final JRadioButton twoPadJRB;
    private final JRadioButton threePadJRB;
    private final JRadioButton fourPadJRB;
    private final JRadioButton fivePadJRB;
    
    public Start_Menu()
    {
        buttonGroup1 = new ButtonGroup();
        buttonGroup2 = new ButtonGroup();
        errorMsg = new JLabel();
        gameTimeJL = new JLabel();
        numOfPadsJL = new JLabel();
        tenSecJRB = new JRadioButton();
        thirtySecJRB = new JRadioButton();
        sixtySecJRB = new JRadioButton();
        onePadJRB = new JRadioButton();
        twoPadJRB = new JRadioButton();
        threePadJRB = new JRadioButton();
        fourPadJRB = new JRadioButton();
        fivePadJRB = new JRadioButton();
        submitJB = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);

        gameTimeJL.setFont(new Font("sansserif", 1, 18));
        gameTimeJL.setText("Please select a game duration:");

        buttonGroup2.add(tenSecJRB);
        tenSecJRB.setFont(new Font("sansserif", 1, 12));
        tenSecJRB.setText("10 Sec");
        tenSecJRB.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent evt)
            {
                tenSecJRBActionPerformed(evt);
            }
        });

        buttonGroup2.add(thirtySecJRB);
        thirtySecJRB.setFont(new Font("sansserif", 1, 12));
        thirtySecJRB.setText("30 Sec");
        thirtySecJRB.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent evt)
            {
                thirtySecJRBActionPerformed(evt);
            }
        });

        buttonGroup2.add(sixtySecJRB);
        sixtySecJRB.setFont(new Font("sansserif", 1, 12));
        sixtySecJRB.setText("60 Sec");
        sixtySecJRB.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent evt)
            {
                sixtySecJRBActionPerformed(evt);
            }
        });

        numOfPadsJL.setFont(new Font("sansserif", 1, 18));
        numOfPadsJL.setText("Please select the number of pads:");

        buttonGroup1.add(onePadJRB);
        onePadJRB.setFont(new Font("sansserif", 1, 12));
        onePadJRB.setText("1");
        onePadJRB.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent evt)
            {
                onePadJRBActionPerformed(evt);
            }
        });

        buttonGroup1.add(twoPadJRB);
        twoPadJRB.setFont(new Font("sansserif", 1, 12));
        twoPadJRB.setText("2");
        twoPadJRB.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent evt)
            {
                twoPadJRBActionPerformed(evt);
            }
        });

        buttonGroup1.add(threePadJRB);
        threePadJRB.setFont(new Font("sansserif", 1, 12));
        threePadJRB.setText("3");
        threePadJRB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                threePadJRBActionPerformed(evt);
            }
        });

       

        buttonGroup1.add(fourPadJRB);
        fourPadJRB.setText("4");
        fourPadJRB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                fourPadJRBActionPerformed(evt);
            }
        });

        buttonGroup1.add(fivePadJRB);
        fivePadJRB.setText("5");
        fivePadJRB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                fivePadJRBActionPerformed(evt);
            }
        });
        
        submitJB.setFont(new Font("sansserif", 1, 18));
        submitJB.setText("Submit");
        submitJB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                submitJBActionPerformed(evt);
            }
        });

        errorMsg.setForeground(new Color(255, 0, 51));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 32, Short.MAX_VALUE)
                .addComponent(errorMsg, GroupLayout.PREFERRED_SIZE, 346, GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(gameTimeJL)
                            .addComponent(numOfPadsJL)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(tenSecJRB)
                        .addGap(18, 18, 18)
                        .addComponent(thirtySecJRB)
                        .addGap(18, 18, 18)
                        .addComponent(sixtySecJRB))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(submitJB, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(onePadJRB)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(twoPadJRB)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(threePadJRB)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fourPadJRB)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fivePadJRB)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(numOfPadsJL)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(onePadJRB)
                    .addComponent(twoPadJRB)
                    .addComponent(threePadJRB)
                    .addComponent(fourPadJRB)
                    .addComponent(fivePadJRB))
                .addGap(46, 46, 46)
                .addComponent(gameTimeJL)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(tenSecJRB)
                    .addComponent(thirtySecJRB)
                    .addComponent(sixtySecJRB))
                .addGap(37, 37, 37)
                .addComponent(submitJB, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(errorMsg)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }

    /*All the functions below give functionality to the buttons and inits vars*/
    
    private void submitJBActionPerformed(ActionEvent evt)                                         
    {                                             
        if(time == 0 || numOfPads == 0)
        {
            errorMsg.setForeground(new Color(255, 0, 51));
            errorMsg.setText("ERROR: Must select number of pads AND game duration.");
        }
        else
        {
            next = true;
            dispose();
        }
    }
    
    /*Sets the timer variable based on radio button selected.*/
    private void tenSecJRBActionPerformed(ActionEvent evt)                                          
    {                                              
       time = 10;
    }                                         

    private void thirtySecJRBActionPerformed(ActionEvent evt)                                             
    {                                                 
        time = 30;
    }                                            

    private void sixtySecJRBActionPerformed(ActionEvent evt)                                            
    {                                                
        time = 60;
    }
    
    /*Sets the Number of pads variable based on radio button selected.*/
    private void onePadJRBActionPerformed(ActionEvent evt)                                          
    {                                              
        numOfPads = 1;
    }                                         
    
    private void twoPadJRBActionPerformed(ActionEvent evt)                                          
    {                                              
        numOfPads = 2;
    }                                         

    private void threePadJRBActionPerformed(ActionEvent evt)                                            
    {                                                
        numOfPads = 3;
    }
    
    private void fourPadJRBActionPerformed(ActionEvent evt)                                           
    {                                               
        numOfPads = 4;
    }                                          

    private void fivePadJRBActionPerformed(ActionEvent evt)                                           
    {                                               
        numOfPads = 5;
    }   
    
    public int getTime()
    {
        return time;
    }
    
    public int getNumOfPads()
    {
        return numOfPads;
    }
    
    public boolean getNext()
    {
        return next;
    }
}