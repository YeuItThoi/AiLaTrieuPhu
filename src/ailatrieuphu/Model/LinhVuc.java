/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ailatrieuphu.Model;

/**
 *
 * @author MyPC
 */
public class LinhVuc {
    private String MaLinhVuc;
    private String TenLinhVuc;
    @Override
    public String toString(){
        return this.TenLinhVuc;
    }
    public String getMaLinhVuc() {
        return MaLinhVuc;
    }

    public void setMaLinhVuc(String MaLinhVuc) {
        this.MaLinhVuc = MaLinhVuc;
    }

    public String getTenLinhVuc() {
        return TenLinhVuc;
    }

    public void setTenLinhVuc(String TenLinhVuc) {
        this.TenLinhVuc = TenLinhVuc;
    }
    
    
}
