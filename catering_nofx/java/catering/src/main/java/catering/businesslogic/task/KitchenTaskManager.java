package catering.businesslogic.task;

import catering.businesslogic.procedure.CookingProcedure;
import catering.businesslogic.user.UserManager;

import java.util.ArrayList;

public class KitchenTaskManager {
    private final ArrayList<TaskEventReceiver> eventReceivers;
    private SummarySheet currentSummarySheet;

    public KitchenTaskManager() {
        eventReceivers = new ArrayList<>();
    }

    public SummarySheet generateSummarySheet(ArrayList<CookingProcedure> procedures) {
        return new SummarySheet(procedures);
    }

    public void addEventReceiver(TaskEventReceiver receiver) {
        eventReceivers.add(receiver);
    }
    public void removeEventReceiver(TaskEventReceiver receiver) {
        eventReceivers.remove(receiver);
    }
}
