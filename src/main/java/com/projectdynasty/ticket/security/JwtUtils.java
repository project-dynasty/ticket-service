package com.projectdynasty.ticket.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.projectdynasty.ticket.TicketService;
import de.alexanderwodarz.code.log.Level;
import de.alexanderwodarz.code.log.Log;
import de.alexanderwodarz.code.web.rest.RequestData;

import java.util.Date;
import java.util.HashMap;

public class JwtUtils {

    private final String secret = TicketService.CONFIG.get("jwt", TicketService.Jwt.class).getKey();


    public boolean isMobileRequest(RequestData data) {
        String jwt = AuthTokenFilter.parseJwt(data.getHeader("authorization"));
        try {
            return TicketService.VERIFIER.verify(jwt).getClaim("mobile").asBoolean();
        } catch (TokenExpiredException | SignatureVerificationException e) {
            return false;
        }
    }

    public long getDeviceId(RequestData data) {
        String jwt = AuthTokenFilter.parseJwt(data.getHeader("authorization"));
        try {
            return TicketService.VERIFIER.verify(jwt).getClaim("deviceId").asLong();
        } catch (TokenExpiredException | SignatureVerificationException e) {
            return 0L;
        }
    }

    public String getSubject(String token) {
        try {
            return TicketService.VERIFIER.verify(token).getSubject();
        } catch (TokenExpiredException | SignatureVerificationException e) {
            return null;
        }
    }

    public boolean validateJwtToken(String authToken) {
        try {
            TicketService.VERIFIER.verify(authToken);
            return getClaim(authToken, "refresh").isMissing();
        } catch (SignatureVerificationException | JWTDecodeException |
                 TokenExpiredException e) {
            Log.log(e.getMessage(), Level.ERROR);
        }
        return false;
    }

    public HashMap<String, String> generatePushAuthorizationToken() {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        Date expire = new Date((new Date()).getTime() + (1000 * 60));
        HashMap<String, String> headers = new HashMap<>();
        String auth = JWT.create().withIssuer(TicketService.CONFIG.get("jwt", TicketService.Jwt.class).getIss())
                .withExpiresAt(expire)
                .sign(algorithm);
        headers.put("Authorization", "Bearer " + auth);
        return headers;
    }

    public Claim getClaim(String token, String claim) {
        try {
            return TicketService.VERIFIER.verify(token).getClaim(claim);
        } catch (TokenExpiredException e) {
            return null;
        }
    }
}
