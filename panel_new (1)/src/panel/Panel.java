/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panel;

import java.awt.FlowLayout;
import java.io.IOException;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import java.util.logging.FileHandler;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import static sun.security.jgss.GSSUtil.login;
import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import static panel.JFrame.createGUI;
import static panel.JFrame.save;

/**
 *
 * @author Дима
 */
public class Panel {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, UnsupportedLookAndFeelException, SQLException, SAXException, ParserConfigurationException {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // final JFrame f = new JFrame();
        // f.setVisible(true);
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        createGUI();

    }

    public static void createGUI() throws SQLException, SAXException, IOException, ParserConfigurationException {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new WindowListener() {

            public void windowActivated(WindowEvent event) {

            }

            public void windowClosed(WindowEvent event) {

            }

            public void windowClosing(WindowEvent event) {
                Object[] options = {"Да", "Нет","Не выходить"};
                int n = JOptionPane
                        .showOptionDialog(event.getWindow(), "Сохранить состояние перед выходом?",
                                "Подтверждение", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options,
                                options[0]);
                if (n == 0) {
                    save();
                    event.getWindow().setVisible(false);
                    System.exit(0);
                } else if(n==1){
                    event.getWindow().setVisible(false);
                    System.exit(0);
                } else if(n==2)
                {
                    
                }
            }

            public void windowDeactivated(WindowEvent event) {

            }

            public void windowDeiconified(WindowEvent event) {

            }

            public void windowIconified(WindowEvent event) {

            }

            public void windowOpened(WindowEvent event) {

            }

        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
