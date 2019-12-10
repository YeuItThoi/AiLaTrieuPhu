/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ailatrieuphu.Form;

import ailatrieuphu.Dao.CauHoiDao;
import ailatrieuphu.Dao.LinhVucDao;
import ailatrieuphu.Dao.TaiKhoanDao;
import ailatrieuphu.Helper.DialogHelper;
import ailatrieuphu.Helper.ShareHelper;
import ailatrieuphu.Model.CauHoi;
import ailatrieuphu.Model.LinhVuc;
import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author admin
 */
public class JFrameChoi extends javax.swing.JInternalFrame {

    /**
     * Creates new form FormChoi
     */
    String MaLinhVuc=null;
    int[] diemThuong=new int[]{200,400,600,1000,2000,3000,6000,10000,15000,21000};
    int soCauHoi;
    int level=1;
    ArrayList DanhSachMaCauHoi = new ArrayList();
    CauHoiDao CauHoiDao = new CauHoiDao();
    LinhVucDao LinhVucDao= new LinhVucDao();
    TaiKhoanDao TaiKhoanDao= new TaiKhoanDao();
    Timer thoiGianTraLoi;
    Timer thoiGianChonDapAnA;
    Timer thoiGianChonDapAnB;
    Timer thoiGianChonDapAnC;
    Timer thoiGianChonDapAnD;
    int count = 0;
    int time=0;
    int tongtime=60 ;
    int hanchot;
    int DemClickButtonA=0;
    int DemClickButtonB=0;
    int DemClickButtonC=0;
    int DemClickButtonD=0;
    CauHoi cauHoi= new CauHoi();
    String TraLoi;
    boolean loaiCauHoi;
    
    
    
    
    public JFrameChoi(LinhVuc linhvuc) throws SQLException {
        //kiểm tra xem người chơi có chọn lĩnh vực hay để chế độ tất cả
        initComponents();
        if (linhvuc==null) {
            soCauHoi=-1;
            LoadCauHoi();
            loaiCauHoi=false;
        }else{
            soCauHoi=-1;
            MaLinhVuc=linhvuc.getMaLinhVuc();
            LoadCauHoiForLinhVuc();
            loaiCauHoi=true;
        }
        
    }
    //hàm kiểm tra xem mã câu hỏi sau khi được lấy ramdom đã tồn tại trong DanhSachMaCauHoi chưa
    public boolean checkRandom(int MaCauHoi){
        if(DanhSachMaCauHoi.contains(MaCauHoi)){
            return true;
        }
        return false;
    }
    //lấy ngẫu nhiên mã câu hỏi của câu hỏi có lĩnh vực
    public int getRandomMaCauHoiForLinhVuc(int level) throws SQLException{
        CauHoi CauHoi=CauHoiDao.getRandomCauHoiForLinhVucAndLevel(MaLinhVuc, level);
        return CauHoi.getMaCauHoi();
    }
    //lấy ngẫu nhiên mã câu hỏi của câu hỏi không lĩnh vực
    public int getRandomMaCauHoi(int level) throws SQLException{
        CauHoi CauHoi=CauHoiDao.getRandomCauHoiForLevel(level);
        return CauHoi.getMaCauHoi();
    }
    //hàm đổi màu lbl mức thưởng theo số câu hỏi người chơi trả lời được
    public void doiMauMucThuong(){
        if (soCauHoi==-1) {
            lblMucThuong21000.setForeground(Color.white);
            lblMucThuong200.setForeground(Color.red);
        }
        if (soCauHoi==0) {
            lblMucThuong200.setForeground(Color.white);
            lblMucThuong400.setForeground(Color.red);
        }
        if (soCauHoi==1) {
            lblMucThuong400.setForeground(Color.white);
            lblMucThuong600.setForeground(Color.red);
        }
        if (soCauHoi==2) {
            lblMucThuong600.setForeground(Color.white);
            lblMucThuong1000.setForeground(Color.red);
        }
        if (soCauHoi==3) {
            lblMucThuong1000.setForeground(Color.white);
            lblMucThuong2000.setForeground(Color.red);
        }
        if (soCauHoi==4) {
            lblMucThuong2000.setForeground(Color.white);
            lblMucThuong3000.setForeground(Color.red);
        }
        if (soCauHoi==5) {
            lblMucThuong3000.setForeground(Color.white);
            lblMucThuong6000.setForeground(Color.red);
        }
        if (soCauHoi==6) {
            lblMucThuong6000.setForeground(Color.white);
            lblMucThuong10000.setForeground(Color.red);
        }
        if (soCauHoi==7) {
            lblMucThuong10000.setForeground(Color.white);
            lblMucThuong15000.setForeground(Color.red);
        }
        if (soCauHoi==8) {
            lblMucThuong15000.setForeground(Color.white);
            lblMucThuong21000.setForeground(Color.red);
        }
    }
    
