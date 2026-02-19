package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB
{

    private static Connection conn= null;

    public static Connection getConnection()//Conexão com o banco de dados
    {
        if (conn==null)
        {
            try
            {
                Properties props = loadedProperties();
                String url = props.getProperty("dburl");
                conn = DriverManager.getConnection(url, props);
            }
            catch (SQLException e)
            {throw new DbException(e.getMessage());}
        }
        return conn;
    }

    public static void closeConnection() //Fecha a conexão com o BD
    {
        if (conn!=null)
        {
            try {
                conn.close();
            }
            catch (SQLException e)
            {throw new DbException(e.getMessage());}

        }
    }


    private static Properties loadedProperties()
    {
        try(FileInputStream fs= new FileInputStream("db.properties"))
        {
            Properties props= new Properties();
            props.load(fs);//Faz a leitura do aquivo propeties
            return props;
        }
        catch (IOException e)
        {
            throw new DbException(e.getMessage());
        }
    }
    public static void closeStatement(Statement st){//Fecha um statemente
        if (st!= null){
            try{
                st.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs){//Fecha um statemente
        if (rs!= null){
            try{
                rs.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }


        }
    }

}
