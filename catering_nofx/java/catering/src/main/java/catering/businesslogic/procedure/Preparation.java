package catering.businesslogic.procedure;

import catering.persistence.PersistenceManager;

import java.util.*;

public class Preparation extends CookingProcedure {
    private static final Map<Integer, Preparation> allPreparations = new HashMap<>();

    public Preparation() {
        super();
    }

    public Preparation(String name) {
        super(name, 0);
    }

    public static ArrayList<Preparation> getAllPreparations() {
        return new ArrayList<>(allPreparations.values());
    }

    // STATIC METHODS FOR PERSISTENCE
    public static Preparation loadPreparationById(int id) {
        if (allPreparations.containsKey(id)) return allPreparations.get(id);
        Preparation prep = new Preparation();
        String query = "SELECT * FROM Preparations WHERE id = " + id;

        PersistenceManager.executeQuery(query, rs -> {
            prep.name = rs.getString("name");
            prep.id = id;
            allPreparations.put(prep.id, prep);
        });

        return prep;
    }

    public static ArrayList<Preparation> loadAllPreparations() {
        String query = "SELECT * FROM Preparations";

        PersistenceManager.executeQuery(query, rs -> {
            int id = rs.getInt("id");
            if (allPreparations.containsKey(id)) {
                Preparation rec = allPreparations.get(id);
                rec.name = rs.getString("name");
            } else {
                Preparation prep = new Preparation(rs.getString("name"));
                prep.id = id;
                allPreparations.put(prep.id, prep);
            }
        });

        ArrayList<Preparation> preps = new ArrayList<>(allPreparations.values());
        preps.sort(Comparator.comparing(Preparation::getName));

        return preps;
    }

    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Preparation && ((Preparation) obj).getId() == this.id;
    }
}
