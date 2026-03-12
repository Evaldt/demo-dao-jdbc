package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


public class Program {
    public static void main(String[] args) {
        Scanner scan= new Scanner(System.in);
        SellerDao sellerDao= DaoFactory.createSellerDao();

        System.out.println("====Teste 1: Seller findByID ====");
        Seller seller= sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("====Teste 2: Seller findByDepartment ====");
        Department department= new Department(2,null);
        List<Seller> list= sellerDao.findByDepartment(department);
        for(Seller obj: list){
            System.out.println(obj);
        }

        System.out.println("====Teste 3: Seller findAll ====");
         list= sellerDao.findAll();
        for(Seller obj: list){
            System.out.println(obj);
        }

        System.out.println("====Teste 4: Seller Insert ====");
        Seller newSeller= new Seller(null,"Lucas Prato","parto@gmail.com", LocalDate.of(2004,12,8),7800, department);

        sellerDao.insert(newSeller);
        System.out.println("Inserido: new Id: "+newSeller.getId());

        System.out.println("====Teste 5: Seller UpDate ====");
        seller= sellerDao.findById(1);
        seller.setName("Bartolomeu Wize");
        sellerDao.update(seller);
        System.out.println("UpDate completed");

        System.out.println("====Teste 6: Seller Delete ====");
        System.out.println("Digite um Id para ser Deletado:");
        int id= scan.nextInt();
        sellerDao.deleteById(id);
        System.out.println("Delete Completed");


    }
}
