package liuyang.testclient.modules.jdk.utils;

import com.hbfec.config.SystemConfig;
import com.hbfec.result.exception.CommonExceptionEnum;
import com.hbfec.result.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class HttpUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

    private HttpUtil() {

    }

    /**
     * post请求
     */
    public static String doPost(String url, String param, SystemConfig sc) { //日期转化标识
        HttpURLConnection connection = null;
        OutputStreamWriter out = null;
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        //创建链接方式，http访问方式
        try {
            //增加签名
            String clientId = sc.getClientId();
            String clientSecret = sc.getClientSecret();
            long timestamp = new Date().getTime();
            final int nine = 9;
            String nonce = AesEncryptUtils.getRandomString(nine);
            //生成签名
            SignTool siginTool = new SignTool(clientId, clientSecret);
            String signatureCheck = siginTool.sign(new HashMap<>(), timestamp, nonce);
            String systemName = PropertiesUtil.getString("spring.application.name");
            url += "?clientId=" + clientId + "&timestamp=" + timestamp + "&nonce=" + nonce + "&sign=" + signatureCheck+"&systemName="+systemName;
            URL readUrl = new URL(url);
            int readTimeoutInMilltons = Integer.parseInt(PropertiesUtil.getString("request.readTimeOut").trim());
            int connTimeoutInMilltons = Integer.parseInt(PropertiesUtil.getString("request.connTimeOut").trim());
            connection = (HttpURLConnection) readUrl.openConnection();
            connection.setConnectTimeout(connTimeoutInMilltons);
            connection.setReadTimeout(readTimeoutInMilltons);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.connect();
            //post请求
            out = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8);

            out.write(param);
            out.flush();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String lines;
            while ((lines = reader.readLine()) != null) {
                sb.append(lines);
            }
            return sb.toString();
        } catch (SocketTimeoutException e) {
            throw new ServiceException(CommonExceptionEnum.REQ_TIMEOUT.getCode(), CommonExceptionEnum.REQ_TIMEOUT.getMessage());
        } catch (ConnectException e) {
            throw new ServiceException(CommonExceptionEnum.REQ_ERROR.getCode(), CommonExceptionEnum.REQ_ERROR.getMessage());
        } catch (MalformedURLException e) {
            LOGGER.error("MalformedURLException error", e);
            throw new ServiceException(CommonExceptionEnum.REQ_ERROR.getCode(), CommonExceptionEnum.REQ_ERROR.getMessage());
        } catch (ProtocolException e) {
            LOGGER.error("ProtocolException error", e);
            throw new ServiceException(CommonExceptionEnum.REQ_ERROR.getCode(), CommonExceptionEnum.REQ_ERROR.getMessage());
        } catch (IOException e) {
            LOGGER.error("IOException error", e);
            throw new ServiceException(CommonExceptionEnum.REQ_ERROR.getCode(), CommonExceptionEnum.REQ_ERROR.getMessage());
        } catch (Exception e) {
            LOGGER.error("Exception error", e);
            throw new ServiceException(CommonExceptionEnum.REQ_ERROR.getCode(), CommonExceptionEnum.REQ_ERROR.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    LOGGER.error("IOException", e);
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    LOGGER.error("IOException", e);
                }
            }
        }
        //return "";按照抛出的异常信息返回到result中
    }

    public static String getFourRandom() {
        Random random = new SecureRandom();
        final int a = 10000;
        StringBuilder fourRandom = new StringBuilder(random.nextInt(a) + "");
        int randLength = fourRandom.length();
        final int len = 3;
        if (randLength < len) {
            for (int i = 1; i <= len - randLength; i++) {
                fourRandom.insert(0, "0");
            }
        }
        return fourRandom.toString();
    }

    /**
     * 2021.04.24 Saturday 性能上报数据日期转换方法暂时不用了
     * 由于北向接口日期处理需要 年月日时分秒  目前只有告警上报是日期格式   操作上报和性能数据上报日期需要处理
     *
     * @param flg
     * @param dataList
     * @return
     */
    public static List<Map<String, Object>> setDateParam(String flg, List<Map<String, Object>> dataList) {

        if ("opeLogFlg".equals(flg)) { //处理操作上报信息中日期的格式
            //接口返回参数日期格式数据转化
            opeLogFlgData(dataList);
        } else if ("asgFlg".equals(flg) || "dtuFlg".equals(flg) || "sameFlg".equals(flg) || "trkFlg".equals(flg)) {
            otherLogFlgData(dataList);
        }
        return dataList;
    }

    private static void opeLogFlgData(List<Map<String, Object>> dataList) {
        for (Map<String, Object> maps : dataList) {
            Set<String> strings = maps.keySet();
            for (String string : strings) {
                if (string.toLowerCase().contains("time") || string.toLowerCase().contains("date")) {
                    Date date = new Date(Long.valueOf(String.valueOf(maps.get(string))));
                    //创建日期格式化对象(把日期转成字符串)
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    String str = sdf.format(date);
                    maps.put(string, str);
                }
            }
        }
    }

    private static void otherLogFlgData(List<Map<String, Object>> dataList) {
        for (Map<String, Object> maps : dataList) {
            Set<String> strings = maps.keySet();
            for (String string : strings) {
                if (string.equals("performanceList")) {
                    List<Map<String, Object>> performanceList = (List) maps.get("performanceList");
                    for (Map<String, Object> objectMap : performanceList) {
                        Date date = new Date(Long.valueOf(String.valueOf(objectMap.get("lastTime"))));
                        //创建日期格式化对象(把日期转成字符串)
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                        String str = sdf.format(date);
                        objectMap.put("lastTime", str);
                    }
                }
            }
        }
    }
}
