package catering.businesslogic.procedure;

import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Recipe extends CookingProcedure {

    public Recipe() {

    }

    public Recipe(String name, int cookingProcedureId, int recipeId) {
        super(cookingProcedureId, recipeId, name, ProcedureType.RECIPE);
    }



    @Override
    public boolean equals(Object obj) {
        return obj instanceof Recipe &&
                ((Recipe) obj).id == this.id &&
                ((Recipe) obj).foreignKeyId == this.foreignKeyId;
    }
}
