/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panel;

import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static panel.GetData.NameDB;
import static panel.GetData.NameSarver;
import static panel.GetData.User_mass;

import static panel.GetData.listModel;
import static panel.GetData.login;
import static panel.GetData.number_port;
import static panel.GetData.pass;

/**
 *
 * @author Дима
 */
public class RemoveNameUser extends javax.swing.JFrame {

    /**
     * Creates new form RemoveNameUser
     */
    public String NameP = "";

    public RemoveNameUser() {
        initComponents();
        NameP = NameDB;
        setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        jTextField1.setText(NameP);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Имя");

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jButton1.setText("Сохранить");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(27, 27, 27)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
            .addGroup(layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jButton1)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
boolean is_connect = false;
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (jTextField1.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Не введено имя", "", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (!NameP.equals(jTextField1.getText())) {

            Setting_S setting = new Setting_S();

            setting.setVisible(false);
            setting.dispatchEvent(new WindowEvent(setting, WindowEvent.WINDOW_CLOSING));

            Setting_S.set();

            int vl = User_mass.get(NameP);
            User_mass.remove(NameP);
            User_mass.put(jTextField1.getText(), vl);

            listModel.removeAllElements();
            for (String name : User_mass.keySet()) {
                listModel.addElement(name);
            }

            Connection c = null;
            Statement stmt = null;
            ResultSet rselbd = null;
            String user = login;
            String password = pass;
            String dbUrl = "jdbc:sqlserver://" + NameSarver + ":" + number_port + ";databaseName=master";

            try {
                GetData.c.close();
                GetData.is_connect = false;
            } catch (SQLException ex) {
                Logger.getLogger(RemoveNameUser.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!is_connect) {
                try {
                    c = DriverManager.getConnection(dbUrl, user, password);
                } catch (SQLException ex) {
                    Logger.getLogger(RemoveNameUser.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Не удалось соедениться с базой", "", JOptionPane.OK_CANCEL_OPTION);
                    return;
                }
            }
            try {
                stmt = c.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(RemoveNameUser.class.getName()).log(Level.SEVERE, null, ex);
            }
            is_connect = true;
            try {
                rselbd = stmt.executeQuery("SELECT name, database_id, create_date FROM sys.databases ;");
            } catch (SQLException ex) {
                Logger.getLogger(GetData.class.getName()).log(Level.SEVERE, null, ex);
            }
            boolean isdb = false;
            try {
                while (rselbd.next()) {

                    String namedb = rselbd.getString("name");
                    if (namedb != null && namedb.equals(NameP + "_VK")) {
                        isdb = true;
                    }
                }
                //
            } catch (SQLException ex) {
                Logger.getLogger(GetData.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (isdb) {
                System.out.println("Существует. Меняем имя");

                try {
                    String sql = "Use master;\n ALTER DATABASE " + NameP + "_VK \n Modify Name = " + jTextField1.getText() + "_VK;";
                    stmt.execute(sql);
                } catch (SQLException ex) {
                    Logger.getLogger(GetData.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Не удалось переимоновать базу", "", JOptionPane.OK_CANCEL_OPTION);
                    return;
                }
            }
        }
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RemoveNameUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RemoveNameUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RemoveNameUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RemoveNameUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RemoveNameUser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
