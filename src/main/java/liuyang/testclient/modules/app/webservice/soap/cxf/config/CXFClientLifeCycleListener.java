package liuyang.testclient.modules.app.webservice.soap.cxf.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.ClientLifeCycleListener;
import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.springframework.stereotype.Component;

/**
 * 配置CXF客户端
 *
 * 参考文档：
 * 1. 超时设置：https://blog.csdn.net/cheng_feng_xiao_zhan/article/details/53792424
 * 2. 绕过https：https://blog.csdn.net/qq_36135928/article/details/88600034
 *
 * 实测：只在Spring容器环境中起效。非容器环境单测该监听器不生效。故使用一个定时模拟容器调用。
 *
 * @author liuyang(wx)
 * @since 2022/4/19
 */
@Component
@Slf4j
public class CXFClientLifeCycleListener implements ClientLifeCycleListener {
    static HTTPClientPolicy clientPolicy;
    static {
        clientPolicy = new HTTPClientPolicy();
        // 各种超时设置
        // Specifies the amount of time, in milliseconds, that the consumer will attempt to establish a connection before it times out. 0 is infinite.
        clientPolicy.setConnectionTimeout(5000);
        // Specifies the amount of time, in milliseconds, that the consumer will wait for a response before it times out. 0 is infinite.
        clientPolicy.setReceiveTimeout(10000);
        //clientPolicy.setReceiveTimeout(60000);
    }

    @Override
    public void clientCreated(Client client) {
        log.info("SOAP clientCreated");
        HTTPConduit conduit = (HTTPConduit) client.getConduit();
        conduit.setClient(clientPolicy);
        client.getInInterceptors().add(new LoggingInInterceptor());// for 与海能达联调
        client.getOutInterceptors().add(new LoggingOutInterceptor());// for 与海能达联调
    }

    @Override
    public void clientDestroyed(Client client) {
        log.info("SOAP clientDestroy");
        /*
        if (client != null) {
            //Indicates that the client is no longer needed and that any resources it holds can now be freed.
            client.destroy();// ? 还是用client.close();?  20220419

        }*/
        // 都会引起死递归，不要在这个钩子里显式调用。
        // client.close()
        // client.destroy()
        HTTPConduit conduit = (HTTPConduit) client.getConduit();
        conduit.close();
    }

