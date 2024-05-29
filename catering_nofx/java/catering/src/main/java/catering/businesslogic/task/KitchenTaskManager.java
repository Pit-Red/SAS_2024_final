package catering.businesslogic.task;

import catering.businesslogic.CatERing;
import catering.businesslogic.errors.UnauthorizedException;
import catering.businesslogic.errors.UseCaseLogicException;
import catering.businesslogic.event.Service;
import catering.businesslogic.user.User;

import java.util.ArrayList;

public class KitchenTaskManager {
    private final ArrayList<TaskEventReceiver> eventReceivers;
    private SummarySheet currentSummarySheet;

    public KitchenTaskManager() {
        SummarySheet.loadAllSummarySheets();
        eventReceivers = new ArrayList<>();
    }

    public SummarySheet generateSummarySheet(Service service) throws UnauthorizedException, UseCaseLogicException {
        // preliminary checks
        User u = CatERing.getInstance().getUserManager().getCurrentUser();
        if (u == null || !u.isChef()) throw new UnauthorizedException("User must be authenticated as Chef");
        if (!service.isChefAssigned(u))
            throw new UnauthorizedException("Chef should be assigned to the service for which the summary sheet shall be created");
        if (service.getUsedMenu() == null) throw new UseCaseLogicException("Specified service must have a menu");

        SummarySheet sheet = new SummarySheet(service.getUsedMenu().getAllRecipes());
        this.currentSummarySheet = sheet;

        return sheet;
    }

    public void addEventReceiver(TaskEventReceiver receiver) {
        eventReceivers.add(receiver);
    }

    public void removeEventReceiver(TaskEventReceiver receiver) {
        eventReceivers.remove(receiver);
    }

    public ArrayList<SummarySheet> getAllSummarySheets() {
        return SummarySheet.getAllSummarySheets();
    }

    public SummarySheet getCurrentSummarySheet() {
        return currentSummarySheet;
    }
}
