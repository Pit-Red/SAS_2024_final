package catering.businesslogic.task;

import catering.businesslogic.CatERing;
import catering.businesslogic.errors.ItemNotFoundException;
import catering.businesslogic.errors.UnauthorizedException;
import catering.businesslogic.errors.UseCaseLogicException;
import catering.businesslogic.event.Service;
import catering.businesslogic.procedure.CookingProcedure;
import catering.businesslogic.procedure.OrderedProcedure;
import catering.businesslogic.shifts.KitchenShift;
import catering.businesslogic.shifts.Shift;
import catering.businesslogic.shifts.ShiftManager;
import catering.businesslogic.user.User;

import java.util.ArrayList;

public class KitchenTaskManager {
    private final ArrayList<TaskEventReceiver> eventReceivers;
    private SummarySheet currentSummarySheet;
    private Service currentService;
    private Task currentWorkingTask;

    public KitchenTaskManager() {
        SummarySheet.loadAllSummarySheets();
        eventReceivers = new ArrayList<>();
    }

    /**
     * DCD 1
     */
    public SummarySheet generateSummarySheet(Service service) throws UnauthorizedException, UseCaseLogicException {
        // preliminary checks
        checkUser();
        if (service.getUsedMenu() == null) throw new UseCaseLogicException("Specified service must have a menu");

        SummarySheet sheet = new SummarySheet(service.getUsedMenu().getAllRecipes());
        this.currentService = service;
        this.currentSummarySheet = sheet;

        this.notifySummarySheetCreated(sheet);

        return sheet;
    }

    /**
     * DCD 1a
     */
    public SummarySheet openSummarySheet(Service service) throws UnauthorizedException {
        checkUser();

        this.currentService = service;
        this.currentSummarySheet = service.getSummarySheet();

        return this.currentSummarySheet;
    }

    /**
     * DCD 2
     */
    public void addCookingProcedure(CookingProcedure procedure) throws UnauthorizedException, UseCaseLogicException {
        checkUser();

        if (this.currentSummarySheet == null) throw new UseCaseLogicException("No Summary Sheet specified");

        OrderedProcedure newProcedure = this.currentSummarySheet.addProcedure(procedure);

        this.notifyCookingProcedureAdded(newProcedure);
    }

    /**
     * DCD 3
     */
    public void orderSheet(CookingProcedure procedure, int position) throws UnauthorizedException, UseCaseLogicException, ItemNotFoundException {
        checkUser();

        if (this.currentSummarySheet == null) throw new UseCaseLogicException("No Summary Sheet specified");

        if (!this.currentSummarySheet.containsProcedure(procedure))
            throw new ItemNotFoundException("The selected procedure is not present in the " +
                    "selected summary sheet's listed procedures");

        OrderedProcedure newProcedure = this.currentSummarySheet.orderProcedure(procedure, position);
        this.notifyOrderedProcedureUpdated(newProcedure);
    }

    //TODO nel DSD questa funzione ha come input il service, ma considerando che unsiamo currentService non dovrebbe servire

    /**
     * DCD 4
     */
    public ArrayList<Shift> checkShiftBoard() throws UnauthorizedException {
        checkUser();

        return null;
    }

    /**
     * DCD 5
     *
     * @param procedure
     * @param shift
     * @param cook
     * @return
     * @throws UnauthorizedException
     * @throws UseCaseLogicException
     */
    public Task assignCookingProcedure(CookingProcedure procedure, KitchenShift shift, User cook) throws UnauthorizedException, UseCaseLogicException {
        checkUser();

        if (this.currentSummarySheet == null) throw new UseCaseLogicException("No Summary Sheet specified");

        if (cook != null) {
            boolean cookAvailable = CatERing.getInstance().getShiftMgr().isAvailable(cook, shift);
            if (!cookAvailable)
                throw new UseCaseLogicException(cook + " is not available");
            if (!cook.isCook())
                throw new UseCaseLogicException(cook + " is not actually a cook");
        }

        boolean isAssigned = this.currentSummarySheet.isAlreadyAssigned(procedure);
        if (isAssigned)
            throw new UseCaseLogicException(procedure + "is already assigned");

        this.currentWorkingTask = this.currentSummarySheet.addAssignment(procedure, shift, cook);

        this.notifyTaskCreated(this.currentWorkingTask);

        return this.currentWorkingTask;
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

    private void notifyOrderedProcedureUpdated(OrderedProcedure procedure) {
        for (TaskEventReceiver receiver : this.eventReceivers) {
            receiver.updateOrderedProcedurePosition(currentSummarySheet, procedure);
        }
    }

    private void notifyTaskCreated(Task task) {
        for (TaskEventReceiver er : this.eventReceivers) {
            er.updateTaskCreated(currentSummarySheet, task);
        }
    }
}
