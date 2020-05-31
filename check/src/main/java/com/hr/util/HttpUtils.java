package com.hr.util;


import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;

public class HttpUtils {

    private static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

    static {
        cm.setMaxTotal(100);//最大连接数
        cm.setDefaultMaxPerRoute(69);//
    }

    /**
     * 根据请求地址 获取页面
     *
     * @param url
     * @return
     */
    public static  String doGet(URI url) {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();//从http连接池里获取对象
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(HttpUtils.getConfig());
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            //4.解析响应获取数据
            if (response.getStatusLine().getStatusCode() == 200) {
                //如果响应成功
                HttpEntity httpEntity = response.getEntity();
                String content = EntityUtils.toString(httpEntity, "utf-8");
                return content;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    private static RequestConfig getConfig() {
        return RequestConfig.custom().setConnectTimeout(1000)
                .setConnectionRequestTimeout(500)
                .setSocketTimeout(10000)
                .build();

    }

}
