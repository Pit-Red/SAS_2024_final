package catering.businesslogic.event;

import catering.businesslogic.menu.Menu;
import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.util.ArrayList;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class Service {
    private final String name;
    private int id;
    private Date date;
    private Time timeStart;
    private Time timeEnd;
    private int participants;
    private Menu usedMenu; // @todo aggiungere nel database
    private User chef; // @todo aggiungere nel database

    public Service(String name) {
        this.name = name;
    }

    public static ArrayList<Service> loadServiceInfoForEvent(int event_id) {
        ArrayList<Service> result = new ArrayList<>();
        String query = "SELECT id, name, service_date, time_start, time_end, expected_participants " +
                "FROM Services WHERE event_id = " + event_id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                String s = rs.getString("name");
                Service serv = new Service(s);
                serv.id = rs.getInt("id");
                serv.date = rs.getDate("service_date");
                serv.timeStart = rs.getTime("time_start");
                serv.timeEnd = rs.getTime("time_end");
                serv.participants = rs.getInt("expected_participants");
                result.add(serv);
            }
        });

        return result;
    }

    public boolean isChefAssigned(User chef) {
        return this.chef.equals(chef);
    }

    public User getChef() {
        return chef;
    }

    public void setChef(User chef) {
        this.chef = chef;
    }

    public Menu getUsedMenu() {
        return usedMenu;
    }

    public void setUsedMenu(Menu usedMenu) {
        this.usedMenu = usedMenu;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return name + ": " + date + " (" + timeStart + "-" + timeEnd + "), " + participants + " pp.";
    }
}
