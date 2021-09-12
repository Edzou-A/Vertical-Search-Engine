package com.test.demo;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class NomaltestApplicationTests {


    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Test
    void test1() throws IOException {
        //创建索引request请求 相当于PUT ES_index
        CreateIndexRequest request = new CreateIndexRequest("ES1_index");

        //执行请求
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);

        System.out.println(createIndexResponse);
    }

}
