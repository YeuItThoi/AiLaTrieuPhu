/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ailatrieuphu.Helper;

import ailatrieuphu.Model.TaiKhoan;

/**
 *
 * @author MyPC
 */
public class ShareHelper {
    /**
     * Đối tượng này chứa thông tin người sử dụng sau khi đăng nhập
     */
    public static TaiKhoan USER = null;
    /**
     * Xóa thông tin của người sử dụng khi có yêu cầu đăng xuất
     */
    public static void logout(){
        ShareHelper.USER=null;
    }
   
     
    public static void refreshUser(TaiKhoan model){
        USER=model;
    }
}
