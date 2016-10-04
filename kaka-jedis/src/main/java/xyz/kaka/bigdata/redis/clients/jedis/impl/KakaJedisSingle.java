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
 * @Title: KakaJedisSingle.java 
 * @Package xyz.kaka.common.redis.clients.jedis.impl 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @Company: kaka.bigdata
 * @author HeroCao herobigdatacao@126.com  
 * @date 2016年8月7日 下午12:03:38 
 * @version V1.0   
 */    
package xyz.kaka.bigdata.redis.clients.jedis.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.BitPosParams;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.params.geo.GeoRadiusParam;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;
import xyz.kaka.bigdata.parent.common.log.analyze.LogAnalyzer;
import xyz.kaka.bigdata.parent.common.utils.LogAnalyzerUtils;
import xyz.kaka.bigdata.redis.clients.jedis.KakaJedis;

/** 
 * @ClassName: KakaJedisSingle 
 * @Description: 单机版本Jedis
 * @Company: kaka.bigdata
 * @author HeroCao herobigdatacao@126.com
 * @date 2016年8月7日 下午12:03:38 
 *  
 */
public class KakaJedisSingle implements KakaJedis {

	@Autowired // Spring 框架 @ Autowrited 注解
	private JedisPool jedisPool;
	
	/** 
	* @Fields logAnalyzer : 日志分析器
	*/
	private LogAnalyzer logAnalyzer;
	
	public KakaJedisSingle() {
		super();
	}
	
	public KakaJedisSingle(LogAnalyzer logAnalyzer) {
		super();
		this.logAnalyzer = logAnalyzer;
	}
	
	/** 
	 * @Title: getJedis 
	 * @Description: 获取Jedis实例对象
	 * @author HeroCao herobigdatacao@126.com
	 * @date 2016年8月7日 下午12:13:49
	 *
	 * @return     
	 */
	private Jedis getJedis() {
		Jedis jedis = jedisPool.getResource();
		return jedis;
	}

	/** 
	* @Title: close 
	* @Description: 关闭连接
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月7日 下午12:17:39
	*
	* @param jedis     
	*/
	private void close(Jedis jedis) {
		// 关闭当前Jedis连接，因为每一次都获取
		jedis.close();

		// 连接池是否需要关闭，因为使用Spring，bean 是单例的（默认），因为不需要关闭！！！
//		jedisPool.close();
	}
	
