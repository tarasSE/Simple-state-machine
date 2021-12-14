package simple.state.machine;

import static simple.state.machine.RunnerState.*;

public class Runner {
    private final String name;
    private final int speed;
    private final int recoveryRate;
    private final int fatigueRate;
    private int distance;
    private int endurance;
    
    private RunnerState currentState;


    public Runner(String name, int distance, int speed, int endurance, int recoveryRate, int fatigueRate) {
        this.name = name;
        this.distance = distance;
        this.speed = speed;
        this.endurance = endurance;
        this.recoveryRate = recoveryRate;
        this.fatigueRate = fatigueRate;
        this.currentState = PREPARING;
    }

    public void run() {
        transitionTo(RUNNING);
        distance -= speed;
        endurance -= fatigueRate;
        if (endurance < 0) leave();
        if (distance <= 0) finish();
    }

    public void stop() {
        transitionTo(STOPPED);
        endurance += recoveryRate;

    }

    private void finish() {
        transitionTo(FINISHED);
        distance = 0;
    }

    public void leave() {
        transitionTo(LEFT_THE_RACE);
    }

    private void transitionTo(RunnerState newState) {
        if (currentState.canTransitionTo(newState)) {
            currentState = newState;
            return;
        }

        throw new IllegalStateException("Don't cheat :) ");
    }

    public RunnerState getState() {
        return currentState;
    }

    @Override
    public String toString() {
        return "{ name:'" + name + '\'' +
                ", distance:" + distance +
                ", speed:" + speed +
                ", endurance:" + endurance +
                ", recoveryRate:" + recoveryRate +
                ", fatigueRate:" + fatigueRate +
                ", currentState:" + currentState + " }";
    }
}
