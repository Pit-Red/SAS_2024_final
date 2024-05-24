package catering.businesslogic.task;

import catering.businesslogic.procedure.CookingProcedure;

import java.util.ArrayList;

public class SummarySheet {
    private final ArrayList<CookingProcedure> listCookingProcedures;
    private ArrayList<Task> tasks;

    public SummarySheet(ArrayList<CookingProcedure> listCookingProcedures) {
        this.listCookingProcedures = listCookingProcedures;
    }

    public void addProcedure(CookingProcedure cookingProcedure){
        this.listCookingProcedures.add(cookingProcedure);
    }

    public boolean containsProcedure(CookingProcedure cookingProcedure){
        return this.listCookingProcedures.contains(cookingProcedure);
    }





}
