package liuyang.testclient.modules.spring;

/**
 * docs:
 * https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html#webflux-client
 *
 * 来自RestTemplate文档：
 * RestTemplate: The original Spring REST client with a synchronous, template method API.
 * WebClient: a non-blocking, reactive alternative that supports both synchronous and asynchronous as well as streaming scenarios.
 *
 * As of 5.0 the RestTemplate is in maintenance mode, with only minor requests for changes and bugs to be accepted going forward.
 * Please, consider using the WebClient which offers a more modern API and supports sync, async, and streaming scenarios.
 *
 *
 * 来自WebClient文档：
 * Spring WebFlux includes a client to perform HTTP requests with. WebClient has a functional, fluent API based on Reactor, see Reactive Libraries, which enables declarative composition of asynchronous logic without the need to deal with threads or concurrency. It is fully non-blocking, it supports streaming, and relies on the same codecs that are also used to encode and decode request and response content on the server side.
 *
 * WebClient needs an HTTP client library to perform requests with. There is built-in support for the following:
 *
 * Reactor Netty
 *
 * Jetty Reactive HttpClient
 *
 * Apache HttpComponents
 *
 * Others can be plugged via ClientHttpConnector.
 *
 *
 * @author liuyang(wx)
 * @since 2021/11/26
 */
public class WebClientTest {
    // TODO
}
