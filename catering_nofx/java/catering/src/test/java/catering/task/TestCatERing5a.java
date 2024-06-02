package catering.task;

import catering.businesslogic.CatERing;
import catering.businesslogic.errors.UnauthorizedException;
import catering.businesslogic.errors.UseCaseLogicException;
import catering.businesslogic.errors.ItemNotFoundException;
import catering.businesslogic.event.Service;
import catering.businesslogic.task.SummarySheet;
import catering.businesslogic.task.Task;

public class TestCatERing5a {
    public static void main(String[] args) {
        try {
            CatERing.getInstance().getUserManager().fakeLogin("Eva");
            System.out.println("Current user -> " + CatERing.getInstance().getUserManager().getCurrentUser());

            Service service = CatERing.getInstance().getEventManager().getServiceById(1);
            SummarySheet sheet =  CatERing.getInstance().getKitchenTaskMgr().openSummarySheet(service);

            // assigning a cooking procedure in order to create the task
            CatERing.getInstance().getKitchenTaskMgr().assignCookingProcedure(sheet.getListedOrderedProcedures().get(0), null, null);

            Task taskToMarkAsDone = sheet.getTasks().get(0);

            System.out.println(taskToMarkAsDone.getToPrepare());
            System.out.println(!taskToMarkAsDone.isCompleted());

            System.out.println("Task before being marked as done -> " + taskToMarkAsDone);
            CatERing.getInstance().getKitchenTaskMgr().markCookingProcedureAsDone(taskToMarkAsDone);
            System.out.println("Task after being marked as done -> " + taskToMarkAsDone);

            System.out.println(!taskToMarkAsDone.getToPrepare());
            System.out.println(taskToMarkAsDone.isCompleted());
        }
        catch (UnauthorizedException | UseCaseLogicException | ItemNotFoundException e){
            System.out.println("An exception occurred: " + e.getMessage());
        }
    }
}
