package catering.businesslogic.task;

import catering.businesslogic.procedure.CookingProcedure;

import java.util.ArrayList;

public class SummarySheet {
    private ArrayList<Task> tasks;
    private final ArrayList<CookingProcedure> listedProcedures;

    public SummarySheet(ArrayList<CookingProcedure> cookingProcedures) {
        this.listedProcedures = cookingProcedures;
    }

    public void addProcedure(CookingProcedure cookingProcedure) {
        this.listedProcedures.add(cookingProcedure);
    }

    public boolean containsProcedure(CookingProcedure cookingProcedure) {
        return this.listedProcedures.contains(cookingProcedure);
    }


}
