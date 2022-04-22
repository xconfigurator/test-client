package liuyang.testclient.scheduler;

import liuyang.testclient.modules.webservice.soap.cxf.stub01.HelloInterface;
import liuyang.testclient.modules.webservice.soap.cxf.stub01.HelloServiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author liuyang(wx)
 * @since 2022/4/19
 */
@Component
@Slf4j
public class CXFClientScheduler {

    //@Scheduled(cron = "0/30 * * * * MON-SAT")
    @Scheduled(cron = "0/1 * * * * MON-SAT")
    public void soapCxfStub01() {
        log.info("bar");
        long begin = System.currentTimeMillis();
        try {
            QName SERVICE_NAME = new QName("http://services.server.modules.testcxf.liuyang/", "HelloServiceService");
            //URL wsdlURL = new URL("http://localhost/soap/hello?wsdl");
            URL wsdlURL = new URL("http://20.30.250.71/soap/hello?wsdl");
            /*
            CityAlmServicePortService ss = new CityAlmServicePortService(wsdlURL, SERVICE_NAME);
            port = ss.getCityAlmServicePortSoap11();
             */
            HelloServiceService ss = new HelloServiceService(wsdlURL, SERVICE_NAME);
            HelloInterface helloServicePort = ss.getHelloServicePort();
            String foo = helloServicePort.sayHello("foo", 123);
            log.info("foo = {}", foo);
        } catch(Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            long end = System.currentTimeMillis();
            log.info("bar 耗时：{} ms", end - begin );
            // 一开始就不启动test-cxf 耗时：      耗时：36248 ms  java.net.ConnectException: Connection refused: connect
            // 启动test-cxf 服务响应超时：       耗时：10524 ms   java.net.SocketTimeoutException: Read timed out
            // 启动test-cxf 服务正常响应后关闭：  耗时：2008 ms    java.net.ConnectException: Connection refused: connect
        }
    }
}
// test-cxf 关-开-关 异常记录
/*
2022-04-20 16:37:06.242 [scheduling-1] ERROR liuyang.testclient.scheduler.CXFClientScheduler - org.apache.cxf.service.factory.ServiceConstructionException: Failed to create service.
javax.xml.ws.WebServiceException: org.apache.cxf.service.factory.ServiceConstructionException: Failed to create service.
	at org.apache.cxf.jaxws.ServiceImpl.initialize(ServiceImpl.java:162)
	at org.apache.cxf.jaxws.ServiceImpl.<init>(ServiceImpl.java:128)
	at org.apache.cxf.jaxws.spi.ProviderImpl.createServiceDelegate(ProviderImpl.java:82)
	at javax.xml.ws.Service.<init>(Service.java:77)
	at liuyang.testclient.modules.webservice.soap.cxf.stub01.HelloServiceService.<init>(HelloServiceService.java:43)
	at liuyang.testclient.scheduler.CXFClientScheduler.soapCxfStub01(CXFClientScheduler.java:32)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.springframework.scheduling.support.ScheduledMethodRunnable.run(ScheduledMethodRunnable.java:84)
	at org.springframework.scheduling.support.DelegatingErrorHandlingRunnable.run(DelegatingErrorHandlingRunnable.java:54)
	at org.springframework.scheduling.concurrent.ReschedulingRunnable.run(ReschedulingRunnable.java:95)
	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.access$201(ScheduledThreadPoolExecutor.java:180)
	at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:293)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
	at java.lang.Thread.run(Thread.java:748)
Caused by: org.apache.cxf.service.factory.ServiceConstructionException: Failed to create service.
	at org.apache.cxf.wsdl11.WSDLServiceFactory.<init>(WSDLServiceFactory.java:87)
	at org.apache.cxf.jaxws.ServiceImpl.initializePorts(ServiceImpl.java:217)
	at org.apache.cxf.jaxws.ServiceImpl.initialize(ServiceImpl.java:160)
	... 19 common frames omitted
Caused by: javax.wsdl.WSDLException: WSDLException: faultCode=PARSER_ERROR: Problem parsing 'http://localhost/soap/hello?wsdl'.: java.net.ConnectException: Connection refused: connect
	at com.ibm.wsdl.xml.WSDLReaderImpl.getDocument(WSDLReaderImpl.java:2198)
	at com.ibm.wsdl.xml.WSDLReaderImpl.readWSDL(WSDLReaderImpl.java:2390)
	at com.ibm.wsdl.xml.WSDLReaderImpl.readWSDL(WSDLReaderImpl.java:2422)
	at org.apache.cxf.wsdl11.WSDLManagerImpl.loadDefinition(WSDLManagerImpl.java:266)
	at org.apache.cxf.wsdl11.WSDLManagerImpl.getDefinition(WSDLManagerImpl.java:165)
	at org.apache.cxf.wsdl11.WSDLServiceFactory.<init>(WSDLServiceFactory.java:85)
	... 21 common frames omitted
Caused by: java.net.ConnectException: Connection refused: connect
	at java.net.DualStackPlainSocketImpl.connect0(Native Method)
	at java.net.DualStackPlainSocketImpl.socketConnect(DualStackPlainSocketImpl.java:75)
	at java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:476)
	at java.net.AbstractPlainSocketImpl.connectToAddress(AbstractPlainSocketImpl.java:218)
	at java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:200)
	at java.net.PlainSocketImpl.connect(PlainSocketImpl.java:162)
	at java.net.SocksSocketImpl.connect(SocksSocketImpl.java:394)
	at java.net.Socket.connect(Socket.java:606)
	at java.net.Socket.connect(Socket.java:555)
	at sun.net.NetworkClient.doConnect(NetworkClient.java:180)
	at sun.net.www.http.HttpClient.openServer(HttpClient.java:463)
	at sun.net.www.http.HttpClient.openServer(HttpClient.java:558)
	at sun.net.www.http.HttpClient.<init>(HttpClient.java:242)
	at sun.net.www.http.HttpClient.New(HttpClient.java:339)
	at sun.net.www.http.HttpClient.New(HttpClient.java:357)
	at sun.net.www.protocol.http.HttpURLConnection.getNewHttpClient(HttpURLConnection.java:1226)
	at sun.net.www.protocol.http.HttpURLConnection.plainConnect0(HttpURLConnection.java:1162)
	at sun.net.www.protocol.http.HttpURLConnection.plainConnect(HttpURLConnection.java:1056)
	at sun.net.www.protocol.http.HttpURLConnection.connect(HttpURLConnection.java:990)
	at sun.net.www.protocol.http.HttpURLConnection.getInputStream0(HttpURLConnection.java:1570)
	at sun.net.www.protocol.http.HttpURLConnection.getInputStream(HttpURLConnection.java:1498)
	at com.sun.org.apache.xerces.internal.impl.XMLEntityManager.setupCurrentEntity(XMLEntityManager.java:646)
	at com.sun.org.apache.xerces.internal.impl.XMLVersionDetector.determineDocVersion(XMLVersionDetector.java:150)
	at com.sun.org.apache.xerces.internal.parsers.XML11Configuration.parse(XML11Configuration.java:831)
	at com.sun.org.apache.xerces.internal.parsers.XML11Configuration.parse(XML11Configuration.java:796)
	at com.sun.org.apache.xerces.internal.parsers.XMLParser.parse(XMLParser.java:142)
	at com.sun.org.apache.xerces.internal.parsers.DOMParser.parse(DOMParser.java:247)
	at com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderImpl.parse(DocumentBuilderImpl.java:339)
	at com.ibm.wsdl.xml.WSDLReaderImpl.getDocument(WSDLReaderImpl.java:2188)
	... 26 common frames omitted
2022-04-20 16:37:06.242 [scheduling-1] INFO  liuyang.testclient.scheduler.CXFClientScheduler - bar 耗时：36240 ms
2022-04-20 16:37:30.001 [scheduling-1] INFO  liuyang.testclient.scheduler.CXFClientScheduler - bar
2022-04-20 16:37:30.178 [scheduling-1] INFO  o.a.c.w.s.factory.ReflectionServiceFactoryBean - Creating Service {http://services.server.modules.testcxf.liuyang/}HelloServiceService from WSDL: http://localhost/soap/hello?wsdl
2022-04-20 16:37:30.328 [scheduling-1] INFO  l.t.c.webservice.cxf.CXFClientLifeCycleListener - SOAP clientCreated
2022-04-20 16:37:30.454 [scheduling-1] INFO  liuyang.testclient.scheduler.CXFClientScheduler - foo = hello, cxf : foo 123
2022-04-20 16:37:30.454 [scheduling-1] INFO  liuyang.testclient.scheduler.CXFClientScheduler - bar 耗时：453 ms
2022-04-20 16:38:00.001 [scheduling-1] INFO  liuyang.testclient.scheduler.CXFClientScheduler - bar
2022-04-20 16:38:00.001 [scheduling-1] INFO  o.a.c.w.s.factory.ReflectionServiceFactoryBean - Creating Service {http://services.server.modules.testcxf.liuyang/}HelloServiceService from WSDL: http://localhost/soap/hello?wsdl
2022-04-20 16:38:00.003 [scheduling-1] INFO  l.t.c.webservice.cxf.CXFClientLifeCycleListener - SOAP clientCreated
2022-04-20 16:38:02.021 [scheduling-1] WARN  org.apache.cxf.phase.PhaseInterceptorChain - Interceptor for {http://services.server.modules.testcxf.liuyang/}HelloServiceService#{http://services.server.modules.testcxf.liuyang/}sayHello has thrown exception, unwinding now
org.apache.cxf.interceptor.Fault: Could not send Message.
	at org.apache.cxf.interceptor.MessageSenderInterceptor$MessageSenderEndingInterceptor.handleMessage(MessageSenderInterceptor.java:67)
	at org.apache.cxf.phase.PhaseInterceptorChain.doIntercept(PhaseInterceptorChain.java:308)
	at org.apache.cxf.endpoint.ClientImpl.doInvoke(ClientImpl.java:530)
	at org.apache.cxf.endpoint.ClientImpl.invoke(ClientImpl.java:441)
	at org.apache.cxf.endpoint.ClientImpl.invoke(ClientImpl.java:356)
	at org.apache.cxf.endpoint.ClientImpl.invoke(ClientImpl.java:314)
	at org.apache.cxf.frontend.ClientProxy.invokeSync(ClientProxy.java:96)
	at org.apache.cxf.jaxws.JaxWsClientProxy.invoke(JaxWsClientProxy.java:140)
	at com.sun.proxy.$Proxy126.sayHello(Unknown Source)
	at liuyang.testclient.scheduler.CXFClientScheduler.soapCxfStub01(CXFClientScheduler.java:34)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.springframework.scheduling.support.ScheduledMethodRunnable.run(ScheduledMethodRunnable.java:84)
	at org.springframework.scheduling.support.DelegatingErrorHandlingRunnable.run(DelegatingErrorHandlingRunnable.java:54)
	at org.springframework.scheduling.concurrent.ReschedulingRunnable.run(ReschedulingRunnable.java:95)
	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.access$201(ScheduledThreadPoolExecutor.java:180)
	at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:293)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
	at java.lang.Thread.run(Thread.java:748)
Caused by: java.net.ConnectException: ConnectException invoking http://localhost/soap/hello: Connection refused: connect
	at sun.reflect.GeneratedConstructorAccessor45.newInstance(Unknown Source)
	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
	at java.lang.reflect.Constructor.newInstance(Constructor.java:423)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.mapException(HTTPConduit.java:1400)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.close(HTTPConduit.java:1384)
	at org.apache.cxf.transport.AbstractConduit.close(AbstractConduit.java:56)
	at org.apache.cxf.transport.http.HTTPConduit.close(HTTPConduit.java:671)
	at org.apache.cxf.interceptor.MessageSenderInterceptor$MessageSenderEndingInterceptor.handleMessage(MessageSenderInterceptor.java:63)
	... 23 common frames omitted
Caused by: java.net.ConnectException: Connection refused: connect
	at java.net.DualStackPlainSocketImpl.waitForConnect(Native Method)
	at java.net.DualStackPlainSocketImpl.socketConnect(DualStackPlainSocketImpl.java:81)
	at java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:476)
	at java.net.AbstractPlainSocketImpl.connectToAddress(AbstractPlainSocketImpl.java:218)
	at java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:200)
	at java.net.PlainSocketImpl.connect(PlainSocketImpl.java:162)
	at java.net.SocksSocketImpl.connect(SocksSocketImpl.java:394)
	at java.net.Socket.connect(Socket.java:606)
	at sun.net.NetworkClient.doConnect(NetworkClient.java:175)
	at sun.net.www.http.HttpClient.openServer(HttpClient.java:463)
	at sun.net.www.http.HttpClient.openServer(HttpClient.java:558)
	at sun.net.www.http.HttpClient.<init>(HttpClient.java:242)
	at sun.net.www.http.HttpClient.New(HttpClient.java:339)
	at sun.net.www.http.HttpClient.New(HttpClient.java:371)
	at sun.net.www.protocol.http.HttpURLConnection.setNewClient(HttpURLConnection.java:776)
	at sun.net.www.protocol.http.HttpURLConnection.setNewClient(HttpURLConnection.java:764)
	at sun.net.www.protocol.http.HttpURLConnection.writeRequests(HttpURLConnection.java:706)
	at sun.net.www.protocol.http.HttpURLConnection.getInputStream0(HttpURLConnection.java:1591)
	at sun.net.www.protocol.http.HttpURLConnection.getInputStream(HttpURLConnection.java:1498)
	at java.net.HttpURLConnection.getResponseCode(HttpURLConnection.java:480)
	at org.apache.cxf.transport.http.URLConnectionHTTPConduit$URLConnectionWrappedOutputStream$2.run(URLConnectionHTTPConduit.java:377)
	at org.apache.cxf.transport.http.URLConnectionHTTPConduit$URLConnectionWrappedOutputStream$2.run(URLConnectionHTTPConduit.java:373)
	at java.security.AccessController.doPrivileged(Native Method)
	at org.apache.cxf.transport.http.URLConnectionHTTPConduit$URLConnectionWrappedOutputStream.getResponseCode(URLConnectionHTTPConduit.java:373)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.doProcessResponseCode(HTTPConduit.java:1598)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.handleResponseInternal(HTTPConduit.java:1626)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.handleResponse(HTTPConduit.java:1571)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.close(HTTPConduit.java:1371)
	... 26 common frames omitted
2022-04-20 16:38:02.022 [scheduling-1] ERROR liuyang.testclient.scheduler.CXFClientScheduler - Could not send Message.
javax.xml.ws.WebServiceException: Could not send Message.
	at org.apache.cxf.jaxws.JaxWsClientProxy.mapException(JaxWsClientProxy.java:183)
	at org.apache.cxf.jaxws.JaxWsClientProxy.invoke(JaxWsClientProxy.java:145)
	at com.sun.proxy.$Proxy126.sayHello(Unknown Source)
	at liuyang.testclient.scheduler.CXFClientScheduler.soapCxfStub01(CXFClientScheduler.java:34)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.springframework.scheduling.support.ScheduledMethodRunnable.run(ScheduledMethodRunnable.java:84)
	at org.springframework.scheduling.support.DelegatingErrorHandlingRunnable.run(DelegatingErrorHandlingRunnable.java:54)
	at org.springframework.scheduling.concurrent.ReschedulingRunnable.run(ReschedulingRunnable.java:95)
	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.access$201(ScheduledThreadPoolExecutor.java:180)
	at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:293)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
	at java.lang.Thread.run(Thread.java:748)
Caused by: java.net.ConnectException: ConnectException invoking http://localhost/soap/hello: Connection refused: connect
	at sun.reflect.GeneratedConstructorAccessor45.newInstance(Unknown Source)
	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
	at java.lang.reflect.Constructor.newInstance(Constructor.java:423)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.mapException(HTTPConduit.java:1400)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.close(HTTPConduit.java:1384)
	at org.apache.cxf.transport.AbstractConduit.close(AbstractConduit.java:56)
	at org.apache.cxf.transport.http.HTTPConduit.close(HTTPConduit.java:671)
	at org.apache.cxf.interceptor.MessageSenderInterceptor$MessageSenderEndingInterceptor.handleMessage(MessageSenderInterceptor.java:63)
	at org.apache.cxf.phase.PhaseInterceptorChain.doIntercept(PhaseInterceptorChain.java:308)
	at org.apache.cxf.endpoint.ClientImpl.doInvoke(ClientImpl.java:530)
	at org.apache.cxf.endpoint.ClientImpl.invoke(ClientImpl.java:441)
	at org.apache.cxf.endpoint.ClientImpl.invoke(ClientImpl.java:356)
	at org.apache.cxf.endpoint.ClientImpl.invoke(ClientImpl.java:314)
	at org.apache.cxf.frontend.ClientProxy.invokeSync(ClientProxy.java:96)
	at org.apache.cxf.jaxws.JaxWsClientProxy.invoke(JaxWsClientProxy.java:140)
	... 16 common frames omitted
Caused by: java.net.ConnectException: Connection refused: connect
	at java.net.DualStackPlainSocketImpl.waitForConnect(Native Method)
	at java.net.DualStackPlainSocketImpl.socketConnect(DualStackPlainSocketImpl.java:81)
	at java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:476)
	at java.net.AbstractPlainSocketImpl.connectToAddress(AbstractPlainSocketImpl.java:218)
	at java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:200)
	at java.net.PlainSocketImpl.connect(PlainSocketImpl.java:162)
	at java.net.SocksSocketImpl.connect(SocksSocketImpl.java:394)
	at java.net.Socket.connect(Socket.java:606)
	at sun.net.NetworkClient.doConnect(NetworkClient.java:175)
	at sun.net.www.http.HttpClient.openServer(HttpClient.java:463)
	at sun.net.www.http.HttpClient.openServer(HttpClient.java:558)
	at sun.net.www.http.HttpClient.<init>(HttpClient.java:242)
	at sun.net.www.http.HttpClient.New(HttpClient.java:339)
	at sun.net.www.http.HttpClient.New(HttpClient.java:371)
	at sun.net.www.protocol.http.HttpURLConnection.setNewClient(HttpURLConnection.java:776)
	at sun.net.www.protocol.http.HttpURLConnection.setNewClient(HttpURLConnection.java:764)
	at sun.net.www.protocol.http.HttpURLConnection.writeRequests(HttpURLConnection.java:706)
	at sun.net.www.protocol.http.HttpURLConnection.getInputStream0(HttpURLConnection.java:1591)
	at sun.net.www.protocol.http.HttpURLConnection.getInputStream(HttpURLConnection.java:1498)
	at java.net.HttpURLConnection.getResponseCode(HttpURLConnection.java:480)
	at org.apache.cxf.transport.http.URLConnectionHTTPConduit$URLConnectionWrappedOutputStream$2.run(URLConnectionHTTPConduit.java:377)
	at org.apache.cxf.transport.http.URLConnectionHTTPConduit$URLConnectionWrappedOutputStream$2.run(URLConnectionHTTPConduit.java:373)
	at java.security.AccessController.doPrivileged(Native Method)
	at org.apache.cxf.transport.http.URLConnectionHTTPConduit$URLConnectionWrappedOutputStream.getResponseCode(URLConnectionHTTPConduit.java:373)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.doProcessResponseCode(HTTPConduit.java:1598)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.handleResponseInternal(HTTPConduit.java:1626)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.handleResponse(HTTPConduit.java:1571)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.close(HTTPConduit.java:1371)
	... 26 common frames omitted
2022-04-20 16:38:02.022 [scheduling-1] INFO  liuyang.testclient.scheduler.CXFClientScheduler - bar 耗时：2021 ms

 */