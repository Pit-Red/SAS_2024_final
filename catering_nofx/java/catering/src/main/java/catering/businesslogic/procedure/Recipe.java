package catering.businesslogic.procedure;

import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Recipe extends CookingProcedure {
    private static final Map<Integer, Recipe> allRecipes = new HashMap<>();

    // todo aggiungere al database la lista di preparazioni per ciascuna ricetta
    private final ArrayList<Preparation> preparations = new ArrayList<>();

    public Recipe() {
        super();
    }

    public Recipe(String name) {
        super(name, 0);
    }

    public static ArrayList<Recipe> getAllRecipes() {
        return new ArrayList<>(allRecipes.values());
    }

    // STATIC METHODS FOR PERSISTENCE
    public static Recipe loadRecipeById(int id) {
        if (allRecipes.containsKey(id)) return allRecipes.get(id);
        Recipe rec = new Recipe();
        String query = "SELECT * FROM Recipes WHERE id = " + id;
        PersistenceManager.executeQuery(query, rs -> {
            rec.name = rs.getString("name");
            rec.id = id;
            allRecipes.put(rec.id, rec);
        });
        return rec;
    }

    public static ArrayList<Recipe> loadAllRecipes() {
        String query = "SELECT * FROM Recipes";

        PersistenceManager.executeQuery(query, rs -> {
            int id = rs.getInt("id");
            if (allRecipes.containsKey(id)) {
                Recipe rec = allRecipes.get(id);

                rec.name = rs.getString("name");
            } else {
                Recipe rec = new Recipe(rs.getString("name"));
                rec.id = id;
                allRecipes.put(rec.id, rec);
            }
        });

        ArrayList<Recipe> ret = new ArrayList<>(allRecipes.values());
        ret.sort(Comparator.comparing(Recipe::getName));
        return ret;
    }

    public ArrayList<Preparation> getPreparations() {
        return preparations;
    }

    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Recipe && ((Recipe) obj).getId() == this.id;
    }
}
