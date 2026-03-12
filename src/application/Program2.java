package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Department;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Program2  {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        DepartmentDao departmentDao = DaoFactory.createDepartmetDao();

        System.out.println("====Teste 1: Department findByID ====");
        Department dep = departmentDao.findById(3);
        System.out.println(dep);

        System.out.println("====Teste 2: Department findAll ====");
        List<Department> list = departmentDao.findAll();
        for (Department obj : list) {
            System.out.println(obj);
        }

        System.out.println("====Teste 3: Department Insert ====");
        Department newDepartment = new Department(null, "Agro");

        departmentDao.insert(newDepartment);
        System.out.println("Inserido: new Id: " + newDepartment.getId());

        System.out.println("====Teste 4: Department UpDate ====");
        dep = departmentDao.findById(1);
        dep.setName("Varejo");
        departmentDao.update(dep);
        System.out.println("UpDate completed");

        System.out.println("====Teste 5: Department Delete ====");
        System.out.println("Digite um Id para ser Deletado:");
        int id = scan.nextInt();
        departmentDao.deleteById(id);
        System.out.println("Delete Completed");
    }
}
