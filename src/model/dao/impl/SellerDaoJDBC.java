package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn){
        this.conn= conn;

    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement st= null;
        ResultSet rs= null;
        try {
            st= conn.prepareStatement(
                    "INSERT INTO seller "
                    +"(Name,Email,BirthDate,BaseSalary,DepartmentId) "
                    +"VALUES "
                    +"(?,?,?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            st.setString(1,obj.getName());
            st.setString(2,obj.getEmail());
            st.setDate(3,  java.sql.Date.valueOf(obj.getBirthDate()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());

            int rowsAffected = st.executeUpdate();// Usa o Update quando é insert, delete, update. Esse cara retorna um int
            //st.executeQuery é usado quando se faz um select pois ele retorna um resultSet

            if (rowsAffected>0){
                rs= st.getGeneratedKeys();//Pega o Id gerado no PreparedStatement.RETURN_GENERATED_KEYS

                if (rs.next()){
                    int id= rs.getInt(1);
                    obj.setId(id);
                }
            }
            else {
                throw new DbException("Erro não esperado");
            }
        }
        catch (SQLException e) {
                throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
        }


    @Override
    public void update(Seller obj) {

        PreparedStatement st= null;

        try {
            st= conn.prepareStatement(
                    "UPDATE  seller "
                            +"SET Name= ?, Email=? , BirthDate=?, BaseSalary=?, DepartmentId=? "
                            +"WHERE Id= ? "
            );
            st.setString(1,obj.getName());
            st.setString(2,obj.getEmail());
            st.setDate(3,  java.sql.Date.valueOf(obj.getBirthDate()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());
            st.setInt(6, obj.getId());

        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {

        PreparedStatement st= null;
        try {
            st= conn.prepareStatement(
                    "DELETE FROM seller WHERE Id= ? "
            );
            st.setInt(1,id);
            st.executeUpdate();
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st= null;
        ResultSet rs= null;

        try {
            st= conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            +"FROM seller INNER JOIN department "
                            +"ON seller.DepartmentId= department.Id "
                            +"WHERE seller.Id= ?"
            );
            st.setInt(1,id);
            rs= st.executeQuery();
            if (rs.next()){
                Department dep= instantiateDepartment(rs);
                Seller seller= instantiateSeller(rs, dep);
                return seller;
            }
            return null;//Se não encontrou nada
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException{
        Department dep =new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException{
        Seller obj= new Seller();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setBirthDate(rs.getDate("BirthDate").toLocalDate());
        obj.setDepartment(dep);
        return obj;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st= null;
        ResultSet rs= null;

        try {
            st= conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            +"FROM seller INNER JOIN department "
                            +"ON seller.DepartmentId= department.Id "
                            +"ORDER BY Name"
            );
            rs= st.executeQuery();
            List<Seller> list= new ArrayList<>();
            Map<Integer, Department> map= new HashMap<>();

            while (rs.next()){
                Department dep = map.get(rs.getInt("DepartmentId"));
                //Impede a criação de varios department iguais
                if (dep==null){
                    dep= instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller seller= instantiateSeller(rs, dep);
                list.add(seller);
            }
            return list;
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st= null;
        ResultSet rs= null;

        try {
            st= conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            +"FROM seller INNER JOIN department "
                            +"ON seller.DepartmentId= department.Id "
                            +"WHERE DepartmentId= ? "
                            +"ORDER BY Name"
            );
            st.setInt(1,department.getId());
            rs= st.executeQuery();
            List<Seller> list= new ArrayList<>();
            Map<Integer, Department> map= new HashMap<>();

            while (rs.next()){
                Department dep = map.get(rs.getInt("DepartmentId"));
                //Impede a criação de varios department iguais
                if (dep==null){
                     dep= instantiateDepartment(rs);
                     map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller seller= instantiateSeller(rs, dep);
                list.add(seller);
            }
            return list;
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
