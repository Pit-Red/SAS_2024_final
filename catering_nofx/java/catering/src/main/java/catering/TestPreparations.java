package catering;

import catering.businesslogic.procedure.CookingProcedureManager;
import catering.businesslogic.task.KitchenTaskManager;

public class TestPreparations {
    public static void main(String[] args) {
        KitchenTaskManager ktm = new KitchenTaskManager();
        System.out.println(ktm.getAllSummarySheets());
    }
}
