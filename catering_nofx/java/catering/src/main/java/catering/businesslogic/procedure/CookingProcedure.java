package catering.businesslogic.procedure;

import catering.persistence.PersistenceManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public abstract class CookingProcedure {
    private static final Map<Integer,CookingProcedure> allCookingProcedures = new HashMap<>();
    private static final Map<Integer,CookingProcedure> allPreparations = new HashMap<>();
    private static final Map<Integer,CookingProcedure> allRecipes = new HashMap<>();

    public abstract String getName();
    public abstract void setName(String name);
    public abstract int getId();
    public abstract void setId(int id);
    public abstract int getForeignKeyId();
    public abstract void setForeignKeyId(int id);
    @Override
    public abstract boolean equals(Object obj);

    // STATIC FOR PERSISTENCE
    public static ArrayList<CookingProcedure> loadAllProcedures(){
        String query = "SELECT * FROM CookingProcedures";

        PersistenceManager.executeQuery(query, rs -> {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            if (allCookingProcedures.containsKey(id)) {
                CookingProcedure procedure = allCookingProcedures.get(id);
                procedure.setName(rs.getString("name"));
            } else {
                CookingProcedure procedure;
                if (rs.getString("type").equals("preparation")){
                    procedure = new Preparation(name, id, rs.getInt("fk_referenced_preparation"));
                    allPreparations.put(procedure.getForeignKeyId(), procedure);
                }
                else {
                    procedure = new Recipe(name, id, rs.getInt("fk_referenced_recipe"));
                    allRecipes.put(procedure.getForeignKeyId(), procedure);
                }

                allCookingProcedures.put(procedure.getId(), procedure);
            }
        });

        ArrayList<CookingProcedure> proc = new ArrayList<>(allCookingProcedures.values());
        proc.sort(Comparator.comparing(CookingProcedure::getName));

        return proc;
    }

    public static Recipe loadRecipeById(int id) {
        if (allRecipes.containsKey(id)) return (Recipe) allRecipes.get(id);
        Recipe rec = new Recipe();
        String query = "SELECT * FROM Recipes WHERE id = " + id;
        PersistenceManager.executeQuery(query, rs -> {
            rec.setName(rs.getString("name"));
            rec.setForeignKeyId(id);
            allRecipes.put(rec.getForeignKeyId(), rec);
        });
        return rec;
    }

    public static Preparation loadPreparationById(int id) {
        if (allRecipes.containsKey(id)) return (Preparation) allRecipes.get(id);
        Preparation prep = new Preparation();
        String query = "SELECT * FROM Recipes WHERE id = " + id;
        PersistenceManager.executeQuery(query, rs -> {
            prep.setName(rs.getString("name"));
            prep.setForeignKeyId(id);
            allRecipes.put(prep.getForeignKeyId(), prep);
        });
        return prep;
    }

    public static ArrayList<CookingProcedure> getAllProcedures() {
        return (ArrayList<CookingProcedure>) allCookingProcedures.values();
    }

    public static ArrayList<CookingProcedure> getAllPreparations() {
        return new ArrayList<>(allPreparations.values());
    }

    public static ArrayList<CookingProcedure> getAllRecipes() {
        return new ArrayList<>(allRecipes.values());
    }



}
