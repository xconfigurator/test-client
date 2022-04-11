package liuyang.testclient.modules.spring;

import com.alibaba.fastjson.JSON;
import liuyang.testclient.common.utils.IdUtils;
import liuyang.testclient.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 服务端代码在test-messaging中
 *
 * @author liuyang(wx)
 * @since 2021/12/2
 */
@Slf4j
public class RestTemplateTestForLinkedBlockingQueue {

    private static RestTemplate restTemplate;

    @BeforeEach
    public void init() {
        restTemplate = new RestTemplate();
    }

    //
    @Test
    void postForObjectBatch() {
        for (int i = 0; i < 10; i++) {
            postForObject(0);
        }
    }

    @Test
    void postForObjectConcurrent() {
        String url = "http://localhost/queue/produce";
        final int MAX_CLIENTS = 50;// 100000
        for (int i = 0; i < MAX_CLIENTS; ++i) {
            new Thread(() -> {
                postForObject(Integer.parseInt(Thread.currentThread().getName()));
            }
            , String.valueOf(i)).start();
        }

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void postForObjectConcurrent2() {
        String url = "http://localhost/queue/produce";
        final int MAX_CLIENTS = 100;
        final int MAX_REQ_PER_CLIENT = 200;
        for (int i = 0; i < MAX_CLIENTS; ++i) {
            new Thread(() -> {
                for (int j = 0; j < MAX_REQ_PER_CLIENT; ++j) {
                    postForObject(Integer.parseInt(Thread.currentThread().getName()));
                }
            }, String.valueOf(i)).start();
        }

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void postForObject(int clientIdx) {
        String url = "http://localhost/queue/produce";
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("msg", "client" + clientIdx + " " + IdUtils.nextTaskId());
        R r = restTemplate.postForObject(url, paramMap, R.class);
        log.info("R = {}", JSON.toJSONString(r));
    }
}
