package catering;

import catering.businesslogic.event.Service;
import catering.businesslogic.procedure.CookingProcedureManager;
import catering.businesslogic.shifts.Shift;
import catering.businesslogic.task.KitchenTaskManager;

public class TestPreparations {
    public static void main(String[] args) {
        KitchenTaskManager ktm = new KitchenTaskManager();
        System.out.println(ktm.getAllSummarySheets());
    }
}
