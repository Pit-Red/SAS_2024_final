package catering.task;

import catering.businesslogic.CatERing;
import catering.businesslogic.errors.UnauthorizedException;
import catering.businesslogic.errors.UseCaseLogicException;
import catering.businesslogic.event.Service;
import catering.businesslogic.procedure.OrderedProcedure;
import catering.businesslogic.task.SummarySheet;
import catering.persistence.PersistenceManager;

import java.util.ArrayList;

public class TestCatERing5 {
    public static void main(String[] args){
        try {
            PersistenceManager.executeSqlFile("catering_nofx/database/catering_db_init.sql");

            CatERing.getInstance().getUserManager().fakeLogin("Eva");
            System.out.println("Current user -> " + CatERing.getInstance().getUserManager().getCurrentUser());

            Service service = CatERing.getInstance().getEventManager().getServiceById(1);
            SummarySheet sheet =  CatERing.getInstance().getKitchenTaskMgr().openSummarySheet(service);

            ArrayList<OrderedProcedure> unassignedProcedures = sheet.getListedOrderedProcedures();

            System.out.println("Tasks before -> " + sheet.getTasks());

            CatERing.getInstance().getKitchenTaskMgr().assignCookingProcedure(unassignedProcedures.get(0), null, null);

            System.out.println("Tasks after -> " + sheet.getTasks());
            System.out.println(sheet.isAlreadyAssigned(unassignedProcedures.get(0)));
            System.out.println(sheet.getTasks().get(0).getProcedure().equals(unassignedProcedures.get(0)));
        }
        catch (UnauthorizedException | UseCaseLogicException e){
            System.out.println("An exception occurred: " + e.getMessage());
        }
    }
}
