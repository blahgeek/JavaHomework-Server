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
        Record rec = new Record();
        rec.user = user;

        JsonNode alt = json.get("altitude");
        if(alt != null) rec.altitude = alt.getDoubleValue();
        JsonNode lat = json.get("latitude");
        if(lat != null) rec.latitude = lat.getDoubleValue();
        JsonNode lon = json.get("longtitude");
        if(lon != null) rec.longtitude = lon.getDoubleValue();
        JsonNode spe = json.get("speed");
        if(spe != null) rec.speed = spe.getDoubleValue();
        JsonNode acc = json.get("accuracy");
        if(acc != null) rec.altitude = acc.getDoubleValue();
        
        rec.save();
        ObjectNode result = Json.newObject();
        result.put("result", "ok");
        return ok(result);
    }

}

