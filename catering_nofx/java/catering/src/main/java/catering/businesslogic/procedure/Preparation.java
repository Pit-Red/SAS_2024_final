package catering.businesslogic.procedure;

import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Preparation extends Procedure {

    private static Map<Integer, Preparation> all = new HashMap<>();
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
        String query = "SELECT * FROM Preparations";

        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                int id = rs.getInt("id");
                if (all.containsKey(id)) {
                    Preparation rec = all.get(id);
                    rec.name = rs.getString("name");
                } else {
                    Preparation rec = new Preparation(rs.getString("name"));
                    rec.id = id;
                    all.put(rec.id, rec);
                }
            }
        });

        ArrayList<Preparation> ret = new ArrayList<Preparation>(all.values());
        Collections.sort(ret, new Comparator<Preparation>() {
            @Override
            public int compare(Preparation o1, Preparation o2) {
                return (o1.getName().compareTo(o2.getName()));
            }
        });
        return ret;
    }

    public static ArrayList<Preparation> getAllRecipes() {
        return new ArrayList<Preparation>(all.values());
    }

    public static Preparation loadRecipeById(int id) {
        if (all.containsKey(id)) return all.get(id);
        Preparation rec = new Preparation();
        String query = "SELECT * FROM Preparations WHERE id = " + id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                rec.name = rs.getString("name");
                rec.id = id;
                all.put(rec.id, rec);
            }
        });
        return rec;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Preparation && ((Preparation) obj).getId() == this.id;
    }
}
