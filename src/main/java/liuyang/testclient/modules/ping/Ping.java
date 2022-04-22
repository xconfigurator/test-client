package liuyang.testclient.modules.ping;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author liuyang(wx)
 * @since 2022/4/20
 */
public class Ping {

    public static boolean ping(String ipAddress){
        return ping(ipAddress, 5000);
    }

    public static boolean ping(String ipAddress, int timeOut) {
        try {
            return InetAddress.getByName(ipAddress).isReachable(timeOut);
        } catch (UnknownHostException unknownHostException) {
            unknownHostException.printStackTrace();
            return false;
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return false;
        }
    }
}
