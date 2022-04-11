package liuyang.testclient.modules.jdk;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

/**
 * 使用JDK提供的工具类
 *
 * @author liuyang(wx)
 * @since 2022/1/12
 */
@Slf4j
public class JDK5ScannerTest {
    @Test
    public void testGet() throws IOException {
        String urlString = "http://www.baidu.com";
        URL url = new URL(urlString);// URL sinc JDK 1.0
        Scanner scanner = new Scanner(url.openStream());
        scanner.useDelimiter("\n");
        while (scanner.hasNext()) {
            System.out.println(scanner.next());
        }
        scanner.close();
    }
}
