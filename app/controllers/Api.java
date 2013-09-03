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

import javax.xml.bind.DatatypeConverter;

import views.html.*;
import models.*;

@Security.Authenticated(ApiSecured.class)
public class Api extends Controller {

    public static Result getUserInfo() {
        ObjectNode result = Json.newObject();
        result.put("username", request().username());
        return ok(result);
    }

    private static Result error(String msg){
        ObjectNode result = Json.newObject();
        result.put("result", "error");
        result.put("message", msg);
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
        result.put("id", rec.id);
        return ok(result);
    }

    @BodyParser.Of(play.mvc.BodyParser.Json.class)
    public static Result addPhoto(Long id) {
        Record rec = Record.findById(id);
        if(rec == null || !rec.user.username.equals(request().username())) 
            return error("invalid id");
        JsonNode json = request().body().asJson();
        if(json == null) return error("invalid data");
        String bin_s = json.findPath("photo").getTextValue();
        if(bin_s == null || bin_s.length() == 0)
            return error("photo field requied");
        byte[] bin = DatatypeConverter.parseBase64Binary(bin_s);
        rec.photo = bin;
        rec.save();

        ObjectNode result = Json.newObject();
        result.put("result", "ok");
        return ok(result);
    }

}

