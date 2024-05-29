package catering.task;

import catering.businesslogic.CatERing;
import catering.businesslogic.errors.ItemNotFoundException;
import catering.businesslogic.errors.UnauthorizedException;
import catering.businesslogic.errors.UseCaseLogicException;
import catering.businesslogic.event.Service;
import catering.businesslogic.procedure.CookingProcedure;
import catering.businesslogic.task.SummarySheet;

import java.util.ArrayList;

public class TestCatERing3 {
    public static void main(String[] args) {
        try {
            CatERing.getInstance().getUserManager().fakeLogin("Eva");
            System.out.println("Current user -> " + CatERing.getInstance().getUserManager().getCurrentUser());

            Service service = CatERing.getInstance().getEventManager().getServiceById(1);

            System.out.println("Generating new summary sheet starting from -> " + service);
            CatERing.getInstance().getKitchenTaskMgr().generateSummarySheet(service);
            SummarySheet sheet = CatERing.getInstance().getKitchenTaskMgr().getCurrentSummarySheet();
            ArrayList<CookingProcedure> generatedProcedures = sheet.getListedProcedures();

            System.out.println("Before -> " + generatedProcedures);

            CatERing.getInstance().getKitchenTaskMgr().orderSheet(generatedProcedures.get(generatedProcedures.size()-1), 0);

            System.out.println("After -> " + generatedProcedures);


        }catch (UnauthorizedException | UseCaseLogicException | ItemNotFoundException e){
            System.out.println("An exception occurred: " + e.getMessage());
        }
    }
}
