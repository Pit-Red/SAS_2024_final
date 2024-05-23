package catering.businesslogic.procedure;

import java.util.ArrayList;

public class CookingProcedureManager {

    public CookingProcedureManager() {
        CookingProcedure.loadAllProcedures();
    }

    public ArrayList<CookingProcedure> getRecipes() {
        return CookingProcedure.getAllProcedures();
    }
}
