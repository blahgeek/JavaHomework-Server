/// @file Record.java  @date 09/02/2013
/// @author i@BlahGeek.com
package models;

import java.util.*;
import java.sql.Timestamp;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;
import com.avaje.ebean.annotation.CreatedTimestamp;

@Entity 
public class Record extends Model {

    @Id
    public Long id;

    @Constraints.Required
    @ManyToOne
    public User user;

    @CreatedTimestamp
    public Timestamp time;

    public Double altitude;
    public Double latitude;
    public Double longtitude;
    public Double speed; // meters/second
    public Double accuracy;

    @Lob
    public String note;

    @Lob
    public byte[] photo;

    public static Model.Finder<Long, Record> find = 
        new Model.Finder(Long.class, Record.class);
    public static List<Record> query(
            User u, Timestamp start, Timestamp end){
        ExpressionList<Record> ret = find.where();
        ret = ret.eq("user", u);
        if(start != null) ret = ret.ge("time", start);
        if(end != null) ret = ret.le("time", end);
        return ret.findList();
    }
    public static Record findById(Long i){
        return find.ref(i);
    }
}

