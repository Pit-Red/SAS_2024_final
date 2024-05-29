package catering.task;

import catering.businesslogic.CatERing;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.Service;
import catering.businesslogic.procedure.CookingProcedure;
import catering.businesslogic.procedure.Recipe;
import catering.businesslogic.task.SummarySheet;

import java.util.ArrayList;

public class TestCatERing1 {
    public static void main(String[] args) {
        try {
            CatERing.getInstance().getUserManager().fakeLogin("Eva");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());

            ArrayList<EventInfo> eventInfos = CatERing.getInstance().getEventManager().getEventInfo();
            Service service = null;

            for (EventInfo eventInfo : eventInfos){
                for (Service s: eventInfo.getServices()){
                    if (s.getId() == 1){
                        service = s;
                        break; // Testing only :P !!!!
                        // Trust me I don't always code like this
                        // I am better than this
                    }
                }
            }

            ArrayList<CookingProcedure> expectedProcedure = new ArrayList<>(service.getUsedMenu().getAllRecipes());

            CatERing.getInstance().getKitchenTaskMgr().generateSummarySheet(service);
            SummarySheet sheet = CatERing.getInstance().getKitchenTaskMgr().getCurrentSummarySheet();
            ArrayList<CookingProcedure> generatedProcedures = sheet.getListedProcedures();

            // testing all recipes in the menu are added corretcly to the summary sheet
            for (CookingProcedure procedure : expectedProcedure){
                System.out.println(generatedProcedures.contains(procedure));
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
