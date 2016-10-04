/**   
 * Copyright (c) 2009-2016 Founder Ltd. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 *   Email: herobigdatacao@gmail.com
 */

/**   
 * @Title: LogAnalyzerUtils.java 
 * @Package xyz.kaka.common.util 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @Company: xyz.hero.cao
 * @author HeroCao herobigdatacao@126.com  
 * @date 2016年8月8日 上午11:16:34 
 * @version V1.0   
 */    
package xyz.kaka.bigdata.parent.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;

import redis.clients.jedis.SortingParams;
import redis.clients.jedis.BinaryClient.LIST_POSITION;

/** 
 * @ClassName: LogAnalyzerUtils 
 * @Description: 日历分析工具类
 * @Company: xyz.hero.cao
 * @author HeroCao herobigdatacao@126.com
 * @date 2016年8月8日 上午11:16:34 
 *  
 */
public class LogAnalyzerUtils {

	/** 
	 * @Fields PARAMETER_SEPARATOR : 参数分隔符
	 */
	public static final String PARAMETER_SEPARATOR = "|";

	/** 
	 * @Fields MAP_PARAMETER_SEPARATOR : Map参数之间分隔符
	 */
	public static final String MAP_PARAMETER_SEPARATOR = "||";

	/** 
	 * @Fields MAP_KEY_VALUE_PARAMETER_SEPARATOR : Map元素中Key和Value的分隔符号 
	 */
	public static final String MAP_KEY_VALUE_PARAMETER_SEPARATOR = "@";

	
	private LogAnalyzerUtils() { }
	
	private static final Logger LOGGER = LogUtils.getLogger(LogAnalyzerUtils.class);

