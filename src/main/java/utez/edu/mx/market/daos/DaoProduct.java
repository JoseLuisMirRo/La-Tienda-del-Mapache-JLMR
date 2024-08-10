package utez.edu.mx.market.daos;

import utez.edu.mx.market.entities.Product;
import utez.edu.mx.market.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoProduct {
    private Connection con;
    private PreparedStatement pstm;
    private ResultSet rs;
    private final DBConnection DB_CONNECTION = new DBConnection();
    private final DaoCategory DAO_CATEGORY = new DaoCategory();

    private final String[] QUERIES = {
            "SELECT * FROM product;",
            "SELECT * FROM product WHERE id=?",
            "INSERT INTO product(name,description,stock,on_sale,id_category) VALUES (?,?,?,?,?);",
            "UPDATE product SET name = ?, description = ?, stock = ?,on_sale=?,id_category=? WHERE id=?;",
            "UPDATE product SET on_sale = ? WHERE id=?;",
            "DELETE FROM product WHERE id = ?;"
    };

    public List<Product> findAllProducts(){
        List<Product> list = new ArrayList<>();
        try {
            con = DB_CONNECTION.getConnection();
            pstm = con.prepareStatement(QUERIES[0]);
            rs = pstm.executeQuery();
            while(rs.next()){
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setStock(rs.getLong("stock"));
                product.setOnSale(rs.getBoolean("on_sale"));
                product.setCategory(DAO_CATEGORY.findCategoryById(rs.getInt("id_category")));
                list.add(product);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return list;
    }
    public Product findProductById(long id) {
        Product product = null;
        try{
            con = DB_CONNECTION.getConnection();
            pstm = con.prepareStatement(QUERIES[1]);
            pstm.setLong(1,id);

            rs = pstm.executeQuery();
            if(rs.next()){
                product=new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setStock(rs.getLong("stock"));
                product.setOnSale(rs.getBoolean("on_sale"));
                product.setCategory(DAO_CATEGORY.findCategoryById(rs.getInt("id_category")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return product;
    }

    public boolean createProduct(Product product){
        boolean flag = false;
        try{
            con = DB_CONNECTION.getConnection();
            pstm = con.prepareStatement(QUERIES[2]);
            pstm.setString(1,product.getName());
            pstm.setString(2, product.getDescription());
            pstm.setLong(3,product.getStock());
            pstm.setBoolean(4,product.isOnSale());
            pstm.setInt(5, product.getCategory().getId());
            return pstm.executeUpdate() ==1;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }finally {
            closeConnection();
        }
    }

    public boolean updateProduct(Product product){
        Product found =findProductById(product.getId());
        if (found != null) {
            boolean flag = false;
            try {
                con = DB_CONNECTION.getConnection();
                pstm = con.prepareStatement(QUERIES[3]);
                pstm.setString(1, product.getName());
                pstm.setString(2, product.getDescription());
                pstm.setLong(3, product.getStock());
                pstm.setBoolean(4, product.isOnSale());
                pstm.setInt(5, product.getCategory().getId());
                pstm.setLong(6, product.getId());
                return pstm.executeUpdate() == 1;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                closeConnection();
            }
        }else{
            return false;
        }
    }

    public boolean changeProductStatus(long id){
        Product found =findProductById(id);
        if (found != null) {
            boolean flag = false;
            try {
                con = DB_CONNECTION.getConnection();
                pstm = con.prepareStatement(QUERIES[4]);
                pstm.setBoolean(1, !found.isOnSale());
                pstm.setLong(2, found.getId());
                return pstm.executeUpdate() == 1;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                closeConnection();
            }
        }else{
            return false;
        }
    }

    public boolean deleteProduct(long id){
        Product found = findProductById(id);
        if(found!=null){
            try{
                con = DB_CONNECTION.getConnection();
                pstm=con.prepareStatement(QUERIES[5]);
                pstm.setLong(1, id);
                return pstm.executeUpdate() == 1;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }finally {
                closeConnection();
            }
        }else {
            return false;
        }
    }



    public static void main(String[] args) {
        DaoProduct dao = new DaoProduct();
        System.out.println(dao.deleteProduct(2));
    }

    public void closeConnection(){
        try {
            if (con!=null){
                con.close();
            }
            if (pstm!=null){
                pstm.close();
            }
            if(rs != null){
                rs.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
