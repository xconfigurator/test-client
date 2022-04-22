package liuyang.testclient.modules.jdk;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetAddress;

/**
 * @author liuyang(wx)
 * @since 2022/4/21
 */
@Slf4j
public class InetAddressTest {

    @Test
    void isReachable(){
        //boolean reachable = InetAddress.getByName("120.52.31.32").isReachable(3000);
        boolean reachable = false;
        try {
            reachable = InetAddress.getByName("20.48.250.70").isReachable(3000);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        log.info("{}",reachable);
    }
}
