/// @file User.java  @date 09/01/2013
/// @author i@BlahGeek.com
package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class User extends Model {

    @Id
    @Constraints.Required
    @Formats.NonEmpty
    public String username;
    
    @Constraints.Required
    public String password;
    
    public static Model.Finder<String,User> find = new Model.Finder(String.class, User.class);
    
    public static List<User> findAll() {
        return find.all();
    }

    public static User findName(String name) {
        return find.where().eq("username", name).findUnique();
    }
    
    public static User auth(String name, String password) {
        return find.where().eq("username", name).eq("password", password).findUnique();
    }
    
}

