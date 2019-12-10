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
 * @author admin
 */
public class XepHangDao {
    public List<TaiKhoan> timKiemNguoiChoiTheoTen(String HoTen){
        String sql = "SELECT * FROM TaiKhoan WHERE HoTen LIKE ?";
        return select(sql,"%" +HoTen + "%");
    }
    public List<TaiKhoan> select(){
        String sql = "SELECT TOP(10)Diem, HoTen FROM TaiKhoan ORDER BY Diem DESC";
        return select(sql);
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
        model.setHoTen(rs.getString("HoTen"));
        model.setDiem(rs.getInt("Diem"));
        return model;   
    }
    
}
