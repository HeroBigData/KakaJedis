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
 * @Title: SimpleLogAnalyzer.java 
 * @Package xyz.kaka.common.log.analyze.impl 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @Company: xyz.hero.cao
 * @author HeroCao herobigdatacao@126.com  
 * @date 2016年8月8日 上午11:04:23 
 * @version V1.0   
 */    
package xyz.kaka.bigdata.parent.common.log.analyze.impl;

import org.slf4j.Logger;

import xyz.kaka.bigdata.parent.common.log.analyze.LogAnalyzer;
import xyz.kaka.bigdata.parent.common.utils.LogUtils;

/** 
 * @ClassName: SimpleLogAnalyzer 
 * @Description: 简单的日志分析器 
 * @Company: xyz.hero.cao
 * @author HeroCao herobigdatacao@126.com
 * @date 2016年8月8日 上午11:04:23 
 *  
 */
public class SimpleLogAnalyzer implements LogAnalyzer {
	
	private final Logger LOGGER = LogUtils.getLogger(getClass());
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param ex
	 * @param parameter 
	 * @see xyz.kaka.common.log.analyze.LogAnalyzer#analyze(java.lang.Exception, java.lang.String) 
	 */
	@Override
	public void analyze(Exception ex, String parameter) {
		// 建议使用 try-catch 捕获异常
		try {
			// 测试，方便就使用
			System.out.println("分析日志=======" + org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(ex));
			System.out.println("分析日志====" + parameter);

			// TODO：根据自己的业务逻辑实现
			
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO:
			LogUtils.error(LOGGER, e);
		}
	}

}
