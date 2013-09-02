/// @file ApiSecured.java  @date 09/02/2013
/// @author i@BlahGeek.com

package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http.*;
import javax.xml.bind.DatatypeConverter;

import models.*;

public class ApiSecured extends Security.Authenticator {
    private static final String AUTHORIZATION = "authorization";
    private static final String WWW_AUTHENTICATE = "WWW-Authenticate";
    private static final String REALM = "Basic realm=\"Please Auth\"";
    
    @Override
    public String getUsername(Context ctx) {
        String authHeader = ctx.request().getHeader(AUTHORIZATION);
        if(authHeader == null) return null;
        authHeader = authHeader.substring(6);
        byte[] decodedAuth = DatatypeConverter.parseBase64Binary(authHeader);
        String []credString = null;
        try{
            credString = new String(decodedAuth, "UTF-8").split(":");
        } catch(Exception e){
            credString = null;
        }
        if(credString == null || credString.length != 2)
            return null;
        final String user = credString[0];
        final String passwd = credString[1];
        User u = User.auth(user, passwd);
        if(u == null) return null;
        return u.username;
    }
    
}
