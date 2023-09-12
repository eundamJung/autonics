//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.user;

import org.apache.http.impl.client.BasicCookieStore;

public class UserSession {
    private String id;
    private String password;
    private BasicCookieStore cookieStore;
    private String securityContext;

    public UserSession(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BasicCookieStore getCookieStore() {
        return this.cookieStore;
    }

    public void setCookieStore(BasicCookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }

    public String getSecurityContext() {
        return this.securityContext;
    }

    public void setSecurityContext(String securityContext) {
        this.securityContext = securityContext;
    }
}
