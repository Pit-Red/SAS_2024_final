package catering.businesslogic.task;

import catering.businesslogic.procedure.CookingProcedure;
import catering.businesslogic.procedure.Preparation;
import catering.businesslogic.procedure.Recipe;
import catering.businesslogic.shifts.KitchenShift;
import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Task {

    private static final Map<Integer, Task> allTasks = new HashMap<>();
    private final int id;
    private Duration timeToComplete;
    private boolean completed;
    private Boolean toPrepare;
    private String amount;
    private String doses;
    private CookingProcedure procedure;
    private User cook;
    private KitchenShift shift;
    private Task initialTask;

    // Constructor for creating a new Task
    public Task(CookingProcedure procedure, KitchenShift shift, User cook) {
        this.id = generateNewId();
        this.procedure = procedure;
        this.shift = shift;
        this.cook = cook;
        saveNewTask();
    }

    // Constructor for creating a new Task without a cook
    public Task(CookingProcedure procedure, KitchenShift shift) {
        this.id = generateNewId();
        this.procedure = procedure;
        this.shift = shift;
        this.cook = null;
        saveNewTask();
    }

    // Constructor for loading an existing Task
    private Task(int id) {
        this.id = id;
    }


    // PERSISTENCE PART

    public static ArrayList<Task> loadAllTasks() {
        String query = "SELECT * FROM Tasks";

        PersistenceManager.executeQuery(query, rs -> {
            int id = rs.getInt("id");
            if (!allTasks.containsKey(id)) {
                taskSetUp(id, rs);
            }
        });

        ArrayList<Task> proc = new ArrayList<>(allTasks.values());
        proc.sort(Comparator.comparing(Task::getTimeToComplete));

        return proc;
    }

    public static Task loadTaskById(int id) {
        if (!allTasks.containsKey(id)) {
            String query = "SELECT * FROM Tasks WHERE id = " + id;
            PersistenceManager.executeQuery(query, rs -> taskSetUp(id, rs));
        }
        return allTasks.get(id);
    }

    private static void taskSetUp(int id, ResultSet rs) throws SQLException {
        CookingProcedure proc = CookingProcedure.loadCookingProcedureById(rs.getInt("cooking_procedure_id"));
        User user = User.loadUserById(rs.getInt("cook_id"));
        // KitchenShift shift = KitchenShift.loadKitchenShiftById(rs.getInt("shift_id")); // todo handle shifts
        Task task = new Task(id);
        task.procedure = proc;
        task.cook = user;
        //task.shift = shift;
        task.completed = rs.getBoolean("completed");
        task.toPrepare = rs.getBoolean("to_prepare");

        String time = rs.getString("time_to_complete");
        if (time != null)
            task.timeToComplete = Duration.parse(time);
        String amount = rs.getString("amount");
        if (amount != null)
            task.amount = amount;
        String doses = rs.getString("doses");
        if (doses != null)
            task.doses = doses;

        int initial_task_id = rs.getInt("initial_task");
        Task initial_task;
        if (initial_task_id != 0) {
            initial_task = Task.loadTaskById(initial_task_id);
            task.initialTask = initial_task;
        }

        allTasks.put(id, task);
    }

    private int generateNewId() {
        String query = "INSERT INTO Tasks (id) VALUES (DEFAULT)";
        PersistenceManager.executeUpdate(query);
        return PersistenceManager.getLastId();
    }

    private void saveNewTask() { // todo add cooking shift to DB
        String query = "INSERT INTO Tasks (id, cooking_procedure_id, cook_id, initial_task, shift_id, time_to_complete, completed, to_prepare, amount, doses) VALUES (" +
                this.id + ", " +
                this.procedure.getId() + ", " +
                (this.cook != null ? this.cook.getId() : "NULL") + ", " +
                (this.initialTask != null ? this.initialTask.getId() : "NULL") + ", " +
                (this.shift != null ? this.shift.getId() : "NULL") + ", " +
                (this.timeToComplete != null ? this.timeToComplete : "NULL") + ", " +
                this.completed + ", " +
                (this.toPrepare != null ? this.toPrepare : "NULL") + ", " +
                (this.amount != null ? this.amount : "NULL") + ", " +
                (this.doses != null ? this.doses : "NULL") + ")";
        PersistenceManager.executeUpdate(query);
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public Duration getTimeToComplete() {
        return timeToComplete;
    }

    public CookingProcedure getProcedure() {
        return procedure;
    }
}
