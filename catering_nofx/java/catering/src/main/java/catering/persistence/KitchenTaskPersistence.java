package catering.persistence;

import catering.businesslogic.procedure.OrderedProcedure;
import catering.businesslogic.task.SummarySheet;
import catering.businesslogic.task.Task;
import catering.businesslogic.task.TaskEventReceiver;

public class KitchenTaskPersistence implements TaskEventReceiver {
    @Override
    public void updateSummarySheetCreated(SummarySheet sheet) {
        for (OrderedProcedure procedure : sheet.getListedOrderedProcedures()) {
            String procedureQuery = "INSERT INTO ListedProcedures (summary_sheet_id, procedure_id, position) VALUES ("
                    + sheet.getId() + ", "
                    + procedure.getId() + ", "
                    + procedure.getPosition() + ")";
            PersistenceManager.executeUpdate(procedureQuery);
        }
    }

    @Override
    public void updateCookingProcedureAdded(SummarySheet sheet, OrderedProcedure p) {
        String procedureQuery = "INSERT INTO ListedProcedures (summary_sheet_id, procedure_id, position) VALUES ("
                + sheet.getId() + ", "
                + p.getId() + ", "
                + p.getPosition() + ")";

        PersistenceManager.executeUpdate(procedureQuery);
    }

    @Override
    public void updateOrderedProcedurePosition(SummarySheet sheet, OrderedProcedure p) {
        String procedureQuery = "UPDATE ListedProcedures SET position = " + p.getPosition() +
                " WHERE summary_sheet_id = " + sheet.getId() +
                " AND procedure_id = " + p.getId();
        PersistenceManager.executeUpdate(procedureQuery);
    }

    @Override
    public void updateTaskCreated(SummarySheet sheet, Task t) {

    }

    @Override
    public void updateTaskUpdated(SummarySheet sheet, Task t) {

    }

    @Override
    public void updateTaskDeleted(SummarySheet sheet, Task t) {

    }
}
