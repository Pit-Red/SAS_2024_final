package catering.task;

import catering.businesslogic.CatERing;
import catering.businesslogic.errors.UnauthorizedException;
import catering.businesslogic.errors.UseCaseLogicException;
import catering.businesslogic.event.Service;
import catering.businesslogic.procedure.CookingProcedure;
import catering.businesslogic.task.SummarySheet;
import catering.persistence.PersistenceManager;

import java.util.ArrayList;

public class TestCatERing1a {
    public static void main(String[] args) {
        try {
            PersistenceManager.executeSqlFile("database/catering_db_init.sql");

            CatERing.getInstance().getUserManager().fakeLogin("Eva");
            System.out.println("Current user -> " + CatERing.getInstance().getUserManager().getCurrentUser());

            Service service = CatERing.getInstance().getEventManager().getServiceById(1);

            CatERing.getInstance().getKitchenTaskMgr().openSummarySheet(service);

            SummarySheet summarySheet = SummarySheet.loadById(1);

            System.out.println(summarySheet.equals(CatERing.getInstance().getKitchenTaskMgr().getCurrentSummarySheet()));
        } catch (UnauthorizedException e) {
            System.out.println("An exception occurred: " + e.getMessage());
        }
    }
}