    // TCP/IP连接异常 触发5秒超时
    // CXF异常：org.apache.cxf.interceptor.Fault: Could not send Message.
    // 根因：Caused by: java.net.ConnectException: Connection refused: connect
    // 符合预期！
    /*
    2022-04-19 10:43:00.001 [scheduling-1] INFO  o.a.c.w.s.factory.ReflectionServiceFactoryBean - Creating Service {http://services.server.modules.testcxf.liuyang/}HelloServiceService from WSDL: http://localhost/soap/hello?wsdl
    2022-04-19 10:43:00.002 [scheduling-1] INFO  l.t.c.webservice.cxf.CXFClientLifeCycleListener - clientCreated
2022-04-19 10:43:02.033 [scheduling-1] WARN  org.apache.cxf.phase.PhaseInterceptorChain - Interceptor for {http://services.server.modules.testcxf.liuyang/}HelloServiceService#{http://services.server.modules.testcxf.liuyang/}sayHello has thrown exception, unwinding now
org.apache.cxf.interceptor.Fault: Could not send Message.
	at org.apache.cxf.interceptor.MessageSenderInterceptor$MessageSenderEndingInterceptor.handleMessage(MessageSenderInterceptor.java:67)
	at org.apache.cxf.phase.PhaseInterceptorChain.doIntercept(PhaseInterceptorChain.java:308)
	at org.apache.cxf.endpoint.ClientImpl.doInvoke(ClientImpl.java:530)
	at org.apache.cxf.endpoint.ClientImpl.invoke(ClientImpl.java:441)
	at org.apache.cxf.endpoint.ClientImpl.invoke(ClientImpl.java:356)
	at org.apache.cxf.endpoint.ClientImpl.invoke(ClientImpl.java:314)
	at org.apache.cxf.frontend.ClientProxy.invokeSync(ClientProxy.java:96)
	at org.apache.cxf.jaxws.JaxWsClientProxy.invoke(JaxWsClientProxy.java:140)
	at com.sun.proxy.$Proxy127.sayHello(Unknown Source)
	at liuyang.testclient.scheduler.CXFClientScheduler.soapCxfStub01(CXFClientScheduler.java:33)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:566)
	at org.springframework.scheduling.support.ScheduledMethodRunnable.run(ScheduledMethodRunnable.java:84)
	at org.springframework.scheduling.support.DelegatingErrorHandlingRunnable.run(DelegatingErrorHandlingRunnable.java:54)
	at org.springframework.scheduling.concurrent.ReschedulingRunnable.run(ReschedulingRunnable.java:95)
	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:515)
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
	at java.base/java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:304)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
	at java.base/java.lang.Thread.run(Thread.java:834)
Caused by: java.net.ConnectException: ConnectException invoking http://localhost/soap/hello: Connection refused: connect
	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
	at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:490)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.mapException(HTTPConduit.java:1400)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.close(HTTPConduit.java:1384)
	at org.apache.cxf.transport.AbstractConduit.close(AbstractConduit.java:56)
	at org.apache.cxf.transport.http.HTTPConduit.close(HTTPConduit.java:671)
	at org.apache.cxf.interceptor.MessageSenderInterceptor$MessageSenderEndingInterceptor.handleMessage(MessageSenderInterceptor.java:63)
	... 22 common frames omitted
Caused by: java.net.ConnectException: Connection refused: connect
	at java.base/java.net.PlainSocketImpl.waitForConnect(Native Method)
	at java.base/java.net.PlainSocketImpl.socketConnect(PlainSocketImpl.java:107)
	at java.base/java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:399)
	at java.base/java.net.AbstractPlainSocketImpl.connectToAddress(AbstractPlainSocketImpl.java:242)
	at java.base/java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:224)
	at java.base/java.net.Socket.connect(Socket.java:608)
	at java.base/sun.net.NetworkClient.doConnect(NetworkClient.java:177)
	at java.base/sun.net.www.http.HttpClient.openServer(HttpClient.java:474)
	at java.base/sun.net.www.http.HttpClient.openServer(HttpClient.java:569)
	at java.base/sun.net.www.http.HttpClient.<init>(HttpClient.java:242)
	at java.base/sun.net.www.http.HttpClient.New(HttpClient.java:341)
	at java.base/sun.net.www.http.HttpClient.New(HttpClient.java:362)
	at java.base/sun.net.www.protocol.http.HttpURLConnection.getNewHttpClient(HttpURLConnection.java:1253)
	at java.base/sun.net.www.protocol.http.HttpURLConnection.plainConnect0(HttpURLConnection.java:1232)
	at java.base/sun.net.www.protocol.http.HttpURLConnection.plainConnect(HttpURLConnection.java:1081)
	at java.base/sun.net.www.protocol.http.HttpURLConnection.connect(HttpURLConnection.java:1015)
	at java.base/sun.net.www.protocol.http.HttpURLConnection.getOutputStream0(HttpURLConnection.java:1367)
	at java.base/sun.net.www.protocol.http.HttpURLConnection.getOutputStream(HttpURLConnection.java:1342)
	at org.apache.cxf.transport.http.URLConnectionHTTPConduit$URLConnectionWrappedOutputStream.setupWrappedStream(URLConnectionHTTPConduit.java:274)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.handleHeadersTrustCaching(HTTPConduit.java:1343)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.onFirstWrite(HTTPConduit.java:1304)
	at org.apache.cxf.transport.http.URLConnectionHTTPConduit$URLConnectionWrappedOutputStream.onFirstWrite(URLConnectionHTTPConduit.java:307)
	at org.apache.cxf.io.AbstractWrappedOutputStream.write(AbstractWrappedOutputStream.java:47)
	at org.apache.cxf.io.AbstractThresholdOutputStream.write(AbstractThresholdOutputStream.java:69)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.close(HTTPConduit.java:1356)
	... 25 common frames omitted
2022-04-19 10:43:02.033 [scheduling-1] ERROR liuyang.testclient.scheduler.CXFClientScheduler - Could not send Message.
javax.xml.ws.WebServiceException: Could not send Message.
	at org.apache.cxf.jaxws.JaxWsClientProxy.mapException(JaxWsClientProxy.java:183)
	at org.apache.cxf.jaxws.JaxWsClientProxy.invoke(JaxWsClientProxy.java:145)
	at com.sun.proxy.$Proxy127.sayHello(Unknown Source)
	at liuyang.testclient.scheduler.CXFClientScheduler.soapCxfStub01(CXFClientScheduler.java:33)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:566)
	at org.springframework.scheduling.support.ScheduledMethodRunnable.run(ScheduledMethodRunnable.java:84)
	at org.springframework.scheduling.support.DelegatingErrorHandlingRunnable.run(DelegatingErrorHandlingRunnable.java:54)
	at org.springframework.scheduling.concurrent.ReschedulingRunnable.run(ReschedulingRunnable.java:95)
	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:515)
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
	at java.base/java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:304)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
	at java.base/java.lang.Thread.run(Thread.java:834)
Caused by: java.net.ConnectException: ConnectException invoking http://localhost/soap/hello: Connection refused: connect
	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
	at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:490)
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
	... 15 common frames omitted
Caused by: java.net.ConnectException: Connection refused: connect
	at java.base/java.net.PlainSocketImpl.waitForConnect(Native Method)
	at java.base/java.net.PlainSocketImpl.socketConnect(PlainSocketImpl.java:107)
	at java.base/java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:399)
	at java.base/java.net.AbstractPlainSocketImpl.connectToAddress(AbstractPlainSocketImpl.java:242)
	at java.base/java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:224)
	at java.base/java.net.Socket.connect(Socket.java:608)
	at java.base/sun.net.NetworkClient.doConnect(NetworkClient.java:177)
	at java.base/sun.net.www.http.HttpClient.openServer(HttpClient.java:474)
	at java.base/sun.net.www.http.HttpClient.openServer(HttpClient.java:569)
	at java.base/sun.net.www.http.HttpClient.<init>(HttpClient.java:242)
	at java.base/sun.net.www.http.HttpClient.New(HttpClient.java:341)
	at java.base/sun.net.www.http.HttpClient.New(HttpClient.java:362)
	at java.base/sun.net.www.protocol.http.HttpURLConnection.getNewHttpClient(HttpURLConnection.java:1253)
	at java.base/sun.net.www.protocol.http.HttpURLConnection.plainConnect0(HttpURLConnection.java:1232)
	at java.base/sun.net.www.protocol.http.HttpURLConnection.plainConnect(HttpURLConnection.java:1081)
	at java.base/sun.net.www.protocol.http.HttpURLConnection.connect(HttpURLConnection.java:1015)
	at java.base/sun.net.www.protocol.http.HttpURLConnection.getOutputStream0(HttpURLConnection.java:1367)
	at java.base/sun.net.www.protocol.http.HttpURLConnection.getOutputStream(HttpURLConnection.java:1342)
	at org.apache.cxf.transport.http.URLConnectionHTTPConduit$URLConnectionWrappedOutputStream.setupWrappedStream(URLConnectionHTTPConduit.java:274)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.handleHeadersTrustCaching(HTTPConduit.java:1343)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.onFirstWrite(HTTPConduit.java:1304)
	at org.apache.cxf.transport.http.URLConnectionHTTPConduit$URLConnectionWrappedOutputStream.onFirstWrite(URLConnectionHTTPConduit.java:307)
	at org.apache.cxf.io.AbstractWrappedOutputStream.write(AbstractWrappedOutputStream.java:47)
	at org.apache.cxf.io.AbstractThresholdOutputStream.write(AbstractThresholdOutputStream.java:69)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.close(HTTPConduit.java:1356)
	... 25 common frames omitted

     */

