package dhaneshproject.data;


import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class DataReader {
	
	//This method is not used
	public List<HashMap<String, String>> getJsonDataToMap() throws IOException 
	{
		
		//Read JSON to String (using java.nio.file)
		Path path = Paths.get(System.getProperty("user.dir")+"//src//test//java//dhaneshproject//data//PurchaseOrder.json");
		String jsonContent = Files.readString(path);
		
		//Convert String to HashMap (using Jackson Databind dependency)
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String,String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>(){	
		});
		return data;
	}

}
