package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Department implements Serializable {
    private static final long serialVersionUID= 1L;
    private int id;
    private String name;

    public Department(){}



    public Department(Integer id, String name){
        this.id=id;
        this.name=name;
    }

    public Integer getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }

    public void setId(Integer id){
        this.id=id;
    }
    public void setName(String name){
        this.name=name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
