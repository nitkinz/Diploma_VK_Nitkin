/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panel;

import com.google.gson.Gson;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Thread.sleep;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import static panel.GatheringUtilities.API_URL;
import static panel.GatheringUtilities.method;
import static java.lang.Thread.sleep;
import static panel.AnalysisPanel.chart1;
import static panel.AnalysisPanel.chart2;
import static panel.AnalysisPanel.chart3;
import static panel.AnalysisPanel.chart4;
import static panel.AnalysisPanel.chart5;
import static panel.AnalysisPanel.cit;
import static panel.AnalysisPanel.countr;
import static panel.AnalysisPanel.namebase;
import static panel.AnalysisPanel.nm;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;



/**
 *
 * @author Дима
 */
public class MainForm extends javax.swing.JFrame {

    /**
     * Creates new form JFrame
     */
    public static String users_sett = "";
    public static int statmax = 0;
    public static Map<String, String> stat = new HashMap<String, String>();
    GatheringPanel getData;
    static AnalysisPanel analysis;
    public HistoryPanel hst;

    public MainForm() throws SQLException, SAXException, IOException, ParserConfigurationException {
        initComponents();

        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("image/g.png")));
        File f = new File("setting.xml");
        if (f.exists() && !f.isDirectory()) {
            readSetting();
        }

        getData = new GatheringPanel(users_sett);
        analysis = new AnalysisPanel();
        hst = new HistoryPanel();

        Font font = new Font("Verdana", Font.PLAIN, 14);
        jTabbedPane1.setFont(font);
        jTabbedPane1.addTab("Сбор", getData);
        jTabbedPane1.addTab("Анализ", analysis);
        jTabbedPane1.addTab("История анализа", hst);

        this.setResizable(false);

    }

    void readSetting() throws SAXException, IOException, ParserConfigurationException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); //создали фабрику строителей, сложный и грамосткий процесс (по реже выполняйте это действие) 
        // f.setValidating(false); // не делать проверку валидации 
        DocumentBuilder db = dbf.newDocumentBuilder(); // создали конкретного строителя документа 
        Document doc = db.parse(("setting.xml")); // стооитель построил документ 
        //Document - тоже является нодом, и импленментирует методы 
        visit(doc, 0);

    }

    public static void visit(Node node, int level) {
        NodeList list = node.getChildNodes();
        List<Integer> lst = new ArrayList<Integer>();
        for (int i = 0; i < list.getLength(); i++) {
            Node childNode = list.item(i); // текущий нод 
            process(childNode, level + 1, lst); // обработка 
            visit(childNode, level + 1); // рекурсия 
        }

        if (lst.size() > 0) {

            Integer fram = lst.get(0);

            for (int i = 0; i < lst.size(); i++) {

                if (lst.get(i) > fram) {
                    fram = lst.get(i);
                }
            }

            statmax = fram;
            statmax++;
        }

    }

    public static void process(Node node, int level, List<Integer> lst) {
        for (int i = 0; i < level; i++) {
            System.out.print('\t');
        }

        if (node.getNodeName().equals("userList")) {
            users_sett = node.getTextContent();
        }
        if (node.getNodeName().equals("NameSarver")) {
            GatheringPanel.nameServer = node.getTextContent();
        }

        if (node.getNodeName().equals("PathExportExcel")) {
            GatheringPanel.pathExportExcel = node.getTextContent();
        }
        if (node.getNodeName().equals("login")) {
            GatheringPanel.login = node.getTextContent();
        }
        if (node.getNodeName().equals("number_port")) {
            GatheringPanel.numberPort = node.getTextContent();
        }
        if (node.getNodeName().equals("pass")) {
            GatheringPanel.pass = node.getTextContent();
        }
        if (node.getNodeName().equals("path_script")) {
            GatheringPanel.pathScript = node.getTextContent();
        }
        if (node.getNodeName().equals("path_database")) {
            GatheringPanel.pathDatabase = node.getTextContent();
        }
        if (node.getNodeName().equals("pathGraph")) {
            GatheringPanel.pathGraph = node.getTextContent();
        }

        if (node.getNodeName().contains("stat")) {
            String st[] = node.getNodeName().split(":");
            String val = node.getTextContent();
            if (st.length> 2) {
                stat.put(st[1] + ":" + st[2], val);
                String stval[] = node.getNodeName().split(":");
                lst.add(Integer.valueOf(stval[1]));
            }

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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        setting = new javax.swing.JMenu();
        aboutProgram = new javax.swing.JMenu();
        saveState = new javax.swing.JMenu();
        saveGraph = new javax.swing.JMenu();
        reference = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Мониторинг друзей из социальной сети \"ВКонтакте\"");
        setForeground(java.awt.Color.white);
        setIconImages(null);

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        setting.setIcon(new javax.swing.ImageIcon(getClass().getResource("/panel/image/gear_16xLG.png"))); // NOI18N
        setting.setText("Настройка");
        setting.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                settingMenuSelected(evt);
            }
        });
        setting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingActionPerformed(evt);
            }
        });
        jMenuBar1.add(setting);

        aboutProgram.setIcon(new javax.swing.ImageIcon(getClass().getResource("/panel/image/user_32xSM.png"))); // NOI18N
        aboutProgram.setText("О программе");
        aboutProgram.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                aboutProgramMouseClicked(evt);
            }
        });
        jMenuBar1.add(aboutProgram);

        saveState.setIcon(new javax.swing.ImageIcon(getClass().getResource("/panel/image/save_16xLG.png"))); // NOI18N
        saveState.setText("Сохранить состояние");
        saveState.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                saveStateMenuSelected(evt);
            }
        });
        jMenuBar1.add(saveState);

        saveGraph.setIcon(new javax.swing.ImageIcon(getClass().getResource("/panel/image/save_16xLG.png"))); // NOI18N
        saveGraph.setText("Сохранить граф");
        saveGraph.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                saveGraphMenuSelected(evt);
            }
        });
        saveGraph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveGraphActionPerformed(evt);
            }
        });
        jMenuBar1.add(saveGraph);

        reference.setIcon(new javax.swing.ImageIcon(getClass().getResource("/panel/image/StatusAnnotations_Help_and_inconclusive_16xLG_color.png"))); // NOI18N
        reference.setText("Справка");
        reference.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                referenceMenuSelected(evt);
            }
        });
        jMenuBar1.add(reference);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 887, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    Connection connectDB;
    public Statement statConn = null;
    public int ii22 = 0;
    public int cnt = 0;
    // public int ppver = 0;
    ArrayList<String> massID = new ArrayList<String>();
    private void settingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingActionPerformed

    }//GEN-LAST:event_settingActionPerformed

    private void settingMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_settingMenuSelected

        if (GatheringPanel.getdata) {
            JOptionPane.showMessageDialog(null, "Идет сбор.Дождитесь", "", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (AnalysisPanel.AnalysisPanel_get) {
            JOptionPane.showMessageDialog(null, "Идет анализ.Дождитесь", "", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        final SettingForm sett = new SettingForm();
        sett.setVisible(true);
    }//GEN-LAST:event_settingMenuSelected

    private void saveGraphActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveGraphActionPerformed

    }//GEN-LAST:event_saveGraphActionPerformed

    private void saveGraphMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_saveGraphMenuSelected

        if (GatheringPanel.getdata) {
            JOptionPane.showMessageDialog(null, "Идет сбор.Дождитесь", "", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (AnalysisPanel.AnalysisPanel_get) {
            JOptionPane.showMessageDialog(null, "Идет анализ.Дождитесь", "", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        EdgelistExportForm sv = new EdgelistExportForm();
        sv.setVisible(true);
    }//GEN-LAST:event_saveGraphMenuSelected

    private void saveStateMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_saveStateMenuSelected

        if (GatheringPanel.getdata) {
            JOptionPane.showMessageDialog(null, "Идет сбор.Дождитесь", "", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (AnalysisPanel.AnalysisPanel_get) {
            JOptionPane.showMessageDialog(null, "Идет анализ.Дождитесь", "", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        saveState();

    }

    static void saveState() {

        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Действительно сохранить?", "Warning", dialogButton);
        if (dialogResult != JOptionPane.YES_OPTION) {
            return;
        }

        saveGraph();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        DOMImplementation impl = builder.getDOMImplementation(); // более сложный, но и более гибкий способ создания документов 
        Document doc = impl.createDocument(null, // namespaceURI 
                null, // qualifiedName 
                null); // doctype 

        SettingForm setting = new SettingForm();

        setting.setVisible(false);
        setting.dispatchEvent(new WindowEvent(setting, WindowEvent.WINDOW_CLOSING));

        SettingForm.installSettings();

        Element base = doc.createElement("base");

        Element eNameSarver = doc.createElement("NameSarver");
        eNameSarver.setTextContent(GatheringPanel.nameServer);

        Element ePathExportExcel = doc.createElement("PathExportExcel");
        ePathExportExcel.setTextContent(GatheringPanel.pathExportExcel);

        Element enumber_port = doc.createElement("number_port");
        enumber_port.setTextContent(GatheringPanel.numberPort);

        Element elogin = doc.createElement("login");
        elogin.setTextContent(GatheringPanel.login);

        Element epass = doc.createElement("pass");
        epass.setTextContent(GatheringPanel.pass);

        Element epath_script = doc.createElement("path_script");
        epath_script.setTextContent(GatheringPanel.pathScript);

        Element epath_database = doc.createElement("path_database");
        epath_database.setTextContent(GatheringPanel.pathDatabase);

        Element epathGraph = doc.createElement("pathGraph");
        epathGraph.setTextContent(GatheringPanel.pathGraph);
        //
        String us = "";
        int siz = GatheringPanel.listModel.size();

        for (int user = 0; user < siz; user++) {
            String cl = user == (siz - 1) ? "" : ";";
            us += (String) GatheringPanel.listModel.get(user) + " - " + GatheringPanel.mapUser.get((String) GatheringPanel.listModel.get(user)) + cl;
        }

        Element euserList = doc.createElement("userList");
        euserList.setTextContent(us);
        //

        doc.appendChild(base);
        base.appendChild(eNameSarver);
        base.appendChild(ePathExportExcel);
        base.appendChild(elogin);
        base.appendChild(enumber_port);
        base.appendChild(epass);
        base.appendChild(epath_script);
        base.appendChild(epath_database);
        base.appendChild(epathGraph);
        base.appendChild(euserList);

        //
        for (String ind : stat.keySet()) {
            Element estat = doc.createElement("stat:" + ind);
            String st = stat.get(ind);
            estat.setTextContent(st);
            base.appendChild(estat);
        }

        //
        Transformer t = null;
        try {
            t = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        t.setOutputProperty(OutputKeys.METHOD, "xml");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        try {
            try {
                t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream("setting.xml")));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (TransformerException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null, "Готово", "", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_saveStateMenuSelected

    static void saveGraph() {
        try {
            //
            String sv = "";
            if (chart1 != null) {
                sv = "png=stat/Chart_" + MainForm.statmax + ";";
                BitmapEncoder.saveBitmap(chart1, "stat/Chart_" + MainForm.statmax, BitmapFormat.PNG);
            }
            if (chart2 != null) {
                sv += "png=stat/Chart2_" + MainForm.statmax + ";";
                BitmapEncoder.saveBitmap(chart2, "stat/Chart2_" + MainForm.statmax, BitmapFormat.PNG);
            }
            if (chart3 != null) {
                sv += "png=stat/Chart3_" + MainForm.statmax + ";";
                BitmapEncoder.saveBitmap(chart3, "stat/Chart3_" + MainForm.statmax, BitmapFormat.PNG);
            }
            if (chart4 != null) {
                sv += "png=stat/Chart4_" + MainForm.statmax + ";";
                BitmapEncoder.saveBitmap(chart4, "stat/Chart4_" + MainForm.statmax, BitmapFormat.PNG);
            }
            if (chart5 != null) {
                sv += "png=stat/Chart5_" + MainForm.statmax + ";";
                BitmapEncoder.saveBitmap(chart5, "stat/Chart5_" + MainForm.statmax, BitmapFormat.PNG);
            }
            if (!cit.equals("cit=")) {
                sv += cit + ";";
            }
            if (!countr.equals("countr=")) {
                sv += countr + ";";
            }
            String dop = "";

            if (!AnalysisPanel.countrySelect.equals("")) {
                dop += "Страна - " + AnalysisPanel.countrySelect;
            }
            if (!AnalysisPanel.citySelect.equals("")) {
                dop += (dop.equals("") ? "" : ",") + "Город - " + AnalysisPanel.citySelect;
            }
            if (AnalysisPanel.isMan) {
                dop += (dop.equals("") ? "" : ",") + "Мужчины";
            }
            if (AnalysisPanel.isFemale) {
                dop += (dop.equals("") ? "" : ",") + "Женщины";
            }

            if (AnalysisPanel.ofAge!= -5) {
                dop += (dop.equals("") ? "" : ",") + "Возраст от - " + String.valueOf(AnalysisPanel.ofAge);
            }
            if (AnalysisPanel.untilAge!= -5) {
                dop += (dop.equals("") ? "" : ",") + "Возраст до - " + String.valueOf(AnalysisPanel.untilAge);
            }

            if (AnalysisPanel.circleToAnalysisPanel != -5) {
                dop += (dop.equals("") ? "" : ",") + "Круг - " + String.valueOf(AnalysisPanel.circleToAnalysisPanel);
            }

            MainForm.stat.put((MainForm.statmax++) + ":" + namebase, "id=" + nm + ";" + sv + (dop.equals("") ? "" : "dop=" + dop));
        } catch (IOException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }

        //

        //
    }

    private void aboutProgramMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aboutProgramMouseClicked

        AboutForm abprg = new AboutForm();
        abprg.show();

    }//GEN-LAST:event_aboutProgramMouseClicked

    private void referenceMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_referenceMenuSelected
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File("manual.pdf");
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }
    }//GEN-LAST:event_referenceMenuSelected

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
            java.util.logging.Logger.getLogger(MainForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                try {
//                    new JFrame().setVisible(true);
                    MainForm.setDefaultLookAndFeelDecorated(true);
                    JDialog.setDefaultLookAndFeelDecorated(true);
                    try {
                        createGUI();
                    } catch (SAXException ex) {
                        Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParserConfigurationException ex) {
                        Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    public static void createGUI() throws SQLException, SAXException, IOException, ParserConfigurationException {

        MainForm frame = new MainForm();
        frame.setDefaultCloseOperation(MainForm.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new WindowListener() {

            public void windowActivated(WindowEvent event) {

            }

            public void windowClosed(WindowEvent event) {

            }

            public void windowClosing(WindowEvent event) {
                Object[] options = {"Да", "Нет", "Не выходить"};
                int n = JOptionPane
                        .showOptionDialog(event.getWindow(), "Сохранить состояние перед выходом?",
                                "Подтверждение", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options,
                                options[0]);
                if (n == 0) {
                    saveState();
                    event.getWindow().setVisible(false);
                    System.exit(0);
                } else if (n == 1) {
                    event.getWindow().setVisible(false);
                    System.exit(0);
                } else if (n == 2) {

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu aboutProgram;
    private javax.swing.JMenuBar jMenuBar1;
    public static javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JMenu reference;
    private javax.swing.JMenu saveGraph;
    private static javax.swing.JMenu saveState;
    private javax.swing.JMenu setting;
    // End of variables declaration//GEN-END:variables

}
