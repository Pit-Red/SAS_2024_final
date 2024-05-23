package catering.businesslogic.procedure;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ProcedureManager {

    public ProcedureManager() {
        Procedure.loadAllProcedures();
    }

    public ArrayList<Procedure> getProcedures() {
        return Procedure.getAllProcedures();
    }

    public ArrayList<Procedure> getRecipes(){
        return Procedure.getAllProcedures()
                .stream()
                .filter(procedure -> procedure instanceof Recipe)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<Procedure> getPreparations(){
        return Procedure.getAllProcedures()
                .stream()
                .filter(procedure -> procedure instanceof Preparation)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
