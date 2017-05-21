/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author James-dt
 */
public abstract class SingleTask extends TimerTask {

    private Timer myTimer;

    public SingleTask(long delay) {
        this.myTimer = new Timer();
        this.myTimer.schedule(this, delay);
    }

    public boolean cancel() {
        boolean result = super.cancel();
        this.myTimer.cancel();
        return result;
    }

    public void run() {
        this.runSingleTask();
        this.cancel();
    }

    public abstract void runSingleTask();
 }