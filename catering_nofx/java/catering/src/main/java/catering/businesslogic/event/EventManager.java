package catering.businesslogic.event;

import java.util.ArrayList;

public class EventManager {
    public ArrayList<EventInfo> getEventInfo() {
        return EventInfo.loadAllEventInfo();
    }

    public Service getServiceById(int id){return Service.loadById(id);}
}