/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panel;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import javax.swing.DefaultListModel;

/**
 *
 * @author Дима
 */
public class city_count extends javax.swing.JPanel {

    /**
     * Creates new form city_count
     */
    public static DefaultListModel listModelcity = new DefaultListModel();
    public static DefaultListModel listModelcountry = new DefaultListModel();

    public city_count(Map<String, Integer> city, Map<String, Integer> country) throws IOException {
        initComponents();
        jList1.setModel(listModelcity);
        jList2.setModel(listModelcountry);
        listModelcity.removeAllElements();
        listModelcountry.removeAllElements();
        Calendar SaveData = Calendar.getInstance();
        basecount(city, listModelcity, 1);
        vr(SaveData, "Закончили сбор городов ");

        SaveData = Calendar.getInstance();
        basecount(country, listModelcountry, 2);
        vr(SaveData, "Закончили сбор стран ");

    }

    public city_count(String city, String country) {
        initComponents();

        listModelcity.removeAllElements();
        listModelcountry.removeAllElements();
        jList1.setModel(listModelcity);
        jList2.setModel(listModelcountry);

        String masscity[] = city.split(",");
        String masscountry[] = country.split(",");

        for (String ct : masscity) {
            if (!ct.equals("")) {
                listModelcity.addElement(ct);
            }

        }
        for (String ctr : masscountry) {
            if (!ctr.equals("")) {
                listModelcountry.addElement(ctr);
            }

        }

    }

    public void basecount(Map<String, Integer> city, DefaultListModel listModelcityl, int select) throws IOException {
        int symm = 0;
        Map<String, Integer> cityy;
        cityy = sortByValue(city);
        for (Object vl : cityy.keySet()) {
            symm += cityy.get(vl);
        }
        Map<String, Double> cityyper = new HashMap<String, Double>();
        for (String vl : cityy.keySet()) {
            int vll = cityy.get(vl);
            double per = (vll * 100);
            double per2 = per / symm;
            cityyper.put(vl, per2);
        }
        Map<String, Integer> cityyper_res = new HashMap<String, Integer>();
        for (String vl : cityyper.keySet()) {
            if (cityyper.get(vl) >= 5) {
                double vll = cityyper.get(vl);
                int vlll = (int) vll;
                cityyper_res.put(vl, vlll);
            }
        }
        Map<String, Integer> cityyper_res_res = sortByValue(cityyper_res);
        int i = 0;
        for (String vl : cityyper_res_res.keySet()) {

            if (select == 1) {
                String cityty = city(vl.replace("'", ""));
                if (!cityty.equals("")) {
                    Analysis.cit += cityty + " - " + cityyper_res_res.get(vl) + "%" + ",";
                    listModelcityl.addElement(cityty + " - " + cityyper_res_res.get(vl) + "%");
                }
            }
            if (select == 2) {
                String country = country(vl.replace("'", ""));
                if (!country.equals("")) {
                    Analysis.countr += country + " - " + cityyper_res_res.get(vl) + "%" + ",";
                    listModelcityl.addElement(country + " - " + cityyper_res_res.get(vl) + "%");
                }
            }
            i++;
        }

    }

    public String city(String ID) throws MalformedURLException, IOException {

        try {
            String API_URL = "https://api.vk.com/method/";
            String method = "database.getCitiesById";
            String url = "";

            url = API_URL + method + "?city_ids=" + ID;
            String json;

            URL url2 = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url2.openStream()));
            json = reader.readLine();
            reader.close();

            String idgl = json.replace("'", "");
            idgl = idgl.replace("{\"response\":[", "").replace("]}", "");
            String res = "[" + idgl + "]";
            ArrayList<CitCountry> la = new ArrayList<CitCountry>();
            ArrayList<Map> list = new Gson().fromJson(res, la.getClass());
            String city = "";
            for (Map t : list) {

                for (Object entry : t.keySet()) {

                    Object val = t.get(entry);

                    if (entry.equals("name")) {

                        city = (String) val;

                    }

                }

            }

            return city;
        } catch (Exception e) {
            return "";
        }
    }

    public String country(String ID) throws MalformedURLException, IOException {

        try {
            String API_URL = "https://api.vk.com/method/";
            String method = "database.getCountriesById";
            String url = "";

            url = API_URL + method + "?country_ids=" + ID;
            String json;

            URL url2 = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url2.openStream()));
            json = reader.readLine();
            reader.close();

            String idgl = json.replace("'", "");
            idgl = idgl.replace("{\"response\":[", "").replace("]}", "");
            String res = "[" + idgl + "]";
            ArrayList<CitCountry> la = new ArrayList<CitCountry>();
            ArrayList<Map> list = new Gson().fromJson(res, la.getClass());
            String city = "";
            for (Map t : list) {

                for (Object entry : t.keySet()) {

                    Object val = t.get(entry);

                    if (entry.equals("name")) {

                        city = (String) val;

                    }

                }

            }

            return city;
        } catch (Exception e) {
            return "";
        }
    }

    public static <K, V extends Comparable<? super V>> Map<K, V>
            sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list
                = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public void vr(Calendar SaveData, String outp) {

        try {
            FileWriter writer5 = new FileWriter(GetData.path_script + "log.txt", true);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Города");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Страны");

        jList1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jScrollPane1.setViewportView(jList1);

        jList2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jScrollPane2.setViewportView(jList2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
            .addGroup(layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(145, 145, 145))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap(66, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
