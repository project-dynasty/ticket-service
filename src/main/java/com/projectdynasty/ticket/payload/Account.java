package com.projectdynasty.ticket.payload;

import com.projectdynasty.ticket.model.AccountData;
import de.alexanderwodarz.code.web.rest.authentication.Authentication;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Account {

    private final AccountData data;

    public long getUserId() {
        return data.userId;
    }

    public String getUsername() {
        return data.username;
    }

    public String getLastName() {
        return data.lastName;
    }

    public String getFirstName() {
        return data.firstName;
    }

    public boolean isDisabled() {
        return data.disabled;
    }

}
