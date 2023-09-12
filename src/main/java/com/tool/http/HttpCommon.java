//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.http;

import com.tool.user.UserSession;
import com.tool.util.Constants;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

public class HttpCommon extends Constants {
    public HttpCommon() {
    }

    static CloseableHttpClient getHttpClient(UserSession userSession) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create().setDefaultCookieStore(userSession.getCookieStore());
        List<BasicHeader> headers = new ArrayList();
        headers.add(new BasicHeader("Accept-Language", Locale.getDefault().getLanguage()));
        String securityContext = userSession.getSecurityContext();
        if (StringUtils.isNotEmpty(securityContext)) {
            headers.add(new BasicHeader("SecurityContext", userSession.getSecurityContext()));
        }

        httpClientBuilder.setDefaultHeaders(headers);
        return httpClientBuilder.build();
    }

    static UrlEncodedFormEntity paramMapToFormEntity(Map<String, String> paramMap) throws UnsupportedEncodingException {
        ArrayList<NameValuePair> postParams = new ArrayList();
        Iterator var2 = paramMap.entrySet().iterator();

        while(var2.hasNext()) {
            Entry<String, String> entry = (Entry)var2.next();
            postParams.add(new BasicNameValuePair((String)entry.getKey(), (String)entry.getValue()));
        }

        return new UrlEncodedFormEntity(postParams, StandardCharsets.UTF_8);
    }

    static String getStringFromStream(InputStream is) throws Exception {
        StringBuffer result = new StringBuffer();

        try {
            BufferedReader getReader = new BufferedReader(new InputStreamReader(is));

            String var4;
            try {
                String inputLine;
                while((inputLine = getReader.readLine()) != null) {
                    result.append(inputLine);
                }

                getReader.close();
                var4 = result.toString();
            } catch (Throwable var6) {
                try {
                    getReader.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }

                throw var6;
            }

            getReader.close();
            return var4;
        } catch (Exception var7) {
            throw var7;
        }
    }

    static String encode(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }
}
