/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panel;

import com.google.gson.Gson;
import java.awt.BorderLayout;
import java.awt.CardLayout;
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
import static panel.TextPaneAppender.API_URL;
import static panel.TextPaneAppender.count;
import static panel.TextPaneAppender.method;

/**
 *
 * @author Дима
 */
public class JFrame extends javax.swing.JFrame {

    /**
     * Creates new form JFrame
     */
    public static String users_sett = "";
    public static int statmax = 0;
    public static Map<String, String> stat = new HashMap<String, String>();

    public JFrame() throws SQLException, SAXException, IOException, ParserConfigurationException {
        initComponents();

        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("image/g.png")));
        File f = new File("setting.xml");
        if (f.exists() && !f.isDirectory()) {
            ReadSetting();
        }

        GetData gt = new GetData(users_sett);
        Analysis anlisys = new Analysis();
        History_analisis hst = new History_analisis();
        Font font = new Font("Verdana", Font.PLAIN, 14);
        jTabbedPane1.setFont(font);
        jTabbedPane1.addTab("Сбор", gt);
        jTabbedPane1.addTab("Анализ", anlisys);
        jTabbedPane1.addTab("История анализа", hst);
        this.setResizable(false);

    }

    public void ReadSetting() throws SAXException, IOException, ParserConfigurationException {

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
            GetData.NameSarver = node.getTextContent();
        }

        if (node.getNodeName().equals("PathExportExcel")) {
            GetData.PathExportExcel = node.getTextContent();
        }
        if (node.getNodeName().equals("login")) {
            GetData.login = node.getTextContent();
        }
        if (node.getNodeName().equals("number_port")) {
            GetData.number_port = node.getTextContent();
        }
        if (node.getNodeName().equals("pass")) {
            GetData.pass = node.getTextContent();
        }
        if (node.getNodeName().equals("path_script")) {
            GetData.path_script = node.getTextContent();
        }
        if (node.getNodeName().equals("path_database")) {
            GetData.path_database = node.getTextContent();
        }
        if (node.getNodeName().equals("pathGraph")) {
            GetData.pathGraph = node.getTextContent();
        }

        if (node.getNodeName().contains("stat")) {
            String st[] = node.getNodeName().split(":");
            String val = node.getTextContent();
            stat.put(st[1] + ":" + st[2], val);
            String stval[] = node.getNodeName().split(":");
            lst.add(Integer.valueOf(stval[1]));
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
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Мониторинг друзей из соц.сети");
        setForeground(java.awt.Color.white);
        setIconImages(null);

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/panel/image/gear_16xLG.png"))); // NOI18N
        jMenu2.setText("Настройка");
        jMenu2.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                jMenu2MenuSelected(evt);
            }
        });
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu2ActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenu2);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/panel/image/user_32xSM.png"))); // NOI18N
        jMenu3.setText("О программе");
        jMenuBar1.add(jMenu3);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/panel/image/save_16xLG.png"))); // NOI18N
        jMenu1.setText("Сохранить состояние");
        jMenu1.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                jMenu1MenuSelected(evt);
            }
        });
        jMenuBar1.add(jMenu1);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/panel/image/save_16xLG.png"))); // NOI18N
        jMenu4.setText("Сохранить граф");
        jMenu4.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                jMenu4MenuSelected(evt);
            }
        });
        jMenu4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu4ActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenu4);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/panel/image/StatusAnnotations_Help_and_inconclusive_16xLG_color.png"))); // NOI18N
        jMenu5.setText("Справка");
        jMenuBar1.add(jMenu5);

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
     public Connection c;
    public Statement stmt = null;
    public int ii22 = 0;
    public int cnt = 0;
    // public int ppver = 0;
    ArrayList<String> massID = new ArrayList<String>();
    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed

    }//GEN-LAST:event_jMenu2ActionPerformed

    private void jMenu2MenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_jMenu2MenuSelected

        if (GetData.getdata) {
            JOptionPane.showMessageDialog(null, "Идет сбор.Дождитесь", "", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (Analysis.analysis_get) {
            JOptionPane.showMessageDialog(null, "Идет анализ.Дождитесь", "", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        final Setting_S sett = new Setting_S();
        sett.setVisible(true);
    }//GEN-LAST:event_jMenu2MenuSelected

    private void jMenu4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu4ActionPerformed

    }//GEN-LAST:event_jMenu4ActionPerformed

    private void jMenu4MenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_jMenu4MenuSelected

        if (GetData.getdata) {
            JOptionPane.showMessageDialog(null, "Идет сбор.Дождитесь", "", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (Analysis.analysis_get) {
            JOptionPane.showMessageDialog(null, "Идет анализ.Дождитесь", "", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        SaveGraph sv = new SaveGraph();
        sv.setVisible(true);
    }//GEN-LAST:event_jMenu4MenuSelected

    private void jMenu1MenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_jMenu1MenuSelected

        if (GetData.getdata) {
            JOptionPane.showMessageDialog(null, "Идет сбор.Дождитесь", "", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (Analysis.analysis_get) {
            JOptionPane.showMessageDialog(null, "Идет анализ.Дождитесь", "", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        save();

    }

    public static void save() {

        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Действительно сохранить?", "Warning", dialogButton);
        if (dialogResult != JOptionPane.YES_OPTION) {
            return;
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        DOMImplementation impl = builder.getDOMImplementation(); // более сложный, но и более гибкий способ создания документов 
        Document doc = impl.createDocument(null, // namespaceURI 
                null, // qualifiedName 
                null); // doctype 

        Setting_S setting = new Setting_S();

        setting.setVisible(false);
        setting.dispatchEvent(new WindowEvent(setting, WindowEvent.WINDOW_CLOSING));

        Setting_S.set();

        Element base = doc.createElement("base");

        Element eNameSarver = doc.createElement("NameSarver");
        eNameSarver.setTextContent(GetData.NameSarver);

        Element ePathExportExcel = doc.createElement("PathExportExcel");
        ePathExportExcel.setTextContent(GetData.PathExportExcel);

        Element enumber_port = doc.createElement("number_port");
        enumber_port.setTextContent(GetData.number_port);

        Element elogin = doc.createElement("login");
        elogin.setTextContent(GetData.login);

        Element epass = doc.createElement("pass");
        epass.setTextContent(GetData.pass);

        Element epath_script = doc.createElement("path_script");
        epath_script.setTextContent(GetData.path_script);

        Element epath_database = doc.createElement("path_database");
        epath_database.setTextContent(GetData.path_database);

        Element epathGraph = doc.createElement("pathGraph");
        epathGraph.setTextContent(GetData.pathGraph);
        //
        String us = "";
        int siz = GetData.listModel.size();

        for (int user = 0; user < siz; user++) {
            String cl = user == (siz - 1) ? "" : ";";
            us += (String) GetData.listModel.get(user) + " - " + GetData.User_mass.get((String) GetData.listModel.get(user)) + cl;
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
            Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        t.setOutputProperty(OutputKeys.METHOD, "xml");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        try {
            try {
                t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream("setting.xml")));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (TransformerException ex) {
            Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null, "Готово", "", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_jMenu1MenuSelected
    // <editor-fold defaultstate="collapsed" desc="Old getdata">

    /* public class ddd {

        public void del() throws IOException {
            try {
              
                TextPaneAppender tnew = new TextPaneAppender();
                Calendar SaveData = Calendar.getInstance();
                ResultSet r1 = null;
                ResultSet prom = null;
                Boolean isprom = false;
                ArrayList<String> massidvr = new ArrayList<String>();
                ArrayList<String> massidvrnew = new ArrayList<String>();
//                if (ppver != 1) {
//                    try {
//
//                        prom = stmt.executeQuery("select * from dbo.prom WHERE (number=" + ii22 + ")");
//                        while (prom.next()) {
//                            isprom = true;
//                        }
//                    } catch (Exception e) {
//                        System.out.println("prom error");
//                    }
//
//                    if (!isprom) {
//                        System.out.println("mass вовремя=0");
//                        ++ii22;
//                        return;
//                    }
//                }

                try {
                    massidvr = new ArrayList<String>();
                    r1 = stmt.executeQuery("select * from dbo.TABL WHERE (num=" + ii22 + ")");

                } catch (Exception e) {
                    System.out.println("132");
                }

                try {

                    while (r1.next()) {

                        massidvr.add(r1.getString("parent"));

                    }
                } catch (Exception e) {
                    System.out.println("183");
                    // Logger.getLogger(JavaApplication5.class.getName()).log(Level.SEVERE, null, e);

                }

                r1.close();
                vr(SaveData, "выбор ii2= " + ii22 + " parent составил= ");
                if (massidvr.size() == 0) {
                    System.out.println("mass=0");
                    ++ii22;
                    return;
                }
                SaveData = Calendar.getInstance();
                ConcurrentHashMap<String, Boolean> msgBoolean = new ConcurrentHashMap<String, Boolean>();
                vibjsbool v = new vibjsbool();
                //  v.dell(massidvr, stmt, msgBoolean);
                System.out.println("msddID size= " + massID.size());
                for (String id : massidvr) {
//                    Boolean g = false;
//                    if (id.equals("53083705")) {
//                        String dfhg = "sdg";
//                    }
//                    g = msgBoolean.get(id.replace("'", ""));
//                    if (g != null && g) {
//                        continue;
//                    }
                    if (massID.contains(id.replace("'", ""))) {
                        continue;
                    }
                    massidvrnew.add(id);
                }
                vr(SaveData, "1) massidvr.size()= " + massidvr.size() + " massidvrnew.size()= " + massidvrnew.size() + " составил= ");

                ddd12 t = new ddd12();
                t.dell(massidvrnew);

                String string = tnew.main222(stmt, ii22, cnt, massidvrnew, massID);
                cnt = Integer.parseInt(string);
                System.out.println("COUNT= " + cnt + "\n");
                FileWriter writer5 = new FileWriter("D:\\log.txt", true);
                writer5.write("COUNT= " + cnt + "\n");
                writer5.close();
                ++ii22;
            } catch (SQLException ex) {
                Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
            }
        }
    };

    public class vibjsbool {

        public void dell(ArrayList<String> massidvr, Statement stmt, ConcurrentHashMap<String, Boolean> msgBoolean) throws SQLException {
            try {

                for (String el : massidvr) {
                    if (massID.contains(el)) {
                        msgBoolean.put(el, true);
                    }

                }

//                String ID = "select * from dbo.id_to WHERE";
//                int cnt = 0;
//                for (String el : massidvr) {
//                    ID += " id=" + el + (cnt == (massidvr.size() - 1) ? "" : " OR ");
//                    cnt++;
//                }
//                ResultSet exs = stmt.executeQuery(ID);
//
//                ArrayList<String> vr = new ArrayList<String>();
//                String id = "";
//                String id2;
//                Boolean b;
//                while (exs.next()) {
//                    vr.add(exs.getString("id"));
//
//                }
//                exs.close();
//
//                for (String s : vr) {
//                    msgBoolean.put(s, true);
//                }
                // vr(SaveData, "5) выбор id для проверки добавлены ли они(bool) " + "(" + msgBoolean.size() + ")" + " для текущих друзей из TABL составил= ");
                //  vr = new ArrayList<>();
            } catch (Exception ex) {
                System.out.println("258 проверка были ли, апрос where");
            } finally {

            }
        }
    }

    public class ddd12 {

        public void dell(ArrayList<String> massidvr) throws SQLException {
            try {
                ArrayList<String> one = new ArrayList<String>();
                ArrayList<String> two = new ArrayList<String>();
                ArrayList<String> three = new ArrayList<String>();
                ArrayList<String> tho = new ArrayList<String>();
                ArrayList<String> fife = new ArrayList<String>();
                ArrayList<String> seven = new ArrayList<String>();
                int pdc = 0;
                for (String s : massidvr) {

                    if (pdc < 1000) {
                        one.add(s);
                    }
                    if (pdc >= 1000 && pdc < 2000) {
                        two.add(s);
                    }
                    if (pdc >= 2000 && pdc < 3000) {
                        three.add(s);
                    }
                    if (pdc >= 3000 && pdc < 4000) {
                        tho.add(s);
                    }
                    if (pdc >= 4000 && pdc < 5000) {
                        fife.add(s);
                    }
                    if (pdc >= 5000 && pdc < 6000) {
                        seven.add(s);
                    }
                    pdc++;

                }
                ddd1 dt = new ddd1();

                ConcurrentSkipListSet<String> err = new ConcurrentSkipListSet<String>();
                if (one.size() > 0) {

                    dt.searchfrend(one, stmt, err);
                }
                if (two.size() > 0) {

                    dt.searchfrend(two, stmt, err);
                }
                if (three.size() > 0) {

                    dt.searchfrend(three, stmt, err);
                }
                if (tho.size() > 0) {

                    dt.searchfrend(tho, stmt, err);
                }
                if (fife.size() > 0) {

                    dt.searchfrend(fife, stmt, err);
                }
                if (seven.size() > 0) {

                    dt.searchfrend(seven, stmt, err);
                }

                one = new ArrayList<String>();
                two = new ArrayList<String>();
                three = new ArrayList<String>();
            } finally {

            }
        }

    }

    public class ddd1 {

        public void searchfrend(ArrayList<String> massidvr, Statement stmt, final ConcurrentSkipListSet<String> err) throws SQLException {

            try {
                final ConcurrentHashMap msg = new ConcurrentHashMap<>();
                Calendar SaveData = Calendar.getInstance();
                ExecutorService exec = Executors.newFixedThreadPool(10);

                try {

                    for (final String o : massidvr) {
                        exec.submit(new Runnable() {
                            @Override
                            public void run() {
                                URL url2 = null;
                                //if (!listid.contains(o)) {

                                try {
                                    url2 = new URL(API_URL + method + "?uid=" + o.replace("'", "") + "&fields=sex,bdate,city,country&name_case=nom&count&offset=0&lid&order=hints");
                                } catch (MalformedURLException ex) {
                                    System.out.println("163");
                                    //  Logger.getLogger(JavaApplication5.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                BufferedReader reader = null;
                                try {
                                    reader = new BufferedReader(new InputStreamReader(url2.openStream()));
                                    String json = reader.readLine();
                                    reader.close();

                                    json = json.replace("{\"response\":[", "").replace("]}", "");
                                    msg.put(o, json);
                                    json = new String();
                                    // System.out.println(msg.size());
                                } catch (IOException ex) {
                                    System.out.println("171");
                                    err.add(o);
                                    // Logger.getLogger(JavaApplication5.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }

                        });

                    }
                } finally {

                    exec.shutdown();
                    try {
                        exec.awaitTermination(1, TimeUnit.DAYS);
                    } catch (InterruptedException ex) {
                        System.out.println("203");
                    }
                }

                if (err.size() > 1) {
                    ArrayList<String> massidvrnew = new ArrayList<String>();
                    for (String ow : err) {
                        massidvrnew.add(ow);
                    }
                    povtor(massidvrnew, msg);
                }

                vr(SaveData, "2) поиск в net = " + massidvr.size() + " составил= ");

                SaveData = Calendar.getInstance();
                ArrayList<String> povt = new ArrayList<String>();
                try (FileWriter writer = new FileWriter("D:\\jsn.txt", false)) {

                    Iterator iterator2 = msg.keySet().iterator();
                    while (iterator2.hasNext()) {
                        Object key = iterator2.next();
                        if (povt.contains(((String) key).replace("'", ""))) {
                            continue;
                        }
                        povt.add(((String) key).replace("'", ""));
                        writer.write(((String) key).replace("'", "") + "," + "'" + msg.get(key) + "'" + "6744782723371285125003");
                    }
                    writer.close();
                    //  lst = new ConcurrentSkipListSet<>();
                    //  msg = new ConcurrentHashMap<>();
                } catch (Exception ex) {
                    System.out.println("225");
                    System.out.println(ex.getMessage());
                }

                try {
                    String g = "BULK INSERT dbo.N1 FROM 'D:\\jsn.txt' WITH(FIELDTERMINATOR = ',',ROWTERMINATOR = '6744782723371285125003')";
                    stmt.executeUpdate(g);
                } catch (Exception ex) {
                    System.out.println("358 errinsert n1");
                    setn1();
                }

                vr(SaveData, "3) добавление json в файл и в базу N1= " + massidvr.size() + " msg_size = " + msg.size() + " составил= ");
            } finally {

            }
        }

        public void setn1() {

            try {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "Вставить повторно json в базу?", "Warning", dialogButton);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    try {

                        String g = "BULK INSERT dbo.N1 FROM 'D:\\jsn.txt' WITH(FIELDTERMINATOR = ',',ROWTERMINATOR = '6744782723371285125003')";
                        stmt.executeUpdate(g);

                    } catch (Exception ex) {
                        System.out.println("381 errinsert n1");
                        setn1();
                    }
                } else {
                }
            } finally {

            }

        }

        public void povtor(ArrayList<String> massidvr, ConcurrentHashMap msg) {

            for (final String o : massidvr) {

                URL url2 = null;
                //if (!listid.contains(o)) {

                try {
                    url2 = new URL(API_URL + method + "?uid=" + o.replace("'", "") + "&fields&name_case=nom&count&offset=0&lid&order=hints");
                } catch (MalformedURLException ex) {
                    System.out.println("416");
                    // Logger.getLogger(JavaApplication5.class.getName()).log(Level.SEVERE, null, ex);
                }
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(url2.openStream()));
                    String json = reader.readLine();
                    reader.close();

                    json = json.replace("{\"response\":[", "").replace("]}", "");
                    msg.put(o, json);
                    json = new String();
                } catch (IOException ex) {
                    System.out.println("437");
                    //  err.add(o);
                    //  Logger.getLogger(JavaApplication5.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }

    }

    public void settabl(Statement stmt) throws SQLException {
        stmt.executeUpdate("USE MASTER;"
                + " IF DB_ID('test') IS NOT NULL"
                + " DROP DATABASE test"
                + " CREATE DATABASE test"
                + " ON PRIMARY (Name=test,FILENAME='D:\\stnd2\\MSSQL12.SQLDIMAST2\\MSSQL\\DATA\\test.mdf',SIZE=1000MB,MAXSIZE=UNLIMITED,FILEGROWTH=290MB)"
                + " LOG ON (NAME=test_log,FILENAME='D:\\stnd2\\MSSQL12.SQLDIMAST2\\MSSQL\\DATA\\test_log.ldf',SIZE=500MB,MAXSIZE=35000MB,FILEGROWTH=200MB)");
        stmt.executeUpdate(
                "USE test\n"
                + "IF EXISTS(SELECT*FROM sys.tables WHERE NAME='TABL')\n"
                + "DROP TABLE TABL;\n"
                + "CREATE TABLE TABL\n"
                + "(\n"
                + "	id bigint NULL,\n"
                + "	parent text NULL,\n"
                + "	num bigint NULL,\n"
                + "	sex text NULL,\n"
                + "	bdate text NULL,\n"
                + "	city text NULL,\n"
                + "	country text NULL,\n"
                + "	hidden text NULL\n"
                + " )\n"
                + "\n"
                + "USE test\n"
                + "IF EXISTS(SELECT*FROM sys.tables WHERE NAME='prom')\n"
                + "DROP TABLE prom;\n"
                + "CREATE TABLE prom\n"
                + "(\n"
                + "	number bigint NULL,\n"
                + "	count bigint NULL,\n"
                + " )\n"
                + "\n"
                + " USE test\n"
                + "IF EXISTS(SELECT*FROM sys.tables WHERE NAME='N1')\n"
                + "DROP TABLE N1;\n"
                + "CREATE TABLE N1\n"
                + "(\n"
                + "	ID bigint NULL,\n"
                + "	JS text NULL,\n"
                + " )"
                + "USE test\n"
                + "IF EXISTS(SELECT*FROM sys.tables WHERE NAME='id_to')\n"
                + "DROP TABLE id_to;\n"
                + "CREATE TABLE id_to\n"
                + "(\n"
                + "	id bigint NULL,\n"
                + ")");
        stmt.executeUpdate("Exec sp_defaultdb @loginame='diman', @defdb='test'");
    }

    public void vr(Calendar SaveData, String outp) {

        try {
            FileWriter writer5 = new FileWriter("D:\\log.txt", true);
            writer5.write(outp);
            System.out.print(outp);

            Calendar CurrentData = Calendar.getInstance();

            Calendar calculate = Calendar.getInstance();
            calculate.setTime(new Date(CurrentData.getTime().getTime() - SaveData.getTime().getTime()));

            System.out.println(calculate.get(Calendar.MINUTE) + ":" + calculate.get(Calendar.SECOND));
            writer5.write(calculate.get(Calendar.MINUTE) + ":" + calculate.get(Calendar.SECOND));
            writer5.write("\n");
            writer5.close();
        } catch (Exception e) {
            System.out.println("391");
        } finally {

        }

    }
     */
    // </editor-fold>
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
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                try {
//                    new JFrame().setVisible(true);
                    JFrame.setDefaultLookAndFeelDecorated(true);
                    JDialog.setDefaultLookAndFeelDecorated(true);
                    try {
                        createGUI();
                    } catch (SAXException ex) {
                        Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParserConfigurationException ex) {
                        Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
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
                Object[] options = {"Да", "Нет", "Не выходить"};
                int n = JOptionPane
                        .showOptionDialog(event.getWindow(), "Сохранить состояние перед выходом?",
                                "Подтверждение", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options,
                                options[0]);
                if (n == 0) {
                    save();
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
    private static javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    public static javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables

}
