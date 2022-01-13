package liuyang.modules.testclient.httpclient;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;

/**
 * @author liuyang(wx)
 * @since 2022/1/13
 */
@Slf4j
public class ResponseUtil {
    public static void showResponse(CloseableHttpResponse response) throws IOException, ParseException {
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
