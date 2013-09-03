package controllers;
import javax.persistence.*;
import java.util.*;
import java.sql.Timestamp;

import play.*;
import play.mvc.*;
import play.data.*;

import views.html.*;
import models.*;

public class Application extends Controller {

    public static class Filter {
        public String startdate;
        public String enddate;
    }
    static Form<Filter> filterForm = Form.form(Filter.class);

    @Security.Authenticated(Secured.class)
    public static Result filterIndex(){
        Form<Filter> form = filterForm.bindFromRequest();
        Timestamp a = null, b = null;
        try{
            if(form.hasErrors()) throw new IllegalArgumentException();
            a = Timestamp.valueOf(form.get().startdate+" 00:00:00");
            b = Timestamp.valueOf(form.get().enddate+" 00:00:00");
        } catch(IllegalArgumentException e){
            return badRequest();
        }
        User user = User.findName(request().username());
        List<Record> recs = Record.query(user, a, b);
        return renderIndex(user, recs);
    }
  
    @Security.Authenticated(Secured.class)
    public static Result index() {
        User user = User.findName(request().username());
        return renderIndex(user, Record.query(user, null, null));
    }

    private static Result renderIndex(User u, List<Record> recs){
        return ok(index.render(filterForm, u.username, recs));
    }

    static Form<User> loginForm = Form.form(User.class);

    public static Result login() {
        return ok(login.render(loginForm));
    }

    public static Result auth() {
        Form<User> form = loginForm.bindFromRequest();
        if(form.hasErrors() || 
                User.auth(form.get().username, form.get().password) == null){
            flash("error", "Invalid");
            return redirect(routes.Application.login());
        }
        session("username", form.get().username);
        return redirect(routes.Application.index());
    }

    public static Result logout() {
        session().clear();
        flash("info", "You are logged out");
        return redirect(routes.Application.login());
    }

    @Security.Authenticated(Secured.class)
    public static Result getPhoto(Long id){
        Record rec = Record.findById(id);
        if(rec == null || !rec.user.username.equals(request().username())) 
            return badRequest();
        return ok(rec.photo).as("image/jpeg");
    }

    public static Result signup() {
        return TODO;
    }
  
}
