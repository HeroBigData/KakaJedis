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
 * @Title: LogUtils.java 
 * @Package xyz.kaka.common.util 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @Company: xyz.hero.cao
 * @author HeroCao herobigdatacao@126.com  
 * @date 2016年8月8日 下午1:57:19 
 * @version V1.0   
 */    
package xyz.kaka.bigdata.parent.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @ClassName: LogUtils 
 * @Description: Log工具类 
 * @Company: xyz.hero.cao
 * @author HeroCao herobigdatacao@126.com
 * @date 2016年8月8日 下午1:57:19 
 *  
 */
public final class LogUtils {

	/** 
	 * @Title: getLogger 
	 * @Description: 获取Log4J中的Logger实例对象
	 * @author HeroCao herobigdatacao@126.com
	 * @date 2016年8月8日 下午2:36:17
	 *
	 * @param clazz
	 * @return     
	 */
	@SuppressWarnings("rawtypes")
	public static Logger getLogger(Class clazz) {
		return LoggerFactory.getLogger(clazz);
	}

	/*
	 * logger.trace("这是一条从TestServlet产生的fatal信息！");
logger.error("这是一条从TestServlet产生的error信息！");
logger.warn("这是一条从TestServlet产生的warn信息！");
logger.debug("这是一条从TestServlet产生的debug信息！");
logger.info("这是一条从TestServlet产生的info信息！"); 
	 */

	public static void trace(Logger logger, String message) {
		try {
			if (null != logger && StringUtils.isNoneBlank(message)) {
				logger.trace(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void trace(Logger logger, Throwable t) {
		try {
			if (null != logger && null != t) {
				logger.trace(ExceptionUtils.getStackTrace(t));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void error(Logger logger, String message) {
		try {
			if (null != logger && StringUtils.isNoneBlank(message)) {
				logger.error(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void error(Logger logger, Throwable t) {
		try {
			if (null != logger && null != t) {
				logger.error(ExceptionUtils.getStackTrace(t));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void warn(Logger logger, String message) {
		try {
			if (null != logger && StringUtils.isNoneBlank(message)) {
				logger.warn(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void warn(Logger logger, Throwable t) {
		try {
			if (null != logger && null != t) {
				logger.warn(ExceptionUtils.getStackTrace(t));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void debug(Logger logger, String message) {
		try {
			if (null != logger && StringUtils.isNoneBlank(message)) {
				logger.debug(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void debug(Logger logger, Throwable t) {
		try {
			if (null != logger && null != t) {
				logger.debug(ExceptionUtils.getStackTrace(t));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void info(Logger logger, String message) {
		try {
			if (null != logger && StringUtils.isNoneBlank(message)) {
				logger.info(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void info(Logger logger, Throwable t) {
		try {
			if (null != logger && null != t) {
				logger.info(ExceptionUtils.getStackTrace(t));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
