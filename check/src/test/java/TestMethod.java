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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

/**
 * 爬虫测试类
 * 导入httpclient 4.5.2   --http客户端工具
 * <dependency>
 * <groupId>org.apache.httpcomponents</groupId>
 * <artifactId>httpclient</artifactId>
 * <version>4.5.6</version>
 * </dependency>
 * <p>
 * <dependency>
 * <groupId>junit</groupId>
 * <artifactId>junit</artifactId>
 * <version>4.12</version>
 * </dependency>
 */
public class TestMethod {

    public static void main(String[] args) {

    }

    /**
     * 访问百度，获取百度首页的html信息
     *
     * @throws IOException
     */
    @org.junit.Test
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
    @org.junit.Test
    public void httpGetTest() {
        //创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet("https://www.baidu.com");
        System.out.println("请求信息: " + httpGet);
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
    @org.junit.Test
    public void httpgettestwithparam() throws URISyntaxException {
        //创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        URIBuilder uriBuilder = new URIBuilder("http://www.baidu.com/s");
        uriBuilder.setParameter("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9,User-Agent");
        uriBuilder.setParameter("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.16 Safari/537.36");
        //https://www.baidu.com/s?wd=关键字&pn=0
        uriBuilder.setParameter("wd", "你家");//搜索的内容
        uriBuilder.setParameter("pn", "1");//第几页


        URI build = uriBuilder.build();
        HttpGet httpGet = new HttpGet(build);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            //4.解析响应获取数据
            if (response.getStatusLine().getStatusCode() == 200) {
                //如果响应成功
                HttpEntity httpEntity = response.getEntity();
                String content = EntityUtils.toString(httpEntity, "utf-8");

                FileOutputStream fi = new FileOutputStream("out");
                fi.write(content.getBytes());
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
    @org.junit.Test
    public void Pool() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);//最大连接数
        cm.setDefaultMaxPerRoute(69);//
        doGet(cm);

    }

    public void doGet(PoolingHttpClientConnectionManager cm) {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();//从http连接池里获取对象
        /**
         *
         */

        //关闭response
        //httpclient 不能关闭
    }


    @org.junit.Test
    public void remove() throws IOException {
        FileReader fileReader = new FileReader("out");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();

        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            line = line.trim();
            System.out.println(line.length());
            if (!line.startsWith(".") || line.contains("\"title\"")) {
                if (!line.startsWith("#")) {
                    if (!line.isEmpty()) {
                        if (!line.startsWith("[data-pmd]")) {
                            if (!line.contains("c-index")) {//去除百度热榜
                                if (!line.contains("opr-toplist1-st c-icon")) {//去除热指数
                                    stringBuilder.append(line);
                                    stringBuilder.append("\n");
                                }
                            }
                        }
                    }
                }
            }

            if (!(!line.startsWith(".") || line.contains("\"title\"")) || line.startsWith("#") || line.isEmpty()||line.startsWith("[data-pmd]")||line.contains("opr-toplist1-st c-icon")) {
                stringBuilder2.append(line);
                stringBuilder2.append("\n");

            }

        }
        FileWriter fileWriter = new FileWriter("write1");
        fileWriter.write(stringBuilder.toString());
        fileWriter.flush();
        FileWriter fileWriter2 = new FileWriter("write2");
        fileWriter2.write(stringBuilder2.toString());
        fileWriter2.flush();
    }

    @org.junit.Test
    public void t() {
        String string = "asdasdgdg<a>a1234vg</a>aaa";
        Document document = Jsoup.parse(string);
        Elements a = document.select("a").remove();
        System.out.println("--------------");
        System.out.println(document);
        System.out.println("--------------");
        System.out.println(a);
        System.out.println("===============");
    }
}
