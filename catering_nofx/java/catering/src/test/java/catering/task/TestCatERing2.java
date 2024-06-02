package catering.task;

import catering.businesslogic.CatERing;
import catering.businesslogic.errors.UnauthorizedException;
import catering.businesslogic.errors.UseCaseLogicException;
import catering.businesslogic.event.Service;
import catering.businesslogic.procedure.CookingProcedure;
import catering.persistence.PersistenceManager;

import java.util.ArrayList;
import java.util.Random;

public class TestCatERing2 {

    // Testing addCookingProcedure function
    public static void main(String[] args) {
        try {
            PersistenceManager.executeSqlFile("database/catering_db_init.sql");

            CatERing.getInstance().getUserManager().fakeLogin("Eva");
            System.out.println("Current user -> " + CatERing.getInstance().getUserManager().getCurrentUser());

            // todo sembra che quando viene caricato un summary sheet già esistente nel db non vengono caricate correttamente tutte le procedure relative
            Service service = CatERing.getInstance().getEventManager().getServiceById(1);

            System.out.println("Opening an already generated summary sheet starting from -> " + service);
            CatERing.getInstance().getKitchenTaskMgr().openSummarySheet(service);

            System.out.println(CatERing.getInstance().getKitchenTaskMgr().getCurrentSummarySheet());

            ArrayList<CookingProcedure> procedures = CatERing.getInstance().getProcedureManager().getProcedures();
            Random random = new Random();
            CookingProcedure randomProcedure = procedures.get(random.nextInt(procedures.size()));
            CatERing.getInstance().getKitchenTaskMgr().addCookingProcedure(randomProcedure);

            System.out.println(CatERing.getInstance().getKitchenTaskMgr().getCurrentSummarySheet());
        }
        catch (UnauthorizedException | UseCaseLogicException e){
            System.out.println("An exception occurred: " + e.getMessage());
        }
    }
}
