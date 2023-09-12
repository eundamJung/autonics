//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.http;

import com.tool.db.sqlite.config.table.Metadata;
import com.tool.user.UserSession;
import com.tool.util.ExceptionUtil;
import com.tool.util.StringUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class FileHttpUtil extends HttpCommon {
    public FileHttpUtil() {
    }

    public static JSONObject searchFiles(UserSession userSession, HashMap<String, String> searchCriteriaMap) throws Exception {
        CloseableHttpClient httpClient = getHttpClient(userSession);

        JSONObject resultJson;
        try {
            HttpPost httpReq = new HttpPost(SERVICE_FILE_SEARCH);
            httpReq.setEntity(paramMapToFormEntity(searchCriteriaMap));
            CloseableHttpResponse httpResp = httpClient.execute(httpReq);
            resultJson = (JSONObject)(new JSONParser()).parse(IOUtils.toString(httpResp.getEntity().getContent()));
        } catch (Exception var9) {
            throw var9;
        } finally {
            IOUtils.closeQuietly(httpClient);
        }

        return resultJson;
    }

    public static JSONObject preCheckout(UserSession userSession, Metadata metadata, boolean objectLock) {
        new JSONObject();
        CloseableHttpClient httpClient = getHttpClient(userSession);

        JSONObject resultJson;
        try {
            URL url = new URL(StringUtil.replaceStr(SERVICE_FILE_PRE_CHECKOUT, new String[]{encode(metadata.getObjectId()), encode(metadata.getFormat()), encode(metadata.getPlmFileName()), encode(objectLock ? "checkout" : "download")}));
            CloseableHttpResponse httpResp = httpClient.execute(new HttpGet(url.toURI()));
            String content = getStringFromStream(httpResp.getEntity().getContent());
            resultJson = (JSONObject)(new JSONParser()).parse(content);
        } catch (Exception var11) {
            resultJson = ExceptionUtil.createErrorJson(var11);
        } finally {
            IOUtils.closeQuietly(httpClient);
        }

        return resultJson;
    }

    public static JSONObject checkout(UserSession userSession, Metadata metadata, File desFile, boolean lock) {
        JSONObject resultJson = new JSONObject();
        CloseableHttpClient httpclient = getHttpClient(userSession);

        try {
            URL url = new URL(StringUtil.replaceStr(SERVICE_FILE_CHECKOUT, new String[]{encode(metadata.getObjectId()), encode(metadata.getFormat()), encode(metadata.getPlmFileName()), encode(lock ? "checkout" : "download")}));
            CloseableHttpResponse httpResp = httpclient.execute(new HttpGet(url.toURI()));
            int statusCode = httpResp.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                FileUtils.copyInputStreamToFile(httpResp.getEntity().getContent(), desFile);
            } else {
                resultJson = (JSONObject)(new JSONParser()).parse(getStringFromStream(httpResp.getEntity().getContent()));
            }
        } catch (Exception var12) {
            resultJson = ExceptionUtil.createErrorJson(var12);
        } finally {
            IOUtils.closeQuietly(httpclient);
        }

        return resultJson;
    }

    public static JSONObject preCheckin(UserSession userSession, Metadata metadata) throws Exception {
        new JSONObject();
        CloseableHttpClient httpClient = getHttpClient(userSession);

        JSONObject resultJson;
        try {
            URL url = new URL(StringUtil.replaceStr(SERVICE_FILE_PRE_CHECKIN, new String[]{encode(metadata.getObjectId()), encode(metadata.getFormat()), encode(metadata.getPlmFileName())}));
            CloseableHttpResponse httpResp = httpClient.execute(new HttpGet(url.toURI()));
            String content = getStringFromStream(httpResp.getEntity().getContent());
            resultJson = (JSONObject)(new JSONParser()).parse(content);
        } catch (Exception var10) {
            resultJson = ExceptionUtil.createErrorJson(var10);
        } finally {
            IOUtils.closeQuietly(httpClient);
        }

        return resultJson;
    }

    public static JSONObject checkin(UserSession userSession, Metadata metadata, File file) {
        new JSONObject();
        CloseableHttpClient httpClient = getHttpClient(userSession);

        JSONObject resultJson;
        try {
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE).addPart("file", new FileBody(file, ContentType.DEFAULT_BINARY, encode(metadata.getLocalFileName()))).addPart("objectId", new StringBody(encode(metadata.getObjectId()), ContentType.MULTIPART_FORM_DATA)).addPart("format", new StringBody(encode(metadata.getFormat()), ContentType.MULTIPART_FORM_DATA)).addPart("fileName", new StringBody(encode(metadata.getPlmFileName()), ContentType.MULTIPART_FORM_DATA));
            RequestBuilder reqBuilder = RequestBuilder.post(SERVICE_FILE_CHECKIN);
            reqBuilder.setEntity(entityBuilder.build());
            HttpResponse httpResp = httpClient.execute(reqBuilder.build());
            int status = httpResp.getStatusLine().getStatusCode();
            String content = getStringFromStream(httpResp.getEntity().getContent());
            if (status != 200 && status != 500) {
                throw new Exception("HTTP status codes " + status);
            }

            resultJson = (JSONObject)(new JSONParser()).parse(content);
        } catch (Exception var13) {
            resultJson = ExceptionUtil.createErrorJson(var13);
        } finally {
            IOUtils.closeQuietly(httpClient);
        }

        return resultJson;
    }

    public static JSONObject lockUnlock(UserSession userSession, Metadata metadata, boolean lock) {
        new JSONObject();
        CloseableHttpClient httpClient = getHttpClient(userSession);

        JSONObject resultJson;
        try {
            URL url = new URL(StringUtil.replaceStr(SERVICE_FILE_LOCKUNLOCK, new String[]{encode(metadata.getObjectId()), encode(metadata.getFormat()), encode(metadata.getPlmFileName()), encode(lock ? "lock" : "unlock")}));
            CloseableHttpResponse httpResp = httpClient.execute(new HttpGet(url.toURI()));
            String content = IOUtils.toString(httpResp.getEntity().getContent());
            resultJson = (JSONObject)(new JSONParser()).parse(content);
        } catch (Exception var11) {
            resultJson = ExceptionUtil.createErrorJson(var11);
        } finally {
            IOUtils.closeQuietly(httpClient);
        }

        return resultJson;
    }

    public static JSONObject getInfo(UserSession userSession, Metadata metadata) {
        new JSONObject();
        CloseableHttpClient httpClient = getHttpClient(userSession);

        JSONObject resultJson;
        try {
            URL url = new URL(StringUtil.replaceStr(SERVICE_FILE_INFO, new String[]{encode(metadata.getObjectId()), encode(metadata.getFormat()), encode(metadata.getPlmFileName())}));
            CloseableHttpResponse httpResp = httpClient.execute(new HttpGet(url.toURI()));
            String content = IOUtils.toString(httpResp.getEntity().getContent());
            resultJson = (JSONObject)(new JSONParser()).parse(content);
        } catch (Exception var10) {
            resultJson = ExceptionUtil.createErrorJson(var10);
        } finally {
            IOUtils.closeQuietly(httpClient);
        }

        return resultJson;
    }

    static class FileDownloadResponseHandler implements ResponseHandler<File> {
        private final File target;

        public FileDownloadResponseHandler(File target) {
            this.target = target;
        }

        public File handleResponse(HttpResponse response) throws IOException {
            InputStream source = response.getEntity().getContent();
            FileUtils.copyInputStreamToFile(source, this.target);
            return this.target;
        }
    }
}
