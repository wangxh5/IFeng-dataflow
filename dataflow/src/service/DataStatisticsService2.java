package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import utils.CalTools;
import utils.LoadConfig;

public class DataStatisticsService2 {

	private static final String defaultPath = LoadConfig.lookUpValueByKey("defaultPath1");

	private JSONArray readFile(String date, String filename) throws IOException {
		FileReader fr = null;
		fr = new FileReader(defaultPath + date + "_" + filename + ".txt");
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		JSONArray jArray = new JSONArray();
		while ((line = br.readLine()) != null) {
			JSONObject innerjObject = new JSONObject();
			String[] s=new String[7];
			s[0]=line.split("\t")[1];
			s[1]=line.split("\t")[2];
			s[2]="-";
			s[3]=line.split("\t")[4];
			s[4]=line.split("\t")[5];
			s[5]=line.split("\t")[6];
			s[6]="-";
			innerjObject.put("name", line.split("\t")[0]);
			innerjObject.put("value", s);
			jArray.add(innerjObject);
		}
		br.close();
		fr.close();
		return jArray;
	}

	private JSONObject readSumFile(String date) throws IOException {
		FileReader fr = null;
		fr = new FileReader(defaultPath + date + "_sum.txt");
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		 line = br.readLine();
		JSONObject innerjObject = new JSONObject();
		innerjObject.put("name", date);
		innerjObject.put("value", Arrays.copyOfRange(line.split("\t"), 1, 8));
		br.close();
		fr.close();
		return innerjObject;
	}

	public String getData(String date) throws ParseException, IOException {
		JSONArray jArray = new JSONArray();
		long terminalTime = CalTools.getTimeStamp(date + " 23:00:00");
		
		for (int i = 0; i < 7; i++) {
			String date1 = CalTools.getDate((terminalTime - i * 24 * 60 * 60) * 1000);
			System.out.println(date1);
			jArray.add(readSumFile(date1));
			if ("20171116".equals(date1)) {
				break;
			}
		}
		FileReader fr = null;
		fr = new FileReader(defaultPath + date + "_sum.txt");
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		br.close();
		fr.close();

		JSONObject jObject = new JSONObject();
		jObject.put("source", readFile(date, "source"));
		jObject.put("category", readFile(date, "category"));
		jObject.put("sourceRate", readFile(date, "sourceRate"));
		jObject.put("qualityEvalLevel", readFile(date, "qualityEvalLevel"));
		jObject.put("topLocation", readFile(date, "topLocation"));
		jObject.put("time", date);
		jObject.put("title", Arrays.copyOfRange(line.split("\t"), 1, 8));
		jObject.put("sum", jArray);
		// Gson gson = new Gson();
		// String json1 = gson.toJson(readFile(date, "source"));
		// String json2 = gson.toJson(readFile(date, "category"));
		// String json3 = gson.toJson(readFile(date, "sourceRate"));
		// String json4 = gson.toJson(readFile(date, "qualityEvalLevel"));
		// String json5 = gson.toJson(readFile(date, "topLocation"));
		// result.put("time", date);
		// result.put("title",
		// "[\"原始抓取数量\",\"排重后数量\",\"进入泛编数量\",\"泛编下线数量\",\"泛编出口数量\",\"推荐数据更新量\",\"可推荐量\"]");
		// result.put("sum",
		// JSONArray.fromObject(Arrays.asList(Arrays.copyOfRange(line.split("\t"),
		// 1, 8))).toString());
		// result.put("source", json1);
		// result.put("category", json2);
		// result.put("sourceRate", json3);
		// result.put("qualityEvalLevel", json4);
		// result.put("topLocation", json5);
		// JSONObject jsonobject = JSONObject.fromObject(result);
		// return "jsonpCallback("+jsonobject.toString()+")";
		return "jsonpCallback(" + jObject.toString() + ")";
	}

	public static void main(String[] args) throws ParseException, IOException {
		DataStatisticsService2 dataStatisticsService = new DataStatisticsService2();
		String string = dataStatisticsService.getData("20171120");
		System.out.println(string);

	}
}
