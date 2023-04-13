package com.projectdynasty.ticket.security;

import com.auth0.jwt.JWT;
import com.projectdynasty.ticket.payload.Account;
import de.alexanderwodarz.code.web.rest.authentication.Authentication;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

@RequiredArgsConstructor
@Getter
public class RequestDetails extends Authentication {

    private final Account account;
    private final String jwt;

    public boolean hasPermission(String required) {
        JSONArray arr = new JSONArray(JWT.decode(jwt).getClaim("permissions") + "");
        boolean has = false;
        for (int i = 0; i < arr.length(); i++) {
            JSONObject tmp = arr.getJSONObject(i);
            boolean match = required.matches(tmp.getString("perm").replaceAll("\\*", "(.*)"));
            if(!match)
                continue;
            if(tmp.getBoolean("negate")){
                has = false;
                break;
            }
            has = true;
        }
        return has;
    }

}
