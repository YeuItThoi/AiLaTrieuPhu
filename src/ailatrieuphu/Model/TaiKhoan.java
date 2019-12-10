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
public class TaiKhoan {
    private String Username;
    private String Password;
    private String HoTen;
    private String Email;
    private int Diem=0;
    private boolean Admin=false;

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setDiem(int Diem) {
        this.Diem = Diem;
    }

    public void setAdmin(boolean Admin) {
        this.Admin = Admin;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public String getHoTen() {
        return HoTen;
    }

    public String getEmail() {
        return Email;
    }

    public int getDiem() {
        return Diem;
    }

    public boolean getAdmin() {
        return Admin;
    }

    public TaiKhoan() {
    }

    public TaiKhoan(String Username, String Password, String HoTen, String Email) {
        this.Username = Username;
        this.Password = Password;
        this.HoTen = HoTen;
        this.Email = Email;
    }

    
}
