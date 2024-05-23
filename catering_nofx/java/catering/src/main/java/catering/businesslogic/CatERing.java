package catering.businesslogic;

import catering.businesslogic.event.EventManager;
import catering.businesslogic.menu.MenuManager;
import catering.businesslogic.procedure.ProcedureManager;
import catering.businesslogic.user.UserManager;
import catering.persistence.MenuPersistence;

public class CatERing {
    private static CatERing singleInstance;

    public static CatERing getInstance() {
        if (singleInstance == null) {
            singleInstance = new CatERing();
        }
        return singleInstance;
    }

    private MenuManager menuMgr;
    private ProcedureManager procedureMgr;
    private UserManager userMgr;
    private EventManager eventMgr;

    private MenuPersistence menuPersistence;

    private CatERing() {

        menuMgr = new MenuManager();

        procedureMgr = new ProcedureManager();

        userMgr = new UserManager();
        eventMgr = new EventManager();

        menuPersistence = new MenuPersistence();

        menuMgr.addEventReceiver(menuPersistence);
    }


    public MenuManager getMenuManager() {
        return menuMgr;
    }

    public ProcedureManager getProcedureManager() {
        return procedureMgr;
    }

    public UserManager getUserManager() {

        return userMgr;
    }

    public EventManager getEventManager() { return eventMgr; }

}