	/** 
	* @Title: tt_set 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午9:45:24
	*
	* @param key
	* @param value
	* @return     
	*/
	private String tt_set(String key, String value) {
		Jedis jedis = this.getJedis();
		String result = jedis.set(key, value);
		this.close(jedis);
		return result;
	}

	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param value
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#set(java.lang.String, java.lang.String) 
	 */
	@Override
	public String set(String key, String value) {
		if (null == this.logAnalyzer) {
			return tt_set(key, value);
		}
		try {
			return tt_set(key, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, value));
			return null;
		}
	}
	
	/** 
	* @Title: tt_set 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午9:48:16
	*
	* @param key
	* @param value
	* @param nxxx
	* @param expx
	* @param time
	* @return     
	*/
	private String tt_set(String key, String value, String nxxx, String expx, long time) {
		Jedis jedis = this.getJedis();
		String result = jedis.set(key, value, nxxx, expx, time);
		this.close(jedis);
		return result;
	}

	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param value
	 * @param nxxx
	 * @param expx
	 * @param time
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#set(java.lang.String, java.lang.String, java.lang.String, java.lang.String, long) 
	 */
	@Override
	public String set(String key, String value, String nxxx, String expx, long time) {
		if (null == this.logAnalyzer) {
			return tt_set(key, value, nxxx, expx, time);
		}
		try {
			return tt_set(key, value, nxxx, expx, time);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "value", value, "nxxx", nxxx, "expx", expx, "time", time +""));
			return null;
		}
	}

	/** 
	* @Title: tt_get 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午11:26:37
	*
	* @param key
	* @return     
	*/
	private String tt_get(String key) {
		Jedis jedis = this.getJedis();
		String result = jedis.get(key);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#get(java.lang.String) 
	 */
	@Override
	public String get(String key) {
		if (null == this.logAnalyzer) {
			return tt_get(key);
		}
		try {
			return tt_get(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
	}

	/** 
	* @Title: tt_exists 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午11:29:33
	*
	* @param key
	* @return     
	*/
	private Boolean tt_exists(String key) {
		Jedis jedis = this.getJedis();
		Boolean result = jedis.exists(key);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#exists(java.lang.String) 
	 */
	@Override
	public Boolean exists(String key) {
		if (null == this.logAnalyzer) {
			return tt_exists(key);
		}
		try {
			return tt_exists(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
	}
	
	/** 
	* @Title: tt_persist 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午11:31:52
	*
	* @param key
	* @return     
	*/
	private Long tt_persist(String key) {
		Jedis jedis = this.getJedis();
		Long result = jedis.persist(key);
		this.close(jedis);
		return result;
	}

	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#persist(java.lang.String) 
	 */
	@Override
	public Long persist(String key) {
		if (null == this.logAnalyzer) {
			return tt_persist(key);
		}
		try {
			return tt_persist(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
	}

	/** 
	* @Title: tt_type 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午11:34:49
	*
	* @param key
	* @return     
	*/
	private String tt_type(String key) {
		Jedis jedis = this.getJedis();
		String result = jedis.type(key);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#type(java.lang.String) 
	 */
	@Override
	public String type(String key) {
		if (null == this.logAnalyzer) {
			return tt_type(key);
		}
		try {
			return tt_type(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
	}

	/** 
	* @Title: tt_expire 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午11:39:23
	*
	* @param key
	* @param seconds
	* @return     
	*/
	private Long tt_expire(String key, int seconds) {
		Jedis jedis = this.getJedis();
		Long result = jedis.expire(key, seconds);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param seconds
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#expire(java.lang.String, int) 
	 */
	@Override
	public Long expire(String key, int seconds) {
		if (null == this.logAnalyzer) {
			return tt_expire(key, seconds);
		}
		try {
			return tt_expire(key, seconds);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, seconds));
			return null;
		}
	}

	/** 
	* @Title: tt_pexpire 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午11:40:02
	*
	* @param key
	* @param milliseconds
	* @return     
	*/
	private Long tt_pexpire(String key, long milliseconds) {
		Jedis jedis = this.getJedis();
		Long result = jedis.pexpire(key, milliseconds);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param milliseconds
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#pexpire(java.lang.String, long) 
	 */
	@Override
	public Long pexpire(String key, long milliseconds) {
		if (null == this.logAnalyzer) {
			return tt_pexpire(key, milliseconds);
		}
		try {
			return tt_pexpire(key, milliseconds);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, milliseconds));
			return null;
		}
	}

	/** 
	* @Title: tt_expireAt 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午11:42:25
	*
	* @param key
	* @param unixTime
	* @return     
	*/
	private Long tt_expireAt(String key, long unixTime) {
		Jedis jedis = this.getJedis();
		Long result = jedis.expireAt(key, unixTime);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param unixTime
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#expireAt(java.lang.String, long) 
	 */
	@Override
	public Long expireAt(String key, long unixTime) {
		if (null == this.logAnalyzer) {
			return tt_expireAt(key, unixTime);
		}
		try {
			return tt_expireAt(key, unixTime);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "unixTime", unixTime +""));
			return null;
		}
	}

	/** 
	* @Title: tt_pexpireAt 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午11:45:56
	*
	* @param key
	* @param millisecondsTimestamp
	* @return     
	*/
	private Long tt_pexpireAt(String key, long millisecondsTimestamp) {
		Jedis jedis = this.getJedis();
		Long result = jedis.pexpireAt(key, millisecondsTimestamp);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param millisecondsTimestamp
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#pexpireAt(java.lang.String, long) 
	 */
	@Override
	public Long pexpireAt(String key, long millisecondsTimestamp) {
		if (null == this.logAnalyzer) {
			return tt_pexpireAt(key, millisecondsTimestamp);
		}
		try {
			return tt_pexpireAt(key, millisecondsTimestamp);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "millisecondsTimestamp", millisecondsTimestamp +""));
			return null;
		}
	}

	/** 
	* @Title: tt_ttl 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午11:48:45
	*
	* @param key
	* @return     
	*/
	private Long tt_ttl(String key) {
		Jedis jedis = this.getJedis();
		Long result = jedis.ttl(key);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#ttl(java.lang.String) 
	 */
	@Override
	public Long ttl(String key) {
		if (null == this.logAnalyzer) {
			return tt_ttl(key);
		}
		try {
			return tt_ttl(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
	}
	
	/** 
	* @Title: tt_setbit 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午11:50:32
	*
	* @param key
	* @param offset
	* @param value
	* @return     
	*/
	private Boolean tt_setbit(String key, long offset, boolean value) {
		Jedis jedis = this.getJedis();
		Boolean result = jedis.setbit(key, offset, value);
		this.close(jedis);
		return result;
	}

	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param offset
	 * @param value
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#setbit(java.lang.String, long, boolean) 
	 */
	@Override
	public Boolean setbit(String key, long offset, boolean value) {
		if (null == this.logAnalyzer) {
			return tt_setbit(key, offset, value);
		}
		try {
			return tt_setbit(key, offset, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "offset", offset +"", "value", value +""));
			return null;
		}
	}
	
	/** 
	* @Title: ttsetbit 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午11:52:59
	*
	* @param key
	* @param offset
	* @param value
	* @return     
	*/
	private Boolean tt_setbit(String key, long offset, String value) {
		Jedis jedis = this.getJedis();
		Boolean result = jedis.setbit(key, offset, value);
		this.close(jedis);
		return result;
	}

	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param offset
	 * @param value
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#setbit(java.lang.String, long, java.lang.String) 
	 */
	@Override
	public Boolean setbit(String key, long offset, String value) {
		if (null == this.logAnalyzer) {
			return tt_setbit(key, offset, value);
		}
		try {
			return tt_setbit(key, offset, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "offset", offset +"", "value", value));
			return null;
		}
	}

	/** 
	* @Title: tt_getbit 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午11:57:53
	*
	* @param key
	* @param offset
	* @return     
	*/
	private Boolean tt_getbit(String key, long offset) {
		Jedis jedis = this.getJedis();
		Boolean result = jedis.getbit(key, offset);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param offset
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#getbit(java.lang.String, long) 
	 */
	@Override
	public Boolean getbit(String key, long offset) {
		if (null == this.logAnalyzer) {
			return tt_getbit(key, offset);
		}
		try {
			return tt_getbit(key, offset);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "offset", offset +""));
			return null;
		}
	}

	/** 
	* @Title: tt_setrange 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月8日 下午11:59:02
	*
	* @param key
	* @param offset
	* @param value
	* @return     
	*/
	private Long tt_setrange(String key, long offset, String value) {
		Jedis jedis = this.getJedis();
		Long result = jedis.setrange(key, offset, value);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param offset
	 * @param value
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#setrange(java.lang.String, long, java.lang.String) 
	 */
	@Override
	public Long setrange(String key, long offset, String value) {
		if (null == this.logAnalyzer) {
			return tt_setrange(key, offset, value);
		}
		try {
			return tt_setrange(key, offset, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "offset", offset +"", "value", value));
			return null;
		}
	}

	/** 
	* @Title: tt_getrange 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午12:02:02
	*
	* @param key
	* @param startOffset
	* @param endOffset
	* @return     
	*/
	private String tt_getrange(String key, long startOffset, long endOffset) {
		Jedis jedis = this.getJedis();
		String result = jedis.getrange(key, startOffset, endOffset);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param startOffset
	 * @param endOffset
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#getrange(java.lang.String, long, long) 
	 */
	@Override
	public String getrange(String key, long startOffset, long endOffset) {
		if (null == this.logAnalyzer) {
			return tt_getrange(key, startOffset, endOffset);
		}
		try {
			return tt_getrange(key, startOffset, endOffset);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "startOffset", startOffset +"", "endOffset", endOffset +""));
			return null;
		}
	}

	/** 
	* @Title: tt_getSet 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午12:04:32
	*
	* @param key
	* @param value
	* @return     
	*/
	private String tt_getSet(String key, String value) {
		Jedis jedis = this.getJedis();
		String result = jedis.getSet(key, value);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param value
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#getSet(java.lang.String, java.lang.String) 
	 */
	@Override
	public String getSet(String key, String value) {
		if (null == this.logAnalyzer) {
			return tt_getSet(key, value);
		}
		try {
			return tt_getSet(key, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, value));
			return null;
		}
	}

	/** 
	* @Title: tt_setnx 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午12:06:41
	*
	* @param key
	* @param value
	* @return     
	*/
	private Long tt_setnx(String key, String value) {
		Jedis jedis = this.getJedis();
		Long result = jedis.setnx(key, value);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param value
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#setnx(java.lang.String, java.lang.String) 
	 */
	@Override
	public Long setnx(String key, String value) {
		if (null == this.logAnalyzer) {
			return tt_setnx(key, value);
		}
		try {
			return tt_setnx(key, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, value));
			return null;
		}
	}

	/** 
	* @Title: tt_setex 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午12:08:42
	*
	* @param key
	* @param seconds
	* @param value
	* @return     
	*/
	private String tt_setex(String key, int seconds, String value) {
		Jedis jedis = this.getJedis();
		String result = jedis.setex(key, seconds, value);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param seconds
	 * @param value
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#setex(java.lang.String, int, java.lang.String) 
	 */
	@Override
	public String setex(String key, int seconds, String value) {
		if (null == this.logAnalyzer) {
			return tt_setex(key, seconds, value);
		}
		try {
			return tt_setex(key, seconds, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "seconds", seconds +"", "value", value));
			return null;
		}
	}

	/** 
	* @Title: tt_decrBy 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午7:07:01
	*
	* @param key
	* @param integer
	* @return     
	*/
	private Long tt_decrBy(String key, long integer) {
		Jedis jedis = this.getJedis();
		Long result = jedis.decrBy(key, integer);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param integer
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#decrBy(java.lang.String, long) 
	 */
	@Override
	public Long decrBy(String key, long integer) {
		if (null == this.logAnalyzer) {
			return tt_decrBy(key, integer);
		}
		try {
			return tt_decrBy(key, integer);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "integer", integer +""));
			return null;
		}
	}

	/** 
	* @Title: tt_decr 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午7:10:31
	*
	* @param key
	* @return     
	*/
	private Long tt_decr(String key) {
		Jedis jedis = this.getJedis();
		Long result = jedis.decr(key);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#decr(java.lang.String) 
	 */
	@Override
	public Long decr(String key) {
		if (null == this.logAnalyzer) {
			return tt_decr(key);
		}
		try {
			return tt_decr(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
	}

	/** 
	* @Title: tt_incrBy 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午7:13:51
	*
	* @param key
	* @param integer
	* @return     
	*/
	private Long tt_incrBy(String key, long integer) {
		Jedis jedis = this.getJedis();
		Long result = jedis.incrBy(key, integer);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param integer
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#incrBy(java.lang.String, long) 
	 */
	@Override
	public Long incrBy(String key, long integer) {
		if (null == this.logAnalyzer) {
			return tt_incrBy(key, integer);
		}
		try {
			return tt_incrBy(key, integer);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "integer", integer +""));
			return null;
		}
	}

	/** 
	* @Title: tt_incrByFloat 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午7:15:44
	*
	* @param key
	* @param value
	* @return     
	*/
	private Double tt_incrByFloat(String key, double value) {
		Jedis jedis = this.getJedis();
		Double result = jedis.incrByFloat(key, value);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param value
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#incrByFloat(java.lang.String, double) 
	 */
	@Override
	public Double incrByFloat(String key, double value) {
		if (null == this.logAnalyzer) {
			return tt_incrByFloat(key, value);
		}
		try {
			return tt_incrByFloat(key, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, value +""));
			return null;
		}
	}

	/** 
	* @Title: tt_incr 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午7:18:06
	*
	* @param key
	* @return     
	*/
	private Long tt_incr(String key) {
		Jedis jedis = this.getJedis();
		Long result = jedis.incr(key);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#incr(java.lang.String) 
	 */
	@Override
	public Long incr(String key) {
		if (null == this.logAnalyzer) {
			return tt_incr(key);
		}
		try {
			return tt_incr(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
	}

	/** 
	* @Title: tt_append 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午7:20:17
	*
	* @param key
	* @param value
	* @return     
	*/
	private Long tt_append(String key, String value) {
		Jedis jedis = this.getJedis();
		Long result = jedis.append(key, value);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param value
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#append(java.lang.String, java.lang.String) 
	 */
	@Override
	public Long append(String key, String value) {
		if (null == this.logAnalyzer) {
			return tt_append(key, value);
		}
		try {
			return tt_append(key, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, value));
			return null;
		}
	}

	/** 
	* @Title: tt_substr 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午7:22:10
	*
	* @param key
	* @param start
	* @param end
	* @return     
	*/
	private String tt_substr(String key, int start, int end) {
		Jedis jedis = this.getJedis();
		String result = jedis.substr(key, start, end);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param start
	 * @param end
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#substr(java.lang.String, int, int) 
	 */
	@Override
	public String substr(String key, int start, int end) {
		if (null == this.logAnalyzer) {
			return tt_substr(key, start, end);
		}
		try {
			return tt_substr(key, start, end);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "start", start +"", "end", end +""));
			return null;
		}
	}

	/** 
	* @Title: tt_hset 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午7:23:55
	*
	* @param key
	* @param field
	* @param value
	* @return     
	*/
	private Long tt_hset(String key, String field, String value) {
		Jedis jedis = this.getJedis();
		Long result = jedis.hset(key, field, value);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param field
	 * @param value
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#hset(java.lang.String, java.lang.String, java.lang.String) 
	 */
	@Override
	public Long hset(String key, String field, String value) {
		if (null == this.logAnalyzer) {
			return tt_hset(key, field, value);
		}
		try {
			return tt_hset(key, field, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "field", field, "value", value));
			return null;
		}
	}

	/** 
	* @Title: tt_hget 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午7:25:54
	*
	* @param key
	* @param field
	* @return     
	*/
	private String tt_hget(String key, String field) {
		Jedis jedis = this.getJedis();
		String result = jedis.hget(key, field);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param field
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#hget(java.lang.String, java.lang.String) 
	 */
	@Override
	public String hget(String key, String field) {
		if (null == this.logAnalyzer) {
			return tt_hget(key, field);
		}
		try {
			return tt_hget(key, field);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "field", field));
			return null;
		}
	}

	/** 
	* @Title: tt_hsetnx 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午7:28:08
	*
	* @param key
	* @param field
	* @param value
	* @return     
	*/
	private Long tt_hsetnx(String key, String field, String value) {
		Jedis jedis = this.getJedis();
		Long result = jedis.hsetnx(key, field, value);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param field
	 * @param value
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#hsetnx(java.lang.String, java.lang.String, java.lang.String) 
	 */
	@Override
	public Long hsetnx(String key, String field, String value) {
		if (null == this.logAnalyzer) {
			return tt_hsetnx(key, field, value);
		}
		try {
			return tt_hsetnx(key, field, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "field", field, "value", value));
			return null;
		}
	}

	/** 
	* @Title: tt_hmset 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午7:30:18
	*
	* @param key
	* @param hash
	* @return     
	*/
	private String tt_hmset(String key, Map<String, String> hash) {
		Jedis jedis = this.getJedis();
		String result = jedis.hmset(key, hash);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param hash
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#hmset(java.lang.String, java.util.Map) 
	 */
	@Override
	public String hmset(String key, Map<String, String> hash) {
		if (null == this.logAnalyzer) {
			return tt_hmset(key, hash);
		}
		try {
			return tt_hmset(key, hash);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, "hash", hash));
			return null;
		}
	}

	/** 
	* @Title: tt_hmget 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午7:32:53
	*
	* @param key
	* @param fields
	* @return     
	*/
	private List<String> tt_hmget(String key, String... fields) {
		Jedis jedis = this.getJedis();
		List<String> result = jedis.hmget(key, fields);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param fields
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#hmget(java.lang.String, java.lang.String[]) 
	 */
	@Override
	public List<String> hmget(String key, String... fields) {
		if (null == this.logAnalyzer) {
			return tt_hmget(key, fields);
		}
		try {
			return tt_hmget(key, fields);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJsonByArray(key, "fields", fields));
			return null;
		}
	}

	/** 
	* @Title: tt_hincrBy 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午7:35:04
	*
	* @param key
	* @param field
	* @param value
	* @return     
	*/
	private Long tt_hincrBy(String key, String field, long value) {
		Jedis jedis = this.getJedis();
		Long result = jedis.hincrBy(key, field, value);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param field
	 * @param value
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#hincrBy(java.lang.String, java.lang.String, long) 
	 */
	@Override
	public Long hincrBy(String key, String field, long value) {
		if (null == this.logAnalyzer) {
			return tt_hincrBy(key, field, value);
		}
		try {
			return tt_hincrBy(key, field, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "field", field, "value", value +""));
			return null;
		}
	}

	/** 
	* @Title: tt_hexists 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午7:36:42
	*
	* @param key
	* @param field
	* @return     
	*/
	private Boolean tt_hexists(String key, String field) {
		Jedis jedis = this.getJedis();
		Boolean result = jedis.hexists(key, field);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param field
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#hexists(java.lang.String, java.lang.String) 
	 */
	@Override
	public Boolean hexists(String key, String field) {
		if (null == this.logAnalyzer) {
			return tt_hexists(key, field);
		}
		try {
			return tt_hexists(key, field);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, "field", field));
			return null;
		}
	}

	/** 
	* @Title: tt_hdel 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午7:45:10
	*
	* @param key
	* @param field
	* @return     
	*/
	private Long tt_hdel(String key, String... field) {
		Jedis jedis = this.getJedis();
		Long result = jedis.hdel(key, field);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param field
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#hdel(java.lang.String, java.lang.String[]) 
	 */
	@Override
	public Long hdel(String key, String... field) {
		if (null == this.logAnalyzer) {
			return tt_hdel(key, field);
		}
		try {
			return tt_hdel(key, field);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJsonByArray(key, "field", field));
			return null;
		}
	}

	/** 
	* @Title: tt_hlen 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午7:47:21
	*
	* @param key
	* @return     
	*/
	private Long tt_hlen(String key) {
		Jedis jedis = this.getJedis();
		Long result = jedis.hlen(key);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#hlen(java.lang.String) 
	 */
	@Override
	public Long hlen(String key) {
		if (null == this.logAnalyzer) {
			return tt_hlen(key);
		}
		try {
			return tt_hlen(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
	}

	/** 
	* @Title: tt_hkeys 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午7:48:46
	*
	* @param key
	* @return     
	*/
	private Set<String> tt_hkeys(String key) {
		Jedis jedis = this.getJedis();
		Set<String> result = jedis.hkeys(key);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#hkeys(java.lang.String) 
	 */
	@Override
	public Set<String> hkeys(String key) {
		if (null == this.logAnalyzer) {
			return tt_hkeys(key);
		}
		try {
			return tt_hkeys(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
	}

	/** 
	* @Title: tt_hvals 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午7:50:23
	*
	* @param key
	* @return     
	*/
	private List<String> tt_hvals(String key) {
		Jedis jedis = this.getJedis();
		List<String> result = jedis.hvals(key);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#hvals(java.lang.String) 
	 */
	@Override
	public List<String> hvals(String key) {
		if (null == this.logAnalyzer) {
			return tt_hvals(key);
		}
		try {
			return tt_hvals(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
	}

	/** 
	* @Title: tt_hgetAll 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午7:51:33
	*
	* @param key
	* @return     
	*/
	private Map<String, String> tt_hgetAll(String key) {
		Jedis jedis = this.getJedis();
		Map<String, String> result = jedis.hgetAll(key);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#hgetAll(java.lang.String) 
	 */
	@Override
	public Map<String, String> hgetAll(String key) {
		if (null == this.logAnalyzer) {
			return tt_hgetAll(key);
		}
		try {
			return tt_hgetAll(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
	}
	
	/** 
	* @Title: tt_rpush 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午7:53:30
	*
	* @param key
	* @param string
	* @return     
	*/
	private Long tt_rpush(String key, String... string) {
		Jedis jedis = this.getJedis();
		Long result = jedis.rpush(key, string);
		this.close(jedis);
		return result;
	}

	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param string
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#rpush(java.lang.String, java.lang.String[]) 
	 */
	@Override
	public Long rpush(String key, String... string) {
		if (null == this.logAnalyzer) {
			return tt_rpush(key, string);
		}
		try {
			return tt_rpush(key, string);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJsonByArray(key, "string", string));
			return null;
		}
	}

	/** 
	* @Title: tt_lpush 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午7:54:40
	*
	* @param key
	* @param string
	* @return     
	*/
	private Long tt_lpush(String key, String... string) {
		Jedis jedis = this.getJedis();
		Long result = jedis.lpush(key, string);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param string
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#lpush(java.lang.String, java.lang.String[]) 
	 */
	@Override
	public Long lpush(String key, String... string) {
		if (null == this.logAnalyzer) {
			return tt_lpush(key, string);
		}
		try {
			return tt_lpush(key, string);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJsonByArray(key, "string", string));
			return null;
		}
	}

	/** 
	* @Title: tt_llen 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午7:55:43
	*
	* @param key
	* @return     
	*/
	private Long tt_llen(String key) {
		Jedis jedis = this.getJedis();
		Long result = jedis.llen(key);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#llen(java.lang.String) 
	 */
	@Override
	public Long llen(String key) {
		if (null == this.logAnalyzer) {
			return tt_llen(key);
		}
		try {
			return tt_llen(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
	}

	/** 
	* @Title: tt_lrange 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午7:57:15
	*
	* @param key
	* @param start
	* @param end
	* @return     
	*/
	private List<String> tt_lrange(String key, long start, long end) {
		Jedis jedis = this.getJedis();
		List<String> result = jedis.lrange(key, start, end);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param start
	 * @param end
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#lrange(java.lang.String, long, long) 
	 */
	@Override
	public List<String> lrange(String key, long start, long end) {
		if (null == this.logAnalyzer) {
			return tt_lrange(key, start, end);
		}
		try {
			return tt_lrange(key, start, end);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "start", start +"", "end", end +""));
			return null;
		}
	}

	/** 
	* @Title: tt_ltrim 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午7:58:51
	*
	* @param key
	* @param start
	* @param end
	* @return     
	*/
	private String tt_ltrim(String key, long start, long end) {
		Jedis jedis = this.getJedis();
		String result = jedis.ltrim(key, start, end);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param start
	 * @param end
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#ltrim(java.lang.String, long, long) 
	 */
	@Override
	public String ltrim(String key, long start, long end) {
		if (null == this.logAnalyzer) {
			return tt_ltrim(key, start, end);
		}
		try {
			return tt_ltrim(key, start, end);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "start", start +"", "end", end +""));
			return null;
		}
	}

	/** 
	* @Title: tt_lindex 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:00:26
	*
	* @param key
	* @param index
	* @return     
	*/
	private String tt_lindex(String key, long index) {
		Jedis jedis = this.getJedis();
		String result = jedis.lindex(key, index);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param index
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#lindex(java.lang.String, long) 
	 */
	@Override
	public String lindex(String key, long index) {
		if (null == this.logAnalyzer) {
			return tt_lindex(key, index);
		}
		try {
			return tt_lindex(key, index);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, "index", index +""));
			return null;
		}
	}

	/** 
	* @Title: tt_lset 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:02:19
	*
	* @param key
	* @param index
	* @param value
	* @return     
	*/
	private String tt_lset(String key, long index, String value) {
		Jedis jedis = this.getJedis();
		String result = jedis.lset(key, index, value);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param index
	 * @param value
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#lset(java.lang.String, long, java.lang.String) 
	 */
	@Override
	public String lset(String key, long index, String value) {
		if (null == this.logAnalyzer) {
			return tt_lset(key, index, value);
		}
		try {
			return tt_lset(key, index, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "index", index +"", "value", value));
			return null;
		}
	}

	/** 
	* @Title: tt_lrem 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:03:46
	*
	* @param key
	* @param count
	* @param value
	* @return     
	*/
	private Long tt_lrem(String key, long count, String value) {
		Jedis jedis = this.getJedis();
		Long result = jedis.lrem(key, count, value);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param count
	 * @param value
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#lrem(java.lang.String, long, java.lang.String) 
	 */
	@Override
	public Long lrem(String key, long count, String value) {
		if (null == this.logAnalyzer) {
			return tt_lrem(key, count, value);
		}
		try {
			return tt_lrem(key, count, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "count", count +"", "value", value));
			return null;
		}
	}

	/** 
	* @Title: tt_lpop 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:05:51
	*
	* @param key
	* @return     
	*/
	private String tt_lpop(String key) {
		Jedis jedis = this.getJedis();
		String result = jedis.lpop(key);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#lpop(java.lang.String) 
	 */
	@Override
	public String lpop(String key) {
		if (null == this.logAnalyzer) {
			return tt_lpop(key);
		}
		try {
			return tt_lpop(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
	}

	/** 
	* @Title: tt_rpop 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:07:11
	*
	* @param key
	* @return     
	*/
	private String tt_rpop(String key) {
		Jedis jedis = this.getJedis();
		String result = jedis.rpop(key);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#rpop(java.lang.String) 
	 */
	@Override
	public String rpop(String key) {
		if (null == this.logAnalyzer) {
			return tt_rpop(key);
		}
		try {
			return tt_rpop(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
	}

	/** 
	* @Title: tt_sadd 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:08:23
	*
	* @param key
	* @param member
	* @return     
	*/
	private Long tt_sadd(String key, String... member) {
		Jedis jedis = this.getJedis();
		Long result = jedis.sadd(key, member);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param member
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#sadd(java.lang.String, java.lang.String[]) 
	 */
	@Override
	public Long sadd(String key, String... member) {
		if (null == this.logAnalyzer) {
			return tt_sadd(key, member);
		}
		try {
			return tt_sadd(key, member);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJsonByArray(key, "member", member));
			return null;
		}
	}

	/** 
	* @Title: tt_smembers 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:09:39
	*
	* @param key
	* @return     
	*/
	private Set<String> tt_smembers(String key) {
		Jedis jedis = this.getJedis();
		Set<String> result = jedis.smembers(key);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#smembers(java.lang.String) 
	 */
	@Override
	public Set<String> smembers(String key) {
		if (null == this.logAnalyzer) {
			return tt_smembers(key);
		}
		try {
			return tt_smembers(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
	}

	/** 
	* @Title: tt_srem 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:12:41
	*
	* @param key
	* @param member
	* @return     
	*/
	private Long tt_srem(String key, String... member) {
		Jedis jedis = this.getJedis();
		Long result = jedis.srem(key, member);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param member
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#srem(java.lang.String, java.lang.String[]) 
	 */
	@Override
	public Long srem(String key, String... member) {
		if (null == this.logAnalyzer) {
			return tt_srem(key, member);
		}
		try {
			return tt_srem(key, member);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJsonByArray(key, "member", member));
			return null;
		}
	}

	/** 
	* @Title: tt_spop 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:15:14
	*
	* @param key
	* @return     
	*/
	private String tt_spop(String key) {
		Jedis jedis = this.getJedis();
		String result = jedis.spop(key);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#spop(java.lang.String) 
	 */
	@Override
	public String spop(String key) {
		if (null == this.logAnalyzer) {
			return tt_spop(key);
		}
		try {
			return tt_spop(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
	}

	/** 
	* @Title: tt_spop 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:16:17
	*
	* @param key
	* @param count
	* @return     
	*/
	private Set<String> tt_spop(String key, long count) {
		Jedis jedis = this.getJedis();
		Set<String> result = jedis.spop(key, count);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param count
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#spop(java.lang.String, long) 
	 */
	@Override
	public Set<String> spop(String key, long count) {
		if (null == this.logAnalyzer) {
			return tt_spop(key, count);
		}
		try {
			return tt_spop(key, count);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "count", count +""));
			return null;
		}
	}

	/** 
	* @Title: tt_scard 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:18:14
	*
	* @param key
	* @return     
	*/
	private Long tt_scard(String key) {
		Jedis jedis = this.getJedis();
		Long result = jedis.scard(key);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#scard(java.lang.String) 
	 */
	@Override
	public Long scard(String key) {
		if (null == this.logAnalyzer) {
			return tt_scard(key);
		}
		try {
			return tt_scard(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
	}

	/** 
	* @Title: tt_sismember 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:19:20
	*
	* @param key
	* @param member
	* @return     
	*/
	private Boolean tt_sismember(String key, String member) {
		Jedis jedis = this.getJedis();
		Boolean result = jedis.sismember(key, member);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param member
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#sismember(java.lang.String, java.lang.String) 
	 */
	@Override
	public Boolean sismember(String key, String member) {
		if (null == this.logAnalyzer) {
			return tt_sismember(key, member);
		}
		try {
			return tt_sismember(key, member);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, "member", member));
			return null;
		}
	}

	/** 
	* @Title: tt_srandmember 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:21:37
	*
	* @param key
	* @return     
	*/
	private String tt_srandmember(String key) {
		Jedis jedis = this.getJedis();
		String result = jedis.srandmember(key);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#srandmember(java.lang.String) 
	 */
	@Override
	public String srandmember(String key) {
		if (null == this.logAnalyzer) {
			return tt_srandmember(key);
		}
		try {
			return tt_srandmember(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
	}

	/** 
	* @Title: tt_srandmember 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:22:50
	*
	* @param key
	* @param count
	* @return     
	*/
	private List<String> tt_srandmember(String key, int count) {
		Jedis jedis = this.getJedis();
		List<String> result = jedis.srandmember(key, count);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param count
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#srandmember(java.lang.String, int) 
	 */
	@Override
	public List<String> srandmember(String key, int count) {
		if (null == this.logAnalyzer) {
			return tt_srandmember(key, count);
		}
		try {
			return tt_srandmember(key, count);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, "count", count +""));
			return null;
		}
	}

	/** 
	* @Title: tt_strlen 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:24:46
	*
	* @param key
	* @return     
	*/
	private Long tt_strlen(String key) {
		Jedis jedis = this.getJedis();
		Long result = jedis.strlen(key);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#strlen(java.lang.String) 
	 */
	@Override
	public Long strlen(String key) {
		if (null == this.logAnalyzer) {
			return tt_strlen(key);
		}
		try {
			return tt_strlen(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
	}

	/** 
	* @Title: tt_zadd 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:26:04
	*
	* @param key
	* @param score
	* @param member
	* @return     
	*/
	private Long tt_zadd(String key, double score, String member) {
		Jedis jedis = this.getJedis();
		Long result = jedis.zadd(key, score, member);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param score
	 * @param member
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zadd(java.lang.String, double, java.lang.String) 
	 */
	@Override
	public Long zadd(String key, double score, String member) {
		if (null == this.logAnalyzer) {
			return tt_zadd(key, score, member);
		}
		try {
			return tt_zadd(key, score, member);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "score", score +"", "member", member));
			return null;
		}
	}

	/** 
	* @Title: tt_zadd 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:27:36
	*
	* @param key
	* @param scoreMembers
	* @return     
	*/
	private Long tt_zadd(String key, Map<String, Double> scoreMembers) {
		Jedis jedis = this.getJedis();
		Long result = jedis.zadd(key, scoreMembers);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param scoreMembers
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zadd(java.lang.String, java.util.Map) 
	 */
	@Override
	public Long zadd(String key, Map<String, Double> scoreMembers) {
		if (null == this.logAnalyzer) {
			return tt_zadd(key, scoreMembers);
		}
		try {
			return tt_zadd(key, scoreMembers);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "scoreMembers", scoreMembers));
			return null;
		}
	}

	/** 
	* @Title: tt_zrange 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:30:03
	*
	* @param key
	* @param start
	* @param end
	* @return     
	*/
	private Set<String> tt_zrange(String key, long start, long end) {
		Jedis jedis = this.getJedis();
		Set<String> result = jedis.zrange(key, start, end);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param start
	 * @param end
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zrange(java.lang.String, long, long) 
	 */
	@Override
	public Set<String> zrange(String key, long start, long end) {
		if (null == this.logAnalyzer) {
			return tt_zrange(key, start, end);
		}
		try {
			return tt_zrange(key, start, end);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "start", start +"", "end", end +""));
			return null;
		}
	}

	/** 
	* @Title: tt_zrem 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:31:53
	*
	* @param key
	* @param member
	* @return     
	*/
	private Long tt_zrem(String key, String... member) {
		Jedis jedis = this.getJedis();
		Long result = jedis.zrem(key, member);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param member
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zrem(java.lang.String, java.lang.String[]) 
	 */
	@Override
	public Long zrem(String key, String... member) {
		if (null == this.logAnalyzer) {
			return tt_zrem(key, member);
		}
		try {
			return tt_zrem(key, member);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJsonByArray(key, "member", member));
			return null;
		}
	}

	/** 
	* @Title: tt_zincrby 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:33:21
	*
	* @param key
	* @param score
	* @param member
	* @return     
	*/
	private Double tt_zincrby(String key, double score, String member) {
		Jedis jedis = this.getJedis();
		Double result = jedis.zincrby(key, score, member);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param score
	 * @param member
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zincrby(java.lang.String, double, java.lang.String) 
	 */
	@Override
	public Double zincrby(String key, double score, String member) {
		if (null == this.logAnalyzer) {
			return tt_zincrby(key, score, member);
		}
		try {
			return tt_zincrby(key, score, member);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "score", score +"", "member", member));
			return null;
		}
	}

	/** 
	* @Title: tt_zrank 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:35:23
	*
	* @param key
	* @param member
	* @return     
	*/
	private Long tt_zrank(String key, String member) {
		Jedis jedis = this.getJedis();
		Long result = jedis.zrank(key, member);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param member
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zrank(java.lang.String, java.lang.String) 
	 */
	@Override
	public Long zrank(String key, String member) {
		if (null == this.logAnalyzer) {
			return tt_zrank(key, member);
		}
		try {
			return tt_zrank(key, member);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, "member", member));
			return null;
		}
	}

	/** 
	* @Title: tt_zrevrank 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:36:55
	*
	* @param key
	* @param member
	* @return     
	*/
	private Long tt_zrevrank(String key, String member) {
		Jedis jedis = this.getJedis();
		Long result = jedis.zrevrank(key, member);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param member
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zrevrank(java.lang.String, java.lang.String) 
	 */
	@Override
	public Long zrevrank(String key, String member) {
		if (null == this.logAnalyzer) {
			return tt_zrevrank(key, member);
		}
		try {
			return tt_zrevrank(key, member);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, "member", member));
			return null;
		}
	}

	/** 
	* @Title: tt_zrevrange 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:38:02
	*
	* @param key
	* @param start
	* @param end
	* @return     
	*/
	private Set<String> tt_zrevrange(String key, long start, long end) {
		Jedis jedis = this.getJedis();
		Set<String> result = jedis.zrevrange(key, start, end);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param start
	 * @param end
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zrevrange(java.lang.String, long, long) 
	 */
	@Override
	public Set<String> zrevrange(String key, long start, long end) {
		if (null == this.logAnalyzer) {
			return tt_zrevrange(key, start, end);
		}
		try {
			return tt_zrevrange(key, start, end);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "start", start +"", "end", end +""));
			return null;
		}
	}

	/** 
	* @Title: tt_zrangeWithScores 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:39:34
	*
	* @param key
	* @param start
	* @param end
	* @return     
	*/
	private Set<Tuple> tt_zrangeWithScores(String key, long start, long end) {
		Jedis jedis = this.getJedis();
		Set<Tuple> result = jedis.zrangeWithScores(key, start, end);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param start
	 * @param end
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zrangeWithScores(java.lang.String, long, long) 
	 */
	@Override
	public Set<Tuple> zrangeWithScores(String key, long start, long end) {
		if (null == this.logAnalyzer) {
			return tt_zrangeWithScores(key, start, end);
		}
		try {
			return tt_zrangeWithScores(key, start, end);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "start", start +"", "end", end +""));
			return null;
		}
	}

	/** 
	* @Title: tt_zrevrangeWithScores 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:40:57
	*
	* @param key
	* @param start
	* @param end
	* @return     
	*/
	private Set<Tuple> tt_zrevrangeWithScores(String key, long start, long end) {
		Jedis jedis = this.getJedis();
		Set<Tuple> result = jedis.zrevrangeWithScores(key, start, end);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param start
	 * @param end
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zrevrangeWithScores(java.lang.String, long, long) 
	 */
	@Override
	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		if (null == this.logAnalyzer) {
			return tt_zrevrangeWithScores(key, start, end);
		}
		try {
			return tt_zrevrangeWithScores(key, start, end);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "start", start +"", "end", end +""));
			return null;
		}
	}

	/** 
	* @Title: tt_zcard 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:42:18
	*
	* @param key
	* @return     
	*/
	private Long tt_zcard(String key) {
		Jedis jedis = this.getJedis();
		Long result = jedis.zcard(key);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zcard(java.lang.String) 
	 */
	@Override
	public Long zcard(String key) {
		if (null == this.logAnalyzer) {
			return tt_zcard(key);
		}
		try {
			return tt_zcard(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
	}

	/** 
	* @Title: tt_zscore 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:43:24
	*
	* @param key
	* @param member
	* @return     
	*/
	private Double tt_zscore(String key, String member) {
		Jedis jedis = this.getJedis();
		Double result = jedis.zscore(key, member);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param member
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zscore(java.lang.String, java.lang.String) 
	 */
	@Override
	public Double zscore(String key, String member) {
		if (null == this.logAnalyzer) {
			return tt_zscore(key, member);
		}
		try {
			return tt_zscore(key, member);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, "member", member));
			return null;
		}
	}

	/** 
	* @Title: tt_sort 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:45:15
	*
	* @param key
	* @return     
	*/
	private List<String> tt_sort(String key) {
		Jedis jedis = this.getJedis();
		List<String> result = jedis.sort(key);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#sort(java.lang.String) 
	 */
	@Override
	public List<String> sort(String key) {
		if (null == this.logAnalyzer) {
			return tt_sort(key);
		}
		try {
			return tt_sort(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
	}

	/** 
	* @Title: tt_sort 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:46:40
	*
	* @param key
	* @param sortingParameters
	* @return     
	*/
	private List<String> tt_sort(String key, SortingParams sortingParameters) {
		Jedis jedis = this.getJedis();
		List<String> result = jedis.sort(key, sortingParameters);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param sortingParameters
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#sort(java.lang.String, redis.clients.jedis.SortingParams) 
	 */
	@Override
	public List<String> sort(String key, SortingParams sortingParameters) {
		if (null == this.logAnalyzer) {
			return tt_sort(key, sortingParameters);
		}
		try {
			return tt_sort(key, sortingParameters);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, sortingParameters));
			return null;
		}
	}

	/** 
	* @Title: tt_zcount 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:48:19
	*
	* @param key
	* @param min
	* @param max
	* @return     
	*/
	private Long tt_zcount(String key, double min, double max) {
		Jedis jedis = this.getJedis();
		Long result = jedis.zcount(key, min, max);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param min
	 * @param max
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zcount(java.lang.String, double, double) 
	 */
	@Override
	public Long zcount(String key, double min, double max) {
		if (null == this.logAnalyzer) {
			return tt_zcount(key, min, max);
		}
		try {
			return tt_zcount(key, min, max);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min +"", "max", max +""));
			return null;
		}
	}

	/** 
	* @Title: tt_zcount 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:51:31
	*
	* @param key
	* @param min
	* @param max
	* @return     
	*/
	private Long tt_zcount(String key, String min, String max) {
		Jedis jedis = this.getJedis();
		Long result = jedis.zcount(key, min, max);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param min
	 * @param max
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zcount(java.lang.String, java.lang.String, java.lang.String) 
	 */
	@Override
	public Long zcount(String key, String min, String max) {
		if (null == this.logAnalyzer) {
			return tt_zcount(key, min, max);
		}
		try {
			return tt_zcount(key, min, max);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min, "max", max));
			return null;
		}
	}

	/** 
	* @Title: tt_zrangeByScore 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:53:25
	*
	* @param key
	* @param min
	* @param max
	* @return     
	*/
	private Set<String> tt_zrangeByScore(String key, double min, double max) {
		Jedis jedis = this.getJedis();
		Set<String> result = jedis.zrangeByScore(key, min, max);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param min
	 * @param max
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zrangeByScore(java.lang.String, double, double) 
	 */
	@Override
	public Set<String> zrangeByScore(String key, double min, double max) {
		if (null == this.logAnalyzer) {
			return tt_zrangeByScore(key, min, max);
		}
		try {
			return tt_zrangeByScore(key, min, max);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min +"", "max", max +""));
			return null;
		}
	}

	/** 
	* @Title: tt_zrangeByScore 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午8:54:47
	*
	* @param key
	* @param min
	* @param max
	* @return     
	*/
	private Set<String> tt_zrangeByScore(String key, String min, String max) {
		Jedis jedis = this.getJedis();
		Set<String> result = jedis.zrangeByScore(key, min, max);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param min
	 * @param max
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zrangeByScore(java.lang.String, java.lang.String, java.lang.String) 
	 */
	@Override
	public Set<String> zrangeByScore(String key, String min, String max) {
		if (null == this.logAnalyzer) {
			return tt_zrangeByScore(key, min, max);
		}
		try {
			return tt_zrangeByScore(key, min, max);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min, "max", max));
			return null;
		}
	}

	/** 
	* @Title: tt_zrevrangeByScore 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:02:00
	*
	* @param key
	* @param max
	* @param min
	* @return     
	*/
	private Set<String> tt_zrevrangeByScore(String key, double max, double min) {
		Jedis jedis = this.getJedis();
		Set<String> result = jedis.zrevrangeByScore(key, max, min);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param max
	 * @param min
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zrevrangeByScore(java.lang.String, double, double) 
	 */
	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min) {
		if (null == this.logAnalyzer) {
			return tt_zrevrangeByScore(key, max, min);
		}
		try {
			return tt_zrevrangeByScore(key, max, min);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "max", max +"", "min", min +""));
			return null;
		}
	}

	/** 
	* @Title: tt_zrangeByScore 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:04:24
	*
	* @param key
	* @param min
	* @param max
	* @param offset
	* @param count
	* @return     
	*/
	private Set<String> tt_zrangeByScore(String key, double min, double max, int offset, int count) {
		Jedis jedis = this.getJedis();
		Set<String> result = jedis.zrangeByScore(key, min, max, offset, count);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zrangeByScore(java.lang.String, double, double, int, int) 
	 */
	@Override
	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
		if (null == this.logAnalyzer) {
			return tt_zrangeByScore(key, min, max, offset, count);
		}
		try {
			return tt_zrangeByScore(key, min, max, offset, count);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min +"", "max", max +"", "offset", offset +"", "count", count +""));
			return null;
		}
	}

	/** 
	* @Title: tt_zrevrangeByScore 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:06:56
	*
	* @param key
	* @param max
	* @param min
	* @return     
	*/
	private Set<String> tt_zrevrangeByScore(String key, String max, String min) {
		Jedis jedis = this.getJedis();
		Set<String> result = jedis.zrevrangeByScore(key, max, min);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param max
	 * @param min
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zrevrangeByScore(java.lang.String, java.lang.String, java.lang.String) 
	 */
	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min) {
		if (null == this.logAnalyzer) {
			return tt_zrevrangeByScore(key, max, min);
		}
		try {
			return tt_zrevrangeByScore(key, max, min);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "max", max, "min", min));
			return null;
		}
	}

	/** 
	* @Title: tt_zrangeByScore 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:09:11
	*
	* @param key
	* @param min
	* @param max
	* @param offset
	* @param count
	* @return     
	*/
	private Set<String> tt_zrangeByScore(String key, String min, String max, int offset, int count) {
		Jedis jedis = this.getJedis();
		Set<String> result = jedis.zrangeByScore(key, min, max, offset, count);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zrangeByScore(java.lang.String, java.lang.String, java.lang.String, int, int) 
	 */
	@Override
	public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
		if (null == this.logAnalyzer) {
			return tt_zrangeByScore(key, min, max, offset, count);
		}
		try {
			return tt_zrangeByScore(key, min, max, offset, count);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min, "max", max, "offset", offset +"", "count", count +""));
			return null;
		}
	}

	/** 
	* @Title: tt_zrevrangeByScore 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:11:44
	*
	* @param key
	* @param max
	* @param min
	* @param offset
	* @param count
	* @return     
	*/
	private Set<String> tt_zrevrangeByScore(String key, double max, double min, int offset, int count) {
		Jedis jedis = this.getJedis();
		Set<String> result = jedis.zrevrangeByScore(key, max, min, offset, count);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param max
	 * @param min
	 * @param offset
	 * @param count
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zrevrangeByScore(java.lang.String, double, double, int, int) 
	 */
	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
		if (null == this.logAnalyzer) {
			return tt_zrevrangeByScore(key, max, min, offset, count);
		}
		try {
			return tt_zrevrangeByScore(key, max, min, offset, count);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "max", max +"", "min", min +"", "offset", offset +"", "count", count +""));
			return null;
		}
	}

	/** 
	* @Title: tt_zrangeByScoreWithScores 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:13:51
	*
	* @param key
	* @param min
	* @param max
	* @return     
	*/
	private Set<Tuple> tt_zrangeByScoreWithScores(String key, double min, double max) {
		Jedis jedis = this.getJedis();
		Set<Tuple> result = jedis.zrangeByScoreWithScores(key, min, max);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param min
	 * @param max
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zrangeByScoreWithScores(java.lang.String, double, double) 
	 */
	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		if (null == this.logAnalyzer) {
			return tt_zrangeByScoreWithScores(key, min, max);
		}
		try {
			return tt_zrangeByScoreWithScores(key, min, max);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min +"", "max", max +""));
			return null;
		}
	}

	/** 
	* @Title: tt_zrevrangeByScoreWithScores 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:15:24
	*
	* @param key
	* @param max
	* @param min
	* @return     
	*/
	private Set<Tuple> tt_zrevrangeByScoreWithScores(String key, double max, double min) {
		Jedis jedis = this.getJedis();
		Set<Tuple> result = jedis.zrevrangeByScoreWithScores(key, max, min);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param max
	 * @param min
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zrevrangeByScoreWithScores(java.lang.String, double, double) 
	 */
	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
		if (null == this.logAnalyzer) {
			return tt_zrevrangeByScoreWithScores(key, max, min);
		}
		try {
			return tt_zrevrangeByScoreWithScores(key, max, min);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "max", max +"", "min", min +""));
			return null;
		}
	}

	/** 
	* @Title: tt_zrangeByScoreWithScores 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:17:47
	*
	* @param key
	* @param min
	* @param max
	* @param offset
	* @param count
	* @return     
	*/
	private Set<Tuple> tt_zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
		Jedis jedis = this.getJedis();
		Set<Tuple> result = jedis.zrangeByScoreWithScores(key, min, max, offset, count);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zrangeByScoreWithScores(java.lang.String, double, double, int, int) 
	 */
	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
		if (null == this.logAnalyzer) {
			return tt_zrangeByScoreWithScores(key, min, max, offset, count);
		}
		try {
			return tt_zrangeByScoreWithScores(key, min, max, offset, count);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min +"", "max", max +"", "offset", offset +"", "count", count +""));
			return null;
		}
	}

	/** 
	* @Title: tt_zrevrangeByScore 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:19:40
	*
	* @param key
	* @param max
	* @param min
	* @param offset
	* @param count
	* @return     
	*/
	private Set<String> tt_zrevrangeByScore(String key, String max, String min, int offset, int count) {
		Jedis jedis = this.getJedis();
		Set<String> result = jedis.zrevrangeByScore(key, max, min, offset, count);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param max
	 * @param min
	 * @param offset
	 * @param count
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zrevrangeByScore(java.lang.String, java.lang.String, java.lang.String, int, int) 
	 */
	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
		if (null == this.logAnalyzer) {
			return tt_zrevrangeByScore(key, max, min, offset, count);
		}
		try {
			return tt_zrevrangeByScore(key, max, min, offset, count);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "max", max, "min", min, "offset", offset +"", "count", count +""));
			return null;
		}
	}

	/** 
	* @Title: tt_zrangeByScoreWithScores 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:21:52
	*
	* @param key
	* @param min
	* @param max
	* @return     
	*/
	private Set<Tuple> tt_zrangeByScoreWithScores(String key, String min, String max) {
		Jedis jedis = this.getJedis();
		Set<Tuple> result = jedis.zrangeByScoreWithScores(key, min, max);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param min
	 * @param max
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zrangeByScoreWithScores(java.lang.String, java.lang.String, java.lang.String) 
	 */
	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
		if (null == this.logAnalyzer) {
			return tt_zrangeByScoreWithScores(key, min, max);
		}
		try {
			return tt_zrangeByScoreWithScores(key, min, max);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min, "max", max));
			return null;
		}
	}

	/** 
	* @Title: tt_zrevrangeByScoreWithScores 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:23:29
	*
	* @param key
	* @param max
	* @param min
	* @return     
	*/
	private Set<Tuple> tt_zrevrangeByScoreWithScores(String key, String max, String min) {
		Jedis jedis = this.getJedis();
		Set<Tuple> result = jedis.zrevrangeByScoreWithScores(key, max, min);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param max
	 * @param min
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zrevrangeByScoreWithScores(java.lang.String, java.lang.String, java.lang.String) 
	 */
	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
		if (null == this.logAnalyzer) {
			return tt_zrevrangeByScoreWithScores(key, max, min);
		}
		try {
			return tt_zrevrangeByScoreWithScores(key, max, min);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "max", max, "min", min));
			return null;
		}
	}

	/** 
	* @Title: tt_zrangeByScoreWithScores 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:25:20
	*
	* @param key
	* @param min
	* @param max
	* @param offset
	* @param count
	* @return     
	*/
	private Set<Tuple> tt_zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
		Jedis jedis = this.getJedis();
		Set<Tuple> result = jedis.zrangeByScoreWithScores(key, min, max, offset, count);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zrangeByScoreWithScores(java.lang.String, java.lang.String, java.lang.String, int, int) 
	 */
	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
		if (null == this.logAnalyzer) {
			return tt_zrangeByScoreWithScores(key, min, max, offset, count);
		}
		try {
			return tt_zrangeByScoreWithScores(key, min, max, offset, count);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min, "max", max, "offset", offset +"", "count", count +""));
			return null;
		}
	}

	/** 
	* @Title: tt_zrevrangeByScoreWithScores 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:26:58
	*
	* @param key
	* @param max
	* @param min
	* @param offset
	* @param count
	* @return     
	*/
	private Set<Tuple> tt_zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
		Jedis jedis = this.getJedis();
		Set<Tuple> result = jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param max
	 * @param min
	 * @param offset
	 * @param count
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zrevrangeByScoreWithScores(java.lang.String, double, double, int, int) 
	 */
	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
		if (null == this.logAnalyzer) {
			return tt_zrevrangeByScoreWithScores(key, max, min, offset, count);
		}
		try {
			return tt_zrevrangeByScoreWithScores(key, max, min, offset, count);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "max", max +"", "min", min +"", "offset", offset +"", "count", count +""));
			return null;
		}
	}

	/** 
	* @Title: tt_zrevrangeByScoreWithScores 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:28:28
	*
	* @param key
	* @param max
	* @param min
	* @param offset
	* @param count
	* @return     
	*/
	private Set<Tuple> tt_zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
		Jedis jedis = this.getJedis();
		Set<Tuple> result = jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param max
	 * @param min
	 * @param offset
	 * @param count
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zrevrangeByScoreWithScores(java.lang.String, java.lang.String, java.lang.String, int, int) 
	 */
	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
		if (null == this.logAnalyzer) {
			return tt_zrevrangeByScoreWithScores(key, max, min, offset, count);
		}
		try {
			return tt_zrevrangeByScoreWithScores(key, max, min, offset, count);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "max", max, "min", min, "offset", offset +"", "count", count +""));
			return null;
		}
	}

	/** 
	* @Title: tt_zremrangeByRank 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:30:22
	*
	* @param key
	* @param start
	* @param end
	* @return     
	*/
	private Long tt_zremrangeByRank(String key, long start, long end) {
		Jedis jedis = this.getJedis();
		Long result = jedis.zremrangeByRank(key, start, end);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param start
	 * @param end
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zremrangeByRank(java.lang.String, long, long) 
	 */
	@Override
	public Long zremrangeByRank(String key, long start, long end) {
		if (null == this.logAnalyzer) {
			return tt_zremrangeByRank(key, start, end);
		}
		try {
			return tt_zremrangeByRank(key, start, end);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "start", start +"", "end", end +""));
			return null;
		}
	}

	/** 
	* @Title: tt_zremrangeByScore 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:32:02
	*
	* @param key
	* @param start
	* @param end
	* @return     
	*/
	private Long tt_zremrangeByScore(String key, double start, double end) {
		Jedis jedis = this.getJedis();
		Long result = jedis.zremrangeByScore(key, start, end);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param start
	 * @param end
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zremrangeByScore(java.lang.String, double, double) 
	 */
	@Override
	public Long zremrangeByScore(String key, double start, double end) {
		if (null == this.logAnalyzer) {
			return tt_zremrangeByScore(key, start, end);
		}
		try {
			return tt_zremrangeByScore(key, start, end);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "start", start +"", "end", end +""));
			return null;
		}
	}

	/** 
	* @Title: tt_zremrangeByScore 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:33:19
	*
	* @param key
	* @param start
	* @param end
	* @return     
	*/
	private Long tt_zremrangeByScore(String key, String start, String end) {
		Jedis jedis = this.getJedis();
		Long result = jedis.zremrangeByScore(key, start, end);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param start
	 * @param end
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zremrangeByScore(java.lang.String, java.lang.String, java.lang.String) 
	 */
	@Override
	public Long zremrangeByScore(String key, String start, String end) {
		if (null == this.logAnalyzer) {
			return tt_zremrangeByScore(key, start, end);
		}
		try {
			return tt_zremrangeByScore(key, start, end);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "start", start, "end", end));
			return null;
		}
	}

	/** 
	* @Title: tt_zlexcount 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:35:03
	*
	* @param key
	* @param min
	* @param max
	* @return     
	*/
	private Long tt_zlexcount(String key, String min, String max) {
		Jedis jedis = this.getJedis();
		Long result = jedis.zlexcount(key, min, max);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param min
	 * @param max
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zlexcount(java.lang.String, java.lang.String, java.lang.String) 
	 */
	@Override
	public Long zlexcount(String key, String min, String max) {
		if (null == this.logAnalyzer) {
			return tt_zlexcount(key, min, max);
		}
		try {
			return tt_zlexcount(key, min, max);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min, "max", max));
			return null;
		}
	}

	/** 
	* @Title: tt_zrangeByLex 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:36:13
	*
	* @param key
	* @param min
	* @param max
	* @return     
	*/
	private Set<String> tt_zrangeByLex(String key, String min, String max) {
		Jedis jedis = this.getJedis();
		Set<String> result = jedis.zrangeByLex(key, min, max);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param min
	 * @param max
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zrangeByLex(java.lang.String, java.lang.String, java.lang.String) 
	 */
	@Override
	public Set<String> zrangeByLex(String key, String min, String max) {
		if (null == this.logAnalyzer) {
			return tt_zrangeByLex(key, min, max);
		}
		try {
			return tt_zrangeByLex(key, min, max);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min, "max", max));
			return null;
		}
	}

	/** 
	* @Title: tt_zrangeByLex 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:37:32
	*
	* @param key
	* @param min
	* @param max
	* @param offset
	* @param count
	* @return     
	*/
	private Set<String> tt_zrangeByLex(String key, String min, String max, int offset, int count) {
		Jedis jedis = this.getJedis();
		Set<String> result = jedis.zrangeByLex(key, min, max, offset, count);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zrangeByLex(java.lang.String, java.lang.String, java.lang.String, int, int) 
	 */
	@Override
	public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
		if (null == this.logAnalyzer) {
			return tt_zrangeByLex(key, min, max, offset, count);
		}
		try {
			return tt_zrangeByLex(key, min, max, offset, count);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min, "max", max, "offset", offset +"", "count", count +""));
			return null;
		}
	}

	/** 
	* @Title: tt_zrevrangeByLex 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:39:09
	*
	* @param key
	* @param max
	* @param min
	* @return     
	*/
	private Set<String> tt_zrevrangeByLex(String key, String max, String min) {
		Jedis jedis = this.getJedis();
		Set<String> result = jedis.zrevrangeByLex(key, max, min);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param max
	 * @param min
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zrevrangeByLex(java.lang.String, java.lang.String, java.lang.String) 
	 */
	@Override
	public Set<String> zrevrangeByLex(String key, String max, String min) {
		if (null == this.logAnalyzer) {
			return tt_zrevrangeByLex(key, max, min);
		}
		try {
			return tt_zrevrangeByLex(key, max, min);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "max", max, "min", min));
			return null;
		}
	}

	/** 
	* @Title: tt_zrevrangeByLex 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:40:56
	*
	* @param key
	* @param max
	* @param min
	* @param offset
	* @param count
	* @return     
	*/
	private Set<String> tt_zrevrangeByLex(String key, String max, String min, int offset, int count) {
		Jedis jedis = this.getJedis();
		Set<String> result = jedis.zrevrangeByLex(key, max, min, offset, count);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param max
	 * @param min
	 * @param offset
	 * @param count
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zrevrangeByLex(java.lang.String, java.lang.String, java.lang.String, int, int) 
	 */
	@Override
	public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
		if (null == this.logAnalyzer) {
			return tt_zrevrangeByLex(key, max, min, offset, count);
		}
		try {
			return tt_zrevrangeByLex(key, max, min, offset, count);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "max", max, "min", min, "offset", offset +"", "count", count +""));
			return null;
		}
	}

	/** 
	* @Title: tt_zremrangeByLex 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:42:27
	*
	* @param key
	* @param min
	* @param max
	* @return     
	*/
	private Long tt_zremrangeByLex(String key, String min, String max) {
		Jedis jedis = this.getJedis();
		Long result = jedis.zremrangeByLex(key, min, max);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param min
	 * @param max
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zremrangeByLex(java.lang.String, java.lang.String, java.lang.String) 
	 */
	@Override
	public Long zremrangeByLex(String key, String min, String max) {
		if (null == this.logAnalyzer) {
			return tt_zremrangeByLex(key, min, max);
		}
		try {
			return tt_zremrangeByLex(key, min, max);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min, "max", max));
			return null;
		}
	}

	/** 
	* @Title: tt_linsert 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:44:11
	*
	* @param key
	* @param where
	* @param pivot
	* @param value
	* @return     
	*/
	private Long tt_linsert(String key, LIST_POSITION where, String pivot, String value) {
		Jedis jedis = this.getJedis();
		Long result = jedis.linsert(key, where, pivot, value);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param where
	 * @param pivot
	 * @param value
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#linsert(java.lang.String, redis.clients.jedis.BinaryClient.LIST_POSITION, java.lang.String, java.lang.String) 
	 */
	@Override
	public Long linsert(String key, LIST_POSITION where, String pivot, String value) {
		if (null == this.logAnalyzer) {
			return tt_linsert(key, where, pivot, value);
		}
		try {
			return tt_linsert(key, where, pivot, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, where, pivot, value));
			return null;
		}
	}

	/** 
	* @Title: tt_lpushx 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:45:48
	*
	* @param key
	* @param string
	* @return     
	*/
	private Long tt_lpushx(String key, String... string) {
		Jedis jedis = this.getJedis();
		Long result = jedis.lpushx(key, string);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param string
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#lpushx(java.lang.String, java.lang.String[]) 
	 */
	@Override
	public Long lpushx(String key, String... string) {
		if (null == this.logAnalyzer) {
			return tt_lpushx(key, string);
		}
		try {
			return tt_lpushx(key, string);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJsonByArray(key, "string", string));
			return null;
		}
	}

	/** 
	* @Title: tt_rpushx 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:47:49
	*
	* @param key
	* @param string
	* @return     
	*/
	private Long tt_rpushx(String key, String... string) {
		Jedis jedis = this.getJedis();
		Long result = jedis.rpushx(key, string);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param string
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#rpushx(java.lang.String, java.lang.String[]) 
	 */
	@Override
	public Long rpushx(String key, String... string) {
		if (null == this.logAnalyzer) {
			return tt_rpushx(key, string);
		}
		try {
			return tt_rpushx(key, string);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJsonByArray(key, "string", string));
			return null;
		}
	}

	/** 
	* @Title: tt_blpop 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:49:11
	*
	* @param arg
	* @return     
	*/
	private List<String> tt_blpop(String arg) {
		Jedis jedis = this.getJedis();
		List<String> result = jedis.blpop(arg);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param arg
	 * @return
	 * @deprecated 
	 * @see redis.clients.jedis.JedisCommands#blpop(java.lang.String) 
	 */
	@Override
	public List<String> blpop(String arg) {
		if (null == this.logAnalyzer) {
			return tt_blpop(arg);
		}
		try {
			return tt_blpop(arg);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJsonByOthers("arg", arg));
			return null;
		}
	}

	/** 
	* @Title: tt_blpop 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:50:57
	*
	* @param timeout
	* @param key
	* @return     
	*/
	private List<String> tt_blpop(int timeout, String key) {
		Jedis jedis = this.getJedis();
		List<String> result = jedis.blpop(timeout, key);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param timeout
	 * @param key
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#blpop(int, java.lang.String) 
	 */
	@Override
	public List<String> blpop(int timeout, String key) {
		if (null == this.logAnalyzer) {
			return tt_blpop(timeout, key);
		}
		try {
			return tt_blpop(timeout, key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("timeout", timeout +"", "key", key));
			return null;
		}
	}

	/** 
	* @Title: tt_brpop 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:53:13
	*
	* @param arg
	* @return     
	*/
	private List<String> tt_brpop(String arg) {
		Jedis jedis = this.getJedis();
		List<String> result = jedis.brpop(arg);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param arg
	 * @return
	 * @deprecated 
	 * @see redis.clients.jedis.JedisCommands#brpop(java.lang.String) 
	 */
	@Override
	public List<String> brpop(String arg) {
		if (null == this.logAnalyzer) {
			return tt_brpop(arg);
		}
		try {
			return tt_brpop(arg);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJsonByOthers("arg", arg));
			return null;
		}
	}

	/** 
	* @Title: tt_brpop 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:54:46
	*
	* @param timeout
	* @param key
	* @return     
	*/
	private List<String> tt_brpop(int timeout, String key) {
		Jedis jedis = this.getJedis();
		List<String> result = jedis.brpop(timeout, key);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param timeout
	 * @param key
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#brpop(int, java.lang.String) 
	 */
	@Override
	public List<String> brpop(int timeout, String key) {
		if (null == this.logAnalyzer) {
			return tt_brpop(timeout, key);
		}
		try {
			return tt_brpop(timeout, key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("timeout", timeout +"", "key", key));
			return null;
		}
	}

	/** 
	* @Title: tt_del 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:57:13
	*
	* @param key
	* @return     
	*/
	private Long tt_del(String key) {
		Jedis jedis = this.getJedis();
		Long result = jedis.del(key);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#del(java.lang.String) 
	 */
	@Override
	public Long del(String key) {
		if (null == this.logAnalyzer) {
			return tt_del(key);
		}
		try {
			return tt_del(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
	}

	/** 
	* @Title: tt_echo 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午9:59:09
	*
	* @param string
	* @return     
	*/
	private String tt_echo(String string) {
		Jedis jedis = this.getJedis();
		String result = jedis.echo(string);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param string
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#echo(java.lang.String) 
	 */
	@Override
	public String echo(String string) {
		if (null == this.logAnalyzer) {
			return tt_echo(string);
		}
		try {
			return tt_echo(string);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJsonByOthers("string", string));
			return null;
		}
	}

	/** 
	* @Title: tt_move 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午10:01:46
	*
	* @param key
	* @param dbIndex
	* @return     
	*/
	private Long tt_move(String key, int dbIndex) {
		Jedis jedis = this.getJedis();
		Long result = jedis.move(key, dbIndex);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param dbIndex
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#move(java.lang.String, int) 
	 */
	@Override
	public Long move(String key, int dbIndex) {
		if (null == this.logAnalyzer) {
			return tt_move(key, dbIndex);
		}
		try {
			return tt_move(key, dbIndex);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, "dbIndex", dbIndex +""));
			return null;
		}
	}

	/** 
	* @Title: tt_bitcount 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午10:03:04
	*
	* @param key
	* @return     
	*/
	private Long tt_bitcount(String key) {
		Jedis jedis = this.getJedis();
		Long result = jedis.bitcount(key);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#bitcount(java.lang.String) 
	 */
	@Override
	public Long bitcount(String key) {
		if (null == this.logAnalyzer) {
			return tt_bitcount(key);
		}
		try {
			return tt_bitcount(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
	}

	/** 
	* @Title: tt_bitcount 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午10:04:36
	*
	* @param key
	* @param start
	* @param end
	* @return     
	*/
	private Long tt_bitcount(String key, long start, long end) {
		Jedis jedis = this.getJedis();
		Long result = jedis.bitcount(key, start, end);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param start
	 * @param end
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#bitcount(java.lang.String, long, long) 
	 */
	@Override
	public Long bitcount(String key, long start, long end) {
		if (null == this.logAnalyzer) {
			return tt_bitcount(key, start, end);
		}
		try {
			return tt_bitcount(key, start, end);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "start", start +"", "end", end +""));
			return null;
		}
	}

	/** 
	* @Title: tt_hscan 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午10:06:24
	*
	* @param key
	* @param cursor
	* @return     
	*/
	private ScanResult<Entry<String, String>> tt_hscan(String key, int cursor) {
		Jedis jedis = this.getJedis();
		ScanResult<Entry<String, String>> result = jedis.hscan(key, cursor);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param cursor
	 * @return
	 * @deprecated 
	 * @see redis.clients.jedis.JedisCommands#hscan(java.lang.String, int) 
	 */
	@Override
	public ScanResult<Entry<String, String>> hscan(String key, int cursor) {
		if (null == this.logAnalyzer) {
			return tt_hscan(key, cursor);
		}
		try {
			return tt_hscan(key, cursor);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, "cursor", cursor +""));
			return null;
		}
	}

	/** 
	* @Title: tt_sscan 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午10:08:08
	*
	* @param key
	* @param cursor
	* @return     
	*/
	private ScanResult<String> tt_sscan(String key, int cursor) {
		Jedis jedis = this.getJedis();
		ScanResult<String> result = jedis.sscan(key, cursor);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param cursor
	 * @return
	 * @deprecated 
	 * @see redis.clients.jedis.JedisCommands#sscan(java.lang.String, int) 
	 */
	@Override
	public ScanResult<String> sscan(String key, int cursor) {
		if (null == this.logAnalyzer) {
			return tt_sscan(key, cursor);
		}
		try {
			return tt_sscan(key, cursor);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, "cursor", cursor +""));
			return null;
		}
	}

	/** 
	* @Title: tt_zscan 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午10:09:25
	*
	* @param key
	* @param cursor
	* @return     
	*/
	private ScanResult<Tuple> tt_zscan(String key, int cursor) {
		Jedis jedis = this.getJedis();
		ScanResult<Tuple> result = jedis.zscan(key, cursor);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param cursor
	 * @return
	 * @deprecated 
	 * @see redis.clients.jedis.JedisCommands#zscan(java.lang.String, int) 
	 */
	@Override
	public ScanResult<Tuple> zscan(String key, int cursor) {
		if (null == this.logAnalyzer) {
			return tt_zscan(key, cursor);
		}
		try {
			return tt_zscan(key, cursor);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, "cursor", cursor +""));
			return null;
		}
	}

	/** 
	* @Title: tt_hscan 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午10:10:34
	*
	* @param key
	* @param cursor
	* @return     
	*/
	private ScanResult<Entry<String, String>> tt_hscan(String key, String cursor) {
		Jedis jedis = this.getJedis();
		ScanResult<Entry<String, String>> result = jedis.hscan(key, cursor);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param cursor
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#hscan(java.lang.String, java.lang.String) 
	 */
	@Override
	public ScanResult<Entry<String, String>> hscan(String key, String cursor) {
		if (null == this.logAnalyzer) {
			return tt_hscan(key, cursor);
		}
		try {
			return tt_hscan(key, cursor);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, "cursor", cursor));
			return null;
		}
	}

	/** 
	* @Title: tt_sscan 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午10:11:52
	*
	* @param key
	* @param cursor
	* @return     
	*/
	private ScanResult<String> tt_sscan(String key, String cursor) {
		Jedis jedis = this.getJedis();
		ScanResult<String> result = jedis.sscan(key, cursor);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param cursor
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#sscan(java.lang.String, java.lang.String) 
	 */
	@Override
	public ScanResult<String> sscan(String key, String cursor) {
		if (null == this.logAnalyzer) {
			return tt_sscan(key, cursor);
		}
		try {
			return tt_sscan(key, cursor);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, "cursor", cursor));
			return null;
		}
	}

	/** 
	* @Title: tt_zscan 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午10:13:02
	*
	* @param key
	* @param cursor
	* @return     
	*/
	private ScanResult<Tuple> tt_zscan(String key, String cursor) {
		Jedis jedis = this.getJedis();
		ScanResult<Tuple> result = jedis.zscan(key, cursor);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param cursor
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#zscan(java.lang.String, java.lang.String) 
	 */
	@Override
	public ScanResult<Tuple> zscan(String key, String cursor) {
		if (null == this.logAnalyzer) {
			return tt_zscan(key, cursor);
		}
		try {
			return tt_zscan(key, cursor);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, "cursor", cursor));
			return null;
		}
	}

	/** 
	* @Title: tt_pfadd 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午10:14:09
	*
	* @param key
	* @param elements
	* @return     
	*/
	private Long tt_pfadd(String key, String... elements) {
		Jedis jedis = this.getJedis();
		Long result = jedis.pfadd(key, elements);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @param elements
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#pfadd(java.lang.String, java.lang.String[]) 
	 */
	@Override
	public Long pfadd(String key, String... elements) {
		if (null == this.logAnalyzer) {
			return tt_pfadd(key, elements);
		}
		try {
			return tt_pfadd(key, elements);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJsonByArray(key, "elements", elements));
			return null;
		}
	}

	/** 
	* @Title: tt_pfcount 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author HeroCao herobigdatacao@126.com
	* @date 2016年8月9日 上午10:15:23
	*
	* @param key
	* @return     
	*/
	private long tt_pfcount(String key) {
		Jedis jedis = this.getJedis();
		Long result = jedis.pfcount(key);
		this.close(jedis);
		return result;
	}
	
	/**
	 * (non-Javadoc) 
	 *  
	 *  
	 * @param key
	 * @return 
	 * @see redis.clients.jedis.JedisCommands#pfcount(java.lang.String) 
	 */
	@Override
	public long pfcount(String key) {
		if (null == this.logAnalyzer) {
			return tt_pfcount(key);
		}
		try {
			return tt_pfcount(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return 0L;
		}
	}

	/**
	* (non-Javadoc) 
	*  
	*  
	* @param arg0
	* @param arg1
	* @return 
	* @see redis.clients.jedis.JedisCommands#bitpos(java.lang.String, boolean) 
	*/
	@Override
	public Long bitpos(String arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* (non-Javadoc) 
	*  
	*  
	* @param arg0
	* @param arg1
	* @param arg2
	* @return 
	* @see redis.clients.jedis.JedisCommands#bitpos(java.lang.String, boolean, redis.clients.jedis.BitPosParams) 
	*/
	@Override
	public Long bitpos(String arg0, boolean arg1, BitPosParams arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* (non-Javadoc) 
	*  
	*  
	* @param arg0
	* @param arg1
	* @return 
	* @see redis.clients.jedis.JedisCommands#geoadd(java.lang.String, java.util.Map) 
	*/
	@Override
	public Long geoadd(String arg0, Map<String, GeoCoordinate> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* (non-Javadoc) 
	*  
	*  
	* @param arg0
	* @param arg1
	* @param arg2
	* @param arg3
	* @return 
	* @see redis.clients.jedis.JedisCommands#geoadd(java.lang.String, double, double, java.lang.String) 
	*/
	@Override
	public Long geoadd(String arg0, double arg1, double arg2, String arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* (non-Javadoc) 
	*  
	*  
	* @param arg0
	* @param arg1
	* @param arg2
	* @return 
	* @see redis.clients.jedis.JedisCommands#geodist(java.lang.String, java.lang.String, java.lang.String) 
	*/
	@Override
	public Double geodist(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* (non-Javadoc) 
	*  
	*  
	* @param arg0
	* @param arg1
	* @param arg2
	* @param arg3
	* @return 
	* @see redis.clients.jedis.JedisCommands#geodist(java.lang.String, java.lang.String, java.lang.String, redis.clients.jedis.GeoUnit) 
	*/
	@Override
	public Double geodist(String arg0, String arg1, String arg2, GeoUnit arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* (non-Javadoc) 
	*  
	*  
	* @param arg0
	* @param arg1
	* @return 
	* @see redis.clients.jedis.JedisCommands#geohash(java.lang.String, java.lang.String[]) 
	*/
	@Override
	public List<String> geohash(String arg0, String... arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* (non-Javadoc) 
	*  
	*  
	* @param arg0
	* @param arg1
	* @return 
	* @see redis.clients.jedis.JedisCommands#geopos(java.lang.String, java.lang.String[]) 
	*/
	@Override
	public List<GeoCoordinate> geopos(String arg0, String... arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* (non-Javadoc) 
	*  
	*  
	* @param arg0
	* @param arg1
	* @param arg2
	* @param arg3
	* @param arg4
	* @return 
	* @see redis.clients.jedis.JedisCommands#georadius(java.lang.String, double, double, double, redis.clients.jedis.GeoUnit) 
	*/
	@Override
	public List<GeoRadiusResponse> georadius(String arg0, double arg1, double arg2, double arg3, GeoUnit arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* (non-Javadoc) 
	*  
	*  
	* @param arg0
	* @param arg1
	* @param arg2
	* @param arg3
	* @param arg4
	* @param arg5
	* @return 
	* @see redis.clients.jedis.JedisCommands#georadius(java.lang.String, double, double, double, redis.clients.jedis.GeoUnit, redis.clients.jedis.params.geo.GeoRadiusParam) 
	*/
	@Override
	public List<GeoRadiusResponse> georadius(String arg0, double arg1, double arg2, double arg3, GeoUnit arg4,
			GeoRadiusParam arg5) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* (non-Javadoc) 
	*  
	*  
	* @param arg0
	* @param arg1
	* @param arg2
	* @param arg3
	* @return 
	* @see redis.clients.jedis.JedisCommands#georadiusByMember(java.lang.String, java.lang.String, double, redis.clients.jedis.GeoUnit) 
	*/
	@Override
	public List<GeoRadiusResponse> georadiusByMember(String arg0, String arg1, double arg2, GeoUnit arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* (non-Javadoc) 
	*  
	*  
	* @param arg0
	* @param arg1
	* @param arg2
	* @param arg3
	* @param arg4
	* @return 
	* @see redis.clients.jedis.JedisCommands#georadiusByMember(java.lang.String, java.lang.String, double, redis.clients.jedis.GeoUnit, redis.clients.jedis.params.geo.GeoRadiusParam) 
	*/
	@Override
	public List<GeoRadiusResponse> georadiusByMember(String arg0, String arg1, double arg2, GeoUnit arg3,
			GeoRadiusParam arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* (non-Javadoc) 
	*  
	*  
	* @param arg0
	* @param arg1
	* @param arg2
	* @return 
	* @see redis.clients.jedis.JedisCommands#hincrByFloat(java.lang.String, java.lang.String, double) 
	*/
	@Override
	public Double hincrByFloat(String arg0, String arg1, double arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* (non-Javadoc) 
	*  
	*  
	* @param arg0
	* @param arg1
	* @param arg2
	* @return 
	* @see redis.clients.jedis.JedisCommands#hscan(java.lang.String, java.lang.String, redis.clients.jedis.ScanParams) 
	*/
	@Override
	public ScanResult<Entry<String, String>> hscan(String arg0, String arg1, ScanParams arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* (non-Javadoc) 
	*  
	*  
	* @param arg0
	* @param arg1
	* @param arg2
	* @return 
	* @see redis.clients.jedis.JedisCommands#psetex(java.lang.String, long, java.lang.String) 
	*/
	@Override
	public String psetex(String arg0, long arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* (non-Javadoc) 
	*  
	*  
	* @param arg0
	* @return 
	* @see redis.clients.jedis.JedisCommands#pttl(java.lang.String) 
	*/
	@Override
	public Long pttl(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* (non-Javadoc) 
	*  
	*  
	* @param arg0
	* @param arg1
	* @param arg2
	* @return 
	* @see redis.clients.jedis.JedisCommands#set(java.lang.String, java.lang.String, java.lang.String) 
	*/
	@Override
	public String set(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* (non-Javadoc) 
	*  
	*  
	* @param arg0
	* @param arg1
	* @param arg2
	* @return 
	* @see redis.clients.jedis.JedisCommands#sscan(java.lang.String, java.lang.String, redis.clients.jedis.ScanParams) 
	*/
	@Override
	public ScanResult<String> sscan(String arg0, String arg1, ScanParams arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* (non-Javadoc) 
	*  
	*  
	* @param arg0
	* @param arg1
	* @param arg2
	* @return 
	* @see redis.clients.jedis.JedisCommands#zadd(java.lang.String, java.util.Map, redis.clients.jedis.params.sortedset.ZAddParams) 
	*/
	@Override
	public Long zadd(String arg0, Map<String, Double> arg1, ZAddParams arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* (non-Javadoc) 
	*  
	*  
	* @param arg0
	* @param arg1
	* @param arg2
	* @param arg3
	* @return 
	* @see redis.clients.jedis.JedisCommands#zadd(java.lang.String, double, java.lang.String, redis.clients.jedis.params.sortedset.ZAddParams) 
	*/
	@Override
	public Long zadd(String arg0, double arg1, String arg2, ZAddParams arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* (non-Javadoc) 
	*  
	*  
	* @param arg0
	* @param arg1
	* @param arg2
	* @param arg3
	* @return 
	* @see redis.clients.jedis.JedisCommands#zincrby(java.lang.String, double, java.lang.String, redis.clients.jedis.params.sortedset.ZIncrByParams) 
	*/
	@Override
	public Double zincrby(String arg0, double arg1, String arg2, ZIncrByParams arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* (non-Javadoc) 
	*  
	*  
	* @param arg0
	* @param arg1
	* @param arg2
	* @return 
	* @see redis.clients.jedis.JedisCommands#zscan(java.lang.String, java.lang.String, redis.clients.jedis.ScanParams) 
	*/
	@Override
	public ScanResult<Tuple> zscan(String arg0, String arg1, ScanParams arg2) {
		// TODO Auto-generated method stub
		return null;
	}

}
