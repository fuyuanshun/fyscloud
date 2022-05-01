package boot.wx.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * es测试
 */
@Slf4j
public class ESClientTest {
    /**
     * es地址
     */
    private static final String ES_ADDR = "127.0.0.1";
    /**
     * es端口号
     */
    private static final Integer ES_PORT = 9200;

    @Test
    public void esClientTest() throws IOException {
        log.debug("DEBUG");
        log.info("INFO");
        log.error("ERROR");
        RestClientBuilder resetClient = RestClient
                .builder(new HttpHost(ES_ADDR, ES_PORT));
        RestHighLevelClient client = new RestHighLevelClient(resetClient);
        //获取索引
        GetIndexResponse shopping = client.indices()
                .get(new GetIndexRequest("shopping", "shopping2"), RequestOptions.DEFAULT);
        System.out.println(shopping);
        //关闭客户端
        client.close();
    }
}
