package catering.businesslogic.procedure;

import catering.persistence.PersistenceManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public abstract class CookingProcedure {
    private static final Map<Integer, CookingProcedure> allCookingProcedures = new HashMap<>();
    private static final Map<Integer, Preparation> allPreparations = new HashMap<>();
    private static final Map<Integer, Recipe> allRecipes = new HashMap<>();

    // STATIC FOR PERSISTENCE
    public static ArrayList<CookingProcedure> loadAllProcedures() {
        String query = "SELECT * FROM CookingProcedures";

        PersistenceManager.executeQuery(query, rs -> {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            if (allCookingProcedures.containsKey(id)) {
                CookingProcedure procedure = allCookingProcedures.get(id);
                procedure.setName(rs.getString("name"));
            } else {
                CookingProcedure procedure;
                if (rs.getString("type").equals("preparation")) {
                    procedure = new Preparation(name, id, rs.getInt("fk_referenced_preparation"));
                    allPreparations.put(procedure.getForeignKeyId(), (Preparation) procedure);
                } else {
                    procedure = new Recipe(name, id, rs.getInt("fk_referenced_recipe"));
                    allRecipes.put(procedure.getForeignKeyId(), (Recipe) procedure);
                }

                allCookingProcedures.put(procedure.getId(), procedure);
            }
        });

        ArrayList<CookingProcedure> proc = new ArrayList<>(allCookingProcedures.values());
        proc.sort(Comparator.comparing(CookingProcedure::getName));

        return proc;
    }

    public static CookingProcedure loadCookingProcedureById(int id) {
        if (allCookingProcedures.containsKey(id)) return allCookingProcedures.get(id);
        String query = "SELECT * FROM CookingProcedures WHERE id = " + id;
        PersistenceManager.executeQuery(query, rs -> {
            CookingProcedure proc;
            String name = rs.getString("name");
            if (rs.getString("type").equals("preparation"))
                proc = new Preparation(name, id, rs.getInt("fk_referenced_preparation"));
            else
                proc = new Recipe(name, id, rs.getInt("fk_referenced_preparation"));
            allCookingProcedures.put(proc.getId(), proc);
        });
        return allCookingProcedures.get(id);
    }

    public static Recipe loadRecipeById(int id) {
        if (allRecipes.containsKey(id)) return (Recipe) allRecipes.get(id);
        Recipe rec = new Recipe();
        String query = "SELECT * FROM CookingProcedures WHERE fk_referenced_recipe = " + id;
        PersistenceManager.executeQuery(query, rs -> {
            rec.setName(rs.getString("name"));
            rec.setForeignKeyId(id);
            allRecipes.put(rec.getForeignKeyId(), rec);
        });
        return rec;
    }

    public static Preparation loadPreparationById(int id) {
        if (allPreparations.containsKey(id)) return (Preparation) allPreparations.get(id);
        Preparation prep = new Preparation();
        String query = "SELECT * FROM Preparation WHERE id = " + id;
        PersistenceManager.executeQuery(query, rs -> {
            prep.setName(rs.getString("name"));
            prep.setForeignKeyId(id);
            allPreparations.put(prep.getForeignKeyId(), prep);
        });
        return prep;
    }

    public static ArrayList<CookingProcedure> getAllProcedures() {
        return (ArrayList<CookingProcedure>) allCookingProcedures.values();
    }

    public static ArrayList<Preparation> getAllPreparations() {
        return new ArrayList<>(allPreparations.values());
    }

    public static ArrayList<Recipe> getAllRecipes() {
        return new ArrayList<>(allRecipes.values());
    }

    public abstract String getName();

    public abstract void setName(String name);

    public abstract int getId();

    public abstract void setId(int id);

    public abstract int getForeignKeyId();

    public abstract void setForeignKeyId(int id);

    @Override
    public abstract boolean equals(Object obj);
}
