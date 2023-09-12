//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.util;

import com.tool.common.resource.AppConfig;
import org.apache.commons.lang3.StringUtils;

public class Constants {
    public static final String SERVICE_BASEURL;
    public static final String SERVICE_CACHE_CLEARCACHE;
    public static final String SERVICE_SESSION_TIMEOUT;
    public static final String SERVICE_SESSION_INVALIDATE;
    public static final String SERVICE_SESSION_HEALTHCHECK;
    public static final String SERVICE_FUNCTIONS;
    public static final String SERVICE_USER_PNO;
    public static final String SERVICE_USER_SECURITYCONTEXT;
    public static final String SERVICE_FILE_SEARCH;
    public static final String SERVICE_FILE_PRE_CHECKOUT;
    public static final String SERVICE_FILE_CHECKOUT;
    public static final String SERVICE_FILE_PRE_CHECKIN;
    public static final String SERVICE_FILE_CHECKIN;
    public static final String SERVICE_FILE_LOCKUNLOCK;
    public static final String SERVICE_FILE_INFO;
    public static final String SERVICE_PNO_ORGANIZATION;
    public static final String SERVICE_PNO_MEMBER;
    public static final String SERVICE_PUBLISH_SEARCH;
    public static final String SERVICE_PUBLISH_ACCEPT;
    public static final String SERVICE_PUBLISH_DOWNLOAD;
    public static final String SERVICE_PUBLISH_UPDATE_COUNT;
    public static final String SERVICE_PUBLISH_DOWNLOAD_PRECHECK;
    public static final String SERVICE_PUBLISH_PRINT;
    public static final String SERVICE_PUBLISH_PRINT_INCREASE;
    public static final String SERVICE_PUBLISH_VIEW;
    public static final String SERVICE_PUBLISH_INFO;
    public static final String SERVICE_PUBLISH_TRANSFEROWNERSHIP;
    public static final String SERVICE_PUBLISH_PRINTSERVICE;
    public static final String SERVICE_LOGIN_TICKET_GET;
    public static final String SERVICE_AUTHENTICATION_LOGIN;

    public Constants() {
    }

    public static String getSapceUrl() {
        return "https://" + SERVICE_BASEURL + "/3dspace";
    }

    public static String getPassportUrl() {
        return "https://" + SERVICE_BASEURL + ":40443/3dpassport";
    }

    static {
        String baseUrl = AppConfig.getUserConfig("service.baseurl");
        if (StringUtils.isEmpty(baseUrl)) {
            baseUrl = AppConfig.getAppConfig("service.baseurl");
        }

        SERVICE_BASEURL = baseUrl;
        String var10000 = getSapceUrl();
        SERVICE_CACHE_CLEARCACHE = var10000 + AppConfig.getAppConfig("service.cache.clearcache");
        var10000 = getSapceUrl();
        SERVICE_SESSION_TIMEOUT = var10000 + AppConfig.getAppConfig("service.session.timeout");
        var10000 = getSapceUrl();
        SERVICE_SESSION_INVALIDATE = var10000 + AppConfig.getAppConfig("service.session.invalidate");
        var10000 = getSapceUrl();
        SERVICE_SESSION_HEALTHCHECK = var10000 + AppConfig.getAppConfig("service.session.healthCheck");
        var10000 = getSapceUrl();
        SERVICE_FUNCTIONS = var10000 + AppConfig.getAppConfig("service.functions");
        var10000 = getSapceUrl();
        SERVICE_USER_PNO = var10000 + AppConfig.getAppConfig("service.user.pno");
        var10000 = getSapceUrl();
        SERVICE_USER_SECURITYCONTEXT = var10000 + AppConfig.getAppConfig("service.user.securitycontext");
        var10000 = getSapceUrl();
        SERVICE_FILE_SEARCH = var10000 + AppConfig.getAppConfig("service.file.search");
        var10000 = getSapceUrl();
        SERVICE_FILE_PRE_CHECKOUT = var10000 + AppConfig.getAppConfig("service.file.precheckout");
        var10000 = getSapceUrl();
        SERVICE_FILE_CHECKOUT = var10000 + AppConfig.getAppConfig("service.file.checkout");
        var10000 = getSapceUrl();
        SERVICE_FILE_PRE_CHECKIN = var10000 + AppConfig.getAppConfig("service.file.precheckin");
        var10000 = getSapceUrl();
        SERVICE_FILE_CHECKIN = var10000 + AppConfig.getAppConfig("service.file.checkin");
        var10000 = getSapceUrl();
        SERVICE_FILE_LOCKUNLOCK = var10000 + AppConfig.getAppConfig("service.file.lockunlock");
        var10000 = getSapceUrl();
        SERVICE_FILE_INFO = var10000 + AppConfig.getAppConfig("service.file.info");
        var10000 = getSapceUrl();
        SERVICE_PNO_ORGANIZATION = var10000 + AppConfig.getAppConfig("service.pno.organization");
        var10000 = getSapceUrl();
        SERVICE_PNO_MEMBER = var10000 + AppConfig.getAppConfig("service.pno.member");
        var10000 = getSapceUrl();
        SERVICE_PUBLISH_SEARCH = var10000 + AppConfig.getAppConfig("service.publish.search");
        var10000 = getSapceUrl();
        SERVICE_PUBLISH_ACCEPT = var10000 + AppConfig.getAppConfig("service.publish.accept");
        var10000 = getSapceUrl();
        SERVICE_PUBLISH_DOWNLOAD = var10000 + AppConfig.getAppConfig("service.publish.download");
        var10000 = getSapceUrl();
        SERVICE_PUBLISH_UPDATE_COUNT = var10000 + AppConfig.getAppConfig("service.publish.update.count");
        var10000 = getSapceUrl();
        SERVICE_PUBLISH_DOWNLOAD_PRECHECK = var10000 + AppConfig.getAppConfig("service.publish.download.precheck");
        var10000 = getSapceUrl();
        SERVICE_PUBLISH_PRINT = var10000 + AppConfig.getAppConfig("service.publish.print");
        var10000 = getSapceUrl();
        SERVICE_PUBLISH_PRINT_INCREASE = var10000 + AppConfig.getAppConfig("service.publish.print.increase");
        var10000 = getSapceUrl();
        SERVICE_PUBLISH_VIEW = var10000 + AppConfig.getAppConfig("service.publish.view");
        var10000 = getSapceUrl();
        SERVICE_PUBLISH_INFO = var10000 + AppConfig.getAppConfig("service.publish.info");
        var10000 = getSapceUrl();
        SERVICE_PUBLISH_TRANSFEROWNERSHIP = var10000 + AppConfig.getAppConfig("service.publish.transferownership");
        var10000 = getSapceUrl();
        SERVICE_PUBLISH_PRINTSERVICE = var10000 + AppConfig.getAppConfig("service.publish.printservice");
        SERVICE_LOGIN_TICKET_GET = getPassportUrl() + "/login?action=get_auth_params";
        SERVICE_AUTHENTICATION_LOGIN = getPassportUrl() + "/login";
    }
}
