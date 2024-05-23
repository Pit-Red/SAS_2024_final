package catering;

import catering.businesslogic.procedure.CookingProcedure;
import catering.businesslogic.procedure.CookingProcedureManager;

public class TestPreparations {
    public static void main(String[] args) {
        CookingProcedureManager cpm = new CookingProcedureManager();
        System.out.println(cpm.getPreparations());
    }
}
