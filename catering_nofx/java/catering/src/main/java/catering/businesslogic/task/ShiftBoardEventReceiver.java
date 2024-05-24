package catering.businesslogic.task;

import catering.businesslogic.procedure.CookingProcedure;

public interface ShiftBoardEventReceiver {
    void addReceiver(ShiftBoardEventReceiver er);

    void removeReceiver(ShiftBoardEventReceiver er);

    void notifySummarySheetCreated();

    void notifyCookingProcedureAdded(CookingProcedure p);

    void notifyTaskCreated(Task t);

    void notifyTaskUpdated(Task t);

    void notifyTaskDeleted(Task t);
}