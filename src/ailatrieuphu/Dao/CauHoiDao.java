/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ailatrieuphu.Dao;

import ailatrieuphu.Helper.JdbcHelper;
import ailatrieuphu.Model.CauHoi;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class CauHoiDao {
    
    public void insert(CauHoi model){
        String sql = "INSERT INTO CauHoi(MaLinhVuc,CauHoi,DapAnA,DapAnB,DapAnC,DapAnD,DapAn,Level) VALUES(?,?,?,?,?,?,?,?)";
        JdbcHelper.executeUpdate(sql,model.getMaLinhVuc(),model.getCauHoi(),model.getDapAnA(),model.getDapAnB(),model.getDapAnC(),model.getDapAnD(),model.getDapAn(),model.getLevel());
    }
    public void update(CauHoi model){
        String sql = "UPDATE CauHoi SET MaLinhVuc=?,CauHoi=?,DapAnA=?,DapAnB=?,DapAnC=?,DapAnD=?,DapAn=?,Level=? WHERE MaCauHoi=?";
        JdbcHelper.executeUpdate(sql, model.getMaLinhVuc(),model.getCauHoi(),model.getDapAnA(),model.getDapAnB(),model.getDapAnC(),model.getDapAnD(),model.getDapAn(),model.getLevel(), model.getMaCauHoi());
    }
    public void delete(Integer MaCauHoi){
        String sql = "DELETE FROM CauHoi WHERE MaCauHoi=?";
        JdbcHelper.executeUpdate(sql, MaCauHoi);
    }
    public CauHoi getRandomCauHoiForLinhVucAndLevel(String maLinhVuc,int level) throws SQLException{
        String sql="SELECT TOP 1[MaCauHoi], MaLinhVuc, CauHoi, DapAnA, DapAnB, DapAnC, DapAnD, DapAn, Level FROM CauHoi WHERE MaLinhVuc=? AND Level=? ORDER BY NEWID()";
        List<CauHoi> list = select(sql,maLinhVuc,level);
        return list.size()>0 ? list.get(0): null;
    }
    public CauHoi getRandomCauHoiForLevel(int level) throws SQLException{
        String sql="SELECT TOP 1[MaCauHoi], MaLinhVuc, CauHoi, DapAnA, DapAnB, DapAnC, DapAnD, DapAn, Level FROM CauHoi WHERE Level=? ORDER BY NEWID()";
        List<CauHoi> list = select(sql,level);
        return list.size()>0 ? list.get(0): null;
    }
    public void findByMaCauHoi(String maCauHoi){
       String sql="SELECT * FROM CauHoi WHERE MaCauHoi=?";
        JdbcHelper.executeUpdate(sql, maCauHoi);
    }
    public CauHoi findById(Integer MaCauHoi){
        String sql = "SELECT * FROM CauHoi WHERE MaCauHoi=?";
        List<CauHoi> list = select(sql,MaCauHoi);
        return list.size()>0 ? list.get(0): null;
    }
    public List<CauHoi> select(){
        String sql = "SELECT * FROM CauHoi";
        return select(sql);
    }
    private List<CauHoi> select(String sql, Object...args){
        List<CauHoi> list = new ArrayList<>();
        try{
            ResultSet rs = null;
            try{
               rs = JdbcHelper.executeQuery(sql, args);
               while(rs.next()){
                   CauHoi model = readFromResultSet(rs);
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
    private CauHoi readFromResultSet(ResultSet rs) throws SQLException{
        CauHoi model = new CauHoi();
        model.setMaCauHoi(rs.getInt("MaCauHoi"));
        model.setMaLinhVuc(rs.getString("MaLinhVuc"));
        model.setCauHoi(rs.getString("CauHoi"));
        model.setDapAnA(rs.getString("DapAnA"));
        model.setDapAnB(rs.getString("DapAnB"));
        model.setDapAnC(rs.getString("DapAnC"));
        model.setDapAnD(rs.getString("DapAnD"));
        model.setDapAn(rs.getString("DapAn"));
        model.setLevel(rs.getInt("Level"));
        return model;
    }
}
