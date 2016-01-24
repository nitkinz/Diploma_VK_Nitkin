package panel;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class GetUserforVK {

    // static ConcurrentHashMap<String, String> msgnew = new ConcurrentHashMap<String, String>();
    //  static ConcurrentHashMap<String, Boolean> msgBoolean = new ConcurrentHashMap<String, Boolean>();
    static int count;
    static ConcurrentHashMap<String, String> msg = new ConcurrentHashMap<String, String>();
    static String API_URL = "https://api.vk.com/method/";
    static String method = "friends.get";
    // static ConcurrentSkipListSet<String> lst = new ConcurrentSkipListSet<String>();
    static ConcurrentSkipListSet<String> err = new ConcurrentSkipListSet<String>();
    String pathsave = "C:\\Users\\Дима\\Documents\\notes3.txt"; //сохранить результат в папку
    //Integer countdrug = 65000; //все друзь¤ дл¤ стольки-та друзей
    public static ArrayList<String> current_friends = new ArrayList<String>();

    public void OneSearch(Statement stmt, ArrayList<String> massID, Integer start_id) throws IOException, SQLException {

        ArrayList<String> listid2 = new ArrayList<String>();

        listid2.add(start_id.toString()); //начинаем с медведева

        for (String IDLIST : listid2) {

            String url = "";

            url = API_URL + method + "?&uid=" + IDLIST + "&fields=sex,bdate,city,country&name_case=nom&count&offset=0&lid&order=hints";
            String json;

            URL url2 = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url2.openStream()));
            json = reader.readLine();
            reader.close();

            ArrayList<User> la = new ArrayList<User>();
            json = json.replace("{\"response\":[", "").replace("]}", "");
            String res = "[" + json + "]";

            json = new String();

            ArrayList<Map> list = new Gson().fromJson(res, la.getClass());
            ConcurrentSkipListSet<String> lst = new ConcurrentSkipListSet<String>();

            ConcurrentSkipListSet<String> id = new ConcurrentSkipListSet<String>();
            addpol(IDLIST, 1, list, lst, id);

            try (FileWriter writer2 = new FileWriter(GetData.path_script + "id.txt", false)) {

                for (String s : id) {
                    massID.add(s);
                    writer2.write(s + "6744782723371285125003");
                }

                writer2.close();
            } catch (Exception ex) {
                System.out.println("82");
                System.out.println(ex.getMessage());
            }

            list = new ArrayList<>();

            try (FileWriter writer = new FileWriter(GetData.path_script + "pr.txt", false)) {

                for (String s : lst) {
                    writer.write(s);
                }
                lst = new ConcurrentSkipListSet<>();
                writer.close();
            } catch (Exception ex) {
                System.out.println("111");
                System.out.println(ex.getMessage());
            }
            try {
                String g = "BULK INSERT " + GetData.NameDB + ".dbo.BASE_TABLE FROM" + "'" + GetData.path_script + "pr.txt' WITH(FIELDTERMINATOR = ',',ROWTERMINATOR = '6744782723371285125003')";
                stmt.executeUpdate(g);
            } catch (Exception ex) {
                System.out.println("264 errinsert tabl");
                Reinserting(stmt);
            }

            try {

                String g = "BULK INSERT " + GetData.NameDB + ".dbo.id_to FROM" + "'" + GetData.path_script + "id.txt' WITH(ROWTERMINATOR = '6744782723371285125003')";
                stmt.executeUpdate(g);
            } catch (Exception ex) {
                System.out.println("97 errinsert err id_to");

            }

        }

