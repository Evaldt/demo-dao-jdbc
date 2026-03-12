package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmetDaoJDBC implements DepartmentDao {

   private  Connection conn;

   public DepartmetDaoJDBC(Connection conn){
       this.conn= conn;
   }
    @Override
    public void insert(Department obj) {
        PreparedStatement st= null;
        ResultSet rs= null;
        try{
            st= conn.prepareStatement(
                    "INSERT INTO department "
                    +"(Name) "
                    +" VALUES "
                    +"(?)",
                     PreparedStatement.RETURN_GENERATED_KEYS //porque não precisa de;?
            );

            st.setString(1,obj.getName());
            int rowsAffect= st.executeUpdate();

            if(rowsAffect>0){
                rs=st.getGeneratedKeys();
                //Importante! O rs começa com o ponteiro no nada, antes da linha. Usa o next() pra avançar para linha com dados e verificar se ela existe retornando True para se tem a linha e False para se não houver
                if(rs.next()){
                    int id= rs.getInt(1);// mesma ideia do next() exite a colunas com dados e sem os dados, escolhe o lugar da coluna que você quer buscar a infomação;
                    obj.setId(id);
                }
            }
            else{
                throw new DbException("Erro não esperado");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

    }

    @Override
    public void update(Department obj) {
       PreparedStatement st= null;
       try{
           st= conn.prepareStatement(
                   "UPDATE department "
                   +"SET Name = ? "
                   +"WHERE Id= ?"
           );
           st.setString(1, obj.getName());
           st.setInt(2,obj.getId());

           int rowsAffected = st.executeUpdate();// Vai servir para verificar se houve mudança no Banco

           if(rowsAffected==0){// se não aconteceu a modificação é porque não encontrou o "Id" no banco
               throw new DbException("ERRO NÃO ESPERADO!!");
           }

       } catch (SQLException e) {
           throw new DbException(e.getMessage());
       }
       finally {
           DB.closeStatement(st);
       }

    }

    @Override
    public void deleteById(Integer obj) {
       PreparedStatement st= null;
       try{
           st= conn.prepareStatement(
                   "DELETE FROM department "
                   +"WHERE Id= ?"
           );
           st.setInt(1,obj);

           int rowsAffected= st.executeUpdate();
           if(rowsAffected==0){// se não aconteceu a modificação é porque não encontrou o "Id" no banco
               throw new DbException("Id não encontrado!!");
           }

       } catch (SQLException e) {
           throw new DbException(e.getMessage());
       }

    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st= null;
        ResultSet rs= null;
        try{
            st= conn.prepareStatement(
                    "SELECT Id, Name FROM department "
                    +"WHERE Id=?"
            );
            st.setInt(1,id);
            rs= st.executeQuery();//Coloca o resultado do select no ResultSet

            if(rs.next()){
                Department dep = instantiateDepartment(rs);
                return dep;
            }
            return null;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Department> findAll() {
       PreparedStatement st= null;
       ResultSet rs= null;
       try{
           st= conn.prepareStatement(
                   "SELECT * FROM department "
           );
           rs= st.executeQuery();
           List<Department> depList= new ArrayList<>();
           Map<Integer, Department> depMap= new HashMap<>();

           while(rs.next()){
               Department dep= depMap.get(rs.getInt("Id"));
               if(dep==null){
                   dep= instantiateDepartment(rs);
                   depMap.put(dep.getId(),dep);
               }
               depList.add(dep);
           }
           return depList;

       } catch (SQLException e) {
           throw new DbException(e.getMessage());
       }
       finally {
           DB.closeStatement(st);
           DB.closeResultSet(rs);
       }

    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException{
       Department dep= new Department();
       dep.setId(rs.getInt("Id"));
       dep.setName(rs.getString("Name"));
       return dep;
    }
}
