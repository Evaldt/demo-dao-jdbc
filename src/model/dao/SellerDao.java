package model.dao;


import model.entities.Department;
import model.entities.Seller;
import java.util.List;

public interface SellerDao {

    public void insert(Seller obj);
    public void update(Seller obj);
    public void deleteById(Integer obj);
    public Seller findById(Integer id);
    public List<Seller> findAll();
    public List<Seller> findByDepartment(Department department);
}
