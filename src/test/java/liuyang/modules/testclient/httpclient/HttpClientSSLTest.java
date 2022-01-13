package liuyang.modules.testclient.httpclient;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.ssl.TLS;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 访问https
 * @author liuyang(wx)
 * @since 2022/1/13
 */
@Slf4j
public class HttpClientSSLTest {

    @Test
    void getHttps() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, IOException, ParseException {
        // ////////////////////////////////////////////////////////////////////////////////////////
        // 注意：这个操作里，不需要在客户端导入证书！
        // SSL第一步：设置SSL上下文
        // Windows上查看安装证书列表：certmgr.msc
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                X509Certificate x509Certificate = chain[0];
                return "CN=muyan-yootk.com".equalsIgnoreCase(x509Certificate.getSubjectDN().getName());
                //return true;// 这样比较流氓，但有效！
            }
        }).build();
        // SSL第二步：创建SSL连接工厂类
        SSLConnectionSocketFactory sslConnectionSocketFactory = SSLConnectionSocketFactoryBuilder.create().setSslContext(sslContext).setTlsVersions(TLS.V_1_2).build();
        // SSL第三步：创建HttpClient连接管理类
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = PoolingHttpClientConnectionManagerBuilder.create().setSSLSocketFactory(sslConnectionSocketFactory).build();
        // SSL第四步：创建http请求
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(poolingHttpClientConnectionManager).build();
        // 对比一下普通http的
        // CloseableHttpClient httpClient = HttpClients.createDefault();
        // ////////////////////////////////////////////////////////////////////////////////////////

        // 注意：后面的就完全一样了
        String url = "https://muyan-yootk.com/";
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpGet);

        //showResponse(response);
        ResponseUtil.showResponse(response);

        response.close();
        httpClient.close();
    }
}
