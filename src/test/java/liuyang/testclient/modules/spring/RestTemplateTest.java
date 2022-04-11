package liuyang.testclient.modules.spring;

import liuyang.testclient.common.utils.IdUtils;
import liuyang.testclient.modules.spring.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * get post exchange
 * docs:
 * https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#rest-resttemplate
 *
 * RestTemplate: The original Spring REST client with a synchronous, template method API.
 * WebClient: a non-blocking, reactive alternative that supports both synchronous and asynchronous as well as streaming scenarios.
 *
 * As of 5.0 the RestTemplate is in maintenance mode, with only minor requests for changes and bugs to be accepted going forward.
 * Please, consider using the WebClient which offers a more modern API and supports sync, async, and streaming scenarios.
 *
 * getForObject 获取请求对象
 * getForEntity 获取请求对象 + Http头等其他信息
 *
 * postForObject
 * postForEntity
 *
 * exchange
 *
 * docs: WebClient
 * https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html#webflux-client
 *
 * @author liuyang(wx)
 * @since   2021/11/25
 * @update  2022/1/12
 */
@Slf4j
//@SpringBootTest
public class RestTemplateTest {
    private static RestTemplate restTemplate;
    static {
        // 文档 1.1.1 Initialization
        // 关于初始化: 找实现了ClientHttpRequestFactory接口的类，注册进RestTemplate即可。
        // 1. 底层使用java.net.HttpURLConnection
        // restTemplate = new RestTemplate();
        // 2. 底层使用ApacheHttpComponents
        restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());// 需要添加相关依赖。
        // 3. 底层使用Netty
        // 4. 底层使用OkHttp
    }
    /*
    @Autowired
    private RestTemplate restTemplate;
    */

    // Http协议状态码解析
    // 状态码
    // https://www.runoob.com/http/http-status-codes.html
    // https://httpstatus.com/
    @Test
    void httpStatusTest() {
        // 100 200 301 302 404 500
    }

    // get https
    @Test
    void httpsTest() {
        String url = "https://muyan-yootk.com/";
        Map<String, Long> paramMap = new HashMap<>();
        ResponseEntity<Object> forEntity = restTemplate.getForEntity(url, Object.class, paramMap);
        log.info("statusCode = {}", forEntity.getStatusCode());
        log.info("statusCodeValue = {}", forEntity.getStatusCodeValue());
        log.info("headers = {}", forEntity.getHeaders());
        log.info("body = {}", forEntity.getBody());
    }


    // 直接获取对象
    @Test
    void getForObjectTest() {
        String url = "http://localhost/hello/dto";
        Map<String, Long> paramMap = new HashMap<>();
        UserDTO forObject = restTemplate.getForObject(url, UserDTO.class, paramMap);
        log.info("forObject = {}", forObject);
    }

    // 对象 + HTTP信息（header, 状态码等）
    @Test
    void getForEntityTest() {
        String url = "http://localhost/hello/dto";
        Map<String, Long> paramMap = new HashMap<>();
        ResponseEntity<UserDTO> forEntity = restTemplate.getForEntity(url, UserDTO.class, paramMap);
        // 可以拿到如下信息
        forEntity.getStatusCode();
        forEntity.getStatusCodeValue();
        forEntity.getHeaders();
        forEntity.getBody();
        log.info("forEntity.getBody() = {}", forEntity.getBody());
    }

    /**
     * 传参注意：
     * postForObject请求中，需要使用MultiValueMap（基本类型、对象类型传参均可）。
     */
    @Test
    void postForObjectTest() {
        String url = "http://localhost/hello/dtopost1"; // 202201121329 ok
        MultiValueMap<String ,Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("id", IdUtils.nextTaskId());
        paramMap.add("username", "liuyang");
        paramMap.add("info", "foo");
        paramMap.add("d", 8.0);
        paramMap.add("bd", new BigDecimal(40.000000));
        UserDTO userDTO = restTemplate.postForObject(url, paramMap, UserDTO.class);
        log.info("postForObject = {}", userDTO);
    }

    /**
     * 注意：
     * postForEntity请求中，
     * 1. 需要使用MultiValueMap传参。
     * 2. 当服务端使用“@RequestBody”传参时（e.g. methodName(@RequestBody Map param)），必须使用HttpEntity（本例演示） 。
     */
    @Test
    void postForEntityTest() {
        String url = "http://localhost/hello/dtopost2"; // 202201121335 ok
        // 头(MultiValueMap<String, String>)
        HttpHeaders httpHeaders = new HttpHeaders();// HttpHeaders是一个MultiValueMap
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);// application/json
        // 数据(T)
        UserDTO userDTO = new UserDTO();
        userDTO.setId(IdUtils.nextTaskId());
        userDTO.setUsername("liuyang");
        userDTO.setInfo("Info test @RequestBody");
        userDTO.setD(0.1);
        userDTO.setBd(new BigDecimal(40.00000));
        // HttpEntity <------------------ methodName(@RequestBody Map param)
        HttpEntity<UserDTO> httpEntity = new HttpEntity<>(userDTO, httpHeaders);
        ResponseEntity<UserDTO> userDTOResponseEntity = restTemplate.postForEntity(url, httpEntity, UserDTO.class);
        userDTOResponseEntity.getStatusCode();
        userDTOResponseEntity.getStatusCodeValue();
        userDTOResponseEntity.getHeaders();
        userDTOResponseEntity.getBody();
        log.info("postForEntity = {}", userDTOResponseEntity.getBody());
    }

    /**
     * 既可以发送GET请求也可以发送POST请求。
     */
    @Test
    void exchangeTest() {
        String url = "http://localhost/hello/dtopost2"; // 202201121336 ok
        // 头(MultiValueMap<String, String>)
        HttpHeaders httpHeaders = new HttpHeaders();// HttpHeaders是一个MultiValueMap
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);// application/json
        // 数据(T)
        UserDTO userDTO = new UserDTO();
        userDTO.setId(IdUtils.nextTaskId());
        userDTO.setUsername("liuyang");
        userDTO.setInfo("Info test @RequestBody via restTemplate.exchange");
        userDTO.setD(0.1);
        userDTO.setBd(new BigDecimal(40.00000));
        // HttpEntity
        HttpEntity<UserDTO> httpEntity = new HttpEntity<>(userDTO, httpHeaders);
        ResponseEntity<UserDTO> exchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity, UserDTO.class);
        log.info("exchange = {}", exchange.getBody());
    }
}
