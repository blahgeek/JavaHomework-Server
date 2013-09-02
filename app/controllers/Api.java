/// @file Api.java  @date 09/02/2013
/// @author i@BlahGeek.com
package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import play.libs.Json;
import play.libs.Json.*;
import static play.libs.Json.toJson;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.JsonNode;

import views.html.*;
import models.*;

@Security.Authenticated(ApiSecured.class)
public class Api extends Controller {

    public static Result getUserInfo() {
        ObjectNode result = Json.newObject();
        result.put("username", request().username());
        return ok(result);
    }

    @BodyParser.Of(play.mvc.BodyParser.Json.class)
    public static Result postRecord() {
        JsonNode json = request().body().asJson();
        User user = User.findName(request().username());
        Record rec = Record.create(user);

        Number alt = json.findPath("altitude").getNumberValue();
        if(alt != null) rec.altitude = alt.doubleValue();
        Number lat = json.findPath("latitude").getNumberValue();
        if(lat != null) rec.latitude = lat.doubleValue();
        Number lon = json.findPath("longtitude").getNumberValue();
        if(lon != null) rec.longtitude = lon.doubleValue();
        Number spe = json.findPath("speed").getNumberValue();
        if(spe != null) rec.speed = spe.doubleValue();
        Number acc = json.findPath("accuracy").getNumberValue();
        if(acc != null) rec.altitude = acc.doubleValue();
        
        rec.save();
        ObjectNode result = Json.newObject();
        result.put("result", "ok");
        return ok(result);
    }

}

