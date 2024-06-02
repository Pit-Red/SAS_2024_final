package catering.businesslogic.task;

import catering.businesslogic.errors.ItemNotFoundException;
import catering.businesslogic.procedure.CookingProcedure;
import catering.businesslogic.procedure.OrderedProcedure;
import catering.businesslogic.procedure.Recipe;
import catering.businesslogic.shifts.KitchenShift;
import catering.businesslogic.shifts.Shift;
import catering.businesslogic.user.User;
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

        String proceduresQuery = "SELECT lp.summary_sheet_id, lp.procedure_id, lp.position, cp.* FROM ListedProcedures lp JOIN CookingProcedures cp ON lp.procedure_id = cp.id";
        PersistenceManager.executeQuery(proceduresQuery, rs -> {
            int summarySheetId = rs.getInt("summary_sheet_id");
            SummarySheet summarySheet = allSummarySheets.get(summarySheetId);
            if (summarySheet != null) {
                int procedureId = rs.getInt("procedure_id");
                int position = rs.getInt("position");
                CookingProcedure procedure = CookingProcedure.loadCookingProcedureById(procedureId);
                summarySheet.addProcedureWithPosition(procedure, position);
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

            String proceduresQuery = "SELECT lp.summary_sheet_id, lp.procedure_id, lp.position, cp.* " +
                    "FROM ListedProcedures lp JOIN CookingProcedures cp ON lp.procedure_id = cp.id " +
                    "WHERE lp.summary_sheet_id = " + id + " ORDER BY lp.position ASC";
            PersistenceManager.executeQuery(proceduresQuery, rs -> {
                int idSummarySheet = rs.getInt("summary_sheet_id");
                SummarySheet summarySheet = allSummarySheets.get(idSummarySheet);
                if (summarySheet != null) {
                    int procedureId = rs.getInt("procedure_id");
                    int position = rs.getInt("position");
                    CookingProcedure procedure = CookingProcedure.loadCookingProcedureById(procedureId);
                    summarySheet.addProcedureWithPosition(procedure, position);
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

    /**
     * Adds a cooking procedure to the summary sheet at a specified position.
     * If the list is not large enough to accommodate the procedure at the given index,
     * it is temporarily filled with nulls to ensure correct positioning.
     */
    public void addProcedureWithPosition(CookingProcedure procedure, int position) {
        OrderedProcedure orderedProcedure = new OrderedProcedure(procedure, position);
        // Ensure the list is large enough to accommodate the procedure at the given index
        while (listedProcedures.size() <= position) {
            listedProcedures.add(null); // Temporarily fill with nulls if necessary
        }
        listedProcedures.set(position, orderedProcedure);
    }

    public OrderedProcedure orderProcedure(CookingProcedure procedure, int newPosition) {
        this.listedProcedures.removeIf(op -> op.getBaseProcedure().equals(procedure));
        OrderedProcedure newProcedure = new OrderedProcedure(procedure, newPosition);
        this.listedProcedures.add(newPosition, newProcedure);
        return newProcedure;
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
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        return "SummarySheet {id:" + this.id + ", Procedures:" + this.listedProcedures + "}";
    }

    public boolean isAlreadyAssigned(CookingProcedure procedure) {
        return this.listedProcedures.contains((OrderedProcedure) procedure);
    }

    public Task addAssignment(CookingProcedure procedure, KitchenShift shift, User cook) {
        Task task = new Task(procedure, shift, cook);
        this.tasks.add(task);
        return task;
    }

    public Task markCookingProcedureAsDone(Task task) throws ItemNotFoundException{
        if (!tasks.contains(task)) throw new ItemNotFoundException("The selected task does not exists");

        task.setCompleted(true);

        return task;
    }

    public Task modifyTask (Task task, CookingProcedure procedure, KitchenShift shift, User cook) throws ItemNotFoundException {
        if (!tasks.contains(task)) throw new ItemNotFoundException("The selected task does not exists"); //TODO non presente nel dsd

        if (procedure != null)
            task.setProcedure(procedure);
        if (shift != null)
            task.setShift(shift);
        if (cook != null)
            task.setCook(cook);

        return task;
    }

    @Override
    public boolean equals(Object obj) {
        // Check for reference equality.
        if (this == obj) {
            return true;
        }

        // Check for null and ensure the exact same class for comparison.
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        // Type cast the object for field comparison.
        SummarySheet other = (SummarySheet) obj;

        // Check for field equality.
        return this.id == other.id;
    }
}
