package catering.businesslogic.procedure;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CookingProcedureManager {

    public CookingProcedureManager() {
        CookingProcedure.loadAllProcedures();
    }

    public ArrayList<CookingProcedure> getProcedures() {
        return CookingProcedure.getAllProcedures();
    }
    public ArrayList<CookingProcedure> getPreparations(){
        return CookingProcedure.getAllProcedures()
                .stream()
                .filter(procedure -> procedure instanceof Preparation)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
