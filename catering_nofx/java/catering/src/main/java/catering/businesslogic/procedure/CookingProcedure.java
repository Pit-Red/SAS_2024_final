package catering.businesslogic.procedure;

import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public abstract class CookingProcedure {
    private static final ArrayList<CookingProcedure> cookingProcedures = new ArrayList<>();
    protected String name;
    protected int id;

    public CookingProcedure() {
    }

    public CookingProcedure(String name, int id) {
        this.name = name;
        this.id = id;
    }

    protected static <T extends CookingProcedure> T loadById(int id, String query, Map<Integer, T> all, T instance) {
        if (all.containsKey(id)) return all.get(id);
        PersistenceManager.executeQuery(query, (rs) -> {
            instance.name = rs.getString("name");
            instance.id = id;
            all.put(instance.id, instance);
        });
        return instance;
    }

    public static void loadAllProcedures() {
        cookingProcedures.addAll(Recipe.loadAllRecipes());
        cookingProcedures.addAll(Preparation.loadAllPreparations());
    }

    public static ArrayList<CookingProcedure> getAllProcedures() {
        return cookingProcedures;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public abstract boolean equals(Object obj);

}
