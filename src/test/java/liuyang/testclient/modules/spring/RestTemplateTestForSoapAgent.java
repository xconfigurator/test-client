package liuyang.testclient.modules.spring;

import com.alibaba.fastjson.JSON;
import liuyang.testclient.modules.spring.dto.AlmQueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyang(wx)
 * @since 2021/12/13
 */
@Slf4j
public class RestTemplateTestForSoapAgent {

    private static RestTemplate restTemplate;

    @BeforeEach
    public void init() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void test() throws Exception{
        /*
        String url = "http://localhost/queue/produce";
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("msg", "client" + clientIdx + " " + IdUtils.nextTaskId());
        R r = restTemplate.postForObject(url, paramMap, R.class);
        log.info("R = {}", JSON.toJSONString(r));
         */

        /*
        // 头(MultiValueMap<String, String>)
        HttpHeaders httpHeaders = new HttpHeaders();// HttpHeaders是一个MultiValueMap
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);// application/json
        // 数据(T)
        Map<String, Long> body = new HashMap<>();
        body.put("foo", 123l);
        // HttpEntity
        HttpEntity<Map<String, Long>> httpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<UserDTO> userDTOResponseEntity = restTemplate.postForEntity(url, httpEntity, UserDTO.class);
        userDTOResponseEntity.getStatusCode();
        userDTOResponseEntity.getStatusCodeValue();
        userDTOResponseEntity.getHeaders();
        userDTOResponseEntity.getBody();
        log.info("postForEntity = {}", userDTOResponseEntity.getBody());
         */

        //
        HttpHeaders httpHeaders = new HttpHeaders();// HttpHeaders是一个MultiValueMap
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);// application/json
        // 数据
        Map<String, String> body = new HashMap<>();
        body.put("startTime", "2021-12-13 00:00:00");
        body.put("entTime", "2021-12-13 00:00:00");
        // HttpEntity
        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(body, httpHeaders);
        String url = "http://localhost:8084/CityAlmService/almQuery";
        AlmQueryResponse r = restTemplate.postForObject(url, httpEntity, AlmQueryResponse.class);
        log.info("r = {}", JSON.toJSONString(r));
        // TODO 有问题
    }
}
