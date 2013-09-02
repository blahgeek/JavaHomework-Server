package controllers;

import play.*;
import play.mvc.*;
import play.data.*;

import views.html.*;
import models.*;

public class Application extends Controller {
  
    @Security.Authenticated(Secured.class)
    public static Result index() {
        return ok(index.render("Your name is " + session().get("username")));
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

    public static Result signup() {
        return TODO;
    }
  
}
