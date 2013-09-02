/// @file Global.java  @date 09/02/2013
/// @author i@BlahGeek.com

import play.*;
import play.libs.*;

import java.util.*;

import com.avaje.ebean.*;

import models.*;

public class Global extends GlobalSettings {
    
    public void onStart(Application app) {
        if(Ebean.find(User.class).findRowCount() == 0) {
            Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("initial-data.yml");
            Ebean.save(all.get("users"));
        }
    }
    
}

