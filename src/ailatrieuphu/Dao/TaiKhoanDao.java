/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ailatrieuphu.Dao;

import ailatrieuphu.Helper.JdbcHelper;
import ailatrieuphu.Model.TaiKhoan;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MyPC
 */
public class TaiKhoanDao {
    public void insert(TaiKhoan model){
        String sql = "INSERT INTO TaiKhoan(Username,Password,HoTen,Email,Diem,Admin) VALUES(?,?,?,?,?,?)";
        JdbcHelper.executeUpdate(sql,model.getUsername(),model.getPassword(),model.getHoTen(),model.getEmail(),model.getDiem(),model.getAdmin());
    }
    public void update(TaiKhoan model){
        String sql = "UPDATE TaiKhoan SET Password=?,HoTen=?,Email=?,Diem=?,Admin=? WHERE Username=?";
        JdbcHelper.executeUpdate(sql, model.getPassword(),model.getHoTen(),model.getEmail(),model.getDiem(),model.getAdmin(),model.getUsername());
    }
    public void updateDiem(int diem, String username){
        String sql= "UPDATE TaiKhoan SET Diem=? WHERE Username=?";
        JdbcHelper.executeUpdate(sql, diem, username);
    }
    public void delete(String Username){
        String sql = "DELETE FROM TaiKhoan WHERE Username=?";
        JdbcHelper.executeUpdate(sql,Username);
    }
    public List<TaiKhoan> select(){
        String sql = "SELECT * FROM TaiKhoan";
        return select(sql);
    }
    public TaiKhoan findID(String Username){
        String sql = "SELECT * FROM TaiKhoan WHERE Username=?";
        List<TaiKhoan> list = select(sql, Username);
        return list.size()>0 ? list.get(0): null;
    }
    private List<TaiKhoan> select(String sql,Object...args){
        List<TaiKhoan> list = new ArrayList<>();
        try{
             ResultSet rs = null;
             try{
                 rs= JdbcHelper.executeQuery(sql, args);
                 while(rs.next()){
                     TaiKhoan model = readFromResultSet(rs);
                     list.add(model);
                 }
             }
             finally{
                 
             }
        }
        catch(SQLException ex){
            throw new RuntimeException(ex);
        }
        return list;
    }
    
    private TaiKhoan readFromResultSet(ResultSet rs) throws SQLException{
        TaiKhoan model = new TaiKhoan();
        model.setUsername(rs.getString("Username"));
        model.setPassword(rs.getString("Password"));
        model.setHoTen(rs.getString("HoTen"));
        model.setEmail(rs.getString("Email"));
        model.setDiem(rs.getInt("Diem"));
        model.setAdmin(rs.getBoolean("Admin"));
        return model;   
    }
}
