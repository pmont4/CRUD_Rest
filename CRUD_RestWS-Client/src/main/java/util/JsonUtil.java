package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AccessLevel;
import lombok.Getter;

public class JsonUtil {
    
    @Getter(AccessLevel.PRIVATE)
    private static final ObjectMapper mapper;
    
    private static JsonUtil instance;
    
    public static JsonUtil getInstance() {
        if (instance == null) {
            instance = new JsonUtil();
        }
        return instance;
    }
    
    private JsonUtil() {
        super();
    }
    
    static {
        mapper = new ObjectMapper()
                .configure(SerializationFeature.INDENT_OUTPUT, true);
        
    }
    
    public <T> String getJsonString(T object) throws JsonProcessingException {
        return getMapper().writeValueAsString(object);
    }
    
    public <T> T getFromJson(String json, TypeReference<T> type) throws JsonProcessingException {
        return getMapper().readValue(json, type);
    }
    
}
