package catering.businesslogic.procedure;

import java.util.ArrayList;

public abstract class Procedure {

    @Override
    public abstract boolean equals(Object obj);
    private static ArrayList<Procedure> cookingProcedures = new ArrayList<>();

    public static ArrayList<Procedure> loadAllProcedures(){
        cookingProcedures.addAll(Recipe.loadAllRecipes());
        cookingProcedures.addAll(Preparation.loadAllPreparations());

        return cookingProcedures;
    }

    public static ArrayList<Procedure> getAllProcedures() {return cookingProcedures;}
}
