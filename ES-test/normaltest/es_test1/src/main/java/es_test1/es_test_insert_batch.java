package es_test1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class es_test_insert_batch {
    public static void main(String args[]) throws IOException {


        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost",9200,"http"))
        );

        //
        BulkRequest request = new BulkRequest();


        request.add(new IndexRequest().index("es_test-create").id("1002").source(XContentType.JSON,"name","test2"));
        request.add(new IndexRequest().index("es_test-create").id("1003").source(XContentType.JSON,"name","test3"));
        request.add(new IndexRequest().index("es_test-create").id("1004").source(XContentType.JSON,"name","test4"));

        restHighLevelClient.bulk(request,RequestOptions.DEFAULT);

        restHighLevelClient.close();
    }
}