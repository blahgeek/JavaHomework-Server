/// @file Secured.java  @date 09/01/2013
/// @author i@BlahGeek.com

package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http.*;

import models.*;

public class Secured extends Security.Authenticator {
    
    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("username");
    }
    
    @Override
    public Result onUnauthorized(Context ctx) {
        ctx.flash().put("error", "Please login");
        return redirect(routes.Application.login());
    }
    
}
