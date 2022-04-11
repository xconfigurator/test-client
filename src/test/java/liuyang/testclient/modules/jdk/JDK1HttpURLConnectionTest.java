package liuyang.testclient.modules.jdk;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * 视频：https://www.bilibili.com/video/BV1W54y1s7BZ?p=3
 * @author liuyang(wx)
 * @since 2022/4/11
 */
@Slf4j
public class JDK1HttpURLConnectionTest {

    @Test
    void test() throws IOException {
        String urlString = "http://www.baidu.com";
        URL url = new URL(urlString);// URL sinc JDK 1.0
        URLConnection urlConnection = url.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
        //httpURLConnection.setRequestMethod("GET");

        try(
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8))
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
