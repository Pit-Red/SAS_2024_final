package catering.businesslogic;

import catering.businesslogic.event.EventManager;
import catering.businesslogic.menu.MenuManager;
import catering.businesslogic.procedure.CookingProcedureManager;
import catering.businesslogic.task.KitchenTaskManager;
import catering.businesslogic.user.UserManager;
import catering.persistence.MenuPersistence;

public class CatERing {
    private static CatERing singleInstance;
    private final MenuManager menuMgr;
    private final CookingProcedureManager procedureMgr;
    private final UserManager userMgr;
    private final EventManager eventMgr;
    private final MenuPersistence menuPersistence;
    private final KitchenTaskManager kitchenTaskMgr;

    private CatERing() {

        menuMgr = new MenuManager();
        procedureMgr = new CookingProcedureManager();
        userMgr = new UserManager();
        eventMgr = new EventManager();
        menuPersistence = new MenuPersistence();
        kitchenTaskMgr = new KitchenTaskManager();

        menuMgr.addEventReceiver(menuPersistence);
    }

    public static CatERing getInstance() {
        if (singleInstance == null) {
            singleInstance = new CatERing();
        }
        return singleInstance;
    }

    public MenuManager getMenuManager() {
        return menuMgr;
    }

    public CookingProcedureManager getProcedureManager() {
        return procedureMgr;
    }

    public UserManager getUserManager() {
        return userMgr;
    }

    public EventManager getEventManager() {
        return eventMgr;
    }

    public KitchenTaskManager getKitchenTaskMgr() {
        return kitchenTaskMgr;
    }
}
