package com.appsecco.dvja.controllers;

import com.appsecco.dvja.models.User;
import com.appsecco.dvja.services.UserAuthenticationService;
import org.apache.commons.lang.StringUtils;

public class Login extends BaseController {

    private UserAuthenticationService userAuthenticationService;

    private String login;
    private String password;

    public UserAuthenticationService getUserAuthenticationService() {
        return userAuthenticationService;
    }

    public void setUserAuthenticationService(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    public String getLogin() {
        return login;
    }

    public String execute() {
        if (isInvalid(login) || isInvalid(password)) {
            return ERROR;
        }
        com.appsecco.dvja.models.User user = userAuthenticationService.authenticate(login, password);
        if (user == null) {
            return ERROR;
        }
        sessionSetUser(user);
        return SUCCESS;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String logout() {
        sessionRemoveUser();
        return SUCCESS;
    }

    public String execute() {
        if (isInvalid(login) || isInvalid(password)) {
            return ERROR;
        }
        User user = userAuthenticationService.authenticate(login, password);
        if (user == null) {
            return ERROR;
        }
        sessionSetUser(user);
        return SUCCESS;
    }

    public String execute_unused() {
        User user;

        if(StringUtils.isEmpty(getLogin()) || StringUtils.isEmpty(getPassword()))
            return INPUT;

        if((user = userAuthenticationService.authenticate(getLogin(), getPassword())) != null) {
            sessionSetUser(user);
            return SUCCESS;
        }

        addActionError("Authentication failed!");
        return INPUT;
    }
}
