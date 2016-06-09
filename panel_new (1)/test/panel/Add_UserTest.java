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
public class Add_UserTest {
    
    public Add_UserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of main method, of class AddUserForm.
     */
    @Test
    public void testMain() {
        System.out.println("main");
       
        AddUserForm usr=new AddUserForm();
        usr.settxt1("12345");
        usr.settxt2("Nm");
        usr.saveUser();
        
    }
    
}
