package liuyang.testclient.modules.webservice.soap.cxf.stub01;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author liuyang(wx)
 * @since 2022/4/19
 */
@Slf4j
public class HelloServiceTest {

    @Test
    void testHello() throws MalformedURLException {
        QName SERVICE_NAME = new QName("http://services.server.modules.testcxf.liuyang/", "HelloServiceService");
        URL wsdlURL = new URL("http://localhost/soap/hello?wsdl");
        /*
        CityAlmServicePortService ss = new CityAlmServicePortService(wsdlURL, SERVICE_NAME);
        port = ss.getCityAlmServicePortSoap11();
         */
        HelloServiceService ss = new HelloServiceService(wsdlURL, SERVICE_NAME);
        HelloInterface helloServicePort = ss.getHelloServicePort();
        String foo = helloServicePort.sayHello("foo", 123);
        log.info("foo = {}", foo);
    }
}
