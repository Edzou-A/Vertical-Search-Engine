package es_test1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class es_test_insert {
    public static void main(String args[]) throws IOException {


        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost",9200,"http"))
        );

        IndexRequest indexRequest = new IndexRequest();
        indexRequest.index("es_test1_create").id("1001");

        User user = new User();
        user.setName("test1");
        user.setUrl("http://baidu.com");
        user.setNumber(1);

        ObjectMapper mapper = new ObjectMapper();
        String userjson = mapper.writeValueAsString(user);
        indexRequest.source(userjson, XContentType.JSON);

        IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        System.out.println((index.getResult()));

        restHighLevelClient.close();
    }
}