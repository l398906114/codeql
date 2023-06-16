package org.com.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @类描述：
 * @项目名称：jeecg-boot-module-system
 * @包名： org.jeecg.modules.util
 * @类名称：JsonUtil	
 * @创建人： 刘凯
 * @创建时间：2020年11月18日下午2:05:41	
 * @修改人：刘凯
 * @修改时间：2020年11月18日下午2:05:41	
 * @修改备注：
 * @version v1.0
 */
public final class JsonUtil {

	private static ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * Json字符串==>ListMap
	 * @param jsonStr json字符串
	 * @return List集合
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String,Object>> toList(String jsonStr){
		try {
			return objectMapper.readValue(jsonStr, List.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Json字符串==>List
	 * @param jsonStr json字符串
	 * @return List集合
	 */
	@SuppressWarnings("unchecked")
	public static List<String> toListStr(String jsonStr){
		try {
			return objectMapper.readValue(jsonStr, List.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Json字符串==>Map
	 * @param jsonStr json字符串
	 * @return Map集合
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> toMap(String jsonStr){
		try {
			return objectMapper.readValue(jsonStr, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/** 
     * Json字符串==>实体对象 
     * @param json 
     * @param clazz 实体对象所属类Class 
     * @return 
     */  
    public static <T> T toEntity(String json, Class<T> clazz){  
        try {  
        	objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            return objectMapper.readValue(json, clazz);  
        } catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }  
	
	/**
	 * 
	 * @方法名: toJson   
	 * @方法描述: 对象==>Json 
	 * @param: @param object
	 * @return: String      
	 * @throws
	 */
	public static String toJson(Object object){
		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String  DateFormat(Date t) {
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date=sdf.format(t);
		return date;
		
		
		
		
	}
	
}
