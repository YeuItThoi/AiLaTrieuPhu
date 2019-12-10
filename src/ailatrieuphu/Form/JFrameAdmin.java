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
import ailatrieuphu.Model.TaiKhoan;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author MyPC
 */
public class JFrameAdmin extends javax.swing.JInternalFrame {

    /**
     * Creates new form QuanLyJInternalFrame
     */
    public JFrameAdmin() {
        initComponents();
        this.loadLinhVuc();
        this.loadTaiKhoan();
        this.loadCauHoi();
        this.fillComboBoxLinhVuc();
    }
    
    
    int index =0;
    LinhVucDao lv = new LinhVucDao();
    TaiKhoanDao tk = new TaiKhoanDao();
    CauHoiDao ch = new CauHoiDao();
    
   
    void loadCauHoi(){
        DefaultTableModel model = (DefaultTableModel) tblqlcauhoi.getModel();
        model.setRowCount(0);
        try{
            List<CauHoi> list = ch.select();
            for(CauHoi ch: list){
                Object[] row={
                    ch.getMaCauHoi(),
                    ch.getMaLinhVuc(),
                    ch.getCauHoi(),
                    ch.getDapAnA(),
                    ch.getDapAnB(),
                    ch.getDapAnC(),
                    ch.getDapAnD(),
                    ch.getDapAn(),
                    ch.getLevel()
                };
                model.addRow(row);
            }
        }
        catch(Exception e){
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    void fillComboBoxLinhVuc(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboLinhVuc.getModel(); 
        model.removeAllElements();
        try{
           List<LinhVuc> list = lv.select();
           for(LinhVuc lv: list){
               model.addElement(lv);
           }
        }
        catch(Exception e){
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    void themCauHoi(){
        CauHoi model = getModelch();
       try{
            ch.insert(model);
            this.loadCauHoi();
            this.clearCauHoi();
            DialogHelper.alert(this, "Thêm thành công");
       }
       catch(Exception e){
           DialogHelper.alert(this, "Thêm thất bại");
       }    
    }
    void suaCauHoi(){
        CauHoi model = getModelch();
        try{
            ch.update(model);
            this.loadCauHoi();
            this.clearCauHoi();
            DialogHelper.alert(this, "Cập nhật thành công");
        }
        catch(Exception e){
            DialogHelper.alert(this, "Cập nhật thất bại");
        }
    }
    void xoaCauHoi(){
        Integer mach = Integer.valueOf(cboLinhVuc.getToolTipText());
        try{
            ch.delete(mach);
            this.loadCauHoi();
            this.clearCauHoi();
            DialogHelper.alert(this, "Xóa thành công");
        }
        catch(Exception e){
            DialogHelper.alert(this, "Xóa thất bại");
        }
    }
    void FillCauHoiToModel(){
        try{
            Integer mach = (Integer) tblqlcauhoi.getValueAt(this.index, 0);
            CauHoi model = ch.findById(mach);
            if(model!=null){
                this.setModelch(model);
            }
        }
        catch(Exception e){
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    void clearCauHoi(){
        CauHoi model = new CauHoi();
        LinhVuc linhVuc = (LinhVuc)cboLinhVuc.getSelectedItem();
        model.setMaLinhVuc(linhVuc.getMaLinhVuc());
        this.setModelch(model);
    }
    CauHoi getModelch(){
        CauHoi model = new CauHoi();
        LinhVuc linhvuc = (LinhVuc)cboLinhVuc.getSelectedItem();
        model.setMaLinhVuc(linhvuc.getMaLinhVuc());
        model.setCauHoi(tacauhoi.getText());
        model.setDapAnA(txtdapanA.getText());
        model.setDapAnB(txtdapanB.getText());
        model.setDapAnC(txtdapanC.getText());
        model.setDapAnD(txtdapanD.getText());
        model.setDapAn(txtdapandung.getText());
        model.setLevel(Integer.valueOf(txtlevel.getText()));
        if (txtMaCauHoi.getText().length()>0) {
            model.setMaCauHoi(Integer.parseInt(txtMaCauHoi.getText()));
        }
        
        return model; 
    }
    void setModelch(CauHoi model){
        cboLinhVuc.setToolTipText(String.valueOf(model.getMaCauHoi()));
        cboLinhVuc.setSelectedItem(lv.findID(model.getMaLinhVuc()));
        txtMaCauHoi.setText(String.valueOf(model.getMaCauHoi()));
        tacauhoi.setText(model.getCauHoi());
        txtdapanA.setText(model.getDapAnA());
        txtdapanB.setText(model.getDapAnB());
        txtdapanC.setText(model.getDapAnC());
        txtdapanD.setText(model.getDapAnD());
        txtdapandung.setText(model.getDapAn());
        txtlevel.setText(String.valueOf(model.getLevel()));
    }
    void loadLinhVuc(){
        DefaultTableModel model = (DefaultTableModel) tbllinhvuc.getModel();
        model.setRowCount(0);
        try{
            List<LinhVuc> list = lv.select();
        for(LinhVuc lv:list){
            Object[] row ={
                lv.getMaLinhVuc(),
                lv.getTenLinhVuc()
            };
            model.addRow(row);
        }
        }
        catch(Exception e){
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
        }  
    }
    void clearlv(){
        this.setModellv(new LinhVuc());
    }
    void editlv(){
        try{
            String MaLinhVuc = (String) tbllinhvuc.getValueAt(this.index, 0);
            LinhVuc model = lv.findID(MaLinhVuc);
            if(model!=null){
                this.setModellv(model);
            }
        }
        catch(Exception e){
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    void themlv(){
        LinhVuc model = getModellv();
        try{
            lv.insert(model);
            this.loadLinhVuc();
            this.clearlv();
            DialogHelper.alert(this, "Thêm mới thành công");
        }
        catch(Exception e){
            DialogHelper.alert(this, "Thêm mới thất bại");
        }  
    }
    void sualv(){
        LinhVuc model = getModellv();
        try{
            lv.update(model);
            this.loadLinhVuc();
            this.clearlv();
            DialogHelper.alert(this, "Cập nhật thành công");
        }
        catch(Exception e){
            DialogHelper.alert(this, "Cập nhật thất bại");
        }
    }
    void xoalv(){
       String malv = txtmalinhvuc.getText();
        try{
            lv.delete(malv);
            this.loadLinhVuc();
            this.clearlv();
            DialogHelper.alert(this, "Xóa thành công");
        }
        catch(Exception e){
            DialogHelper.alert(this, "Xóa thất bại");
        }
    }
    LinhVuc getModellv(){
        LinhVuc model = new LinhVuc();
        model.setMaLinhVuc(txtmalinhvuc.getText());
        model.setTenLinhVuc(txttenlinhvuc.getText());
        return model;
    }
    void setModellv(LinhVuc model){
        txtmalinhvuc.setText(model.getMaLinhVuc());
        txttenlinhvuc.setText(model.getTenLinhVuc());
    }
    void loadTaiKhoan(){
        DefaultTableModel model = (DefaultTableModel) tblNguoiChoi.getModel();
        model.setRowCount(0);
        try{
            List<TaiKhoan> list = tk.select();
            for(TaiKhoan tk:list){
                Object[] row = {
                    tk.getUsername(),tk.getPassword(),tk.getHoTen(),tk.getEmail(),tk.getDiem(),tk.getAdmin()?"Admin":"Người Chơi"
                };
                model.addRow(row);
            }
        }
        catch(Exception e){
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
        }     
    }
    void themtk(){
        TaiKhoan model = getModeltk();
        try{
            tk.insert(model);
            this.loadTaiKhoan();
            this.cleartk();
            DialogHelper.alert(this, "Thêm thành công");
        }
        catch(Exception e){
            DialogHelper.alert(this, "Thêm thất bại");
        }
    }
    void suatk(){
        TaiKhoan model = getModeltk();
        try{
            tk.update(model);
            this.loadTaiKhoan();
            this.cleartk();
            DialogHelper.alert(this, "Cập nhật thành công");
        }
        catch(Exception e){
            DialogHelper.alert(this, "Cập nhật thất bại");
        }
    }
    void xoatk(){
        String matk = txtUsername.getText();
        try{
            tk.delete(matk);
            this.loadTaiKhoan();
            this.cleartk();
            DialogHelper.alert(this, "Xóa thành công");
        }
        catch(Exception e){
            DialogHelper.alert(this, "Xóa thất bại");
        }
    }
    void cleartk(){
        this.setModeltk(new TaiKhoan());
    }
    void edittk(){
        try{
            String matk = (String)tblNguoiChoi.getValueAt(this.index, 0);
            TaiKhoan model = tk.findID(matk);
            if(model!=null){
                this.setModeltk(model);
            }
        }
        catch(Exception e){
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    TaiKhoan getModeltk(){
        TaiKhoan model = new TaiKhoan();
        model.setUsername(txtUsername.getText());
        model.setPassword(txtPassword.getText());
        model.setHoTen(txtHoTen.getText());
        model.setEmail(txtEmail.getText());
        model.setDiem(Integer.valueOf(txtDiem.getText()));
        model.setAdmin(rdoAdmin.isSelected());
        return model;
        
    }
    void setModeltk(TaiKhoan model){
        txtUsername.setText(model.getUsername());
        txtPassword.setText(model.getPassword());
        txtHoTen.setText(model.getHoTen());
        txtEmail.setText(model.getEmail());
        txtDiem.setText(String.valueOf(model.getDiem()));
        rdoAdmin.setSelected(model.getAdmin());
        rdoNguoiChoi.setSelected(!model.getAdmin());
        
    }
    public void ThemCauHoiBangFileExcel(){
        //mở form hướng dẫn người dùng;
        JFrameHuongDanChonFileExcel form = new JFrameHuongDanChonFileExcel(null, true);
        form.setVisible(true);
        
        //khai báo các trường dữ liệu vào File 
        
        CauHoi CauHoiExcel = new CauHoi();
        int TongCauHoi=0;
        File excelFile;
        FileInputStream excelFis= null;
        BufferedInputStream excelBis=null;
        XSSFWorkbook excelJtableImport=null;
        
        //Lấy File
        JFileChooser excelFileChooser = new JFileChooser();
        int excelChooser = excelFileChooser.showOpenDialog(null);
        
        //Nếu button được click và được chọn File
        if(excelChooser == JFileChooser.APPROVE_OPTION){
            try {
                
                excelFile = excelFileChooser.getSelectedFile();
                excelFis = new FileInputStream(excelFile);
                excelBis = new BufferedInputStream(excelFis);
                
                
                    excelJtableImport = new XSSFWorkbook(excelBis);
                
                XSSFSheet excelSheet = excelJtableImport.getSheetAt(0);
                for (int row = 1; row < excelSheet.getLastRowNum()+1; row++) {//row=1 vì bỏ đi dòng đầu tiên của file excel(tên cột)
                    try {
                    TongCauHoi++;//đếm tổng số câu hỏi được thêm và database
                    XSSFRow excelRow = excelSheet.getRow(row);
                    //Lấy dữ liệu của từng ô trong 1 dòng rồi gán vào trường
                    XSSFCell MaLinhVuc = excelRow.getCell(0);
                    XSSFCell CauHoi = excelRow.getCell(1);
                    XSSFCell DapAnA = excelRow.getCell(2);
                    XSSFCell DapAnB = excelRow.getCell(3);
                    XSSFCell DapAnC = excelRow.getCell(4);
                    XSSFCell DapAnD = excelRow.getCell(5);
                    XSSFCell DapAn = excelRow.getCell(6);
                    XSSFCell Level = excelRow.getCell(7);
                    
                    //Lấy dữ liệu từ các trường gán vào các trường của đối tượng CauHoiExecl
                    int lv=(int) Level.getNumericCellValue();
                    CauHoiExcel.setMaLinhVuc(MaLinhVuc.getStringCellValue());
                    CauHoiExcel.setCauHoi(CauHoi.getStringCellValue());
                    CauHoiExcel.setDapAnA(DapAnA.getStringCellValue());
                    CauHoiExcel.setDapAnB(DapAnB.getStringCellValue());
                    CauHoiExcel.setDapAnC(DapAnC.getStringCellValue());
                    CauHoiExcel.setDapAnD(DapAnD.getStringCellValue());
                    CauHoiExcel.setDapAn(DapAn.getStringCellValue());
                    CauHoiExcel.setLevel(lv);
                    //Insert dữ liệu vào database 
                    ch.insert(CauHoiExcel);
                    } catch (Exception e) {
                        DialogHelper.alert(form, "Sai dữ liệu của câu hỏi "+TongCauHoi+", Đã thêm "+(TongCauHoi-1)+" Câu hỏi");
                    }
                }
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                
            }
            JOptionPane.showMessageDialog(rootPane, "Bạn đã thêm "+TongCauHoi+" câu hỏi");
        }
        //Load lại table
        loadCauHoi();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupQuyen = new javax.swing.ButtonGroup();
        TabbedPane = new javax.swing.JTabbedPane();
        PanelCauHoi = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblqlcauhoi = new javax.swing.JTable();
        PanelChucNangCauHoi = new javax.swing.JPanel();
        lblDapAnA = new javax.swing.JLabel();
        lblDapAnB = new javax.swing.JLabel();
        lblDapAnC = new javax.swing.JLabel();
        lblDapAnD = new javax.swing.JLabel();
        lblDapAn = new javax.swing.JLabel();
        txtdapanA = new javax.swing.JTextField();
        txtdapanB = new javax.swing.JTextField();
        txtdapanC = new javax.swing.JTextField();
        txtdapanD = new javax.swing.JTextField();
        txtdapandung = new javax.swing.JTextField();
        lblLevel = new javax.swing.JLabel();
        txtlevel = new javax.swing.JTextField();
        btnThemCauHoi = new javax.swing.JButton();
        btnSuaCauHoi = new javax.swing.JButton();
        btnXoaCauHoi = new javax.swing.JButton();
        btnMoiCauHoi = new javax.swing.JButton();
        lblLinhVuc = new javax.swing.JLabel();
        cboLinhVuc = new javax.swing.JComboBox();
        btnChonFileExcel = new javax.swing.JButton();
        txtMaCauHoi = new javax.swing.JFormattedTextField();
        ScrollPaneCauHoi = new javax.swing.JScrollPane();
        tacauhoi = new javax.swing.JEditorPane();
        lblCauHoi = new javax.swing.JLabel();
        PanelNguoiChoi = new javax.swing.JPanel();
        ScrollPaneTaiKhoan = new javax.swing.JScrollPane();
        tblNguoiChoi = new javax.swing.JTable();
        PanelChucNangTaiKhoan = new javax.swing.JPanel();
        txtUsername = new javax.swing.JFormattedTextField();
        lblUsername = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JFormattedTextField();
        txtHoTen = new javax.swing.JFormattedTextField();
        lblHoTen = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JFormattedTextField();
        txtDiem = new javax.swing.JFormattedTextField();
        lblDiem = new javax.swing.JLabel();
        lblQuyen = new javax.swing.JLabel();
        rdoAdmin = new javax.swing.JRadioButton();
        rdoNguoiChoi = new javax.swing.JRadioButton();
        btnThemTaiKhoan = new javax.swing.JButton();
        btnSuaTaiKhoan = new javax.swing.JButton();
        btnXoaTaiKhoan = new javax.swing.JButton();
        btnMoiTaiKhoan = new javax.swing.JButton();
        PanelLinhVuc = new javax.swing.JPanel();
        ScrollPaneLinhVuc = new javax.swing.JScrollPane();
        tbllinhvuc = new javax.swing.JTable();
        btnThemLinhVuc = new javax.swing.JButton();
        btnSuaLinhVuc = new javax.swing.JButton();
        btnXoaLinhVuc = new javax.swing.JButton();
        btnMoiLinhVuc = new javax.swing.JButton();
        lblMaLinhVuc = new javax.swing.JLabel();
        lblTenLinhVuc = new javax.swing.JLabel();
        txtmalinhvuc = new javax.swing.JTextField();
        txttenlinhvuc = new javax.swing.JTextField();

        setClosable(true);
        setTitle("QUẢN LÝ");

        TabbedPane.setBackground(new java.awt.Color(102, 0, 102));

        PanelCauHoi.setLayout(null);

        tblqlcauhoi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Câu Hỏi", "Lĩnh vực", "Câu Hỏi", "Đáp án A", "Đáp án B", "Đáp án C", "Đáp án D", "Đáp Án", "Level"
            }
        ));
        tblqlcauhoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblqlcauhoiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblqlcauhoi);

        PanelCauHoi.add(jScrollPane1);
        jScrollPane1.setBounds(12, 8, 712, 216);

        PanelChucNangCauHoi.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblDapAnA.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblDapAnA.setForeground(java.awt.Color.blue);
        lblDapAnA.setText("A.");

        lblDapAnB.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblDapAnB.setForeground(java.awt.Color.blue);
        lblDapAnB.setText("B.");

        lblDapAnC.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblDapAnC.setForeground(java.awt.Color.blue);
        lblDapAnC.setText("C.");

        lblDapAnD.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblDapAnD.setForeground(java.awt.Color.blue);
        lblDapAnD.setText("D.");

        lblDapAn.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblDapAn.setForeground(java.awt.Color.blue);
        lblDapAn.setText("Đáp án ");

        lblLevel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblLevel.setForeground(java.awt.Color.blue);
        lblLevel.setText("Level");

        btnThemCauHoi.setBackground(java.awt.Color.blue);
        btnThemCauHoi.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnThemCauHoi.setForeground(new java.awt.Color(255, 255, 255));
        btnThemCauHoi.setText("Thêm");
        btnThemCauHoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemCauHoiActionPerformed(evt);
            }
        });

        btnSuaCauHoi.setBackground(java.awt.Color.blue);
        btnSuaCauHoi.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnSuaCauHoi.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaCauHoi.setText("Sửa");
        btnSuaCauHoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaCauHoiActionPerformed(evt);
            }
        });

        btnXoaCauHoi.setBackground(java.awt.Color.blue);
        btnXoaCauHoi.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnXoaCauHoi.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaCauHoi.setText("Xóa");
        btnXoaCauHoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaCauHoiActionPerformed(evt);
            }
        });

        btnMoiCauHoi.setBackground(java.awt.Color.blue);
        btnMoiCauHoi.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnMoiCauHoi.setForeground(new java.awt.Color(255, 255, 255));
        btnMoiCauHoi.setText("Mới");
        btnMoiCauHoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiCauHoiActionPerformed(evt);
            }
        });

        lblLinhVuc.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblLinhVuc.setForeground(java.awt.Color.blue);
        lblLinhVuc.setText("Lĩnh vực");

        cboLinhVuc.setBackground(java.awt.Color.blue);
        cboLinhVuc.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        cboLinhVuc.setForeground(new java.awt.Color(255, 255, 255));
        cboLinhVuc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Thể thao", "Địa lý", "Động vật" }));

        btnChonFileExcel.setBackground(java.awt.Color.blue);
        btnChonFileExcel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnChonFileExcel.setForeground(new java.awt.Color(255, 255, 255));
        btnChonFileExcel.setText("Thêm Câu Hỏi Bằng File Excel");
        btnChonFileExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonFileExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelChucNangCauHoiLayout = new javax.swing.GroupLayout(PanelChucNangCauHoi);
        PanelChucNangCauHoi.setLayout(PanelChucNangCauHoiLayout);
        PanelChucNangCauHoiLayout.setHorizontalGroup(
            PanelChucNangCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelChucNangCauHoiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelChucNangCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelChucNangCauHoiLayout.createSequentialGroup()
                        .addGroup(PanelChucNangCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDapAnA)
                            .addComponent(lblDapAnB)
                            .addComponent(lblDapAnC)
                            .addComponent(lblDapAnD))
                        .addGap(33, 33, 33))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelChucNangCauHoiLayout.createSequentialGroup()
                        .addComponent(lblLinhVuc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(PanelChucNangCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboLinhVuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelChucNangCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtdapanD, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                        .addComponent(txtdapanA, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtdapanB, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtdapanC, javax.swing.GroupLayout.Alignment.LEADING)))
                .addGap(58, 58, 58)
                .addGroup(PanelChucNangCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelChucNangCauHoiLayout.createSequentialGroup()
                        .addGroup(PanelChucNangCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDapAn)
                            .addComponent(lblLevel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(PanelChucNangCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtdapandung, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtlevel))
                        .addGap(51, 51, 51))
                    .addGroup(PanelChucNangCauHoiLayout.createSequentialGroup()
                        .addGroup(PanelChucNangCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnChonFileExcel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelChucNangCauHoiLayout.createSequentialGroup()
                                .addGroup(PanelChucNangCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnThemCauHoi, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                                    .addComponent(btnXoaCauHoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(PanelChucNangCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btnMoiCauHoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnSuaCauHoi, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        PanelChucNangCauHoiLayout.setVerticalGroup(
            PanelChucNangCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelChucNangCauHoiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelChucNangCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblLevel)
                    .addGroup(PanelChucNangCauHoiLayout.createSequentialGroup()
                        .addGroup(PanelChucNangCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDapAnA)
                            .addComponent(txtdapanA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDapAn))
                        .addGap(18, 18, 18)
                        .addGroup(PanelChucNangCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDapAnB)
                            .addComponent(txtdapanB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PanelChucNangCauHoiLayout.createSequentialGroup()
                        .addComponent(txtdapandung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtlevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(PanelChucNangCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelChucNangCauHoiLayout.createSequentialGroup()
                        .addGroup(PanelChucNangCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDapAnC)
                            .addComponent(txtdapanC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(PanelChucNangCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDapAnD)
                            .addComponent(txtdapanD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(PanelChucNangCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblLinhVuc)
                            .addComponent(cboLinhVuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PanelChucNangCauHoiLayout.createSequentialGroup()
                        .addGroup(PanelChucNangCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThemCauHoi)
                            .addComponent(btnSuaCauHoi))
                        .addGap(18, 18, 18)
                        .addGroup(PanelChucNangCauHoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnXoaCauHoi)
                            .addComponent(btnMoiCauHoi))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnChonFileExcel)))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        PanelCauHoi.add(PanelChucNangCauHoi);
        PanelChucNangCauHoi.setBounds(212, 272, 512, 230);

        txtMaCauHoi.setEditable(false);
        txtMaCauHoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaCauHoiActionPerformed(evt);
            }
        });
        PanelCauHoi.add(txtMaCauHoi);
        txtMaCauHoi.setBounds(80, 230, 64, 30);

        ScrollPaneCauHoi.setViewportView(tacauhoi);

        PanelCauHoi.add(ScrollPaneCauHoi);
        ScrollPaneCauHoi.setBounds(12, 272, 182, 230);

        lblCauHoi.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblCauHoi.setForeground(java.awt.Color.blue);
        lblCauHoi.setText("Câu Hỏi: ");
        PanelCauHoi.add(lblCauHoi);
        lblCauHoi.setBounds(12, 240, 57, 16);

        TabbedPane.addTab("Câu hỏi", PanelCauHoi);

        tblNguoiChoi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "User", "Pass", "Name", "Email", "Quyền"
            }
        ));
        tblNguoiChoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNguoiChoiMouseClicked(evt);
            }
        });
        ScrollPaneTaiKhoan.setViewportView(tblNguoiChoi);

        lblUsername.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblUsername.setForeground(java.awt.Color.blue);
        lblUsername.setText("Username");

        lblPassword.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblPassword.setForeground(java.awt.Color.blue);
        lblPassword.setText("Password");

        lblHoTen.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblHoTen.setForeground(java.awt.Color.blue);
        lblHoTen.setText("Họ Tên");

        lblEmail.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblEmail.setForeground(java.awt.Color.blue);
        lblEmail.setText("Email");

        lblDiem.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblDiem.setForeground(java.awt.Color.blue);
        lblDiem.setText("Diem");

        lblQuyen.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblQuyen.setForeground(java.awt.Color.blue);
        lblQuyen.setText("Quyền");

        buttonGroupQuyen.add(rdoAdmin);
        rdoAdmin.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        rdoAdmin.setForeground(java.awt.Color.blue);
        rdoAdmin.setText("Admin");

        buttonGroupQuyen.add(rdoNguoiChoi);
        rdoNguoiChoi.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        rdoNguoiChoi.setForeground(java.awt.Color.blue);
        rdoNguoiChoi.setText("Người Chơi");

        btnThemTaiKhoan.setBackground(java.awt.Color.blue);
        btnThemTaiKhoan.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnThemTaiKhoan.setForeground(new java.awt.Color(255, 255, 255));
        btnThemTaiKhoan.setText("Thêm");
        btnThemTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemTaiKhoanActionPerformed(evt);
            }
        });

        btnSuaTaiKhoan.setBackground(java.awt.Color.blue);
        btnSuaTaiKhoan.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnSuaTaiKhoan.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaTaiKhoan.setText("Sửa");
        btnSuaTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaTaiKhoanActionPerformed(evt);
            }
        });

        btnXoaTaiKhoan.setBackground(java.awt.Color.blue);
        btnXoaTaiKhoan.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnXoaTaiKhoan.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaTaiKhoan.setText("Xóa");
        btnXoaTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTaiKhoanActionPerformed(evt);
            }
        });

        btnMoiTaiKhoan.setBackground(java.awt.Color.blue);
        btnMoiTaiKhoan.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnMoiTaiKhoan.setForeground(new java.awt.Color(255, 255, 255));
        btnMoiTaiKhoan.setText("Mới");
        btnMoiTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiTaiKhoanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelChucNangTaiKhoanLayout = new javax.swing.GroupLayout(PanelChucNangTaiKhoan);
        PanelChucNangTaiKhoan.setLayout(PanelChucNangTaiKhoanLayout);
        PanelChucNangTaiKhoanLayout.setHorizontalGroup(
            PanelChucNangTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelChucNangTaiKhoanLayout.createSequentialGroup()
                .addGroup(PanelChucNangTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThemTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(PanelChucNangTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSuaTaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMoiTaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(PanelChucNangTaiKhoanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelChucNangTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelChucNangTaiKhoanLayout.createSequentialGroup()
                        .addGroup(PanelChucNangTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblHoTen)
                            .addComponent(lblEmail))
                        .addGap(43, 43, 43)
                        .addGroup(PanelChucNangTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDiem)
                            .addGroup(PanelChucNangTaiKhoanLayout.createSequentialGroup()
                                .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 3, Short.MAX_VALUE))
                            .addComponent(txtEmail)))
                    .addGroup(PanelChucNangTaiKhoanLayout.createSequentialGroup()
                        .addGroup(PanelChucNangTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblQuyen)
                            .addGroup(PanelChucNangTaiKhoanLayout.createSequentialGroup()
                                .addGap(125, 125, 125)
                                .addComponent(rdoAdmin)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rdoNguoiChoi))
                    .addGroup(PanelChucNangTaiKhoanLayout.createSequentialGroup()
                        .addGroup(PanelChucNangTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDiem)
                            .addGroup(PanelChucNangTaiKhoanLayout.createSequentialGroup()
                                .addGroup(PanelChucNangTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblUsername)
                                    .addComponent(lblPassword))
                                .addGap(26, 26, 26)
                                .addGroup(PanelChucNangTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                                    .addComponent(txtPassword))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(28, 28, 28))
        );
        PanelChucNangTaiKhoanLayout.setVerticalGroup(
            PanelChucNangTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelChucNangTaiKhoanLayout.createSequentialGroup()
                .addGroup(PanelChucNangTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsername)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelChucNangTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelChucNangTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHoTen))
                .addGap(18, 18, 18)
                .addGroup(PanelChucNangTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmail))
                .addGap(17, 17, 17)
                .addGroup(PanelChucNangTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDiem)
                    .addComponent(txtDiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelChucNangTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoAdmin)
                    .addComponent(rdoNguoiChoi)
                    .addComponent(lblQuyen))
                .addGap(18, 18, 18)
                .addGroup(PanelChucNangTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemTaiKhoan)
                    .addComponent(btnSuaTaiKhoan))
                .addGap(18, 18, 18)
                .addGroup(PanelChucNangTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoaTaiKhoan)
                    .addComponent(btnMoiTaiKhoan))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelNguoiChoiLayout = new javax.swing.GroupLayout(PanelNguoiChoi);
        PanelNguoiChoi.setLayout(PanelNguoiChoiLayout);
        PanelNguoiChoiLayout.setHorizontalGroup(
            PanelNguoiChoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNguoiChoiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ScrollPaneTaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, 713, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelNguoiChoiLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(PanelChucNangTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(168, 168, 168))
        );
        PanelNguoiChoiLayout.setVerticalGroup(
            PanelNguoiChoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNguoiChoiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ScrollPaneTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelChucNangTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        TabbedPane.addTab("Tài Khoản", PanelNguoiChoi);

        tbllinhvuc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã lĩnh vực", "Tên lĩnh vực"
            }
        ));
        tbllinhvuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbllinhvucMouseClicked(evt);
            }
        });
        ScrollPaneLinhVuc.setViewportView(tbllinhvuc);

        btnThemLinhVuc.setBackground(java.awt.Color.blue);
        btnThemLinhVuc.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnThemLinhVuc.setForeground(new java.awt.Color(255, 255, 255));
        btnThemLinhVuc.setText("Thêm");
        btnThemLinhVuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemLinhVucActionPerformed(evt);
            }
        });

        btnSuaLinhVuc.setBackground(java.awt.Color.blue);
        btnSuaLinhVuc.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnSuaLinhVuc.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaLinhVuc.setText("Sửa");
        btnSuaLinhVuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaLinhVucActionPerformed(evt);
            }
        });

        btnXoaLinhVuc.setBackground(java.awt.Color.blue);
        btnXoaLinhVuc.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnXoaLinhVuc.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaLinhVuc.setText("Xóa");
        btnXoaLinhVuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaLinhVucActionPerformed(evt);
            }
        });

        btnMoiLinhVuc.setBackground(java.awt.Color.blue);
        btnMoiLinhVuc.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnMoiLinhVuc.setForeground(new java.awt.Color(255, 255, 255));
        btnMoiLinhVuc.setText("Mới");
        btnMoiLinhVuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiLinhVucActionPerformed(evt);
            }
        });

        lblMaLinhVuc.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblMaLinhVuc.setForeground(java.awt.Color.blue);
        lblMaLinhVuc.setText("Mã lĩnh vực :");

        lblTenLinhVuc.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblTenLinhVuc.setForeground(java.awt.Color.blue);
        lblTenLinhVuc.setText("Tên lĩnh vực :");

        javax.swing.GroupLayout PanelLinhVucLayout = new javax.swing.GroupLayout(PanelLinhVuc);
        PanelLinhVuc.setLayout(PanelLinhVucLayout);
        PanelLinhVucLayout.setHorizontalGroup(
            PanelLinhVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLinhVucLayout.createSequentialGroup()
                .addGap(0, 1, Short.MAX_VALUE)
                .addGroup(PanelLinhVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ScrollPaneLinhVuc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)
                    .addGroup(PanelLinhVucLayout.createSequentialGroup()
                        .addGroup(PanelLinhVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(PanelLinhVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelLinhVucLayout.createSequentialGroup()
                                    .addComponent(lblTenLinhVuc)
                                    .addGap(199, 199, 199))
                                .addGroup(PanelLinhVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(PanelLinhVucLayout.createSequentialGroup()
                                        .addComponent(lblMaLinhVuc)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtmalinhvuc, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelLinhVucLayout.createSequentialGroup()
                                        .addGap(106, 106, 106)
                                        .addComponent(txttenlinhvuc, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(PanelLinhVucLayout.createSequentialGroup()
                                .addGap(237, 237, 237)
                                .addGroup(PanelLinhVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnXoaLinhVuc, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnThemLinhVuc, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanelLinhVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btnMoiLinhVuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnSuaLinhVuc, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        PanelLinhVucLayout.setVerticalGroup(
            PanelLinhVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLinhVucLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ScrollPaneLinhVuc, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addGroup(PanelLinhVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelLinhVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblMaLinhVuc)
                        .addComponent(txtmalinhvuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelLinhVucLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(PanelLinhVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txttenlinhvuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTenLinhVuc))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addGroup(PanelLinhVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemLinhVuc)
                    .addComponent(btnMoiLinhVuc))
                .addGap(31, 31, 31)
                .addGroup(PanelLinhVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSuaLinhVuc)
                    .addComponent(btnXoaLinhVuc))
                .addGap(87, 87, 87))
        );

        TabbedPane.addTab("Lĩnh vực", PanelLinhVuc);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabbedPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabbedPane)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemLinhVucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemLinhVucActionPerformed
        // TODO add your handling code here:
        if(txtmalinhvuc.getText().length()==0){
            DialogHelper.alert(this, "Không để trống mã lĩnh vực!");
            return;
        }
        if(txttenlinhvuc.getText().length()==0){
            DialogHelper.alert(this, "Không để trống tên lĩnh vực!");
            return;
        }
        themlv();
    }//GEN-LAST:event_btnThemLinhVucActionPerformed

    private void btnSuaLinhVucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaLinhVucActionPerformed
        // TODO add your handling code here:
        if(txtmalinhvuc.getText().length()==0){
            DialogHelper.alert(this, "Không để trống mã lĩnh vực!");
            return;
        }
        if(txttenlinhvuc.getText().length()==0){
            DialogHelper.alert(this, "Không để trống tên lĩnh vực!");
            return;
        }
        sualv();
        
    }//GEN-LAST:event_btnSuaLinhVucActionPerformed

    private void btnXoaLinhVucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaLinhVucActionPerformed
        // TODO add your handling code here:
        xoalv();
    }//GEN-LAST:event_btnXoaLinhVucActionPerformed

    private void btnMoiLinhVucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiLinhVucActionPerformed
        // TODO add your handling code here:
        clearlv();
    }//GEN-LAST:event_btnMoiLinhVucActionPerformed

    private void btnThemTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemTaiKhoanActionPerformed
        // TODO add your handling code here:
        if(txtUsername.getText().length()==0){
            DialogHelper.alert(this, "Không để trống Username!");
            return;
        }
        if(txtPassword.getText().length()==0){
            DialogHelper.alert(this, "Không để trống Password!");
            return;
        }
        if(txtPassword.getText().length()<5){
            DialogHelper.alert(this,"Mật khẩu phải chứa ít nhất 5 kí tự!!");
            return;
        }
        if(txtHoTen.getText().length()==0){
            DialogHelper.alert(this, "Không để trống Name!");
            return;
        }
        if(txtEmail.getText().length()==0){
            DialogHelper.alert(this, "Không để trống Email!");
            return;
        }
        if(!txtEmail.getText().matches("\\w+@\\w+(\\.\\w+)")){
            DialogHelper.alert(this,"Sai định dang email!");
            return;
        }
        themtk();
    }//GEN-LAST:event_btnThemTaiKhoanActionPerformed

    private void btnSuaTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaTaiKhoanActionPerformed
        // TODO add your handling code here:
        if(txtUsername.getText().length()==0){
            DialogHelper.alert(this, "Không để trống Username!");
            return;
        }
        if(txtPassword.getText().length()==0){
            DialogHelper.alert(this, "Không để trống Password!");
            return;
        }
        if(txtPassword.getText().length()<5){
            DialogHelper.alert(this,"Mật khẩu phải chứa ít nhất 5 kí tự!!");
            return;
        }
        if(txtHoTen.getText().length()==0){
            DialogHelper.alert(this, "Không để trống Name!");
            return;
        }
        if(txtEmail.getText().length()==0){
            DialogHelper.alert(this, "Không để trống Email!");
            return;
        }
        if(!txtEmail.getText().matches("\\w+@\\w+(\\.\\w+)")){
            DialogHelper.alert(this,"Sai định dang email!");
            return;
        }
        suatk();
        ShareHelper.refreshUser(tk.findID(ShareHelper.USER.getUsername()));
    }//GEN-LAST:event_btnSuaTaiKhoanActionPerformed

    private void btnXoaTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTaiKhoanActionPerformed
        // TODO add your handling code here:
        xoatk();
    }//GEN-LAST:event_btnXoaTaiKhoanActionPerformed

    private void btnMoiTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiTaiKhoanActionPerformed
        // TODO add your handling code here:
        cleartk();
    }//GEN-LAST:event_btnMoiTaiKhoanActionPerformed

    private void btnThemCauHoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemCauHoiActionPerformed
        // TODO add your handling code here:
        String ch = tacauhoi.getText();
        String dapanA = txtdapanA.getText();
        String dapanB = txtdapanB.getText();
        String dapanC = txtdapanC.getText();
        String dapanD = txtdapanD.getText();
        String dapandung = txtdapandung.getText();
        String level = txtlevel.getText();
        if(ch.length()==0){
            DialogHelper.alert(this, "Không để trống câu hỏi!");
            return;
        }
        if(dapanA.length()==0){
            DialogHelper.alert(this, "Không để trống đáp án A!");
            return;
        }
        if(dapanB.length()==0){
            DialogHelper.alert(this, "Không để trống đáp án B!");
            return;
        }
        if(dapanC.length()==0){
            DialogHelper.alert(this, "Không để trống đáp án C!");
            return;
        }
        if(dapanD.length()==0){
            DialogHelper.alert(this, "Không để trống đáp án D!");
            return;
        }
        if(dapandung.length()==0){
            DialogHelper.alert(this, "Không để trống đáp án đúng!");
            return;
        }
        if(level.length()==0){
            DialogHelper.alert(this, "Không để trống level!");
            return;
        }
        themCauHoi();
    }//GEN-LAST:event_btnThemCauHoiActionPerformed

    private void btnSuaCauHoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaCauHoiActionPerformed
        // TODO add your handling code here:
        String ch = tacauhoi.getText();
        String dapanA = txtdapanA.getText();
        String dapanB = txtdapanB.getText();
        String dapanC = txtdapanC.getText();
        String dapanD = txtdapanD.getText();
        String dapandung = txtdapandung.getText();
        String level = txtlevel.getText();
        if(ch.length()==0){
            DialogHelper.alert(this, "Không để trống câu hỏi!");
            return;
        }
        if(dapanA.length()==0){
            DialogHelper.alert(this, "Không để trống đáp án A!");
            return;
        }
        if(dapanB.length()==0){
            DialogHelper.alert(this, "Không để trống đáp án B!");
            return;
        }
        if(dapanC.length()==0){
            DialogHelper.alert(this, "Không để trống đáp án C!");
            return;
        }
        if(dapanD.length()==0){
            DialogHelper.alert(this, "Không để trống đáp án D!");
            return;
        }
        if(dapandung.length()==0){
            DialogHelper.alert(this, "Không để trống đáp án đúng!");
            return;
        }
        if(level.length()==0){
            DialogHelper.alert(this, "Không để trống level!");
            return;
        }
        suaCauHoi();
    }//GEN-LAST:event_btnSuaCauHoiActionPerformed

    private void btnXoaCauHoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaCauHoiActionPerformed
        // TODO add your handling code here:
        xoaCauHoi();
    }//GEN-LAST:event_btnXoaCauHoiActionPerformed

    private void btnMoiCauHoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiCauHoiActionPerformed
        // TODO add your handling code here:
        clearCauHoi();
    }//GEN-LAST:event_btnMoiCauHoiActionPerformed

    private void tbllinhvucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbllinhvucMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()==1){
            this.index = tbllinhvuc.rowAtPoint(evt.getPoint());
            if(this.index>=0){
                this.editlv();
            }
            
        }
    }//GEN-LAST:event_tbllinhvucMouseClicked

    private void tblNguoiChoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNguoiChoiMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()==1){
            this.index = tblNguoiChoi.rowAtPoint(evt.getPoint());
            if(this.index>=0){
                this.edittk();
            }
        }
    }//GEN-LAST:event_tblNguoiChoiMouseClicked

    private void tblqlcauhoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblqlcauhoiMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()==1){
            this.index = tblqlcauhoi.rowAtPoint(evt.getPoint());
            if(this.index>=0){
                this.FillCauHoiToModel();
            }
        }
    }//GEN-LAST:event_tblqlcauhoiMouseClicked

    private void btnChonFileExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonFileExcelActionPerformed
        // TODO add your handling code here:
        ThemCauHoiBangFileExcel();
    }//GEN-LAST:event_btnChonFileExcelActionPerformed

    private void txtMaCauHoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaCauHoiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaCauHoiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelCauHoi;
    private javax.swing.JPanel PanelChucNangCauHoi;
    private javax.swing.JPanel PanelChucNangTaiKhoan;
    private javax.swing.JPanel PanelLinhVuc;
    private javax.swing.JPanel PanelNguoiChoi;
    private javax.swing.JScrollPane ScrollPaneCauHoi;
    private javax.swing.JScrollPane ScrollPaneLinhVuc;
    private javax.swing.JScrollPane ScrollPaneTaiKhoan;
    private javax.swing.JTabbedPane TabbedPane;
    private javax.swing.JButton btnChonFileExcel;
    private javax.swing.JButton btnMoiCauHoi;
    private javax.swing.JButton btnMoiLinhVuc;
    private javax.swing.JButton btnMoiTaiKhoan;
    private javax.swing.JButton btnSuaCauHoi;
    private javax.swing.JButton btnSuaLinhVuc;
    private javax.swing.JButton btnSuaTaiKhoan;
    private javax.swing.JButton btnThemCauHoi;
    private javax.swing.JButton btnThemLinhVuc;
    private javax.swing.JButton btnThemTaiKhoan;
    private javax.swing.JButton btnXoaCauHoi;
    private javax.swing.JButton btnXoaLinhVuc;
    private javax.swing.JButton btnXoaTaiKhoan;
    private javax.swing.ButtonGroup buttonGroupQuyen;
    private javax.swing.JComboBox cboLinhVuc;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCauHoi;
    private javax.swing.JLabel lblDapAn;
    private javax.swing.JLabel lblDapAnA;
    private javax.swing.JLabel lblDapAnB;
    private javax.swing.JLabel lblDapAnC;
    private javax.swing.JLabel lblDapAnD;
    private javax.swing.JLabel lblDiem;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblHoTen;
    private javax.swing.JLabel lblLevel;
    private javax.swing.JLabel lblLinhVuc;
    private javax.swing.JLabel lblMaLinhVuc;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblQuyen;
    private javax.swing.JLabel lblTenLinhVuc;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JRadioButton rdoAdmin;
    private javax.swing.JRadioButton rdoNguoiChoi;
    private javax.swing.JEditorPane tacauhoi;
    private javax.swing.JTable tblNguoiChoi;
    private javax.swing.JTable tbllinhvuc;
    private javax.swing.JTable tblqlcauhoi;
    private javax.swing.JFormattedTextField txtDiem;
    private javax.swing.JFormattedTextField txtEmail;
    private javax.swing.JFormattedTextField txtHoTen;
    private javax.swing.JFormattedTextField txtMaCauHoi;
    private javax.swing.JFormattedTextField txtPassword;
    private javax.swing.JFormattedTextField txtUsername;
    private javax.swing.JTextField txtdapanA;
    private javax.swing.JTextField txtdapanB;
    private javax.swing.JTextField txtdapanC;
    private javax.swing.JTextField txtdapanD;
    private javax.swing.JTextField txtdapandung;
    private javax.swing.JTextField txtlevel;
    private javax.swing.JTextField txtmalinhvuc;
    private javax.swing.JTextField txttenlinhvuc;
    // End of variables declaration//GEN-END:variables
}
