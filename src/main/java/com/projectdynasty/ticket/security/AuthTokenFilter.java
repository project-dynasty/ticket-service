package com.projectdynasty.ticket.security;

import com.projectdynasty.ticket.TicketService;
import com.projectdynasty.ticket.model.AccountData;
import com.projectdynasty.ticket.payload.Account;
import de.alexanderwodarz.code.web.rest.RequestData;
import de.alexanderwodarz.code.web.rest.authentication.AuthenticationFilter;
import de.alexanderwodarz.code.web.rest.authentication.AuthenticationFilterResponse;
import de.alexanderwodarz.code.web.rest.authentication.AuthenticationManager;
import de.alexanderwodarz.code.web.rest.authentication.CorsResponse;

import java.util.ArrayList;
import java.util.List;

public class AuthTokenFilter extends AuthenticationFilter {

    public static AuthenticationFilterResponse doFilter(RequestData request) {
        try {
            String jwt = parseJwt(request.getAuthorization());
            if (jwt != null && TicketService.JWT_UTILS.validateJwtToken(jwt)) {
                String username = TicketService.JWT_UTILS.getSubject(jwt);
                AccountData account = (AccountData) TicketService.USER_DATABASE.getTable(AccountData.class).query().addParameter("username", username).executeOne();
                if (account == null) {
                    return AuthenticationFilterResponse.UNAUTHORIZED();
                }
                AuthenticationManager.setAuthentication(new RequestDetails(new Account(account), jwt));
                return AuthenticationFilterResponse.OK();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AuthenticationFilterResponse.UNAUTHORIZED();
    }

    public static CorsResponse doCors(RequestData data) {
        CorsResponse response = new CorsResponse();
        response.setCredentials(true);
        response.setOrigin("*");
        response.setHeaders("authorization, content-type, token");
        return response;
    }

    public static String parseJwt(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}