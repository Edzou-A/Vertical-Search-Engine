package com.edz.edz_esapi;

import com.alibaba.fastjson.JSON;
import com.edz.edz_esapi.pojo.User;
import com.edz.edz_esapi.utils.ESconst;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class EdzEsApiApplicationTests {

	@Autowired
	private RestHighLevelClient restHighLevelClient;

	@Test
	//创建索引
	void testCreateIndex() throws IOException {
		CreateIndexRequest request = new CreateIndexRequest("edz_es");
		CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);

		System.out.println(createIndexResponse);
	}
	//获取索引
	@Test
	void testGetIndex() throws IOException {
		GetIndexRequest request = new GetIndexRequest("edz_es");
		boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);

		System.out.println(exists);
	}
	//删除索引
	@Test
	void testDeleteIndex() throws IOException {
		DeleteIndexRequest request = new DeleteIndexRequest("edz_es");
		AcknowledgedResponse delete = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);

		System.out.println(delete.isAcknowledged());
	}

	//创建文档
	@Test
	void testAddDocument() throws IOException {
		User user = new User("edz",1);
		IndexRequest request = new IndexRequest("edz_es");

		//规则，可以不写
		request.id("0001");
		request.timeout(TimeValue.timeValueSeconds(1));
		request.timeout("1s");

		//数据放入请求
		request.source(JSON.toJSONString(user), XContentType.JSON);

		//客户端发送请求,获取响应的结果
		IndexResponse index = restHighLevelClient.index(request, RequestOptions.DEFAULT);

		System.out.println(index.toString());
		System.out.println(index.status());
	}

	//获取文档，判断是否存在
	@Test
	void testIsExits() throws IOException {
		GetRequest request = new GetRequest("edz_es","0001");


		boolean exists = restHighLevelClient.exists(request, RequestOptions.DEFAULT);

		System.out.println(exists);

	}

	//获取文档信息
	@Test
	void getDocument() throws IOException {
		GetRequest request = new GetRequest("test","1");
		GetResponse documentFields = restHighLevelClient.get(request, RequestOptions.DEFAULT);
		System.out.println(documentFields.getSourceAsString());
	}

	//更新文档信息
	@Test
	void testUpdateDocument() throws IOException {
		UpdateRequest request = new UpdateRequest("edz_es","0001");

		User user = new User("test1", 1);
		request.doc(JSON.toJSONString(user), XContentType.JSON);

		UpdateResponse updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);
		System.out.println(updateResponse.status());
	}

	//删除文档记录
	@Test
	void testDeleteDocument() throws IOException {
		DeleteRequest request = new DeleteRequest("edz_es","0001");


		DeleteResponse delete = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
		System.out.println(delete.status());
	}

	//批量插入数据
	@Test
	void testBulkDocument() throws IOException {
		BulkRequest bulkRequest = new BulkRequest();
		bulkRequest.timeout("10s");

		ArrayList<User> userArrayList = new ArrayList<>();
		userArrayList.add(new User("test1",1));
		userArrayList.add(new User("test2",2));
		userArrayList.add(new User("test3",3));
		userArrayList.add(new User("test4",4));
		userArrayList.add(new User("test5",5));
		userArrayList.add(new User("test6",6));
		userArrayList.add(new User("test7",7));

		for(int i = 0;i<userArrayList.size();i++){
			bulkRequest.add(new IndexRequest("edz_es")
					.id(""+(i+1))
					.source(JSON.toJSONString(userArrayList.get(i)),XContentType.JSON)
			);
		}
		BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
		System.out.println(bulk.hasFailures());
	}

	//查询数据
	@Test
	void testSearchDocument() throws IOException{
		SearchRequest request = new SearchRequest(ESconst.ES_INDEX);

		//构建搜索的条件
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		//搜索条件，可使用QueryBuilders 工具类来实现
		//termQuery精确匹配
		//matchAllQuery匹配所有
		//FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery("password","12345");
		//MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("password","123");
		//MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("123","password");
		TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("password","123456");
		//MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
		searchSourceBuilder.query(termQueryBuilder);
		searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

		request.source(searchSourceBuilder);

		SearchResponse searchResponse = restHighLevelClient.search(request, RequestOptions.DEFAULT);

		System.out.println(JSON.toJSONString(searchResponse.getHits()));
		System.out.println("==================================");
		for (SearchHit hit : searchResponse.getHits().getHits()) {
			System.out.println(hit.getSourceAsMap().get("name"));
		}
	}
}
