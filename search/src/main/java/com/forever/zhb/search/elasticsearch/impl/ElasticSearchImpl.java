package com.forever.zhb.search.elasticsearch.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.forever.zhb.search.elasticsearch.ElasticSearch;
import com.forever.zhb.search.elasticsearch.indexdata.ElasticSearchIndexData;
import com.forever.zhb.search.util.UUIDUtil;

public class ElasticSearchImpl implements ElasticSearch {

	protected final Logger logger = LoggerFactory.getLogger(ElasticSearchImpl.class);

	private TransportClient client = null;

	public final static int PORT = 9300; // http请求的端口是9200，客户端是9300

	public final static String HOST = "127.0.0.1";
	public final static String HOST2 = "172.16.10.215";

	@Override
	@SuppressWarnings("resource")
	public void getConnect() throws UnknownHostException {
		if (null == client) {
			Settings settings = Settings.builder().put("client.transport.sniff", true).put("cluster.name", "my-application").build();
			client = new PreBuiltTransportClient(settings)
					.addTransportAddresses(new TransportAddress(InetAddress.getByName(HOST), PORT));
			logger.info("连接信息:" + client.toString());
		}
	}

	public void createIndex() {
		try {
			for (int i = 300; i <= 350; i++) {
				Map<String, Object> json = new HashMap<String, Object>();
				json.put("id",UUIDUtil.getRandomUUID());
				json.put("name","zhanghubin" + i);
				json.put("age",i % 10);
				IndexResponse indexResponse = client.prepareIndex("zhb", "forever",i+"")
						.setSource(json)
						.get();
				System.out.println("responseIsCreated: " + indexResponse);
			}
			System.out.println("it is ok ！");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void initIndex(ElasticSearchIndexData data) {
		try {
			Map<String, Object> json = new HashMap<String, Object>();
			json.put("id",UUIDUtil.getRandomUUID());
			json.put("name",data.getName());
			json.put("sex", data.getSex());
			json.put("age",data.getAge());
			json.put("phone", data.getPhone());
			json.put("email", data.getEmail());
			client.prepareIndex("zhb", "forever").setSource(json).get();
			logger.info("initIndex data total 1  个");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("initIndex data fail.....");
		}
	}
	
	public void initIndex(List<ElasticSearchIndexData> datas) {
		try {
			if (null != datas && datas.size() > 0) {
				for (ElasticSearchIndexData data : datas) {
					Map<String, Object> json = new HashMap<String, Object>();
					json.put("id",UUIDUtil.getRandomUUID());
					json.put("name",data.getName());
					json.put("sex", data.getSex());
					json.put("age",data.getAge());
					json.put("phone", data.getPhone());
					json.put("email", data.getEmail());
					client.prepareIndex("zhb", "forever").setSource(json).get();
				}
				logger.info("initIndex data total " + datas.size() + " 个");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("initIndex data fail.....");
		}

	}

	public void query() throws Exception {
		try {
			QueryBuilder rangeQuery = QueryBuilders.rangeQuery("age").gt(1); //大于1
			QueryBuilder matchQueryName = QueryBuilders.matchQuery("name","zhanghubin340"); //精确匹配
			QueryBuilder matchQueryAge = QueryBuilders.matchQuery("age",1); 
			SearchResponse searchResponse = client.prepareSearch("zhb")
					.setTypes("forever")
					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
					.setQuery(rangeQuery)
					//.setPostFilter(QueryBuilders.rangeQuery("age").from(20).to(30)) // Filter
					//.addSort("age", SortOrder.DESC) 
					.setFrom(0).setSize(20).setExplain(true)// 不设置的话，默认取10条数据
					.get();
			SearchHits hits = searchResponse.getHits();
			System.out.println("查到记录数：" + hits.getTotalHits());
			SearchHit[] searchHists = hits.getHits();
			if (searchHists.length > 0) {
				for (SearchHit hit : searchHists) {
					String name = (String) hit.getSourceAsMap().get("name");
					Integer age = Integer.parseInt(hit.getSourceAsMap().get("age").toString());
					System.out.format("name:%s ,age :%d \n", name, age);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("query index fail......");
		}

	}

	@Override
	public void closeConnect() {
		if (null != client) {
			logger.info("执行关闭连接操作...");
			client.close();
		}
	}

	/**
	 * 转换成json对象
	 *
	 * @param user
	 * @return
	 */
	private String generateJson(ElasticSearchIndexData user) {
		String json = "";
		try {
			XContentBuilder contentBuilder = XContentFactory.jsonBuilder().startObject();
			contentBuilder.field("id", user.getId());
			contentBuilder.field("name", user.getName());
			contentBuilder.field("age", user.getAge());
			json = contentBuilder.endObject().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
}
