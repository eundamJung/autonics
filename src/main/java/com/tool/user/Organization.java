//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Organization extends UserComboVO {
    private List<Role> roles = new ArrayList();

    public Organization(String name, String title) {
        super(name, title);
    }

    public Role getRole(String name) {
        Iterator var2 = this.roles.iterator();

        Role role;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            role = (Role)var2.next();
        } while(!role.getName().equals(name));

        return role;
    }

    public List<Role> getRoles() {
        return this.roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }
}
