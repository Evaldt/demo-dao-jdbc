package application;

import model.entities.Department;
import model.entities.Seller;

import java.time.LocalDate;


public class Program {
    public static void main(String[] args) {
        Department obj= new Department(1,"Books");

        Seller obj2= new Seller(57,"Kauan","eu@gmail.com",LocalDate.of(2006,12,04),8900, obj);
        System.out.println(obj);
        System.out.println(obj2);
    }
}
