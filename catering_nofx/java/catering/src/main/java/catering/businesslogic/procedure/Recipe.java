package catering.businesslogic.procedure;

import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Recipe extends CookingProcedure {

    private int cookingProcedureId;
    private int recipeId;
    private String name;

    public Recipe() {

    }

    public Recipe(String name, int cookingProcedureId, int recipeId) {
        this.name = name;
        this.cookingProcedureId = cookingProcedureId;
        this.recipeId = recipeId;
    }


    public String toString() {
        return String.format("{%s, %d, %d}", name, cookingProcedureId, recipeId);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getId() {
        return this.cookingProcedureId;
    }

    @Override
    public void setId(int cookingProcedureId) {
        this.cookingProcedureId = cookingProcedureId;
    }

    @Override
    public int getForeignKeyId() {
        return this.recipeId;
    }


    @Override
    public void setForeignKeyId(int recipeId) {
        this.recipeId = recipeId;
    }
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Recipe &&
                ((Recipe) obj).getId() == this.cookingProcedureId &&
                ((Recipe) obj).getForeignKeyId() == this.recipeId;
    }

}