    //hàm check đáp án người chơi chọn
    public void checkDapAn(String TraLoi) throws SQLException{
        thoiGianTraLoi.stop();
        if(TraLoi.equals(cauHoi.getDapAn())){//kiểm tra đáp án người chơi chọn đúng hay sai
            soCauHoi++;
            //nếu người chơi đã trả lời hết hệ thống sẽ thông báo người chơi chiến thắng
            if(soCauHoi==9){
                int diem = ShareHelper.USER.getDiem()+diemThuong[soCauHoi];//lấy tất cả điểm của người chơi rồi cộng thêm số điểm người chơi vừa được
                TaiKhoanDao.updateDiem(diem, ShareHelper.USER.getUsername());//update lại điểm cho người chơi
                ShareHelper.refreshUser(TaiKhoanDao.findID(ShareHelper.USER.getUsername()));//làm mới lại tài khoàn người chơi đã đăng nhập(cập nhật điểm để hiển thị main chính)
                DialogHelper.alert(this, "Chúc mừng bạn đã chiến thắng, bạn nhận được 21000 điểm !");
                setVisible(false);
            }
           //load câu hỏi tiếp theo
            else if(loaiCauHoi){
               JOptionPane.showMessageDialog(rootPane, "Trả lời đúng");
               LoadCauHoiForLinhVuc();
               thoiGianTraLoi.restart();
           }else{
               JOptionPane.showMessageDialog(rootPane, "Trả lời đúng");
               LoadCauHoi();
           }
        }else{//khi người chơi chọn đáp án sai
            if(soCauHoi==-1){//nếu người chơi không trả lời được câu nào đúng
                DialogHelper.alert(this, "Bạn đã không nhận được phần thưởng của chương trình");
            }else if(checkLogin()){//kiểm tra xem người chơi có đăng nhập không
                int diem = ShareHelper.USER.getDiem()+diemThuong[soCauHoi];//lấy tất cả điểm của người chơi rồi cộng thêm số điểm người chơi vừa được
                TaiKhoanDao.updateDiem(diem, ShareHelper.USER.getUsername());//update lại điểm cho người chơi
                ShareHelper.refreshUser(TaiKhoanDao.findID(ShareHelper.USER.getUsername()));//làm mới lại tài khoàn người chơi đã đăng nhập(cập nhật điểm để hiển thị main chính)
                DialogHelper.alert(this, "Trò chơi kết thúc, "+diemThuong[soCauHoi]+" điểm đã được cộng vào tài khoản của bạn");
            }else{
                //nếu người chơi không đăng nhập
                DialogHelper.alert(this, "Đáp án sai, bạn nhận được "+diemThuong[soCauHoi]+" điểm");
            }
            setVisible(false);
        }
    }
    //hàm kiểm tra trạng thái đăng nhập của người chơi
    public boolean checkLogin(){
        if(ShareHelper.USER!=null){
            return true;
        }
        return false;
    }
    //hàm đếm thời gian
    public void timecouter() {
        count=0;
        thoiGianTraLoi = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                count++;
                hanchot = tongtime - count;
                lblTime.setText(hanchot + "");
                //nếu qua 1 phút sẽ hết giờ, hệ thống sẽ tự động check đáp án, nếu đúng người chơi sẽ chơi tiếp;
                if (hanchot == 0) {
                    StopThoiGianChonCauHoi();
                    DialogHelper.alert(PanelTime, "Hết Thời Gian");
                    try {
                        checkDapAn(TraLoi);
                    } catch (SQLException ex) {
                        Logger.getLogger(JFrameChoi.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }
        });
        thoiGianTraLoi.start();
    }
    
    public void doiMauButtonDapAn(){
        btnDapAnA.setBackground(Color.blue);
        btnDapAnB.setBackground(Color.blue);
        btnDapAnC.setBackground(Color.blue);
        btnDapAnD.setBackground(Color.blue);
    }
    //fill câu hỏi khi không chọn lĩnh vực
    
