//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SecurityContext {
    private String preferredCollabspace = "";
    private String preferredOrganization = "";
    private String preferredRole = "";
    private List<CollaborationSpace> collaborationSpaces = new ArrayList();

    public SecurityContext() {
    }

    public void addCollaborationSpaces(CollaborationSpace collaborationSpace) {
        this.collaborationSpaces.add(collaborationSpace);
    }

    public List<CollaborationSpace> getCollaborationSpaces() {
        return this.collaborationSpaces;
    }

    public CollaborationSpace getCollaborationSpace(String name) {
        Iterator var2 = this.collaborationSpaces.iterator();

        CollaborationSpace cs;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            cs = (CollaborationSpace)var2.next();
        } while(!cs.getName().equals(name));

        return cs;
    }

    public String getPreferredCollabspace() {
        return this.preferredCollabspace;
    }

    public String getPreferredOrganization() {
        return this.preferredOrganization;
    }

    public String getPreferredRole() {
        return this.preferredRole;
    }

    public void setPreferredCredentials(String preferredCollabspace, String preferredOrganization, String preferredRole) {
        this.preferredCollabspace = preferredCollabspace;
        this.preferredOrganization = preferredOrganization;
        this.preferredRole = preferredRole;
    }
}
