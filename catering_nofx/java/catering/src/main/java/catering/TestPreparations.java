package catering;

import catering.businesslogic.procedure.ProcedureManager;

public class TestPreparations {
    public static void main(String[] args) {
        ProcedureManager cpm = new ProcedureManager();
        System.out.println(cpm.getPreparations());
    }
}