//        for (int j = current_friends.size() - 1; j >= 0; j--) {
//            String url = "";
//
//            url = API_URL + method + "?&uid=" + current_friends.get(j) + "&fields=sex,bdate,city,country&name_case=nom&count&offset=0&lid&order=hints";
//            String json;
//
//            URL url2 = new URL(url);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(url2.openStream()));
//            json = reader.readLine();
//            reader.close();
//            if (json != null && !json.contains("err") && json.length() > 15) {
//                GetData.circle = current_friends.get(j);
//                break;
//            }
//        }
    }

    public String SearchMain(Statement stmt, Integer ii2, Integer cntt, ArrayList<String> massidvr, ArrayList<String> massID) throws SQLException {

        this.count = cntt;

        if (massidvr.size() == 0) {
            return "" + count;
        }

        try {

            Calendar SaveData = Calendar.getInstance();

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

            //метод заноса в tabl
            settabl st = new settabl();
            if (one.size() > 0) {
                st.settbl(one, ii2, stmt, massID);
            }
            if (two.size() > 0) {
                st.settbl(two, ii2, stmt, massID);
            }
            if (three.size() > 0) {
                st.settbl(three, ii2, stmt, massID);
            }
            if (tho.size() > 0) {

                st.settbl(tho, ii2, stmt, massID);
            }
            if (fife.size() > 0) {

                st.settbl(fife, ii2, stmt, massID);
            }
            if (seven.size() > 0) {

                st.settbl(seven, ii2, stmt, massID);
            }
        } catch (Exception e) {
            System.out.println("280");
        }

        return "" + count;

    }

    public class settabl {

        public void settbl(ArrayList<String> massidvr, int ii2, Statement stmt, ArrayList<String> massID) throws SQLException, IOException {
            try {
                final ConcurrentHashMap<String, String> msgnew = new ConcurrentHashMap<String, String>();
                String N1 = "select * from " + GetData.NameDB + ".dbo.ID_JSON WHERE";

                int cnt = 0;
                for (String el : massidvr) {
                    N1 += " ID=" + el + (cnt == (massidvr.size() - 1) ? "" : " OR ");
                    cnt++;
                }
                ResultSet r = stmt.executeQuery(N1);

                String js = "";
                String id22;
                String jsr;
                while (r.next()) {
                    id22 = r.getString("ID");
                    jsr = r.getString("JS");
                    msgnew.put(id22, jsr);
                }

                Calendar SaveData = Calendar.getInstance();
                final ConcurrentSkipListSet<String> lst = new ConcurrentSkipListSet<String>();
                final ConcurrentSkipListSet<String> id = new ConcurrentSkipListSet<String>();
                ExecutorService exec2 = Executors.newFixedThreadPool(120);
                try {

                    for (final String el : massidvr) {
                        exec2.submit(new Runnable() {
                            @Override
                            public void run() {

                                String js = "";
                                String json2 = "";
                                String idgl = el.replace("'", "");

                                json2 = msgnew.get(idgl).replace("'", "");
                                if (json2 != null && !json2.contains("err") && json2.length() > 15) {
                                    int cntvn = count++;
//                                    if (GetData.circle.equals(idgl)) {
//                                        GetData.count_circle++;
//                                    }
                                    ArrayList<User> la = new ArrayList<User>();
                                    String res = "[" + json2 + "]";
                                    json2 = new String();
                                    ArrayList<Map> list = new Gson().fromJson(res, la.getClass());
                                    res = new String();
                                    addpol(idgl, cntvn, list, lst, id);

                                }
                                // }
                            }

                        });

                    }
                } finally {

                    exec2.shutdown();
                    try {
                        exec2.awaitTermination(1, TimeUnit.DAYS);
                    } catch (InterruptedException ex) {
                        System.out.println("370");
                    }
                }

                try (FileWriter writer2 = new FileWriter(GetData.path_script + "id.txt", false)) {

                    for (String s : id) {
                        massID.add(s);
                        writer2.write(s + "6744782723371285125003");
                    }

                    writer2.close();
                } catch (Exception ex) {
                    System.out.println("268");
                    // System.out.println(ex.getMessage());
                }

                vr(SaveData, "4) заполнение lst друзей = " + massidvr.size() + " составило= ");
                SaveData = Calendar.getInstance();
                try (FileWriter writer = new FileWriter(GetData.path_script + "pr.txt", false)) {

                    for (String s : lst) {
                        writer.write(s);

                    }
                    writer.close();

                } catch (Exception ex) {
                    System.out.println("385");
                    System.out.println(ex.getMessage());
                }

                vr(SaveData, "5) запись в файл pr.txt " + massidvr.size() + " составило= ");
                SaveData = Calendar.getInstance();
                try {
                    String g = "BULK INSERT " + GetData.NameDB + ".dbo.BASE_TABLE FROM" + "'" + GetData.path_script + "pr.txt' WITH(FIELDTERMINATOR = ',',ROWTERMINATOR = '6744782723371285125003')";
                    stmt.executeUpdate(g);
                } catch (Exception ex) {
                    System.out.println("264 errinsert tabl");
                    Reinserting(stmt);
                }

                try {
                    stmt.executeUpdate("INSERT INTO " + GetData.NameDB + ".dbo.statistic VALUES(" + ii2 + "," + count + ")");
                } catch (Exception ex) {
                    System.out.println("270");
                }

                try {
                    String g = "BULK INSERT " + GetData.NameDB + ".dbo.id_to FROM" + "'" + GetData.path_script + "id.txt' WITH(ROWTERMINATOR = '6744782723371285125003')";
                    stmt.executeUpdate(g);
                } catch (Exception ex) {
                    System.out.println("284 errinsert err id_to");

                }

                vr(SaveData, "6) добавление в таблице BASE_TABLE " + massidvr.size() + " (lst.size()= " + lst.size() + " ) " + " составило= ");

            } finally {

            }
        }
    }

    public void Reinserting(Statement stmt) {

        try {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Вставить повторно id_to_id в базу?", "Warning", dialogButton);
            if (dialogResult == JOptionPane.YES_OPTION) {
                try {

                    String g = "BULK INSERT " + GetData.NameDB + ".dbo.BASE_TABLE FROM" + "'" + GetData.path_script + "pr.txt' WITH(FIELDTERMINATOR = ',',ROWTERMINATOR = '6744782723371285125003')";
                    stmt.executeUpdate(g);

                } catch (Exception ex) {
                    System.out.println("381 errinsert n1");
                    Reinserting(stmt);
                }
            } else {
            }
        } finally {

        }

    }

    public void vr(Calendar SaveData, String outp) {

        try {
            FileWriter writer5 = new FileWriter(GetData.path_script + "logp.txt", true);
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
        }

    }

    public static void Reiterative(ArrayList<String> massidvr) {

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

    public static void addpol(String idgl, Integer cnt, ArrayList<Map> list, ConcurrentSkipListSet<String> lst, ConcurrentSkipListSet<String> id) {
        String z1 = "";
        User s;
        Double g;
        Integer g2;
        //  FileWriter writer = new FileWriter(GetData.path_script+"id.txt", false);

        for (Map t : list) {

            s = new User();
            z1 = "";
            for (Object entry : t.keySet()) {
                Object val = t.get(entry);
                if (entry.equals("uid")) {
                    g = (Double) val;
                    g2 = g.intValue();
                    s.uid = g2.toString();
                }
                if (entry.equals("sex")) {
                    g = (Double) val;
                    g2 = g.intValue();
                    s.sex = g2.toString();
                }
                if (entry.equals("bdate")) {

                    String str = "";
                    String st = (String) val;
                    int p = st.indexOf('.');
                    if (p >= 0) {
                        String left = st.substring(p + 1);

                        p = left.indexOf('.');

                        if (p >= 0) {
                            str = left.substring(p + 1);
                        }

                    }
                    if (!"".equals(str)) {
                        s.bdate = String.valueOf(2015 - Integer.parseInt(str));
                    }

                }
                if (entry.equals("city")) {
                    g = (Double) val;
                    g2 = g.intValue();
                    s.city = g2.toString();
                }
                if (entry.equals("country")) {
                    g = (Double) val;
                    g2 = g.intValue();
                    s.country = g2.toString();
                }
                if (entry.equals("hidden")) {
                    g = (Double) val;
                    g2 = g.intValue();
                    s.hidden = g2.toString();
                }
                if (entry.equals("user_id")) {
                    g = (Double) val;
                    g2 = g.intValue();
                    s.user_id = g2.toString();
                }
            }
            z1 += idgl + ","
                    + "'" + s.uid + "'" + ","
                    + cnt + ","
                    + (s.sex == null ? "" : "'" + s.sex + "'") + ","
                    + (s.bdate == null ? "" : "'" + s.bdate + "'") + ","
                    + (s.city == null ? "" : "'" + s.city + "'") + ","
                    + (s.country == null ? "" : "'" + s.country + "'") + ","
                    + (s.hidden == null ? "" : "'" + s.hidden + "'") + ","
                    + GetData.count_circle + "6744782723371285125003";
            lst.add(z1);
            current_friends.add(s.uid);

        }

        //   writer.close();
        z1 = new String();
        id.add(idgl);

    }

}
