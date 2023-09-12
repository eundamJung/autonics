//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.http;

import com.tool.user.UserSession;
import com.tool.util.ExceptionUtil;
import com.tool.util.StringUtil;
import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PublishHttpUtil extends HttpCommon {
    public static final int DOWNLOAD_ACTION = 0;
    public static final int PRINT_ACTION = 1;
    public static final int VIEW_ACTION = 2;

    public PublishHttpUtil() {
    }

    public static JSONObject search(UserSession userSession, String state) throws Exception {
        CloseableHttpClient httpClient = getHttpClient(userSession);

        JSONObject resultJson;
        try {
            HttpGet httpReq = new HttpGet(StringUtil.replaceStr(SERVICE_PUBLISH_SEARCH, new String[]{state}));
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

    public static JSONObject info(UserSession userSession, String objectId) throws Exception {
        CloseableHttpClient httpClient = getHttpClient(userSession);

        JSONObject resultJson;
        try {
            HttpGet httpReq = new HttpGet(StringUtil.replaceStr(SERVICE_PUBLISH_INFO, new String[]{objectId}));
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

    public static JSONObject accept(UserSession session, List<String> checkedIds) {
        JSONObject resultJson = new JSONObject();
        CloseableHttpClient httpClient = getHttpClient(session);

        try {
            HttpPost httpReq = new HttpPost(SERVICE_PUBLISH_ACCEPT);
            ArrayList<NameValuePair> parameters = new ArrayList();
            Iterator var6 = checkedIds.iterator();

            while(var6.hasNext()) {
                String id = (String)var6.next();
                parameters.add(new BasicNameValuePair("objectId", id));
            }

            httpReq.setEntity(new UrlEncodedFormEntity(parameters, StandardCharsets.UTF_8));
            CloseableHttpResponse httpResp = httpClient.execute(httpReq);
            int status = httpResp.getStatusLine().getStatusCode();
            String content = getStringFromStream(httpResp.getEntity().getContent());
            if (status != 200 && status != 500) {
                throw new Exception("HTTP status codes " + status);
            }

            resultJson = (JSONObject)(new JSONParser()).parse(content);
        } catch (Exception var12) {
            var12.printStackTrace();
        } finally {
            IOUtils.closeQuietly(httpClient);
        }

        return resultJson;
    }

    public static JSONObject checkout(UserSession userSession, String url, List<String> checkedIds, File desFile) {
        JSONObject resultJson = new JSONObject();
        CloseableHttpClient httpClient = getHttpClient(userSession);

        try {
            HttpPost httpReq = new HttpPost(url);
            ArrayList<NameValuePair> parameters = new ArrayList();
            Iterator var8 = checkedIds.iterator();

            while(var8.hasNext()) {
                String id = (String)var8.next();
                parameters.add(new BasicNameValuePair("objectId", id));
            }

            httpReq.setEntity(new UrlEncodedFormEntity(parameters, StandardCharsets.UTF_8));
            CloseableHttpResponse httpResp = httpClient.execute(httpReq);
            int statusCode = httpResp.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                FileUtils.copyInputStreamToFile(httpResp.getEntity().getContent(), desFile);
            } else {
                resultJson = (JSONObject)(new JSONParser()).parse(getStringFromStream(httpResp.getEntity().getContent()));
            }
        } catch (Exception var13) {
            resultJson = ExceptionUtil.createErrorJson(var13);
        } finally {
            IOUtils.closeQuietly(httpClient);
        }

        return resultJson;
    }

    /** @deprecated */
    @Deprecated
    public static JSONObject printIncrease(UserSession userSession, String objectId) {
        CloseableHttpClient httpClient = getHttpClient(userSession);

        JSONObject var5;
        try {
            HttpGet httpReq = new HttpGet(StringUtil.replaceStr(SERVICE_PUBLISH_PRINT_INCREASE, new String[]{objectId}));
            CloseableHttpResponse httpResp = httpClient.execute(httpReq);
            String content = getStringFromStream(httpResp.getEntity().getContent());
            JSONObject resultJson = (JSONObject)(new JSONParser()).parse(content);
            return resultJson;
        } catch (Exception var10) {
            var5 = ExceptionUtil.createErrorJson(var10);
        } finally {
            IOUtils.closeQuietly(httpClient);
        }

        return var5;
    }

    public static JSONObject getPrintService(UserSession session) {
        CloseableHttpClient httpClient = getHttpClient(session);

        JSONObject var3;
        try {
            HttpGet httpReq = new HttpGet(SERVICE_PUBLISH_PRINTSERVICE);
            CloseableHttpResponse httpResp = httpClient.execute(httpReq);
            JSONObject var4 = (JSONObject)(new JSONParser()).parse(getStringFromStream(httpResp.getEntity().getContent()));
            return var4;
        } catch (Exception var8) {
            var3 = ExceptionUtil.createErrorJson(var8);
        } finally {
            IOUtils.closeQuietly(httpClient);
        }

        return var3;
    }

    public static JSONObject downloadPreCheck(UserSession userSession, List<String> checkedIds) {
        JSONObject resultJson = new JSONObject();
        CloseableHttpClient httpClient = getHttpClient(userSession);

        try {
            HttpPost httpReq = new HttpPost(SERVICE_PUBLISH_DOWNLOAD_PRECHECK);
            ArrayList<NameValuePair> parameters = new ArrayList();
            Iterator var6 = checkedIds.iterator();

            while(var6.hasNext()) {
                String id = (String)var6.next();
                parameters.add(new BasicNameValuePair("objectId", id));
            }

            httpReq.setEntity(new UrlEncodedFormEntity(parameters, StandardCharsets.UTF_8));
            CloseableHttpResponse httpResp = httpClient.execute(httpReq);
            int statusCode = httpResp.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                resultJson = (JSONObject)(new JSONParser()).parse(getStringFromStream(httpResp.getEntity().getContent()));
            }
        } catch (Exception var8) {
            resultJson = ExceptionUtil.createErrorJson(var8);
        }

        return resultJson;
    }

    public static JSONObject download(UserSession userSession, List<String> checkedIds, String directory) {
        JSONObject resultJson = new JSONObject();
        CloseableHttpClient httpClient = getHttpClient(userSession);

        try {
            HttpPost httpReq = new HttpPost(SERVICE_PUBLISH_DOWNLOAD);
            ArrayList<NameValuePair> parameters = new ArrayList();
            Iterator var7 = checkedIds.iterator();

            while(var7.hasNext()) {
                String id = (String)var7.next();
                parameters.add(new BasicNameValuePair("objectId", id));
            }

            httpReq.setEntity(new UrlEncodedFormEntity(parameters, StandardCharsets.UTF_8));
            CloseableHttpResponse httpResp = httpClient.execute(httpReq);
            int statusCode = httpResp.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                String fileName = URLDecoder.decode(httpResp.getHeaders("FileName")[0].getValue(), StandardCharsets.UTF_8);
                File file = Paths.get(directory).resolve(fileName).toFile();
                if (file.isFile()) {
                    String baseName = FilenameUtils.getBaseName(fileName);
                    String extension = FilenameUtils.getExtension(fileName);
                    List<String> fileNames = new ArrayList();
                    String filterRegex = baseName + "\\([0-9]+\\)." + extension;
                    File fDirectory = new File(directory);
                    File[] var16 = fDirectory.listFiles();
                    int var17 = var16.length;

                    for(int var18 = 0; var18 < var17; ++var18) {
                        File f = var16[var18];
                        if (f.isFile() && f.getName().matches(filterRegex)) {
                            fileNames.add(f.getName());
                        }
                    }

                    int seq = 1;
                    if (fileNames.size() > 0) {
                        Collections.sort(fileNames);
                        String lastFileName = (String)fileNames.get(fileNames.size() - 1);
                        String strNum = lastFileName.substring(lastFileName.indexOf("(") + 1, lastFileName.lastIndexOf(")"));
                        seq = Integer.parseInt(strNum) + 1;
                    }

                    fileName = baseName + "(" + seq + ")." + extension;
                }

                FileUtils.copyInputStreamToFile(httpResp.getEntity().getContent(), Paths.get(directory).resolve(fileName).toFile());
            } else {
                resultJson = (JSONObject)(new JSONParser()).parse(getStringFromStream(httpResp.getEntity().getContent()));
            }
        } catch (Exception var23) {
            resultJson = ExceptionUtil.createErrorJson(var23);
        } finally {
            IOUtils.closeQuietly(httpClient);
        }

        return resultJson;
    }

    public static JSONObject writeLogAndIncreaseCount(UserSession userSession, List<String> objectIds, int action) {
        new JSONObject();
        CloseableHttpClient httpClient = getHttpClient(userSession);

        JSONObject resultJson;
        try {
            HttpPost httpReq = new HttpPost(SERVICE_PUBLISH_UPDATE_COUNT);
            ArrayList<NameValuePair> parameters = new ArrayList();
            Iterator var7 = objectIds.iterator();

            while(var7.hasNext()) {
                String id = (String)var7.next();
                parameters.add(new BasicNameValuePair("objectId", id));
            }

            parameters.add(new BasicNameValuePair("action", String.valueOf(action)));
            httpReq.setEntity(new UrlEncodedFormEntity(parameters, StandardCharsets.UTF_8));
            CloseableHttpResponse httpResp = httpClient.execute(httpReq);
            resultJson = (JSONObject)(new JSONParser()).parse(IOUtils.toString(httpResp.getEntity().getContent()));
        } catch (Exception var12) {
            resultJson = ExceptionUtil.createErrorJson(var12);
        } finally {
            IOUtils.closeQuietly(httpClient);
        }

        return resultJson;
    }

    public static JSONObject transferOwnership(UserSession userSession, List<String> objectIds, String userId) {
        new JSONObject();
        CloseableHttpClient httpClient = getHttpClient(userSession);

        JSONObject resultJson;
        try {
            HttpPost httpReq = new HttpPost(SERVICE_PUBLISH_TRANSFEROWNERSHIP);
            ArrayList<NameValuePair> parameters = new ArrayList();
            Iterator var7 = objectIds.iterator();

            while(var7.hasNext()) {
                String id = (String)var7.next();
                parameters.add(new BasicNameValuePair("objectId", id));
            }

            parameters.add(new BasicNameValuePair("userId", userId));
            httpReq.setEntity(new UrlEncodedFormEntity(parameters, StandardCharsets.UTF_8));
            CloseableHttpResponse httpResp = httpClient.execute(httpReq);
            resultJson = (JSONObject)(new JSONParser()).parse(IOUtils.toString(httpResp.getEntity().getContent()));
        } catch (Exception var12) {
            resultJson = ExceptionUtil.createErrorJson(var12);
        } finally {
            IOUtils.closeQuietly(httpClient);
        }

        return resultJson;
    }
}
