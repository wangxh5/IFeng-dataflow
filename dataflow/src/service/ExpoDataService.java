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

public class ExpoDataService {
	private String sourceRatePath = LoadConfig.lookUpValueByKey("sourceRatePath");
	List<JsonStruction> expoRate = new ArrayList<JsonStruction>();
	List<JsonStruction> expoRate2 = new ArrayList<JsonStruction>();
	List<JsonStruction> expoRate3 = new ArrayList<JsonStruction>();

	private void readFile(String date) throws IOException {
		String date1=date.replaceAll("-", "");
		FileReader fr = new FileReader(sourceRatePath + date1 + "_expoRate.txt");

		BufferedReader br = new BufferedReader(fr);
		String line = null;
		while ((line = br.readLine()) != null) {
			if (line.contains("当日数据曝光比")) {
				JsonStruction jsonStruction = new JsonStruction();
				String s[] = new String[2];
				s[0] = date;
				s[1] = line.split("：")[1].split("%")[0];
				jsonStruction.setName(s[0]);
				jsonStruction.setValue(s);
				expoRate.add(jsonStruction);
			}
			if (line.contains("当日曝光数据比")) {
				JsonStruction jsonStruction = new JsonStruction();
				String s[] = new String[2];
				s[0] = date;
				s[1] = line.split("：")[1].split("%")[0];
				jsonStruction.setName(s[0]);
				jsonStruction.setValue(s);
				expoRate2.add(jsonStruction);
			}
			if (line.contains("总体曝光比")) {
				JsonStruction jsonStruction = new JsonStruction();
				String s[] = new String[2];
				s[0] = date;
				s[1] = line.split("：")[1].split("%")[0];
				jsonStruction.setName(s[0]);
				jsonStruction.setValue(s);
				expoRate3.add(jsonStruction);
			}

		}
		fr.close();
		br.close();
	}

	public String getData(String startDate, String endDate) throws ParseException, IOException {
		System.out.println(startDate);
		System.out.println(endDate);
		expoRate = new ArrayList<JsonStruction>();
		expoRate2 = new ArrayList<JsonStruction>();
		expoRate3 = new ArrayList<JsonStruction>();
		long terminalTime = CalTools.getRecommendTimeStamp(endDate + " 23:00:00") * 1000;

		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			String date = CalTools.getRecommendDate((terminalTime / 1000 - i * 24 * 60 * 60) * 1000);
			System.out.println("日期" + date);
			readFile(date);
			// readFileOd(date);
			if (date.equals(startDate)) {
				break;
			}
		}

		HashMap<String, String> result = new HashMap<String, String>();
		// Collections.reverse(data);
		Gson gson = new Gson();
		String json1 = gson.toJson(expoRate);
		String json2 = gson.toJson(expoRate2);
		String json3 = gson.toJson(expoRate3);
		result.put("当日数据曝光比", json1);
		result.put("当日曝光数据比", json2);
		result.put("总体曝光比", json3);
		JSONObject jsonobject = JSONObject.fromObject(result);

		return jsonobject.toString();
	}

	public static void main(String[] args) throws ParseException, IOException {

		ExpoDataService expoDataService = new ExpoDataService();
		String json = expoDataService.getData("2017-11-01", "2017-11-10");
		System.out.println(json);

	}

}
