package catering.businesslogic.procedure;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CookingProcedureManager {

    public CookingProcedureManager() {
        CookingProcedure.loadAllProcedures();
    }

    public ArrayList<CookingProcedure> getCookingProcedures() {
        return CookingProcedure.getAllProcedures();
    }

    public ArrayList<CookingProcedure> getRecipes() {
        return CookingProcedure.getAllProcedures()
                .stream()
                .filter(procedure -> procedure instanceof Recipe)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<CookingProcedure> getPreparations() {
        return CookingProcedure.getAllProcedures()
                .stream()
                .filter(procedure -> procedure instanceof Preparation)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
