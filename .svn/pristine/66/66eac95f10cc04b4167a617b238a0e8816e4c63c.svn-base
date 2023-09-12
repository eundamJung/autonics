//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.http;

import com.tool.user.UserSession;
import com.tool.util.StringUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PnOHttpUtil extends HttpCommon {
    public PnOHttpUtil() {
    }

    public static JSONObject getOrganization(UserSession userSession) throws Exception {
        CloseableHttpClient httpClient = getHttpClient(userSession);

        JSONObject resultJson;
        try {
            HttpGet httpReq = new HttpGet(StringUtil.replaceStr(SERVICE_PNO_ORGANIZATION, new String[0]));
            CloseableHttpResponse httpResp = httpClient.execute(httpReq);
            String content = IOUtils.toString(httpResp.getEntity().getContent());
            resultJson = (JSONObject)(new JSONParser()).parse(content);
        } catch (Exception var9) {
            throw var9;
        } finally {
            IOUtils.closeQuietly(httpClient);
        }

        return resultJson;
    }

    public static JSONObject getMember(UserSession userSession, String orgId) throws Exception {
        CloseableHttpClient httpClient = getHttpClient(userSession);

        JSONObject resultJson;
        try {
            HttpGet httpReq = new HttpGet(StringUtil.replaceStr(SERVICE_PNO_MEMBER, new String[]{orgId}));
            CloseableHttpResponse httpResp = httpClient.execute(httpReq);
            String content = getStringFromStream(httpResp.getEntity().getContent());
            resultJson = (JSONObject)(new JSONParser()).parse(content);
        } catch (Exception var10) {
            throw var10;
        } finally {
            IOUtils.closeQuietly(httpClient);
        }

        return resultJson;
    }
}
