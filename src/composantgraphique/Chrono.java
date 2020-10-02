/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package composantgraphique;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Castor
 */
public class Chrono {
    private Timer timer = new Timer();
    private long milli = 0;
    private boolean isStart = false;
    
    public void start(){
        if(isStart) return;
        timer.schedule(new TimerTask(){
            public void run(){
                milli++;
            }
        }, 1L, 1L);
    }
    
    public long getMilliseconde(){
        return milli;
    }
    
}
