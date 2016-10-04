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
* @Title: AnalyzeLog.java 
* @Package xyz.kaka.common.log.analyze 
* @Description: TODO(用一句话描述该文件做什么) 
* @Company: kaka.bigdata
* @author HeroCao herobigdatacao@126.com  
* @date 2016年8月8日 上午10:54:10 
* @version V1.0   
*/    
package xyz.kaka.bigdata.parent.common.log.analyze;    
    
/** 
* @ClassName: LogAnalyzer 
* @Description: 日志分析器
* @Company: kaka.bigdata
* @author HeroCao herobigdatacao@126.com
* @date 2016年8月8日 上午10:54:10 
*  
*/
public interface LogAnalyzer {

	/** 
	* @Title: analyze 
	* @Description: 分析日志 ，说明：实现这个方法，在当前方法内部建议使用try-catch捕获异常
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午9:54:18
	*
	* @param ex 异常
	* @param parameter  参数，以Json字符串的方式传递   
	*/
	void analyze(Exception ex, String parameter);
}
