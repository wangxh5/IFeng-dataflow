package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

import bean.JsonStruction;
import net.sf.json.JSONObject;
import utils.CalTools;
import utils.LoadConfig;

public class DataStatisticsService {
	List<JsonStruction> orData = new ArrayList<JsonStruction>();
	List<JsonStruction> reData = new ArrayList<JsonStruction>();
	List<JsonStruction> inEdData = new ArrayList<JsonStruction>();
	List<JsonStruction> olData = new ArrayList<JsonStruction>();
	List<JsonStruction> oEdData = new ArrayList<JsonStruction>();
	List<JsonStruction> laData = new ArrayList<JsonStruction>();
	List<JsonStruction> recomData = new ArrayList<JsonStruction>();

	private static final String defaultPath = LoadConfig.lookUpValueByKey("defaultPath1");

	private void readFile(String date) throws IOException {
		String date1=date.replaceAll("-", "");
		System.out.println(date1);
		FileReader fr = null;
		fr = new FileReader(defaultPath + date1 + "_sum.txt");
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		line = br.readLine();
		JsonStruction jsonStruction = new JsonStruction();
		String s[] = new String[2];
		s[0] = date;
		s[1] = line.split("\t")[1];
		jsonStruction.setName(s[0]);
		jsonStruction.setValue(s);
		orData.add(jsonStruction);

		JsonStruction jsonStruction2 = new JsonStruction();
		String s2[] = new String[2];
		s2[0] = date;
		s2[1] = line.split("\t")[2];
		jsonStruction2.setName(s2[0]);
		jsonStruction2.setValue(s2);
		reData.add(jsonStruction2);

		JsonStruction jsonStruction3 = new JsonStruction();
		String s3[] = new String[2];
		s3[0] = date;
		s3[1] = line.split("\t")[3];
		jsonStruction3.setName(s3[0]);
		jsonStruction3.setValue(s3);
		inEdData.add(jsonStruction3);

		JsonStruction jsonStruction4 = new JsonStruction();
		String s4[] = new String[2];
		s4[0] = date;
		s4[1] = line.split("\t")[4];
		jsonStruction4.setName(s4[0]);
		jsonStruction4.setValue(s4);
		olData.add(jsonStruction4);

		JsonStruction jsonStruction5 = new JsonStruction();
		String s5[] = new String[2];
		s5[0] = date;
		s5[1] = line.split("\t")[5];
		jsonStruction5.setName(s5[0]);
		jsonStruction5.setValue(s5);
		oEdData.add(jsonStruction5);

		JsonStruction jsonStruction6 = new JsonStruction();
		String s6[] = new String[2];
		s6[0] = date;
		s6[1] = line.split("\t")[6];
		jsonStruction6.setName(s6[0]);
		jsonStruction6.setValue(s6);
		laData.add(jsonStruction6);

		JsonStruction jsonStruction7 = new JsonStruction();
		String s7[] = new String[2];
		s7[0] = date;
		s7[1] = line.split("\t")[7];
		jsonStruction7.setName(s7[0]);
		jsonStruction7.setValue(s7);
		recomData.add(jsonStruction7);

		br.close();
		fr.close();

	}

	public String getData(String startDate, String endDate) throws ParseException, IOException {
		System.out.println(startDate);
		System.out.println(endDate);
		orData = new ArrayList<JsonStruction>();
		reData = new ArrayList<JsonStruction>();
		inEdData = new ArrayList<JsonStruction>();
		olData = new ArrayList<JsonStruction>();
		oEdData = new ArrayList<JsonStruction>();
		laData = new ArrayList<JsonStruction>();
		recomData = new ArrayList<JsonStruction>();

		long terminalTime = CalTools.getRecommendTimeStamp(endDate + " 23:00:00");

		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			String date = CalTools.getRecommendDate((terminalTime - i * 24 * 60 * 60) * 1000);
			readFile(date);
			if (startDate.equals(date)) {
				break;
			}
		}
		HashMap<String, String> result = new HashMap<String, String>();
		// Collections.reverse(data);
		Gson gson = new Gson();
		String json1 = gson.toJson(orData);
		String json2 = gson.toJson(reData);
		String json3 = gson.toJson(inEdData);
		String json4 = gson.toJson(olData);
		String json5 = gson.toJson(oEdData);
		String json6 = gson.toJson(laData);
		String json7 = gson.toJson(recomData);
		result.put("原始抓取数量", json1);
		result.put("排重后数量", json2);
		result.put("进入泛编数量", json3);
		result.put("泛编下线数量", json4);
		result.put("泛编出口数量", json5);
		result.put("推荐数据更新量", json6);
		result.put("可推荐量", json7);
		JSONObject jsonobject = JSONObject.fromObject(result);

		return jsonobject.toString();
	}

	public static void main(String[] args) throws ParseException, IOException {
		DataStatisticsService dataStatisticsService = new DataStatisticsService();
		String string = dataStatisticsService.getData("2017-11-16", "2017-11-18");
		System.out.println(string);

	}
}
