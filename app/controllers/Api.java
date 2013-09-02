/// @file Api.java  @date 09/02/2013
/// @author i@BlahGeek.com
package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import play.libs.Json;
import org.codehaus.jackson.node.ObjectNode;

import views.html.*;
import models.*;

@Security.Authenticated(ApiSecured.class)
public class Api extends Controller {

    public static Result getUserInfo() {
        ObjectNode result = Json.newObject();
        result.put("username", request().username());
        return ok(result);
    }

}

