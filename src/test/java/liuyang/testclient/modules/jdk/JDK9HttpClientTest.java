package liuyang.testclient.modules.jdk;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * 支持HTTP2.0, WebSocket
 * 视频：https://www.bilibili.com/video/BV17W411g7eK?p=21
 * 参考：JDK9 https://zhuanlan.zhihu.com/p/30632947
 * 参考：JDK11 https://www.cnblogs.com/JiangWJ/p/10823112.html#:~:text=java.net.http.HttpClient%20%E6%98%AF%20jdk11%20%E4%B8%AD%E6%AD%A3%E5%BC%8F%E5%90%AF%E7%94%A8%E7%9A%84%E4%B8%80%E4%B8%AA%20http%20%E5%B7%A5%E5%85%B7%E7%B1%BB%20%28%E5%85%B6%E5%AE%9E%E6%97%A9%E5%9C%A8,jdk9%20%E7%9A%84%E6%97%B6%E5%80%99%E5%B0%B1%E5%B7%B2%E7%BB%8F%E5%AD%98%E5%9C%A8%E4%BA%86%EF%BC%8C%E5%8F%AA%E6%98%AF%E5%A4%84%E4%BA%8E%E5%AD%B5%E5%8C%96%E6%9C%9F%29%EF%BC%8C%E5%AE%98%E6%96%B9%E5%AF%93%E6%84%8F%E4%B8%BA%E6%83%B3%E8%A6%81%E5%8F%96%E4%BB%A3%20HttpURLConnection%20%E5%92%8C%20Apache%20HttpClient%20%E7%AD%89%E6%AF%94%E8%BE%83%E5%8F%A4%E8%80%81%E7%9A%84%E5%BC%80%E5%8F%91%E5%B7%A5%E5%85%B7%E3%80%82
 * @author liuyang(wx)
 * @since 2022/4/11
 */
@Slf4j
public class JDK9HttpClientTest {

    @Test
    void test() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder(URI.create("https://www.baidu.com")).GET().build();
        HttpResponse httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());// HttpResponse.BodyHandlers.ofString()。 在JDK9中不是这个。
        log.info("status = {}", httpResponse.statusCode());
        log.info("body = {}", httpResponse.body());

    }
}
