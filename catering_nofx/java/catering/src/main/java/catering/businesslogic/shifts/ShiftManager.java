package catering.businesslogic.shifts;

import catering.businesslogic.user.User;

public class ShiftManager {

    public ShiftManager(){

    }

    public boolean isAvailable (User cook, KitchenShift shift){
        // visto che il caso d'uso non è da gestire usppongo che il cuoco sia sempre disponibile
        return true;
    }
}
