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
* @Title: TTJedisClient.java 
* @Package xyz.kaka.common.redis.jedis 
* @Description: 封装Jedis单机版和集群版接口
* @Company: kaka.bigdata
* @author HeroCao herobigdatacao@126.com  
* @date 2016年8月7日 上午11:58:18 
* @version V1.0   
*/    
package xyz.kaka.bigdata.redis.clients.jedis;

import redis.clients.jedis.JedisCommands;

/** 
* @ClassName: KakaJedis 
* @Description: 封装Jedis单机版和集群版接口
* @Company: kaka.bigdata
* @author HeroCao herobigdatacao@126.com
* @date 2016年8月7日 上午11:58:18 
*  
*/
public interface KakaJedis extends JedisCommands {
	
}
