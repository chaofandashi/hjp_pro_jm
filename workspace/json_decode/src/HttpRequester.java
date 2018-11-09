
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.*;

   
/** 
 * HTTP请求对象 
 *  
 * @author YYmmiinngg 
 */  
public class HttpRequester {  
    private String defaultContentEncoding;  
   
    public HttpRequester() {  
        this.defaultContentEncoding = Charset.defaultCharset().name();  
    }  
   
    /** 
     * 发�?�GET请求 
     *  
     * @param urlString 
     *            URL地址 
     * @return 响应对象 
     * @throws Exception 
     */  
    public HttpRespons sendGet(String urlString) throws Exception {  
        return this.send(urlString, "GET", null, null);  
    }  
   
    /** 
     * 发�?�GET请求 
     *  
     * @param urlString 
     *            URL地址 
     * @param params 
     *            参数集合 
     * @return 响应对象 
     * @throws Exception 
     */  
    public HttpRespons sendGet(String urlString, Map<String, String> params)  
            throws Exception {  
        return this.send(urlString, "GET", params, null);  
    }  
   
    /** 
     * 发�?�GET请求 
     *  
     * @param urlString 
     *            URL地址 
     * @param params 
     *            参数集合 
     * @param propertys 
     *            请求属�?? 
     * @return 响应对象 
     * @throws Exception 
     */  
    public HttpRespons sendGet(String urlString, Map<String, String> params,  
            Map<String, String> propertys) throws Exception {  
        return this.send(urlString, "GET", params, propertys);  
    }  
   
    /** 
     * 发�?�POST请求 
     *  
     * @param urlString 
     *            URL地址 
     * @return 响应对象 
     * @throws Exception 
     */  
    public HttpRespons sendPost(String urlString) throws Exception {  
        return this.send(urlString, "POST", null, null);  
    }  
   
    /** 
     * 发�?�POST请求 
     *  
     * @param urlString 
     *            URL地址 
     * @param params 
     *            参数集合 
     * @return 响应对象 
     * @throws Exception 
     */  
    public HttpRespons sendPost(String urlString, Map<String, String> params)  
            throws Exception {  
        return this.send(urlString, "POST", params, null);  
    }  
   
    /** 
     * 发�?�POST请求 
     *  
     * @param urlString 
     *            URL地址 
     * @param params 
     *            参数集合 
     * @param propertys 
     *            请求属�?? 
     * @return 响应对象 
     * @throws Exception 
     */  
    public HttpRespons sendPost(String urlString, Map<String, String> params,  
            Map<String, String> propertys) throws Exception {  
        return this.send(urlString, "POST", params, propertys);  
    }  
   
    /** 
     * 发�?�HTTP请求 
     *  
     * @param urlString 
     * @return 响映对象 
     * @throws Exception 
     */  
    private HttpRespons send(String urlString, String method,  
            Map<String, String> parameters, Map<String, String> propertys)  
            throws Exception {  
        HttpURLConnection urlConnection = null;  
   
        if (method.equalsIgnoreCase("GET") && parameters != null) {  
            StringBuffer param = new StringBuffer();  
            int i = 0;  
            for (String key : parameters.keySet()) {  
                if (i == 0)  
                    param.append("?");  
                else  
                    param.append("&");  
                param.append(key).append("=").append(parameters.get(key));  
                i++;  
            }  
            urlString += param;  
        }  
        URL url = new URL(urlString);  
        urlConnection = (HttpURLConnection) url.openConnection();  
   
        urlConnection.setRequestMethod(method);  
        urlConnection.setDoOutput(true);  
        urlConnection.setDoInput(true);  
        urlConnection.setUseCaches(false);  
        urlConnection.setConnectTimeout(10000);
   
        
        if (propertys != null)  
            for (String key : propertys.keySet()) {  
                urlConnection.addRequestProperty(key, propertys.get(key));  
            }  
   
        if (method.equalsIgnoreCase("POST") && parameters != null) {  
            StringBuffer param = new StringBuffer();  
            for (String key : parameters.keySet()) {  
                param.append("&");  
                param.append(key).append("=").append(parameters.get(key));  
            }  
            urlConnection.getOutputStream().write(param.toString().getBytes());  
            urlConnection.getOutputStream().flush();  
            urlConnection.getOutputStream().close();  
        }  
   
        return this.makeContent(urlString, urlConnection);  
    }  
   
    /** 
     * 得到响应对象 
     *  
     * @param urlConnection 
     * @return 响应对象 
     * @throws Exception 
     */  
    private HttpRespons makeContent(String urlString,  
            HttpURLConnection urlConnection) throws Exception {  
        HttpRespons httpResponser = new HttpRespons();  
        try {  
//            InputStream in = urlConnection.getInputStream();  
            
            boolean isError = urlConnection.getResponseCode() >= 400;
            InputStream in = isError ? urlConnection.getErrorStream() : urlConnection.getInputStream();
            
            BufferedReader bufferedReader = new BufferedReader(  
                    new InputStreamReader(in));  
            httpResponser.contentCollection = new Vector<String>();  
            StringBuffer temp = new StringBuffer();  
            String line = bufferedReader.readLine();  
            while (line != null) {  
                httpResponser.contentCollection.add(line);  
                temp.append(line).append("\r\n");  
                line = bufferedReader.readLine();  
            }  
            bufferedReader.close();  
   
            String ecod = urlConnection.getContentEncoding();  
            if (ecod == null)  
                ecod = this.defaultContentEncoding;  
   
            httpResponser.urlString = urlString;  
            
   
            httpResponser.defaultPort = urlConnection.getURL().getDefaultPort();  
            httpResponser.file = urlConnection.getURL().getFile();  
            httpResponser.host = urlConnection.getURL().getHost();  
            httpResponser.path = urlConnection.getURL().getPath();  
            httpResponser.port = urlConnection.getURL().getPort();  
            httpResponser.protocol = urlConnection.getURL().getProtocol();  
            httpResponser.query = urlConnection.getURL().getQuery();  
            httpResponser.ref = urlConnection.getURL().getRef();  
            httpResponser.userInfo = urlConnection.getURL().getUserInfo();  
   
            httpResponser.content = new String(temp.toString().getBytes(), ecod);  
            httpResponser.contentEncoding = ecod;  
            httpResponser.code = urlConnection.getResponseCode();  
            httpResponser.message = urlConnection.getResponseMessage();  
            httpResponser.contentType = urlConnection.getContentType();  
            httpResponser.method = urlConnection.getRequestMethod();  
            httpResponser.connectTimeout = urlConnection.getConnectTimeout();  
            httpResponser.readTimeout = urlConnection.getReadTimeout();  
   
            return httpResponser;  
        } catch (Exception e) {  
            throw e;  
        }
        finally {  
            if (urlConnection != null)  
                urlConnection.disconnect();  
        }  
    }  
   
    /** 
     * 默认的响应字符集 
     */  
    public String getDefaultContentEncoding() {  
        return this.defaultContentEncoding;  
    }  
   
    /** 
     * 设置默认的响应字符集 
     */  
    public void setDefaultContentEncoding(String defaultContentEncoding) {  
        this.defaultContentEncoding = defaultContentEncoding;  
    }  
}  