	/** 
	 * @Title: getParameter 
	 * @Description: 获取参数
	 * @author HeroCao herobigdatacao@126.com
	 * @date 2016年8月8日 上午11:26:54
	 *
	 * @param params
	 * @return     
	 */
	public static String getParameter(String... params) {
		try {
			if (null != params && params.length > 0) {
				StringBuilder sb = new StringBuilder();
				for (String param : params) {
					sb.append(param);
					sb.append(LogAnalyzerUtils.PARAMETER_SEPARATOR);
				}
				String str = sb.toString();
				return str = str.substring(0, str.length() - 2);
			}
		} catch (Exception e) {
			// 使用 Logger
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
		return null;
	}
	
	// 应该转换成 Json
	
	/** 
	* @Title: getHashMap 
	* @Description: 获取HashMap实例对象
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午3:34:26
	*
	* @return     
	*/
	public static HashMap<String, String> getHashMap() {
		return new HashMap<String, String>();
	}
	
//	/** 
//	* @Title: getMap 
//	* @Description: 得到日志分析器Map
//	* @author HeroCao herobigdatacao@126.com
//	* @date 2016年8月8日 下午3:09:38
//	*
//	* @param key
//	* @param value
//	* @return     
//	*/
//	public static Map<String, String> getLogAnalyzerMap(String key, String value) {
//		try {
//			Map<String, String> map = new HashMap<String, String>();
//			map.put("key", key);
//			map.put("value", value);
//			return map;
//		} catch (Exception e) {
//			LogUtils.error(LOGGER, e);
//			return null;
//		}
//	}
	
//	/** 
//	* @Title: getLogAnalyzerJson 
//	* @Description: 得到日志分析器Json
//	* @author HeroCao herobigdatacao@126.com
//	* @date 2016年8月8日 下午3:18:01
//	*
//	* @param key
//	* @param value
//	* @return     
//	*/
//	public static String getLogAnalyzerJson(String key, String value) {
//		try {
//			Map<String, String> map = getLogAnalyzerMap(key, value);
//			return JsonUtils.objectToJson(map);
//		} catch (Exception e) {
//			LogUtils.error(LOGGER, e);
//			return null;
//		}
//	}
	
	/** 
	* @Title: getLogAnalyzerMap 
	* @Description:  得到日志分析器Map
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午3:30:02
	*
	* @param key
	* @param value
	* @param nxxx
	* @param expx
	* @param time
	* @return     
	*/
	public static Map<String, String> getLogAnalyzerMap(String key, String value, String nxxx, String expx, long time) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("key", key);
			map.put("value", value);
			map.put("nxxx", nxxx);
			map.put("expx", expx);
			map.put("time", time +"");
			return map;
		} catch (Exception e) {
			LogUtils.error(LOGGER, e);
			return null;
		}
	}
	
	/** 
	* @Title: getLogAnalyzerJson 
	* @Description:  得到日志分析器Json
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午3:30:21
	*
	* @param key
	* @param value
	* @param nxxx
	* @param expx
	* @param time
	* @return     
	*/
	public static String getLogAnalyzerJson(String key, String value, String nxxx, String expx, long time) {
		try {
			return JsonUtils.objectToJson(getLogAnalyzerMap(key, value, nxxx, expx, time));
		} catch (Exception e) {
			LogUtils.error(LOGGER, e);
			return null;
		}
	}
	
	/** 
	* @Title: getLogAnalyzerMap 
	* @Description: 得到日志分析器Map 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午3:38:24
	*
	* @param key
	* @return     
	*/
	public static Map<String, String> getLogAnalyzerMap(String key) {
		try {
			HashMap<String,String> map = getHashMap();
			map.put("key", key);
			return map;
		} catch (Exception e) {
			LogUtils.error(LOGGER, e);
			return null;
		}
	}
	
	/** 
	* @Title: getLogAnalyzerJson 
	* @Description: 得到日志分析器Json
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午3:38:21
	*
	* @param key
	* @return     
	*/
	public static String getLogAnalyzerJson(String key) { 
		try {
			 return JsonUtils.objectToJson(getLogAnalyzerMap(key));
		} catch (Exception e) {
			LogUtils.error(LOGGER, e);
			return null;
		}
	}
	
	/** 
	* @Title: getLogAnalyzerMap 
	* @Description: 得到日志分析器Map
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午4:39:36
	*
	* @param key
	* @param seconds
	* @return     
	*/
	public static Map<String, String> getLogAnalyzerMap(String key, int seconds) {
		HashMap<String,String> map = getHashMap();
		map.put("key", key);
		map.put("seconds", seconds +"");
		return map;
	}
	
	/** 
	* @Title: getLogAnalyzerJson 
	* @Description: 得到日志分析器Json
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午4:39:40
	*
	* @param key
	* @param seconds
	* @return     
	*/
	public static String getLogAnalyzerJson(String key, int seconds) {
		try {
			return JsonUtils.objectToJson(getLogAnalyzerMap(key, seconds));
		} catch (Exception e) {
			LogUtils.error(LOGGER, e);
			return null;
		}
	}
	
	/** 
	 * @Title: getLogAnalyzerMap 
	 * @Description: 得到日志分析器Map
	 * @author HeroCao herobigdatacao@126.com
	 * @date 2016年8月8日 下午4:39:36
	 *
	 * @param key
	 * @param seconds
	 * @return     
	 */
	public static Map<String, String> getLogAnalyzerMap(String key, long milliseconds) {
		HashMap<String,String> map = getHashMap();
		map.put("key", key);
		map.put("milliseconds", milliseconds +"");
		return map;
	}
	
	/** 
	 * @Title: getLogAnalyzerJson 
	 * @Description: 得到日志分析器Json
	 * @author HeroCao herobigdatacao@126.com
	 * @date 2016年8月8日 下午4:39:40
	 *
	 * @param key
	 * @param seconds
	 * @return     
	 */
	public static String getLogAnalyzerJson(String key, long milliseconds) {
		try {
			return JsonUtils.objectToJson(getLogAnalyzerMap(key, milliseconds));
		} catch (Exception e) {
			LogUtils.error(LOGGER, e);
			return null;
		}
	}
	

	/** 
	 * @Title: getLogAnalyzerMap 
	 * @Description: 得到日志分析器Map
	 * @author HeroCao herobigdatacao@126.com
	 * @date 2016年8月8日 下午4:39:36
	 *
	 * @param key
	 * @param seconds
	 * @return     
	 */
	public static Map<String, String> getLogAnalyzerMapByUnixTime(String key, long unixTime) {
		HashMap<String,String> map = getHashMap();
		map.put("key", key);
		map.put("unixTime", unixTime +"");
		return map;
	}
	
	/** 
	 * @Title: getLogAnalyzerJson 
	 * @Description: 得到日志分析器Json
	 * @author HeroCao herobigdatacao@126.com
	 * @date 2016年8月8日 下午4:39:40
	 *
	 * @param key
	 * @param seconds
	 * @return     
	 */
	public static String getLogAnalyzerJsonByUnixTime(String key, long unixTime) {
		try {
			return JsonUtils.objectToJson(getLogAnalyzerMapByUnixTime(key, unixTime));
		} catch (Exception e) {
			LogUtils.error(LOGGER, e);
			return null;
		}
	}
	
	/** 
	* @Title: getLogAnalyzerMap 
	* @Description: 得到日志分析器Map 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午5:42:12
	*
	* @param value
	* @param key2
	* @param value2
	* @return     
	*/
	public static Map<String, String> getLogAnalyzerMapByArray(String value, String key2, String... value2) {
		try {
			HashMap<String, String> map = getHashMap();
			if (StringUtils.isNotEmpty(value)) {
				map.put("key", value);
			}
			if (StringUtils.isNotEmpty(key2) && null != value2 && value2.length > 0) {
				String json = JsonUtils.objectToJson(value2);
				map.put(key2, json);
			}
			return map;
		} catch (Exception e) {
			LogUtils.error(LOGGER, e);
			return null;
		}
	}
	
	/** 
	* @Title: getLogAnalyzerJson 
	* @Description: 得到日志分析器Json
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午5:42:32
	*
	* @param value
	* @param key2
	* @param value2
	* @return     
	*/
	public static String getLogAnalyzerJsonByArray(String value, String key2, String... value2) {
		try {
			return JsonUtils.objectToJson(getLogAnalyzerMapByArray(value, key2, value2));
		} catch (Exception e) {
			LogUtils.error(LOGGER, e);
			return null;
		}
	}
	
	/** 
	* @Title: getLogAnalyzerMap 
	* @Description: 得到日志分析器Map 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午7:42:54
	*
	* @param key
	* @param value
	* @param key2
	* @param scoreMembers
	* @return     
	*/
	public static Map<String, String> getLogAnalyzerMap(String key, String value, String key2, Map<String, Double> scoreMembers) {
		try {
			HashMap<String, String> map = getHashMap();
			if (StringUtils.isNotEmpty(value)) {
				map.put(key, value);
			}
			if (StringUtils.isNotEmpty(key2) && null != scoreMembers && !scoreMembers.isEmpty()) {
				String json = JsonUtils.objectToJson(scoreMembers);
				map.put(key2, json);
			}
			return map;
		} catch (Exception e) {
			LogUtils.error(LOGGER, e);
			return null;
		}
	}
	
	/** 
	* @Title: getLogAnalyzerJson 
	* @Description: 得到日志分析器Json
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午7:44:48
	*
	* @param key
	* @param value
	* @param key2
	* @param scoreMembers
	* @return     
	*/
	public static String getLogAnalyzerJson(String key, String value, String key2, Map<String, Double> scoreMembers) {
		try {
			return JsonUtils.objectToJson(getLogAnalyzerMap(key, value, key2, scoreMembers));
		} catch (Exception e) {
			LogUtils.error(LOGGER, e);
			return null;
		}
	}
	
	/** 
	* @Title: getLogAnalyzerMap 
	* @Description:  得到日志分析器Map
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午5:34:14
	*
	* @param value
	* @param key2
	* @param hash
	* @return     
	*/
	public static Map<String, String> getLogAnalyzerMap(String value, String key2, Map<String, String> hash) {
		try {
			HashMap<String, String> map = getHashMap();
			if (StringUtils.isNotEmpty(value)) {
				map.put("key", value);
			}
			if (StringUtils.isNotEmpty(key2) && null != hash && !hash.isEmpty()) {
				String json = JsonUtils.objectToJson(hash);
				map.put(key2, json);
			}
			return map;
		} catch (Exception e) {
			LogUtils.error(LOGGER, e);
			return null;
		}
	}

	/** 
	* @Title: getLogAnalyzerJson 
	* @Description:  得到日志分析器Map
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午5:38:57
	*
	* @param value
	* @param key2
	* @param hash
	* @return     
	*/
	public static String getLogAnalyzerJson(String value, String key2, Map<String, String> hash) {
		try {
			return JsonUtils.objectToJson(getLogAnalyzerMap(value, key2, hash));
		} catch (Exception e) {
			LogUtils.error(LOGGER, e);
			return null;
		}
	}
	
	/** 
	* @Title: getLogAnalyzerJson 
	* @Description: 得到日志分析器Json
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午7:43:01
	*
	* @param value
	* @param key2
	* @param value2
	* @return     
	*/
	public static String getLogAnalyzerJson(String value, String key2, String value2) {
		try {
			return getLogAnalyzerJson("key", value, key2, value2);
		} catch (Exception e) {
			LogUtils.error(LOGGER, e);
			return null;
		}
	}
	/** 
	* @Title: getLogAnalyzerJsonByOthers 
	* @Description: 得到日志分析器Json
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午7:39:11
	*
	* @param otherKey1
	* @param otherValue1
	* @return     
	*/
	public static String getLogAnalyzerJsonByOthers(String otherKey1, String otherValue1) {
		try {
			return JsonUtils.objectToJson(getLogAnalyzerMap(otherKey1, otherValue1, null, null, null, null, null, null, null, null));
		} catch (Exception e) {
			LogUtils.error(LOGGER, e);
			return null;
		}
	}
	
	/** 
	 * @Title: getLogAnalyzerJson 
	 * @Description: 得到日志分析器Json
	 * @author HeroCao herobigdatacao@126.com
	 * @date 2016年8月8日 下午4:39:40
	 *
	 * @param key
	 * @param value
	 * @return     
	 */
	public static String getLogAnalyzerJson(String key, String value) {
		try {
			return JsonUtils.objectToJson(getLogAnalyzerMap("key", key, "value", value, null, null, null, null, null, null));
		} catch (Exception e) {
			LogUtils.error(LOGGER, e);
			return null;
		}
	}
	
	/** 
	 * @Title: getLogAnalyzerJson 
	 * @Description: 得到日志分析器Json
	 * @author HeroCao herobigdatacao@126.com
	 * @date 2016年8月8日 下午4:39:40
	 *
	 * @param key
	 * @param seconds
	 * @return     
	 */
	public static String getLogAnalyzerJson(String key1, String value1, String key2, String value2) {
		try {
			return JsonUtils.objectToJson(getLogAnalyzerMap(key1, value1, key2, value2, null, null, null, null, null, null));
		} catch (Exception e) {
			LogUtils.error(LOGGER, e);
			return null;
		}
	}
	
	/** 
	* @Title: getLogAnalyzerJson 
	* @Description: 得到日志分析器Json
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午8:31:08
	*
	* @param key1
	* @param value1
	* @param key2
	* @param value2
	* @param key3
	* @param value3
	* @return     
	*/
	public static String getLogAnalyzerJson(String key1, String value1, String key2, String value2, String key3, String value3) {
		try {
			return JsonUtils.objectToJson(getLogAnalyzerMap(key1, value1, key2, value2, key3, value3, null, null, null, null));
		} catch (Exception e) {
			LogUtils.error(LOGGER, e);
			return null;
		}
	}
	
	/** 
	* @Title: getLogAnalyzerJson 
	* @Description: 得到日志分析器Json
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午8:31:26
	*
	* @param key1
	* @param value1
	* @param key2
	* @param value2
	* @param key3
	* @param value3
	* @param key4
	* @param value4
	* @return     
	*/
	public static String getLogAnalyzerJson(String key1, String value1, String key2, String value2, String key3, String value3, String key4, String value4) {
		try {
			return JsonUtils.objectToJson(getLogAnalyzerMap(key1, value1, key2, value2, key3, value3, key4, value4, null, null));
		} catch (Exception e) {
			LogUtils.error(LOGGER, e);
			return null;
		}
	}
	
	/** 
	* @Title: getLogAnalyzerMap 
	* @Description: 得到日志分析器Map 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午8:31:45
	*
	* @param key1
	* @param value1
	* @param key2
	* @param value2
	* @param key3
	* @param value3
	* @param key4
	* @param value4
	* @param key5
	* @param value5
	* @return     
	*/
	public static Map<String, String> getLogAnalyzerMap(String key1, String value1, String key2, String value2, String key3, String value3, String key4, String value4, String key5, String value5) {
		HashMap<String,String> map = getHashMap();
		if (StringUtils.isNotEmpty(key1) && StringUtils.isNotEmpty(value1)) {
			map.put(key1, value1);
		}
		if (StringUtils.isNotEmpty(key2) && StringUtils.isNotEmpty(value2)) {
			map.put(key2, value2);
		}
		if (StringUtils.isNotEmpty(key3) && StringUtils.isNotEmpty(value3)) {
			map.put(key3, value3);
		}
		if (StringUtils.isNotEmpty(key4) && StringUtils.isNotEmpty(value4)) {
			map.put(key4, value4);
		}
		if (StringUtils.isNotEmpty(key5) && StringUtils.isNotEmpty(value5)) {
			map.put(key5, value5);
		}
		return map;
	}
	
	/** 
	* @Title: getLogAnalyzerJson 
	* @Description: 得到日志分析器Json 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午8:32:13
	*
	* @param key1
	* @param value1
	* @param key2
	* @param value2
	* @param key3
	* @param value3
	* @param key4
	* @param value4
	* @param key5
	* @param value5
	* @return     
	*/
	public static String getLogAnalyzerJson(String key1, String value1, String key2, String value2, String key3, String value3, String key4, String value4, String key5, String value5) {
		try {
			return JsonUtils.objectToJson(getLogAnalyzerMap(key1, value1, key2, value2, key3, value3, key4, value4, key5, value5));
		} catch (Exception e) {
			LogUtils.error(LOGGER, e);
			return null;
		}
	}
	
	/** 
	* @Title: getLogAnalyzerJson 
	* @Description: 得到日志分析器Json
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午8:19:35
	*
	* @param key
	* @param sortingParameters
	* @return     
	*/
	public static String getLogAnalyzerJson(String key, SortingParams sortingParameters) {
		try {
			return JsonUtils.objectToJson(getLogAnalyzerJson("key", key, "sortingParameters", JsonUtils.objectToJson(sortingParameters)));
		} catch (Exception e) {
			LogUtils.error(LOGGER, e);
			return null;
		}
	}
	
	/** 
	* @Title: getLogAnalyzerJson 
	* @Description: 得到日志分析器Json
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午9:08:19
	*
	* @param key
	* @param where
	* @param pivot
	* @param value
	* @return     
	*/
	public static String getLogAnalyzerJson(String key, LIST_POSITION where, String pivot, String value) {
		try {
			return JsonUtils.objectToJson(getLogAnalyzerJson("key", key, "where", JsonUtils.objectToJson(where), "pivot", pivot, "value", value));
		} catch (Exception e) {
			LogUtils.error(LOGGER, e);
			return null;
		}
	}
	
}
