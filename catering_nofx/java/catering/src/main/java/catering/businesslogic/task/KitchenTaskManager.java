package catering.businesslogic.task;

import catering.businesslogic.CatERing;
import catering.businesslogic.errors.ItemNotFoundException;
import catering.businesslogic.errors.UnauthorizedException;
import catering.businesslogic.errors.UseCaseLogicException;
import catering.businesslogic.event.Service;
import catering.businesslogic.procedure.CookingProcedure;
import catering.businesslogic.procedure.OrderedProcedure;
import catering.businesslogic.user.User;

import java.util.ArrayList;

public class KitchenTaskManager {
    private final ArrayList<TaskEventReceiver> eventReceivers;
    private SummarySheet currentSummarySheet;
    private Service currentService;

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
        this.currentService = service;
        this.currentSummarySheet = sheet;

        this.notifySummarySheetCreated(sheet);

        return sheet;
    }

    public SummarySheet openSummarySheet(Service service) throws UnauthorizedException {
        checkUser();

        this.currentService = service;
        this.currentSummarySheet = service.getSummarySheet();

        return this.currentSummarySheet;
    }

    public void addCookingProcedure(CookingProcedure procedure) throws UnauthorizedException, UseCaseLogicException {
        checkUser();

        if (this.currentSummarySheet == null) throw new UseCaseLogicException("No Summary Sheet specified");

        OrderedProcedure newProcedure = this.currentSummarySheet.addProcedure(procedure);

        this.notifyCookingProcedureAdded(newProcedure);
    }

    public void orderSheet(CookingProcedure procedure, int position) throws UnauthorizedException, UseCaseLogicException, ItemNotFoundException {
        checkUser();

        if (this.currentSummarySheet == null) throw new UseCaseLogicException("No Summary Sheet specified");

        if (!this.currentSummarySheet.containsProcedure(procedure))
            throw new ItemNotFoundException("The selected procedure is not present in the " +
                    "listed procedures of the selected summary sheet");
        this.currentSummarySheet.orderProcedure(procedure, position);
    }

    private void checkUser() throws UnauthorizedException {
        User u = CatERing.getInstance().getUserManager().getCurrentUser();
        if (u == null || !u.isChef()) throw new UnauthorizedException("User must be authenticated as Chef");
        if (this.currentService != null && !this.currentService.isChefAssigned(u))
            throw new UnauthorizedException("The selected service is not assigned to: " + u + ", therefore is impossible to proceed");
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

    public Service getCurrentService() {
        return currentService;
    }

    private void notifySummarySheetCreated(SummarySheet sheet) {
        for (TaskEventReceiver er : this.eventReceivers) {
            er.updateSummarySheetCreated(sheet);
        }
    }

    private void notifyCookingProcedureAdded(OrderedProcedure procedure) {
        for (TaskEventReceiver er : this.eventReceivers) {
            er.updateCookingProcedureAdded(currentSummarySheet, procedure);
        }
    }
}
