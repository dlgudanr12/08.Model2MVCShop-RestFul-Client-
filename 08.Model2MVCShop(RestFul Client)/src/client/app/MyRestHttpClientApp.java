package client.app;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.model2.mvc.service.domain.User;

public class MyRestHttpClientApp {

	private static ObjectMapper objectMapper = new ObjectMapper();

	// main Method
	public static void main(String[] args) throws Exception {

//		addUserTest();
//		System.out.print("\n// ================================================================//\n\n");
//		addUserViewTest();
//		getUserTest();
//		System.out.print("\n// ================================================================//\n\n");
//		loginTest();
//		System.out.print("\n// ================================================================//\n\n");
		updateUserViewTest();

	}

	// ================================================================//
	// 1.1 Http Protocol GET Request : JsonSimple 3rd party lib ���

	public static void addUserViewTest() throws Exception {
		String url = "http://127.0.0.1:8080/rest/user/json/addUser/gotowell";
		System.out.println(getTest_JsonSimple(url).get("message"));
	}

	public static void addUserTest() throws Exception {
		String url = "http://127.0.0.1:8080/rest/user/json/addUser";
		JSONObject json = new JSONObject();
		json.put("userId", "user31");
		json.put("userName", "õ��μ�");
		json.put("password", "3131");

		System.out.println(objectMapper.readValue(postTest_JsonSimple(url, json).toString(), User.class));
	}

	public static void getUserTest() throws Exception {
		String url = "http://127.0.0.1:8080/rest/user/json/getUser/admin";
		System.out.println(objectMapper.readValue(getTest_JsonSimple(url).toString(), User.class));

	}

	public static void loginTest() throws Exception {
		String url = "http://127.0.0.1:8080/rest/user/json/login";
		JSONObject json = new JSONObject();
		json.put("userId", "admin");
		json.put("password", "1234");

		System.out.println(objectMapper.readValue(postTest_JsonSimple(url, json).toString(), User.class));

	}

	public static void updateUserViewTest() throws Exception {
		String url = "http://127.0.0.1:8080/rest/user/json/updateUser/user30";
		System.out.println(objectMapper.readValue(getTest_JsonSimple(url).get("user").toString(), User.class));
	}

	private static JSONObject getTest_JsonSimple(String url) throws Exception {

		// HttpClient : Http Protocol �� client �߻�ȭ
		HttpClient httpClient = new DefaultHttpClient();

		// HttpGet : Http Protocol �� GET ��� Request
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");

		// HttpResponse : Http Protocol ���� Message �߻�ȭ
		HttpResponse httpResponse = httpClient.execute(httpGet);

		// ==> Response Ȯ��
		System.out.println(httpResponse);
		System.out.println();

		// ==> Response �� entity(DATA) Ȯ��
		HttpEntity httpEntity = httpResponse.getEntity();

		// ==> InputStream ����
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

//		System.out.println("[ Server ���� ���� Data Ȯ�� ] ");
//		String serverData = br.readLine();
//		System.out.println(serverData);

		// ==> �����б�(JSON Value Ȯ��)
		JSONObject jsonobj = (JSONObject) JSONValue.parse(br);
		System.out.println("JSONObject :: " + jsonobj);
		return jsonobj;
	}

	// ================================================================//
	// 2.1 Http Protocol POST Request : FromData ���� / JsonSimple 3rd party lib ���
	private static JSONObject postTest_JsonSimple(String url, JSONObject json) throws Exception {

		// HttpClient : Http Protocol �� client �߻�ȭ
		HttpClient httpClient = new DefaultHttpClient();

		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");

		HttpEntity httpEntity01 = new StringEntity(json.toString(), "utf-8");

		httpPost.setEntity(httpEntity01);
		HttpResponse httpResponse = httpClient.execute(httpPost);

		// ==> Response Ȯ��
		System.out.println(httpResponse);
		System.out.println();

		// ==> Response �� entity(DATA) Ȯ��
		HttpEntity httpEntity = httpResponse.getEntity();

		// ==> InputStream ����
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		System.out.println("[ Server ���� ���� Data Ȯ�� ] ");
		String serverData = br.readLine();
		System.out.println(serverData);

		// ==> �����б�(JSON Value Ȯ��)
		JSONObject jsonobj = (JSONObject) JSONValue.parse(serverData);
		System.out.println(jsonobj);

		return jsonobj;

	}

}