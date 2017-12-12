package service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.floragunn.searchguard.ssl.SearchGuardSSLPlugin;
import com.floragunn.searchguard.ssl.util.SSLConfigConstants;
import com.google.gson.Gson;

import bean.JsonStruction;

public class ESClientHelperService {

	static Logger LOG = Logger.getLogger(ESClientHelperService.class);

	static final String INDEX = "cmpptoikv-*";
	static final int SIZE = 1000;

	private Settings.Builder settingsBuilder;
	private Settings settings;
	private static TransportClient client;

	@SuppressWarnings({ "resource" })
	public ESClientHelperService() {

		try {
			settingsBuilder = Settings.builder().put("path.home", ".").put("cluster.name", "ELK")
					.put("client.transport.sniff", true)
					.put(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_ENABLE_OPENSSL_IF_AVAILABLE, true)
					.put(SSLConfigConstants.SEARCHGUARD_SSL_TRANSPORT_ENABLE_OPENSSL_IF_AVAILABLE, true)
					// .put(SSLConfigConstants.SEARCHGUARD_SSL_TRANSPORT_ENABLE_OPENSSL_IF_AVAILABLE,true)
					// .put(SSLConfigConstants.SEARCHGUARD_SSL_TRANSPORT_KEYSTORE_ALIAS,
					// "node-1")
					.put("searchguard.ssl.transport.enabled", true)
					// .put("searchguard.ssl.transport.keystore_filepath",
					// "/data/hexl/logstash/deleteIndex/conf/node-1-keystore.jks")
					// .put("searchguard.ssl.transport.truststore_filepath",
					// "/data/hexl/logstash/deleteIndex/conf/truststore.jks")
					.put("searchguard.ssl.transport.keystore_filepath", "D:/code/node-1-keystore.jks")
					.put("searchguard.ssl.transport.truststore_filepath", "D:/code/truststore.jks")
					.put("searchguard.ssl.transport.keystore_password", "elastic_ifeng")
					.put("searchguard.ssl.transport.truststore_password", "elastic_ifeng")
					.put("searchguard.ssl.transport.enforce_hostname_verification", false);

			settings = settingsBuilder.build();

			client = new PreBuiltTransportClient(settings, SearchGuardSSLPlugin.class)
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.90.35.11"), 9300));

		} catch (Exception e) {
			LOG.error("create ESClientHelper failed!");
		}
	}

	/**
	 * 获取数据
	 * 
	 * @param indexStr
	 * @param fromN
	 * @param sizeN
	 * @throws IOException
	 */
	public String getResponse(String startTime, String endTime) throws IOException {

		String startDate = startTime.replace(" ", "T") + ".000Z";
		String endDate = endTime.replace(" ", "T") + ".000Z";
		System.out.println(startDate);
		System.out.println(endDate);
		List<JsonStruction> locationTimeList = new ArrayList<JsonStruction>();

		FieldSortBuilder sortBuilders = SortBuilders.fieldSort("@timestamp").order(SortOrder.ASC);
		RangeQueryBuilder rangequerybuilder = QueryBuilders.rangeQuery("@timestamp").from(startDate).to(endDate);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(rangequerybuilder);
		SearchResponse response = client.prepareSearch(INDEX)
				.setQuery(QueryBuilders.boolQuery()
						.must(QueryBuilders.matchPhraseQuery("content", "LocIdentify used time"))
						.must(rangequerybuilder))
				.addSort(sortBuilders).setFrom(0).setSize(1000).setExplain(true).execute().actionGet();
		System.out.println(response.getHits().getTotalHits());
		String message = null;

		for (int i = 0; i < 1000; i++) {
			JsonStruction jsonStruction = new JsonStruction();
			String s[] = new String[2];
			message = response.getHits().getAt(i).getSource().get("message").toString();
			System.out.println(i);
			System.out.println(message);
			System.out.println(message.split("time ")[1]);
			s[0] = String.valueOf(i);
			s[1] = message.split("time ")[1];

			jsonStruction.setName(String.valueOf(i));
			jsonStruction.setValue(s);

			locationTimeList.add(jsonStruction);
		}

		Gson gson = new Gson();
		String json = gson.toJson(locationTimeList);
		System.out.println(json);
		return json;
	}

	public void closeClient() {
		try {
			client.close();
		} catch (Exception e) {
			LOG.error("close ES java client failed!");
		}
	}

}
