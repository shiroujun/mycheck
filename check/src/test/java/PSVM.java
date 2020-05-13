import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.params.DefaultedHttpParams;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 爬虫测试类
 * 导入httpclient 4.5.2   --http客户端工具
 * <dependency>
 * <groupId>org.apache.httpcomponents</groupId>
 * <artifactId>httpclient</artifactId>
 * <version>4.5.6</version>
 * </dependency>
 *
 * <dependency>
 * <groupId>junit</groupId>
 * <artifactId>junit</artifactId>
 * <version>4.12</version>
 * </dependency>
 */
public class PSVM {

    public static void main(String[] args) {

    }

    /**
     * 访问百度，获取百度首页的html信息
     *
     * @throws IOException
     */
    @Test
    public void test() throws IOException {
        //1.打开一个浏览器(创建一个httpclient对象)
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //2.输入一个网址
        HttpGet httpGet = new HttpGet("https://www.baidu.com/");

        //3.回车发起请求(把网址放入client对象中) 并获取响应对象CloseableHttpResponse
        CloseableHttpResponse response = httpClient.execute(httpGet);

        //4.解析响应获取数据
        if (response.getStatusLine().getStatusCode() == 200) {
            //如果响应成功
            HttpEntity httpEntity = response.getEntity();
            String content = EntityUtils.toString(httpEntity, "utf-8");
            System.out.println(content);
        }
    }

    /**
     * http Get请求测试
     */
    @Test
    public void httpGetTest() {
        //创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet("https://www.baidu.com");
        System.out.println("请求信息: "+httpGet);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            //4.解析响应获取数据
            if (response.getStatusLine().getStatusCode() == 200) {
                //如果响应成功
                HttpEntity httpEntity = response.getEntity();
                String content = EntityUtils.toString(httpEntity, "utf-8");
                System.out.println(content);
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

            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * http Get方法带参数的请求
     */
    @Test
    public void httpgettestwithparam() throws URISyntaxException {
        //创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        URIBuilder uriBuilder = new URIBuilder("http://www.baidu.com/s");
        //https://www.baidu.com/s?wd=关键字&pn=0
        uriBuilder.setParameter("wd","123");//搜索的内容
        uriBuilder.setParameter("pn","1");//第几页


        URI build = uriBuilder.build();
        HttpGet httpGet = new HttpGet(build);
        System.out.println("请求URI："+build);
        System.out.println("请求信息: "+httpGet);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            //4.解析响应获取数据
            if (response.getStatusLine().getStatusCode() == 200) {
                //如果响应成功
                HttpEntity httpEntity = response.getEntity();
                String content = EntityUtils.toString(httpEntity, "utf-8");
                System.out.println(content);
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

            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 用http连接池 get方式发起请求
     */
    @Test
    public void Pool(){
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);//最大连接数
        cm.setDefaultMaxPerRoute(69);//
        doGet(cm);

    }

    public void doGet(PoolingHttpClientConnectionManager cm){
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();//从http连接池里获取对象
        /**
         *
         */

        //关闭response
        //httpclient 不能关闭
    }

}
