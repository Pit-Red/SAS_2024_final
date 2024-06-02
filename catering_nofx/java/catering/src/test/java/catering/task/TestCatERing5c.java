package catering.task;

import catering.businesslogic.CatERing;
import catering.businesslogic.errors.ItemNotFoundException;
import catering.businesslogic.errors.UnauthorizedException;
import catering.businesslogic.errors.UseCaseLogicException;
import catering.businesslogic.event.Service;
import catering.businesslogic.procedure.CookingProcedure;
import catering.businesslogic.procedure.OrderedProcedure;
import catering.businesslogic.task.SummarySheet;
import catering.businesslogic.task.Task;
import catering.persistence.PersistenceManager;

import java.util.ArrayList;
import java.util.Random;

public class TestCatERing5c {
    public static void main(String[] args) {
        try {
            PersistenceManager.executeSqlFile("catering_nofx/database/catering_db_init.sql");

            CatERing.getInstance().getUserManager().fakeLogin("Eva");
            System.out.println("Current user -> " + CatERing.getInstance().getUserManager().getCurrentUser());

            Service service = CatERing.getInstance().getEventManager().getServiceById(1);
            SummarySheet sheet =  CatERing.getInstance().getKitchenTaskMgr().openSummarySheet(service);

            ArrayList<OrderedProcedure> unassignedProcedures = sheet.getListedOrderedProcedures();

            System.out.println("Tasks before adding a new procedure -> " + sheet.getTasks());

            Task task = CatERing.getInstance().getKitchenTaskMgr().assignCookingProcedure(unassignedProcedures.get(0), null, null);

            System.out.println("Tasks after adding a new procedure -> " + sheet.getTasks());
            System.out.println(sheet.isAlreadyAssigned(unassignedProcedures.get(0)));

            CatERing.getInstance().getKitchenTaskMgr().deleteTask(task);

            System.out.println("Tasks after removing the latest task -> " + sheet.getTasks());
        }
        catch (UnauthorizedException | UseCaseLogicException | ItemNotFoundException e){
            System.out.println("An exception occurred: " + e.getMessage());
        }
    }
}
