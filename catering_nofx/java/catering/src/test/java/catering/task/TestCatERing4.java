package catering.task;

import catering.businesslogic.CatERing;
import catering.businesslogic.errors.UnauthorizedException;
import catering.businesslogic.event.Service;
import catering.businesslogic.task.SummarySheet;

public class TestCatERing4 {
    public static void main(String[] args) {
        try {
            CatERing.getInstance().getUserManager().fakeLogin("Eva");
            System.out.println("Current user -> " + CatERing.getInstance().getUserManager().getCurrentUser());

            Service service = CatERing.getInstance().getEventManager().getServiceById(1);

            CatERing.getInstance().getKitchenTaskMgr().openSummarySheet(service);

            // todo
        } catch (UnauthorizedException e) {
            System.out.println("An exception occurred: " + e.getMessage());
        }
    }
}
