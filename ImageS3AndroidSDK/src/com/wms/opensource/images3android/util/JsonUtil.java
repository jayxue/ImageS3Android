package com.wms.opensource.images3android.util;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtil {

	public static ObjectMapper mapper;
	
	static {
		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}

	public static ObjectMapper getJsonMapper() {
		return mapper;
	}
		  
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object deserialize(String json, String containerType, Class cls) throws JsonParseException, JsonMappingException, IOException {
		if("List".equals(containerType)) {
			JavaType typeInfo = JsonUtil.getJsonMapper().getTypeFactory().constructCollectionType(List.class, cls);
	        List response = (List<?>) JsonUtil.getJsonMapper().readValue(json, typeInfo);
	        return response;
		}
		else if(String.class.equals(cls)) {
			if(json != null && json.startsWith("\"") && json.endsWith("\"") && json.length() > 1) {
				return json.substring(1, json.length() - 2);
			}
	        else { 
	        	return json;
	        }
		}
		else {
			return JsonUtil.getJsonMapper().readValue(json, cls);
		}
	}
}
