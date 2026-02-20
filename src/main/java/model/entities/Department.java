package model.entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Department {
//    @Serial
//    private static final long serialVersionUID =1L;

    private Integer id;
    private String name;

    public Department(){

    }

    public Department (Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    //let only name toString. And you will see only the name os department on comboBox sellerFormView
    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
