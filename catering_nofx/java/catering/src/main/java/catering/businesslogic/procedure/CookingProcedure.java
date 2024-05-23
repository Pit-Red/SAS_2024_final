package catering.businesslogic.procedure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class CookingProcedure {

    private static ArrayList<CookingProcedure> cookingProcedures = new ArrayList<>();
    private int id;
    private String name;

    public CookingProcedure(){

    }

    public CookingProcedure(String name){
        id = 0;
        this.name = name;
    }
    public static ArrayList<CookingProcedure> loadAllProcedures(){
        cookingProcedures.addAll(Recipe.loadAllRecipes());
        cookingProcedures.addAll(Preparation.loadAllPreparations());

        return cookingProcedures;
    }

    public static ArrayList<CookingProcedure> getAllProcedures() {return cookingProcedures;}
}
