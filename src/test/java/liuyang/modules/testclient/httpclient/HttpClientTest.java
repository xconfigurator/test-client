package liuyang.modules.testclient.httpclient;

import liuyang.common.utils.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyang(wx)
 * @since 2022/1/12
 */
@Slf4j
public class HttpClientTest {

    @Test
    void testGet() throws IOException, ParseException {
        //String url = "http://www.baidu.com";
        String url = "http://www.sina.com";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpGet);

        showResponse(response);

        response.close();
        httpClient.close();
    }

    // 0711 13:55
    @Test
    void testPost() throws IOException, ParseException {
        String url = "http://localhost/hello/dtopost1";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        // 传参
        // 参数名称相同表示数组。
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("id", IdUtils.nextTaskId()));
        params.add(new BasicNameValuePair("username", "liuyang"));
        params.add(new BasicNameValuePair("info", "from HttpClient"));
        params.add(new BasicNameValuePair("d", String.valueOf(0.8)));
        params.add(new BasicNameValuePair("bd", new BigDecimal(40.0000).toString()));
        // 编码配置
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, Charset.forName("UTF-8"));
        // 配置到httpPost之中
        httpPost.setEntity(urlEncodedFormEntity);
        CloseableHttpResponse response = httpClient.execute(httpPost);

        showResponse(response);

        response.close();
        httpClient.close();
    }

    private void showResponse(CloseableHttpResponse response) throws IOException, ParseException {
        //log.info("header = {}", response.getHeaders());// 这样输出是打不全的
        log.info("header begin");
        for (Header header : response.getHeaders()) {
            log.info("header = {}", header);
        }
        log.info("header end");
        log.info("body = {}", EntityUtils.toString(response.getEntity()));
        log.info("code = {}", response.getCode());
        log.info("version = {}", response.getVersion());
    }
}
