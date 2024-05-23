package catering.businesslogic.task;

import catering.businesslogic.procedure.CookingProcedure;

import java.util.ArrayList;

public class SummarySheet {
    private ArrayList<CookingProcedure> listProcedures;

    public SummarySheet(ArrayList<CookingProcedure> listProcedures) {
        this.listProcedures = listProcedures;
    }

    public void addProcedure(CookingProcedure procedure){
        this.listProcedures.add(procedure);
    }

}
