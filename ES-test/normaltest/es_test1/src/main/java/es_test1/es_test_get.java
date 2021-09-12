package es_test1;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class es_test_get {
    public static void main(String args[]) throws IOException {


        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost",9200,"http"))
        );

        GetRequest getRequest = new GetRequest();

        getRequest.index("es_test1_create").id("1001");


        GetResponse documentFields = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);

        System.out.println((documentFields.getSourceAsString()));

        restHighLevelClient.close();
    }
}