package api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.cucumber.core.internal.com.fasterxml.jackson.core.type.TypeReference;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import utils.TestUtility;

public class DeserialzedJson {

	/* Class Level Variables Declaration */
	public MainAPI wrapperObject = null;
	public static String JWTToken = null;
	Map<String,String> map = new HashMap<String,String>();
	ObjectMapper mapper;

	/* Constructor of the class here different wrappers instance is made */
	public DeserialzedJson() {
		wrapperObject = MainAPI.getInstance();
	}

	/*Method to set token globally*/
	public void setTokenGlobally(Response local_response) {
		mapper = new ObjectMapper();
		TypeReference<HashMap<String,String>> typeRef = new TypeReference<HashMap<String,String>>(){
		};
		try {
			map = mapper.readValue(local_response.getBody().asString(), typeRef);
		}catch(Exception e) {
			TestUtility.logger.error(e.getMessage());
			e.printStackTrace();
		}
		wrapperObject.setToken(map.get("token"));
		
	}

	public void deserialzedJsonStoreData(Response local_response) throws IOException {
		System.out.println(local_response.getBody().asPrettyString());
	}
}
