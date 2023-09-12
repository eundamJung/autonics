//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CollaborationSpace extends UserComboVO {
    private List<Organization> organizations = new ArrayList();

    public CollaborationSpace(String name, String title) {
        super(name, title);
    }

    public List<Organization> getOrganizations() {
        return this.organizations;
    }

    public Organization getOrganization(String name) {
        Iterator var2 = this.organizations.iterator();

        Organization org;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            org = (Organization)var2.next();
        } while(!org.getName().equals(name));

        return org;
    }

    public void addRole(Organization organization, Role role) {
        boolean exist = false;
        Iterator var4 = this.organizations.iterator();

        while(var4.hasNext()) {
            Organization org = (Organization)var4.next();
            if (org.getName().equals(organization.getName())) {
                exist = true;
                organization = org;
                break;
            }
        }

        if (!exist) {
            this.organizations.add(organization);
        }

        organization.addRole(role);
    }
}
