package catering.businesslogic.task;

import catering.businesslogic.procedure.CookingProcedure;
import catering.businesslogic.shifts.KitchenShift;
import catering.businesslogic.user.User;

import java.time.Duration;

public class Task {
    private Duration timeToComplete;
    private boolean completed;
    private Boolean toPrepare;
    private String amount;
    private String doses;
    private CookingProcedure procedure;
    private User cook;
    private KitchenShift shift;

    private Task initialTask;

    public Task(CookingProcedure procedure, KitchenShift shift, User cook) {
        this.procedure = procedure;
        this.shift = shift;
        this.cook = cook;
    }

    public Task(CookingProcedure procedure, KitchenShift shift) {
        this.procedure = procedure;
        this.shift = shift;
    }

    // Getters and Setters
    public Duration getTimeToComplete() {
        return timeToComplete;
    }

    public void setTimeToComplete(Duration timeToComplete) {
        this.timeToComplete = timeToComplete;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Boolean getToPrepare() {
        return toPrepare;
    }

    public void setToPrepare(Boolean toPrepare) {
        this.toPrepare = toPrepare;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDoses() {
        return doses;
    }

    public void setDoses(String doses) {
        this.doses = doses;
    }

    public CookingProcedure getProcedure() {
        return procedure;
    }

    public void setProcedure(CookingProcedure procedure) {
        this.procedure = procedure;
    }

    public User getCook() {
        return cook;
    }

    public void setCook(User cook) {
        this.cook = cook;
    }

    public KitchenShift getShift() {
        return shift;
    }

    public void setShift(KitchenShift shift) {
        this.shift = shift;
    }

    public Task getInitialTask() {
        return initialTask;
    }

    public void setInitialTask(Task initialTask) {
        this.initialTask = initialTask;
    }
}
