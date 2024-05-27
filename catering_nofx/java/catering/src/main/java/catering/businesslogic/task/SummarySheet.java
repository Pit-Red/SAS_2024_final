package catering.businesslogic.task;

import catering.businesslogic.procedure.CookingProcedure;
import catering.businesslogic.procedure.Recipe;
import catering.persistence.PersistenceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SummarySheet {

    private static final Map<Integer, SummarySheet> allSummarySheets = new HashMap<>();
    private final int id;
    private final ArrayList<Task> tasks;
    private final ArrayList<CookingProcedure> listedProcedures;

    // Constructor for creating a new SummarySheet from a list of cooking procedures
    public SummarySheet(ArrayList<Recipe> procedures) {
        this.id = generateNewId();
        this.listedProcedures = new ArrayList<>(procedures);
        this.tasks = new ArrayList<>();
        saveNewSummarySheet();
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

    private void saveNewSummarySheet() {
        for (Task task : tasks) {
            String taskQuery = "INSERT INTO ListedTasks (summary_sheet_id, task_id) VALUES (" + this.id + ", " + task.getId() + ")";
            PersistenceManager.executeUpdate(taskQuery);
        }

        for (CookingProcedure procedure : listedProcedures) {
            String procedureQuery = "INSERT INTO ListedProcedures (summary_sheet_id, procedure_id) VALUES (" + this.id + ", " + procedure.getId() + ")";
            PersistenceManager.executeUpdate(procedureQuery);
        }
    }

    private int generateNewId() {
        String query = "INSERT INTO SummarySheets (id) VALUES (DEFAULT)";
        PersistenceManager.executeUpdate(query);
        return PersistenceManager.getLastId();
    }

    public void addProcedure(CookingProcedure cookingProcedure) {
        this.listedProcedures.add(cookingProcedure);
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public boolean containsProcedure(CookingProcedure cookingProcedure) {
        return this.listedProcedures.contains(cookingProcedure);
    }

    @Override
    public String toString() {
        return "SummarySheet {id:" + this.id + ", Procedures:" + this.listedProcedures + "}";
    }
}
