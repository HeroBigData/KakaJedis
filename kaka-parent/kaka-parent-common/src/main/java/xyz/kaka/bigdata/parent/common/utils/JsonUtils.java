package xyz.kaka.bigdata.parent.common.utils;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.core.serializer.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/** 
 * @ClassName: JsonUtils 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @Company: kaka.bigdata
 * @author HeroCao herobigdatacao@gmail.com
 * @date 2016年10月3日 下午10:18:25 
 *  
 */
public class JsonUtils {

	private static final Logger LOGGER = LogUtils.getLogger(JsonUtils.class);

	private JsonUtils() { }

	/** 
	 * @Fields MAPPER : 定义jackson对象 
	 */
	private static final ObjectMapper MAPPER = new ObjectMapper();

	/** 
	 * @Title: objectToJson 
	 * @Description: 将Pojo实例对象转换成Json字符串
	 * @author HeroCao herobigdatacao@gmail.com
	 * @date 2016年10月3日 下午10:20:02
	 *
	 * @param pojo
	 * @return     
	 */
	public static String objectToJson(Object pojo) {
		try {
			return MAPPER.writeValueAsString(pojo);
		} catch (JsonProcessingException e) {
			LogUtils.error(LOGGER, e);
		}
		return null;
	}

	/** 
	* @Title: jsonToPojo 
	* @Description: 将Json结果集转化为Pojo实例对象
	* @author HeroCao herobigdatacao@gmail.com
	* @date 2016年10月3日 下午10:21:08
	*
	* @param json Json字符串 
	* @param clazz 目标Pojo的Class，例如：Item.class
	* @return     
	*/
	public static <T> T jsonToPojo(String json, Class<T> clazz) {
		try {
			T t = MAPPER.readValue(json, clazz);
			return t;
		} catch (Exception e) {
			LogUtils.error(LOGGER, e);
		}
		return null;
	}

	/** 
	* @Title: jsonToList 
	* @Description: 将Json数据转换成Pojo实例对象的List
	* @author HeroCao herobigdatacao@gmail.com
	* @date 2016年10月3日 下午10:24:36
	*
	* @param json Json字符串
	* @param clazz 目标Pojo的Class，例如：Item.class
	* @return     
	*/
	public static <T> List<T> jsonToList(String json, Class<T> clazz) {
		JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, clazz);
		try {
			return MAPPER.readValue(json, javaType);
		} catch (Exception e) {
			LogUtils.error(LOGGER, e);
		}
		return null;
	}
	
	/** 
	* @Title: fromJson 
	* @Description: 使用Gson将Json转换成Pojo实例对象
	* @author HeroCao herobigdatacao@gmail.com
	* @date 2016年10月3日 下午10:55:44
	*
	* @param json Json字符串
	* @param classOfT 目标Pojo的Class
	* @return     
	*/
	@SuppressWarnings("rawtypes")
	public static <T extends Serializer> T fromJson(String json, Class<T> classOfT) {
		return new Gson().fromJson(json, classOfT);
	}
	
	/** 
	* @Title: toJson 
	* @Description: 使用Gson将Pojo实例对象转换成Json字符串
	* @author HeroCao herobigdatacao@gmail.com
	* @date 2016年10月3日 下午11:00:44
	*
	* @param serializer 实现Serializer接口的Pojo实例对象
	* @return     
	*/
	@SuppressWarnings("rawtypes")
	public static String toJson(Serializer serializer) {
		return new Gson().toJson(serializer);
	}

}
