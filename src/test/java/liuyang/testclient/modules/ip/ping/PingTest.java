package liuyang.testclient.modules.ip.ping;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author liuyang(wx)
 * @since 2022/4/20
 */
@Slf4j
public class PingTest {

    @Test
    void testPing() {
        long begin = System.currentTimeMillis();
        log.info("ping result = {}", Ping.ping("1.2.3.4", 10000));// 1.2.3.4 // 127.0.0.1
        long end = System.currentTimeMillis();
        log.info("测试网络耗时：{} 毫秒", end - begin);
    }
}
