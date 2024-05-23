package catering.businesslogic.procedure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Preparation extends CookingProcedure {

    private static Map<Integer, Recipe> all = new HashMap<>();
    private int id;
    private String name;

    public Preparation(){
        super();
    }

    public Preparation(String name) {
        id = 0;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return name;
    }

    // STATIC METHODS FOR PERSISTENCE
    public static ArrayList<Preparation> loadAllPreparations(){
        return new ArrayList<>(); //TODO add the schema for the preparations
    }
}
