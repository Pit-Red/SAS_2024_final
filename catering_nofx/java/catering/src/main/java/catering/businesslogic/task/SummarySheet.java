package catering.businesslogic.task;

import catering.businesslogic.procedure.Procedure;

import java.util.ArrayList;

public class SummarySheet {
    private final ArrayList<Procedure> listProcedures;
    private ArrayList<Task> tasks;

    public SummarySheet(ArrayList<Procedure> listProcedures) {
        this.listProcedures = listProcedures;
    }

    public void addProcedure(Procedure procedure){
        this.listProcedures.add(procedure);
    }

    public boolean containsProcedure(Procedure procedure){
        return this.listProcedures.contains(procedure);
    }





}
