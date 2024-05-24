package catering.businesslogic.task;

import catering.businesslogic.procedure.CookingProcedure;

public interface TaskEventReceiver {
    void addReceiver(TaskEventReceiver er);

    void removeReceiver(TaskEventReceiver er);

    void notifySummarySheetCreated();

    void notifyCookingProcedureAdded(CookingProcedure p);

    void notifyTaskCreated(Task t);

    void notifyTaskUpdated(Task t);

    void notifyTaskDeleted(Task t);
}