    // 收到响应异常 触发10秒超时
    // CXF异常：org.apache.cxf.interceptor.Fault: Could not receive Message.
    // 根因：Caused by: java.net.SocketTimeoutException: Read timed out
    //
    /*
    2022-04-19 10:47:00.002 [scheduling-1] INFO  o.a.c.w.s.factory.ReflectionServiceFactoryBean - Creating Service {http://services.server.modules.testcxf.liuyang/}HelloServiceService from WSDL: http://localhost/soap/hello?wsdl
2022-04-19 10:47:00.003 [scheduling-1] INFO  l.t.c.webservice.cxf.CXFClientLifeCycleListener - clientCreated
2022-04-19 10:47:10.006 [scheduling-1] WARN  org.apache.cxf.phase.PhaseInterceptorChain - Interceptor for {http://services.server.modules.testcxf.liuyang/}HelloServiceService#{http://services.server.modules.testcxf.liuyang/}sayHello has thrown exception, unwinding now
org.apache.cxf.interceptor.Fault: Could not receive Message.
	at org.apache.cxf.interceptor.MessageSenderInterceptor$MessageSenderEndingInterceptor.handleMessage(MessageSenderInterceptor.java:65)
	at org.apache.cxf.phase.PhaseInterceptorChain.doIntercept(PhaseInterceptorChain.java:308)
	at org.apache.cxf.endpoint.ClientImpl.doInvoke(ClientImpl.java:530)
	at org.apache.cxf.endpoint.ClientImpl.invoke(ClientImpl.java:441)
	at org.apache.cxf.endpoint.ClientImpl.invoke(ClientImpl.java:356)
	at org.apache.cxf.endpoint.ClientImpl.invoke(ClientImpl.java:314)
	at org.apache.cxf.frontend.ClientProxy.invokeSync(ClientProxy.java:96)
	at org.apache.cxf.jaxws.JaxWsClientProxy.invoke(JaxWsClientProxy.java:140)
	at com.sun.proxy.$Proxy127.sayHello(Unknown Source)
	at liuyang.testclient.scheduler.CXFClientScheduler.soapCxfStub01(CXFClientScheduler.java:33)
	at jdk.internal.reflect.GeneratedMethodAccessor94.invoke(Unknown Source)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:566)
	at org.springframework.scheduling.support.ScheduledMethodRunnable.run(ScheduledMethodRunnable.java:84)
	at org.springframework.scheduling.support.DelegatingErrorHandlingRunnable.run(DelegatingErrorHandlingRunnable.java:54)
	at org.springframework.scheduling.concurrent.ReschedulingRunnable.run(ReschedulingRunnable.java:95)
	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:515)
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
	at java.base/java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:304)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
	at java.base/java.lang.Thread.run(Thread.java:834)
Caused by: java.net.SocketTimeoutException: SocketTimeoutException invoking http://localhost/soap/hello: Read timed out
	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
	at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:490)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.mapException(HTTPConduit.java:1400)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.close(HTTPConduit.java:1384)
	at org.apache.cxf.transport.AbstractConduit.close(AbstractConduit.java:56)
	at org.apache.cxf.transport.http.HTTPConduit.close(HTTPConduit.java:671)
	at org.apache.cxf.interceptor.MessageSenderInterceptor$MessageSenderEndingInterceptor.handleMessage(MessageSenderInterceptor.java:63)
	... 21 common frames omitted
Caused by: java.net.SocketTimeoutException: Read timed out
	at java.base/java.net.SocketInputStream.socketRead0(Native Method)
	at java.base/java.net.SocketInputStream.socketRead(SocketInputStream.java:115)
	at java.base/java.net.SocketInputStream.read(SocketInputStream.java:168)
	at java.base/java.net.SocketInputStream.read(SocketInputStream.java:140)
	at java.base/java.io.BufferedInputStream.fill(BufferedInputStream.java:252)
	at java.base/java.io.BufferedInputStream.read1(BufferedInputStream.java:292)
	at java.base/java.io.BufferedInputStream.read(BufferedInputStream.java:351)
	at java.base/sun.net.www.http.HttpClient.parseHTTPHeader(HttpClient.java:746)
	at java.base/sun.net.www.http.HttpClient.parseHTTP(HttpClient.java:689)
	at java.base/sun.net.www.protocol.http.HttpURLConnection.getInputStream0(HttpURLConnection.java:1615)
	at java.base/sun.net.www.protocol.http.HttpURLConnection.getInputStream(HttpURLConnection.java:1520)
	at java.base/java.net.HttpURLConnection.getResponseCode(HttpURLConnection.java:527)
	at org.apache.cxf.transport.http.URLConnectionHTTPConduit$URLConnectionWrappedOutputStream$2.run(URLConnectionHTTPConduit.java:377)
	at org.apache.cxf.transport.http.URLConnectionHTTPConduit$URLConnectionWrappedOutputStream$2.run(URLConnectionHTTPConduit.java:373)
	at java.base/java.security.AccessController.doPrivileged(Native Method)
	at org.apache.cxf.transport.http.URLConnectionHTTPConduit$URLConnectionWrappedOutputStream.getResponseCode(URLConnectionHTTPConduit.java:373)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.doProcessResponseCode(HTTPConduit.java:1598)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.handleResponseInternal(HTTPConduit.java:1626)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.handleResponse(HTTPConduit.java:1571)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.close(HTTPConduit.java:1371)
	... 24 common frames omitted
2022-04-19 10:47:10.006 [scheduling-1] ERROR liuyang.testclient.scheduler.CXFClientScheduler - Could not receive Message.
javax.xml.ws.WebServiceException: Could not receive Message.
	at org.apache.cxf.jaxws.JaxWsClientProxy.mapException(JaxWsClientProxy.java:183)
	at org.apache.cxf.jaxws.JaxWsClientProxy.invoke(JaxWsClientProxy.java:145)
	at com.sun.proxy.$Proxy127.sayHello(Unknown Source)
	at liuyang.testclient.scheduler.CXFClientScheduler.soapCxfStub01(CXFClientScheduler.java:33)
	at jdk.internal.reflect.GeneratedMethodAccessor94.invoke(Unknown Source)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:566)
	at org.springframework.scheduling.support.ScheduledMethodRunnable.run(ScheduledMethodRunnable.java:84)
	at org.springframework.scheduling.support.DelegatingErrorHandlingRunnable.run(DelegatingErrorHandlingRunnable.java:54)
	at org.springframework.scheduling.concurrent.ReschedulingRunnable.run(ReschedulingRunnable.java:95)
	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:515)
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
	at java.base/java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:304)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
	at java.base/java.lang.Thread.run(Thread.java:834)
Caused by: java.net.SocketTimeoutException: SocketTimeoutException invoking http://localhost/soap/hello: Read timed out
	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
	at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:490)
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
	... 14 common frames omitted
Caused by: java.net.SocketTimeoutException: Read timed out
	at java.base/java.net.SocketInputStream.socketRead0(Native Method)
	at java.base/java.net.SocketInputStream.socketRead(SocketInputStream.java:115)
	at java.base/java.net.SocketInputStream.read(SocketInputStream.java:168)
	at java.base/java.net.SocketInputStream.read(SocketInputStream.java:140)
	at java.base/java.io.BufferedInputStream.fill(BufferedInputStream.java:252)
	at java.base/java.io.BufferedInputStream.read1(BufferedInputStream.java:292)
	at java.base/java.io.BufferedInputStream.read(BufferedInputStream.java:351)
	at java.base/sun.net.www.http.HttpClient.parseHTTPHeader(HttpClient.java:746)
	at java.base/sun.net.www.http.HttpClient.parseHTTP(HttpClient.java:689)
	at java.base/sun.net.www.protocol.http.HttpURLConnection.getInputStream0(HttpURLConnection.java:1615)
	at java.base/sun.net.www.protocol.http.HttpURLConnection.getInputStream(HttpURLConnection.java:1520)
	at java.base/java.net.HttpURLConnection.getResponseCode(HttpURLConnection.java:527)
	at org.apache.cxf.transport.http.URLConnectionHTTPConduit$URLConnectionWrappedOutputStream$2.run(URLConnectionHTTPConduit.java:377)
	at org.apache.cxf.transport.http.URLConnectionHTTPConduit$URLConnectionWrappedOutputStream$2.run(URLConnectionHTTPConduit.java:373)
	at java.base/java.security.AccessController.doPrivileged(Native Method)
	at org.apache.cxf.transport.http.URLConnectionHTTPConduit$URLConnectionWrappedOutputStream.getResponseCode(URLConnectionHTTPConduit.java:373)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.doProcessResponseCode(HTTPConduit.java:1598)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.handleResponseInternal(HTTPConduit.java:1626)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.handleResponse(HTTPConduit.java:1571)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.close(HTTPConduit.java:1371)
	... 24 common frames omitted

     */
}
