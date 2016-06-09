/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panel;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dima
 */
public class Setting_STest {

    public Setting_STest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of setnotebook method, of class SettingForm.
     */
    @Test
    public void testSetnotebook() {
        System.out.println("установка первоначальных настроек");
        try {
            SettingForm setting = new SettingForm();
            setting.setnotebook();
        } catch (Exception e) {
            fail("Ошибка настроек");
        }
    }

    /**
     * Test of main method, of class SettingForm.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        try {
            SettingForm setting = new SettingForm();
            setting.main(args);
        }catch (Exception e) {
            fail("Ошибка main");
        }
        
    }

    /**
     * Test of installSettings method, of class SettingForm.
     */
    @Test
    public void testSet() {
        System.out.println("set");
        try {
            SettingForm.installSettings();
        }catch (Exception e) {
            fail("Ошибка заполнения переменных");
        }
      
    }

}