    public void LoadCauHoi() throws SQLException{
        doiMauButtonDapAn();//đổi màu lại button đáp án
        int MaCauHoi;
        //nâng độ khó của câu hỏi
        if(soCauHoi==3){
            level=2;
        }
        if(soCauHoi==6){
            level=3;
        }
        //lấy ramdom mã câu hỏi và kiểm tra xem mã câu hỏi đã tồn tại trong danh sách chưa(check trùng)
        //nếu đã tồn tại thì lấy lại mã câu hỏi khác
        MaCauHoi=getRandomMaCauHoi(level);
        while (checkRandom(MaCauHoi)) {            
            MaCauHoi=getRandomMaCauHoi(level);
        }
        doiMauMucThuong();
        DanhSachMaCauHoi.add(MaCauHoi);
        cauHoi=CauHoiDao.findById(MaCauHoi);//lấy câu hỏi theo mã câu hỏi sau
        setModel(cauHoi);//fill câu hỏi lên model
        HienThiButtonTroGiup();
        timecouter();//đếm thời gian
        
    }
    //fill câu hỏi khi chọn lĩnh vực
    public void LoadCauHoiForLinhVuc() throws SQLException{
        doiMauButtonDapAn();//đổi màu lại button đáp án
        int MaCauHoi;
        //nâng độ khó của câu hỏi
        if(soCauHoi==3){
            level=2;
        }
        if(soCauHoi==6){
            level=3;
        }
        //lấy ramdom mã câu hỏi và kiểm tra xem mã câu hỏi đã tồn tại trong danh sách chưa(check trùng)
        //nếu đã tồn tại thì lấy lại mã câu hỏi khác
        MaCauHoi=getRandomMaCauHoiForLinhVuc(level);
        while (checkRandom(MaCauHoi)) {            
            MaCauHoi=getRandomMaCauHoiForLinhVuc(level);
        }
        doiMauMucThuong();
        DanhSachMaCauHoi.add(MaCauHoi);
        cauHoi=CauHoiDao.findById(MaCauHoi);//lấy câu hỏi theo mã câu hỏi sau
        setModel(cauHoi);//fill câu hỏi lên model
        HienThiButtonTroGiup();
        timecouter();//đếm thời gian
    }
    
    
    //đếm thời gian người chơi chọn đáp án
    public void thoiGianChonDapAnA(String TraLoi){
        time=0;
        thoiGianChonDapAnA = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                time++;
                //sau 5 giây kể từ lúc bắt đầu chọn đáp án, hệ thống sẽ check xem người chơi trả lời đúng hay không
                if (time==5) {
                    try {
                        checkDapAn(TraLoi);
                        thoiGianChonDapAnA.stop();
                    } catch (SQLException ex) {
                        Logger.getLogger(JFrameChoi.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        thoiGianChonDapAnA.start();   
    }
    public void thoiGianChonDapAnB(String TraLoi){
        time=0;
        thoiGianChonDapAnB = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                time++;
                //sau 5 giây kể từ lúc bắt đầu chọn đáp án, hệ thống sẽ check xem người chơi trả lời đúng hay không
                if (time==5) {
                    try {
                        checkDapAn(TraLoi);
                        thoiGianChonDapAnB.stop();
                    } catch (SQLException ex) {
                        Logger.getLogger(JFrameChoi.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        thoiGianChonDapAnB.start();   
    }
    public void thoiGianChonDapAnC(String TraLoi){
        time=0;
        thoiGianChonDapAnC = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                time++;
                //sau 5 giây kể từ lúc bắt đầu chọn đáp án, hệ thống sẽ check xem người chơi trả lời đúng hay không
                if (time==5) {
                    try {
                        checkDapAn(TraLoi);
                        thoiGianChonDapAnC.stop();
                    } catch (SQLException ex) {
                        Logger.getLogger(JFrameChoi.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        thoiGianChonDapAnC.start();   
    }
    public void thoiGianChonDapAnD(String TraLoi){
        time=0;
        thoiGianChonDapAnD= new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                time++;
                //sau 5 giây kể từ lúc bắt đầu chọn đáp án, hệ thống sẽ check xem người chơi trả lời đúng hay không
                if (time==5) {
                    try {
                        checkDapAn(TraLoi);
                        thoiGianChonDapAnD.stop();
                    } catch (SQLException ex) {
                        Logger.getLogger(JFrameChoi.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        thoiGianChonDapAnD.start();   
    }
    
    //hàm set model theo đối tượng được truyền vào
    public void setModel(CauHoi model){
        lblCauHoi.setText(model.getCauHoi());
        btnDapAnA.setText(model.getDapAnA());
        btnDapAnB.setText(model.getDapAnB());
        btnDapAnC.setText(model.getDapAnC());
        btnDapAnD.setText(model.getDapAnD());
    }
    
    //hàm dừng lại lượt chơi
    public void StopGame(){
        StopThoiGianChonCauHoi();
        thoiGianTraLoi.stop();
        if(soCauHoi==-1){//nếu người chơi không trả lời được câu nào đúng
                DialogHelper.alert(this, "Bạn đã không nhận được phần thưởng của chương trình");
        }else if(checkLogin()){
                int diem = ShareHelper.USER.getDiem()+diemThuong[soCauHoi]+1;
                TaiKhoanDao.updateDiem(diem, ShareHelper.USER.getUsername());
                ShareHelper.refreshUser(TaiKhoanDao.findID(ShareHelper.USER.getUsername()));
                DialogHelper.alert(this, "Trò chơi kết thúc, "+diemThuong[soCauHoi]+" điểm đã được cộng vào tài khoản của bạn");
            }else{
                DialogHelper.alert(this, "Trò chơi kết thúc, bạn nhận được "+diemThuong[soCauHoi]+" điểm");
            }
        setVisible(false);
    }
    
    
    public void TroGiupYKienKG(){
        String DapAnA=btnDapAnA.getText();
        String DapAnB=btnDapAnB.getText();
        String DapAnC=btnDapAnC.getText();
        String DapAnD=btnDapAnD.getText();
        String DapAn=cauHoi.getDapAn();
        if (DapAnA.equals(DapAn)) {
            JFrameYKienKGA form = new JFrameYKienKGA(null, true);
            form.setVisible(true);
        }else if (DapAnB.equals(DapAn)) {
            JFrameYKienKGB form = new JFrameYKienKGB(null, true);
            form.setVisible(true);
        }else if (DapAnC.equals(DapAn)) {
            JFrameYKienKGC form = new JFrameYKienKGC(null, true);
            form.setVisible(true);
        }else if (DapAnD.equals(DapAn)) {
            JFrameYKienKGD form = new JFrameYKienKGD(null, true);
            form.setVisible(true);
        }
        btnYKienKG.setEnabled(false);
    }
    public void TroGiup5050(){
        int dem = 0;
        if (!btnDapAnA.getText().equals(cauHoi.getDapAn()) && dem < 2) {
            btnDapAnA.setText("");
            dem++;
        }
        if (!btnDapAnB.getText().equals(cauHoi.getDapAn()) && dem < 2) {
            btnDapAnB.setText("");
            dem++;
        }
        if (!btnDapAnC.getText().equals(cauHoi.getDapAn()) && dem < 2) {
            btnDapAnC.setText("");
            dem++;
        }
        if (!btnDapAnD.getText().equals(cauHoi.getDapAn()) && dem < 2) {
            btnDapAnD.setText("");
            dem++;
        }
        btnLoaiDapAnSai.setEnabled(false);
    }
    /**
     *Hàm dừng đếm thời gian của các button khi thay đổi lựa chọn đáp án
     * 
     */
    public void StopThoiGianChonCauHoi(){
       
        if (DemClickButtonA==1) {
            thoiGianChonDapAnA.stop();
        }
        if (DemClickButtonB==1) {
            thoiGianChonDapAnB.stop();
        }
        if (DemClickButtonC==1) {
            thoiGianChonDapAnC.stop();
        }
        if (DemClickButtonD==1) {
            thoiGianChonDapAnD.stop();
        }
    }   
    public void HienThiButtonTroGiup(){
        if (soCauHoi==2){
            btnCong1Phut.setEnabled(true);
            btnYKienKG.setEnabled(true);
            btnLoaiDapAnSai.setEnabled(true);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelMucThuong = new javax.swing.JPanel();
        lblTitleMucThuong = new javax.swing.JLabel();
        lblMucThuong21000 = new javax.swing.JLabel();
        lblMucThuong15000 = new javax.swing.JLabel();
        lblMucThuong10000 = new javax.swing.JLabel();
        lblMucThuong6000 = new javax.swing.JLabel();
        lblMucThuong3000 = new javax.swing.JLabel();
        lblMucThuong2000 = new javax.swing.JLabel();
        lblMucThuong1000 = new javax.swing.JLabel();
        lblMucThuong600 = new javax.swing.JLabel();
        lblMucThuong400 = new javax.swing.JLabel();
        lblMucThuong200 = new javax.swing.JLabel();
        PanelCauHoi = new javax.swing.JPanel();
        lblTitleCauHoi = new javax.swing.JLabel();
        lblLogo = new javax.swing.JLabel();
        btnLoaiDapAnSai = new javax.swing.JButton();
        btnCong1Phut = new javax.swing.JButton();
        btnYKienKG = new javax.swing.JButton();
        btnDungLai = new javax.swing.JButton();
        PanelTime = new javax.swing.JPanel();
        lblTime = new javax.swing.JLabel();
        lblCauHoi = new javax.swing.JLabel();
        PanelDapAn = new javax.swing.JPanel();
        lblDapAnA = new javax.swing.JLabel();
        lblDapAnC = new javax.swing.JLabel();
        lblDapAnB = new javax.swing.JLabel();
        lblDapAnD = new javax.swing.JLabel();
        btnDapAnA = new javax.swing.JButton();
        btnDapAnC = new javax.swing.JButton();
        btnDapAnB = new javax.swing.JButton();
        btnDapAnD = new javax.swing.JButton();

        PanelMucThuong.setBackground(new java.awt.Color(88, 3, 88));

        lblTitleMucThuong.setFont(new java.awt.Font("Sitka Heading", 3, 24)); // NOI18N
        lblTitleMucThuong.setForeground(new java.awt.Color(102, 153, 255));
        lblTitleMucThuong.setText("Mức thưởng");

        lblMucThuong21000.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMucThuong21000.setForeground(new java.awt.Color(255, 255, 255));
        lblMucThuong21000.setText("10.        21000");

        lblMucThuong15000.setBackground(new java.awt.Color(255, 255, 255));
        lblMucThuong15000.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMucThuong15000.setForeground(new java.awt.Color(255, 255, 255));
        lblMucThuong15000.setText("9.          15000");

        lblMucThuong10000.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMucThuong10000.setForeground(new java.awt.Color(255, 255, 255));
        lblMucThuong10000.setText("8.          10000");

        lblMucThuong6000.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMucThuong6000.setForeground(new java.awt.Color(255, 255, 255));
        lblMucThuong6000.setText("7.            6000");

        lblMucThuong3000.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMucThuong3000.setForeground(new java.awt.Color(255, 255, 255));
        lblMucThuong3000.setText("6.            3000");

        lblMucThuong2000.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMucThuong2000.setForeground(new java.awt.Color(255, 255, 255));
        lblMucThuong2000.setText("5.            2000");

        lblMucThuong1000.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMucThuong1000.setForeground(new java.awt.Color(255, 255, 255));
        lblMucThuong1000.setText("4.            1000");

        lblMucThuong600.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMucThuong600.setForeground(new java.awt.Color(255, 255, 255));
        lblMucThuong600.setText("3.              600");

        lblMucThuong400.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMucThuong400.setForeground(new java.awt.Color(255, 255, 255));
        lblMucThuong400.setText("2.              400");

        lblMucThuong200.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMucThuong200.setForeground(new java.awt.Color(255, 255, 255));
        lblMucThuong200.setText("1.              200");

        javax.swing.GroupLayout PanelMucThuongLayout = new javax.swing.GroupLayout(PanelMucThuong);
        PanelMucThuong.setLayout(PanelMucThuongLayout);
        PanelMucThuongLayout.setHorizontalGroup(
            PanelMucThuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMucThuongLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelMucThuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitleMucThuong)
                    .addGroup(PanelMucThuongLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(PanelMucThuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMucThuong21000)
                            .addComponent(lblMucThuong15000)
                            .addGroup(PanelMucThuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblMucThuong6000, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblMucThuong10000, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(lblMucThuong3000)
                            .addComponent(lblMucThuong2000)
                            .addComponent(lblMucThuong1000)
                            .addComponent(lblMucThuong600)
                            .addComponent(lblMucThuong400)
                            .addComponent(lblMucThuong200))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        PanelMucThuongLayout.setVerticalGroup(
            PanelMucThuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMucThuongLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitleMucThuong, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(lblMucThuong21000)
                .addGap(34, 34, 34)
                .addComponent(lblMucThuong15000)
                .addGap(34, 34, 34)
                .addComponent(lblMucThuong10000)
                .addGap(34, 34, 34)
                .addComponent(lblMucThuong6000)
                .addGap(34, 34, 34)
                .addComponent(lblMucThuong3000)
                .addGap(34, 34, 34)
                .addComponent(lblMucThuong2000)
                .addGap(34, 34, 34)
                .addComponent(lblMucThuong1000)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(lblMucThuong600)
                .addGap(34, 34, 34)
                .addComponent(lblMucThuong400)
                .addGap(34, 34, 34)
                .addComponent(lblMucThuong200)
                .addGap(34, 34, 34))
        );

        PanelCauHoi.setBackground(new java.awt.Color(0, 51, 102));

        lblTitleCauHoi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTitleCauHoi.setForeground(new java.awt.Color(255, 255, 255));
        lblTitleCauHoi.setText("Câu hỏi:");

        lblLogo.setBackground(new java.awt.Color(0, 51, 102));
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ailatrieuphu/Form/Img/2.png"))); // NOI18N

        btnLoaiDapAnSai.setBackground(java.awt.Color.blue);
        btnLoaiDapAnSai.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnLoaiDapAnSai.setForeground(new java.awt.Color(255, 255, 255));
        btnLoaiDapAnSai.setText("50/50");
        btnLoaiDapAnSai.setEnabled(false);
        btnLoaiDapAnSai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoaiDapAnSaiActionPerformed(evt);
            }
        });

        btnCong1Phut.setBackground(java.awt.Color.blue);
        btnCong1Phut.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnCong1Phut.setForeground(new java.awt.Color(255, 255, 255));
        btnCong1Phut.setText("+1 Phút");
        btnCong1Phut.setEnabled(false);
        btnCong1Phut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCong1PhutActionPerformed(evt);
            }
        });

        btnYKienKG.setBackground(java.awt.Color.blue);
        btnYKienKG.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnYKienKG.setForeground(new java.awt.Color(255, 255, 255));
        btnYKienKG.setText("Ý kiến KG");
        btnYKienKG.setEnabled(false);
        btnYKienKG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnYKienKGActionPerformed(evt);
            }
        });

        btnDungLai.setBackground(java.awt.Color.blue);
        btnDungLai.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnDungLai.setForeground(new java.awt.Color(255, 255, 255));
        btnDungLai.setText("Dừng Lại");
        btnDungLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDungLaiActionPerformed(evt);
            }
        });

        PanelTime.setBackground(new java.awt.Color(0, 51, 102));
        PanelTime.setLayout(null);

        lblTime.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblTime.setForeground(java.awt.Color.red);
        lblTime.setText("60");
        PanelTime.add(lblTime);
        lblTime.setBounds(30, 10, 70, 44);

        lblCauHoi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCauHoi.setForeground(new java.awt.Color(255, 255, 255));
        lblCauHoi.setText("Con chó có mấy chân ?");
        lblCauHoi.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblCauHoi.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout PanelCauHoiLayout = new javax.swing.GroupLayout(PanelCauHoi);
        PanelCauHoi.setLayout(PanelCauHoiLayout);
        PanelCauHoiLayout.setHorizontalGroup(
            PanelCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelCauHoiLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(PanelCauHoiLayout.createSequentialGroup()
                        .addComponent(btnCong1Phut, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLoaiDapAnSai, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(PanelTime, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(btnYKienKG, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDungLai, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelCauHoiLayout.createSequentialGroup()
                        .addComponent(lblTitleCauHoi)
                        .addGap(18, 18, 18)
                        .addComponent(lblCauHoi, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelCauHoiLayout.createSequentialGroup()
                .addContainerGap(200, Short.MAX_VALUE)
                .addComponent(lblLogo)
                .addGap(183, 183, 183))
        );
        PanelCauHoiLayout.setVerticalGroup(
            PanelCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelCauHoiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelCauHoiLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(PanelCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelCauHoiLayout.createSequentialGroup()
                                .addGroup(PanelCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnCong1Phut)
                                    .addComponent(btnLoaiDapAnSai))
                                .addGap(14, 14, 14))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelCauHoiLayout.createSequentialGroup()
                                .addGroup(PanelCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnYKienKG)
                                    .addComponent(btnDungLai))
                                .addGap(18, 18, 18))))
                    .addGroup(PanelCauHoiLayout.createSequentialGroup()
                        .addComponent(PanelTime, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)))
                .addGroup(PanelCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitleCauHoi)
                    .addComponent(lblCauHoi, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        PanelDapAn.setBackground(new java.awt.Color(0, 51, 102));

        lblDapAnA.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblDapAnA.setForeground(new java.awt.Color(255, 255, 255));
        lblDapAnA.setText("A");

        lblDapAnC.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblDapAnC.setForeground(new java.awt.Color(255, 255, 255));
        lblDapAnC.setText("C");

        lblDapAnB.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblDapAnB.setForeground(new java.awt.Color(255, 255, 255));
        lblDapAnB.setText("B");

        lblDapAnD.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblDapAnD.setForeground(new java.awt.Color(255, 255, 255));
        lblDapAnD.setText("D");

        btnDapAnA.setBackground(java.awt.Color.blue);
        btnDapAnA.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDapAnA.setForeground(new java.awt.Color(255, 255, 255));
        btnDapAnA.setText("1");
        btnDapAnA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDapAnAActionPerformed(evt);
            }
        });

        btnDapAnC.setBackground(java.awt.Color.blue);
        btnDapAnC.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDapAnC.setForeground(new java.awt.Color(255, 255, 255));
        btnDapAnC.setText("4");
        btnDapAnC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDapAnCActionPerformed(evt);
            }
        });

        btnDapAnB.setBackground(java.awt.Color.blue);
        btnDapAnB.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDapAnB.setForeground(new java.awt.Color(255, 255, 255));
        btnDapAnB.setText("6");
        btnDapAnB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDapAnBActionPerformed(evt);
            }
        });

        btnDapAnD.setBackground(java.awt.Color.blue);
        btnDapAnD.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDapAnD.setForeground(new java.awt.Color(255, 255, 255));
        btnDapAnD.setText("8");
        btnDapAnD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDapAnDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelDapAnLayout = new javax.swing.GroupLayout(PanelDapAn);
        PanelDapAn.setLayout(PanelDapAnLayout);
        PanelDapAnLayout.setHorizontalGroup(
            PanelDapAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDapAnLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelDapAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblDapAnC)
                    .addComponent(lblDapAnA))
                .addGap(18, 18, 18)
                .addGroup(PanelDapAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnDapAnC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDapAnA, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(PanelDapAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDapAnD)
                    .addComponent(lblDapAnB))
                .addGap(18, 18, 18)
                .addGroup(PanelDapAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnDapAnD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDapAnB, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49))
        );
        PanelDapAnLayout.setVerticalGroup(
            PanelDapAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDapAnLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelDapAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDapAnA)
                    .addComponent(lblDapAnB)
                    .addComponent(btnDapAnA, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDapAnB, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelDapAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDapAnC)
                    .addComponent(lblDapAnD)
                    .addComponent(btnDapAnC, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDapAnD, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelDapAn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PanelCauHoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addComponent(PanelMucThuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelMucThuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PanelCauHoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(PanelDapAn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDapAnAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDapAnAActionPerformed
        // TODO add your handling code here:
            
        
        StopThoiGianChonCauHoi();
        DemClickButtonB=0;
        DemClickButtonC=0;
        DemClickButtonD=0;
        DemClickButtonA++;
        if(DemClickButtonA%2==0){//khi người chơi click vào button lần 2 hệ thống sẽ xóa việc lựa chọn button đó
            TraLoi="";
            doiMauButtonDapAn();
            DemClickButtonA=0;
            thoiGianChonDapAnA.restart();
            thoiGianChonDapAnA.stop();
        }else{//khi người chơi click vào button lần đầu hệ thống sẽ đếm thời gian và check đáp án
            btnDapAnA.setBackground(Color.red);
            btnDapAnB.setBackground(Color.blue);
            btnDapAnC.setBackground(Color.blue);
            btnDapAnD.setBackground(Color.blue);
            TraLoi=btnDapAnA.getText();
            thoiGianChonDapAnA(TraLoi);//đếm thời gian, sau 5s hệ thống sẽ check đáp án
        }
        
    }//GEN-LAST:event_btnDapAnAActionPerformed

    private void btnDapAnCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDapAnCActionPerformed
        // TODO add your handling code here:

        StopThoiGianChonCauHoi();
        DemClickButtonA=0;
        DemClickButtonB=0;
        DemClickButtonD=0;
        DemClickButtonC++;
        if(DemClickButtonC%2==0){
            TraLoi="";
            doiMauButtonDapAn();
            DemClickButtonC=0;
            thoiGianChonDapAnC.restart();
            thoiGianChonDapAnC.stop();
        }else{
            btnDapAnA.setBackground(Color.blue);
            btnDapAnB.setBackground(Color.blue);
            btnDapAnC.setBackground(Color.red);
            btnDapAnD.setBackground(Color.blue);
            TraLoi=btnDapAnC.getText();

            thoiGianChonDapAnC(TraLoi);
        }
    }//GEN-LAST:event_btnDapAnCActionPerformed

    private void btnDapAnBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDapAnBActionPerformed
        // TODO add your handling code here:
        

        StopThoiGianChonCauHoi();
        DemClickButtonA=0;
        DemClickButtonC=0;
        DemClickButtonD=0;
        DemClickButtonB++;
        if(DemClickButtonB%2==0){
            TraLoi="";
            doiMauButtonDapAn();
            DemClickButtonB=0;
            thoiGianChonDapAnB.restart();
            thoiGianChonDapAnB.stop();
        }else{
            btnDapAnA.setBackground(Color.blue);
            btnDapAnB.setBackground(Color.red);
            btnDapAnC.setBackground(Color.blue);
            btnDapAnD.setBackground(Color.blue);
            TraLoi=btnDapAnB.getText();

            thoiGianChonDapAnB(TraLoi);
        }
    }//GEN-LAST:event_btnDapAnBActionPerformed

    private void btnDapAnDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDapAnDActionPerformed
        // TODO add your handling code here:
        StopThoiGianChonCauHoi();
        DemClickButtonA=0;
        DemClickButtonB=0;
        DemClickButtonC=0;
        DemClickButtonD++;
        if(DemClickButtonD==0){
            TraLoi="";
            doiMauButtonDapAn();
            thoiGianChonDapAnD.restart();
            thoiGianChonDapAnD.stop();
            DemClickButtonD=0;
        }else{
            btnDapAnA.setBackground(Color.blue);
            btnDapAnB.setBackground(Color.blue);
            btnDapAnC.setBackground(Color.blue);
            btnDapAnD.setBackground(Color.red);
            TraLoi=btnDapAnD.getText();

            thoiGianChonDapAnD(TraLoi);
            
        }
    }//GEN-LAST:event_btnDapAnDActionPerformed

    private void btnCong1PhutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCong1PhutActionPerformed
        // TODO add your handling code here:
        tongtime=tongtime+60;
        btnCong1Phut.setEnabled(false);
        
    }//GEN-LAST:event_btnCong1PhutActionPerformed

    private void btnDungLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDungLaiActionPerformed
        // TODO add your handling code here:
        boolean confirm = DialogHelper.confirm(PanelTime, "Bạn chắc chắn muốn thoát trò chơi ?");
        if(confirm){
            StopGame();
        }
    }//GEN-LAST:event_btnDungLaiActionPerformed

    private void btnLoaiDapAnSaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoaiDapAnSaiActionPerformed
        // TODO add your handling code here:
        TroGiup5050();
    }//GEN-LAST:event_btnLoaiDapAnSaiActionPerformed

    private void btnYKienKGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnYKienKGActionPerformed
        // TODO add your handling code here:
        TroGiupYKienKG();
    }//GEN-LAST:event_btnYKienKGActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelCauHoi;
    private javax.swing.JPanel PanelDapAn;
    private javax.swing.JPanel PanelMucThuong;
    private javax.swing.JPanel PanelTime;
    private javax.swing.JButton btnCong1Phut;
    private javax.swing.JButton btnDapAnA;
    private javax.swing.JButton btnDapAnB;
    private javax.swing.JButton btnDapAnC;
    private javax.swing.JButton btnDapAnD;
    private javax.swing.JButton btnDungLai;
    private javax.swing.JButton btnLoaiDapAnSai;
    private javax.swing.JButton btnYKienKG;
    private javax.swing.JLabel lblCauHoi;
    private javax.swing.JLabel lblDapAnA;
    private javax.swing.JLabel lblDapAnB;
    private javax.swing.JLabel lblDapAnC;
    private javax.swing.JLabel lblDapAnD;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMucThuong1000;
    private javax.swing.JLabel lblMucThuong10000;
    private javax.swing.JLabel lblMucThuong15000;
    private javax.swing.JLabel lblMucThuong200;
    private javax.swing.JLabel lblMucThuong2000;
    private javax.swing.JLabel lblMucThuong21000;
    private javax.swing.JLabel lblMucThuong3000;
    private javax.swing.JLabel lblMucThuong400;
    private javax.swing.JLabel lblMucThuong600;
    private javax.swing.JLabel lblMucThuong6000;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblTitleCauHoi;
    private javax.swing.JLabel lblTitleMucThuong;
    // End of variables declaration//GEN-END:variables
}
