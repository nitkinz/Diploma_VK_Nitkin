/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panel;

import java.io.Serializable;
import static java.lang.reflect.Array.get;

/**
 *
 * @author Дима
 */
public class User implements Serializable {

    public String uid;
    public String sex;
    public String bdate;
    public String city;
    public String country;
    public String hidden;
    public String user_id;

    public User() {
    }

}
