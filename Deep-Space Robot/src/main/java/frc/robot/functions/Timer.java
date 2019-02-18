package frc.robot.functions;

import edu.wpi.first.wpilibj.RobotController;

public class Timer {
    public static void delay(final double seconds){
        try {
            Thread.sleep((long) (seconds * 1e3));
        } catch (final InterruptedException ex){
            Thread.currentThread().interrupt();
        }
    }

    private double  m_startTime;
    private double  m_accumulatedTime;
    private boolean m_running;

    public Timer(){
        reset();
    }

    private double getMsClock() {
        return RobotController.getFPGATime() / 1000.0;
    }

    public synchronized double get() {
        if(m_running){
            return m_accumulatedTime + (getMsClock() - m_startTime) / 1000.0;
        } else {
            return m_accumulatedTime;
        }
    }

    public synchronized void reset(){
        m_accumulatedTime = 0;
        m_startTime = getMsClock();
    }

    public synchronized void start() {
        m_startTime = getMsClock();
        m_running = true;
    }

    public synchronized void stop() {
        final double temp = get();
        m_accumulatedTime = temp;
        m_running = false;
    }

    public synchronized boolean hasPeriodPassed(double period){
        if(get() > period){
            m_startTime += period * 1000;
            return true;
        }

        return false;
    }
}