package catering.businesslogic.procedure;

import catering.persistence.PersistenceManager;

import java.util.*;

public class Preparation extends CookingProcedure {

    private int cookingProcedureId;
    private int preparationId;
    private String name;

    public Preparation() {
    }

    public Preparation(String name, int cookingProcedureId, int preparationId) {
        this.name = name;
        this.cookingProcedureId = cookingProcedureId;
        this.preparationId = preparationId;
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
        return this.preparationId;
    }

    @Override
    public void setForeignKeyId(int preparationId) {
        this.preparationId = preparationId;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Preparation &&
                ((Preparation) obj).getId() == this.cookingProcedureId &&
                ((Preparation) obj).getForeignKeyId() == this.preparationId;
    }

    public String toString() {
        return String.format("{%s, %d, %d}", name, cookingProcedureId, preparationId);
    }

}
