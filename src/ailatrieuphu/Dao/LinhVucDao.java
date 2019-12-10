/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ailatrieuphu.Dao;

import ailatrieuphu.Helper.JdbcHelper;
import ailatrieuphu.Model.LinhVuc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MyPC
 */
public class LinhVucDao {
    public void insert(LinhVuc model){
        String sql = "INSERT INTO LinhVuc(MaLinhVuc,TenLinhVuc) VALUES(?,?)";
        JdbcHelper.executeUpdate(sql, model.getMaLinhVuc(),model.getTenLinhVuc());
    }
    public void update(LinhVuc model){
        String sql = "UPDATE LinhVuc SET TenLinhVuc=? WHERE MaLinhVuc=?";
        JdbcHelper.executeUpdate(sql, model.getTenLinhVuc(),model.getMaLinhVuc());
    }
    public void delete(String MaLinhVuc){
        String sql = "DELETE FROM LinhVuc WHERE MaLinhVuc=?";
        JdbcHelper.executeUpdate(sql, MaLinhVuc);
    }
    public LinhVuc findMaLinhVuc(String TenLinhVuc) throws SQLException{
        String sql="SELECT * FROM LinhVuc WHERE TenLinhVuc=?";
        List<LinhVuc> list = select(sql,TenLinhVuc);
        return list.size()>0 ? list.get(0): null;
    }
    public LinhVuc findID(String MaLinhVuc){
        String sql = "SELECT * FROM LinhVuc WHERE MaLinhVuc=?";
        List<LinhVuc> list = select(sql, MaLinhVuc);
        return list.size()>0 ? list.get(0): null;
        
    }
    public List<LinhVuc> select(){
        String sql = "SELECT * FROM LinhVuc";
        return select(sql);
    }
    private List<LinhVuc> select(String sql, Object...args){
        List<LinhVuc> list = new ArrayList<>();
        try{
            ResultSet rs = null;
            try{
                rs = JdbcHelper.executeQuery(sql, args);
                while(rs.next()){
                    LinhVuc model = readFromResultSet(rs);
                    list.add(model);
                }
            }
           finally{
                rs.getStatement().getConnection().close();
            }
        }
        catch(SQLException ex){
            throw new RuntimeException(ex);
        }
        return list;
    }
    private LinhVuc readFromResultSet(ResultSet rs) throws SQLException{
        LinhVuc model = new LinhVuc();
        model.setMaLinhVuc(rs.getString("MaLinhVuc"));
        model.setTenLinhVuc(rs.getString("TenLinhVuc"));
        return model;
    }
}
