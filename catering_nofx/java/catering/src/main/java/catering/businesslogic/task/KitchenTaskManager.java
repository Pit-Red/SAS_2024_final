package catering.businesslogic.task;

import catering.businesslogic.procedure.CookingProcedure;
import catering.businesslogic.user.UserManager;

import java.util.ArrayList;

public class KitchenTaskManager {
    private final ArrayList<TaskEventReceiver> eventReceivers;
    private SummarySheet currentSummarySheet;

    public KitchenTaskManager() {
        SummarySheet.loadAllSummarySheet();
        eventReceivers = new ArrayList<>();
    }

    public SummarySheet generateSummarySheet(ArrayList<CookingProcedure> procedures) {
        return new SummarySheet(0, procedures);
    }

    public void addEventReceiver(TaskEventReceiver receiver) {
        eventReceivers.add(receiver);
    }
    public void removeEventReceiver(TaskEventReceiver receiver) {
        eventReceivers.remove(receiver);
    }

    public ArrayList<SummarySheet> getAllSummarySheets(){
        return SummarySheet.getAllSummarySheets();
    }

}
