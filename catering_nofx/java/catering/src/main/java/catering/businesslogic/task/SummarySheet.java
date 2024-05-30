package catering.businesslogic.task;

import catering.businesslogic.procedure.CookingProcedure;
import catering.businesslogic.procedure.OrderedProcedure;
import catering.businesslogic.procedure.Recipe;
import catering.persistence.PersistenceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SummarySheet {
    private static final Map<Integer, SummarySheet> allSummarySheets = new HashMap<>();
    private final int id;
    private final ArrayList<Task> tasks;
    private final ArrayList<OrderedProcedure> listedProcedures;

    // Constructor for creating a new SummarySheet from a list of recipes (coming from a menu)
    public SummarySheet(ArrayList<Recipe> recipes) {
        this.id = generateNewId();
        this.listedProcedures = new ArrayList<>();
        this.tasks = new ArrayList<>();
        for (int i = 0; i < recipes.size(); i++) {
            // Convert each Recipe into an OrderedProcedure and assign position
            this.listedProcedures.add(new OrderedProcedure(recipes.get(i), i));
        }
    }

    // Constructor for loading an existing SummarySheet
    private SummarySheet(int id) {
        this.id = id;
        this.listedProcedures = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    public static void loadAllSummarySheets() {
        String query = "SELECT * FROM SummarySheets";
        PersistenceManager.executeQuery(query, rs -> {
            int id = rs.getInt("id");
            if (!allSummarySheets.containsKey(id)) {
                allSummarySheets.put(id, new SummarySheet(id));
            }
        });

        String tasksQuery = "SELECT * FROM ListedTasks lt JOIN Tasks t ON lt.task_id = t.id";
        PersistenceManager.executeQuery(tasksQuery, rs -> {
            int id = rs.getInt("summary_sheet_id");
            SummarySheet summarySheet = allSummarySheets.get(id);
            if (summarySheet != null) {
                summarySheet.addTask(Task.loadTaskById(rs.getInt("task_id")));
            }
        });

        String proceduresQuery = "SELECT * FROM ListedProcedures lp JOIN CookingProcedures cp ON lp.procedure_id = cp.id";
        PersistenceManager.executeQuery(proceduresQuery, rs -> {
            int id = rs.getInt("summary_sheet_id");
            SummarySheet summarySheet = allSummarySheets.get(id);
            if (summarySheet != null) {
                summarySheet.addProcedure(CookingProcedure.loadCookingProcedureById(rs.getInt("procedure_id")));
            }
        });
    }

    public static ArrayList<SummarySheet> getAllSummarySheets() {
        return new ArrayList<>(allSummarySheets.values());
    }

    public static SummarySheet loadById(int id) {
        if (!allSummarySheets.containsKey(id)) {
            String query = "SELECT * FROM SummarySheets WHERE id = " + id;
            PersistenceManager.executeQuery(query, rs -> {
                int idSummarySheet = rs.getInt("id");
                if (!allSummarySheets.containsKey(idSummarySheet)) {
                    allSummarySheets.put(idSummarySheet, new SummarySheet(idSummarySheet));
                }
            });

            String tasksQuery = "SELECT * FROM ListedTasks lt JOIN Tasks t ON lt.task_id = t.id WHERE id = " + id;
            PersistenceManager.executeQuery(tasksQuery, rs -> {
                int idSummarySheet = rs.getInt("summary_sheet_id");
                SummarySheet summarySheet = allSummarySheets.get(idSummarySheet);
                if (summarySheet != null) {
                    summarySheet.addTask(Task.loadTaskById(rs.getInt("task_id")));
                }
            });

            String proceduresQuery = "SELECT * FROM ListedProcedures lp JOIN CookingProcedures cp ON lp.procedure_id = cp.id WHERE id = " + id;
            PersistenceManager.executeQuery(proceduresQuery, rs -> {
                int idSummarySheet = rs.getInt("summary_sheet_id");
                SummarySheet summarySheet = allSummarySheets.get(idSummarySheet);
                if (summarySheet != null) {
                    summarySheet.addProcedure(CookingProcedure.loadCookingProcedureById(rs.getInt("procedure_id")));
                }
            });
        }

        return allSummarySheets.get(id);
    }

    public int getId() {
        return id;
    }

    private int generateNewId() {
        String query = "INSERT INTO SummarySheets (id) VALUES (DEFAULT)";
        PersistenceManager.executeUpdate(query);
        return PersistenceManager.getLastId();
    }

    public OrderedProcedure addProcedure(CookingProcedure newProcedure) {
        int newPosition = listedProcedures.size();  // Position is the next index in the list
        OrderedProcedure newOrderedProcedure = new OrderedProcedure(newProcedure, newPosition);
        listedProcedures.add(newOrderedProcedure);  // Add the new OrderedProcedure to the list
        return newOrderedProcedure;
    }

    public void orderProcedure(CookingProcedure procedure, int position) {
        this.listedProcedures.removeIf(op -> op.getBaseProcedure().equals(procedure));
        this.listedProcedures.add(position, new OrderedProcedure(procedure, position));
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public boolean containsProcedure(CookingProcedure cookingProcedure) {
        // Loop through each OrderedProcedure and check if it wraps the given cookingProcedure
        for (OrderedProcedure op : listedProcedures) {
            if (op.getBaseProcedure().equals(cookingProcedure)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<CookingProcedure> getListedProcedures() {
        ArrayList<CookingProcedure> procedures = new ArrayList<>();
        for (OrderedProcedure op : listedProcedures) {
            procedures.add(op.getBaseProcedure());
        }
        return procedures;
    }

    public ArrayList<OrderedProcedure> getListedOrderedProcedures() {
        return new ArrayList<>(listedProcedures);
    }

    @Override
    public String toString() {
        return "SummarySheet {id:" + this.id + ", Procedures:" + this.listedProcedures + "}";
    }
}
