package es_test1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class es_test_update {
    public static void main(String args[]) throws IOException {


        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost",9200,"http"))
        );

        UpdateRequest updateRequest = new UpdateRequest();

        updateRequest.index("es_test1_create").id("1001");

        updateRequest.doc(XContentType.JSON,"url","http://guge.com");

        UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);

        System.out.println((update.getResult()));

        restHighLevelClient.close();
    }
}