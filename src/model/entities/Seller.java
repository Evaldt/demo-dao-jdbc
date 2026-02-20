package model.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Seller implements Serializable {

    private static final long serialVersionUID= 1L;

    private int id;
    private String name;
    private String email;
    private LocalDate bithDate;
    private double baseSalary;

    private Department department;

    public Seller(){}


    public Seller(Integer id, String name, String email, LocalDate bithDate, double baseSalary, Department department){
        this.id=id;
        this.name= name;
        this.email=email;
        this.bithDate=bithDate;
        this.baseSalary=baseSalary;
        this.department= department;


    }

    public Integer getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public LocalDate getBithDate(){
        return this.bithDate;
    }

    public double getBaseSalary() {
        return this.baseSalary;
    }

    public Department getDepartment(){
        return this.department;
    }

    public void setId(Integer id){
        this.id=id;
    }

    public void setName(String name){
        this.name= name;
    }

    public void setEmai(String email){
        this.email= email;
    }

    public void setBithDate(LocalDate bithDate){
        this.bithDate= bithDate;
    }

    public void setBaseSalary(double baseSalary){
        this.baseSalary=baseSalary;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o){
        if (o==null || getClass() != o.getClass()){return false;}
        Seller seller= (Seller) o;
        return id==seller.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Seller{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", bithDate=" + bithDate +
                ", baseSalary=" + baseSalary +
                ", Department=" + department +
                '}';
    }
}
