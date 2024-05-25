package catering.businesslogic.task;

import catering.businesslogic.procedure.CookingProcedure;
import catering.businesslogic.procedure.Preparation;
import catering.businesslogic.procedure.Recipe;
import catering.persistence.PersistenceManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SummarySheet {

    private static final Map<Integer, SummarySheet> allSummarySheets = new HashMap<>();
    private int id;
    private ArrayList<Task> tasks;
    private ArrayList<CookingProcedure> listedProcedures;

    public SummarySheet(int id, ArrayList<CookingProcedure> procedures) {
        this.id = id;
        this.listedProcedures = new ArrayList<>(procedures);
        this.tasks = new ArrayList<>();
    }

    public SummarySheet(int id) {
        this.id = id;
        this.listedProcedures = new ArrayList<>();
        this.tasks = new ArrayList<>();
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
        return "SummarySheet {id:"+ this.id + ", Procedures:" + this.listedProcedures + "}";
    }

    // PERSISTENCE PART

    public static ArrayList<SummarySheet> loadAllSummarySheet(){

        String query = "SELECT * " +
                "FROM ListedTasks l join Tasks t on l.task_id = t.id";

        PersistenceManager.executeQuery(query, rs -> {
            int id = rs.getInt("summary_sheet_id");
            if (!allSummarySheets.containsKey(id))
                allSummarySheets.put(id, new SummarySheet(id));
            SummarySheet summarySheet = allSummarySheets.get(id);
            summarySheet.addTask(Task.loadTastById(rs.getInt("task_id")));
            summarySheet.addProcedure(CookingProcedure.loadCookingProcedureById(rs.getInt("cooking_procedure_id")));
        });

        return new ArrayList<>(allSummarySheets.values());
    }

    public static ArrayList<SummarySheet> getAllSummarySheets(){
        return new ArrayList<>(allSummarySheets.values());
    }
}
