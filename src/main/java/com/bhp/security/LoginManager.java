package com.bhp.security;

import com.bhp.facades.AdminFacade;
import com.bhp.facades.ClientFacade;
import com.bhp.facades.CompanyFacade;
import com.bhp.facades.CustomerFacade;

import java.sql.SQLException;

public class LoginManager {

    private static LoginManager instance = null;

    private LoginManager(){

    }

    public static LoginManager getInstance() {
        if(instance == null){
            synchronized (LoginManager.class){
                if(instance == null){
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    //factory method
    public ClientFacade login(String email,String password,ClientType clientType) throws SQLException {
        switch (clientType){
            case Administrator:
                AdminFacade adminFacade = new AdminFacade();
                if(adminFacade.login(email,password)){
                    return adminFacade;
                }
                break;
            case Company:
                CompanyFacade companyFacade = new CompanyFacade();
                if(companyFacade.login(email,password)){
                    return companyFacade;
                }
                break;
            case Customer:
                CustomerFacade customerFacade = new CustomerFacade();
                if(customerFacade.login(email, password)){
                    return customerFacade;
                }
                break;
            default:
                break;
        }
        return null;
    }
}
