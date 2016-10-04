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
* @Title: TTClusterJedis.java 
* @Package xyz.kaka.common.redis.clients.jedis.impl 
* @Description: Jedis集群封装
* @Company: xyz.hero.cao
* @author HeroCao herobigdatacao@126.com  
* @date 2016年8月7日 下午12:04:39 
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
import redis.clients.jedis.JedisCluster;
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
* @ClassName: KakaJedisCluster 
* @Description: 集群版本Jedis 
* @Company: xyz.hero.cao
* @author HeroCao herobigdatacao@126.com
* @date 2016年8月7日 下午12:04:39 
*  
*/
public class KakaJedisCluster implements KakaJedis {
	
	@Autowired
	private JedisCluster jedisCluster;
	
	private LogAnalyzer logAnalyzer;
	
	public KakaJedisCluster() {
		super();
	}
	
	/**
	 * @param logAnalyzer 日志分析器
	 */
	public KakaJedisCluster(LogAnalyzer logAnalyzer) {
		super();
		this.logAnalyzer = logAnalyzer;
	}
	
//	private void close() {
//		// 由于Spring 容器中bean 默认是单例，整个应用就一个实例，不可以关闭！！！
////		this.jedisCluster.close();
//	}

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
			return this.jedisCluster.set(key, value);
		}
		try {
			return this.jedisCluster.set(key, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, value));
			return null;
		}
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
			return this.jedisCluster.set(key, value, nxxx, expx, time);
		} 
		try {
			return this.jedisCluster.set(key, value, nxxx, expx, time);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "value", value, "nxxx", nxxx, "expx", expx, "time", time +""));
			return null;
		}
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
			return this.jedisCluster.get(key);
		} 
		try {
			return this.jedisCluster.get(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
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
			return this.jedisCluster.exists(key);
		}
		try {
			return this.jedisCluster.exists(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
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
			return this.jedisCluster.persist(key);
		} 
		try {
			return this.jedisCluster.persist(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
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
			return this.jedisCluster.type(key);
		} 
		try {
			return this.jedisCluster.type(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
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
			return this.jedisCluster.expire(key, seconds);
		}
		try {
			return this.jedisCluster.expire(key, seconds);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, seconds));
			return null;
		}
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
			return this.jedisCluster.pexpire(key, milliseconds);
		} 
		try { 
			return this.jedisCluster.pexpire(key, milliseconds);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, milliseconds));
			return null;
		}
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
			return this.jedisCluster.expireAt(key, unixTime);
		} 
		try {
			return this.jedisCluster.expireAt(key, unixTime);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "unixTime", unixTime +""));
			return null;
		}
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
			return this.jedisCluster.pexpireAt(key, millisecondsTimestamp);
		}
		try {
			return this.jedisCluster.pexpireAt(key, millisecondsTimestamp);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "millisecondsTimestamp", millisecondsTimestamp +""));
			return null;
		}
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
			return this.jedisCluster.ttl(key);
		}
		try {
			return this.jedisCluster.ttl(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
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
			return this.jedisCluster.setbit(key, offset, value);
		} 
		try {
			return this.jedisCluster.setbit(key, offset, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "offset", offset +"", "value", value +""));
			return null;
		}
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
			return this.jedisCluster.setbit(key, offset, value);
		}
		try {
			return this.jedisCluster.setbit(key, offset, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "offset", offset +"", "value", value));
			return null;
		}
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
			return this.jedisCluster.getbit(key, offset);
		}
		try {
			return this.jedisCluster.getbit(key, offset);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "offset", offset +""));
			return null;
		}
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
			return this.jedisCluster.setrange(key, offset, value);
		}
		try {
			return this.jedisCluster.setrange(key, offset, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "offset", offset +"", "value", value));
			return null;
		}
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
			return this.jedisCluster.getrange(key, startOffset, endOffset);
		}
		try {
			return this.jedisCluster.getrange(key, startOffset, endOffset);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "startOffset", startOffset +"", "endOffset", endOffset +""));
			return null;
		}
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
			return this.jedisCluster.getSet(key, value);
		} 
		try {
			return this.jedisCluster.getSet(key, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "value", value));
			return null;
		}
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
			return this.jedisCluster.setnx(key, value);
		}
		try {
			return this.jedisCluster.setnx(key, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "value", value));
			return null;
		}
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
			return this.jedisCluster.setex(key, seconds, value);
		}
		try {
			return this.jedisCluster.setex(key, seconds, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "seconds", seconds +"",  "value", value));
			return null;
		}
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
			return this.jedisCluster.decrBy(key, integer);
		}
		try {
			return this.jedisCluster.decrBy(key, integer);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "integer", integer +""));
			return null;
		}
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
			return this.jedisCluster.decr(key);
		}
		try {
			return this.jedisCluster.decr(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
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
			return this.jedisCluster.incrBy(key, integer);
		}
		try {
			return this.jedisCluster.incrBy(key, integer);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "integer", integer +""));
			return null;
		}
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
			return this.jedisCluster.incrByFloat(key, value);
		}
		try {
			return this.jedisCluster.incrByFloat(key, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "value", value +""));
			return null;
		}
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
			return this.jedisCluster.incr(key);
		}
		try {
			return this.jedisCluster.incr(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
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
			return this.jedisCluster.append(key, value);
		}
		try {
			return this.jedisCluster.append(key, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, value));
			return null;
		}
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
			return this.jedisCluster.substr(key, start, end);
		}
		try {
			return this.jedisCluster.substr(key, start, end);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "start", start +"", "end", end +""));
			return null;
		}
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
			return this.jedisCluster.hset(key, field, value);
		}
		try {
			return this.jedisCluster.hset(key, field, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "field", field, "value", value));
			return null;
		}
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
			return this.jedisCluster.hget(key, field);
		}
		try {
			return this.jedisCluster.hget(key, field);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "field", field));
			return null;
		}
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
			return this.jedisCluster.hsetnx(key, field, value);
		}
		try {
			return this.jedisCluster.hsetnx(key, field, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "field", field, "value", value));
			return null;
		}
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
			return this.jedisCluster.hmset(key, hash);
		}
		try {
			return this.jedisCluster.hmset(key, hash);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, "hash", hash));
			return null;
		}
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
			return this.jedisCluster.hmget(key, fields);
		}
		try {
			return this.jedisCluster.hmget(key, fields);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJsonByArray(key, "fields", fields));
			return null;
		}
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
			return this.jedisCluster.hincrBy(key, field, value);
		}
		try {
			return this.jedisCluster.hincrBy(key, field, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "field", field, "value", value +""));
			return null;
		}
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
			return this.jedisCluster.hexists(key, field);
		}
		try {
			return this.jedisCluster.hexists(key, field);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "field", field));
			return null;
		}
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
			return this.jedisCluster.hdel(key, field);
		}
		try {
			return this.jedisCluster.hdel(key, field);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJsonByArray(key, "field", field));
			return null;
		}
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
			return this.jedisCluster.hlen(key);
		}
		try {
			return this.jedisCluster.hlen(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
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
			return this.jedisCluster.hkeys(key);
		}
		try {
			return this.jedisCluster.hkeys(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
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
			return this.jedisCluster.hvals(key);
		}
		try {
			return this.jedisCluster.hvals(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
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
			return this.jedisCluster.hgetAll(key);
		}
		try {
			return this.jedisCluster.hgetAll(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
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
			return this.jedisCluster.rpush(key, string);
		}
		try {
			return this.jedisCluster.rpush(key, string);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJsonByArray(key, "string", string));
			return null;
		}
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
			return this.jedisCluster.lpush(key, string);
		}
		try {
			return this.jedisCluster.lpush(key, string);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJsonByArray(key, "string", string));
			return null;
		}
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
			return this.jedisCluster.llen(key);
		}
		try {
			return this.jedisCluster.llen(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
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
			return this.jedisCluster.lrange(key, start, end);
		}
		try {
			return this.jedisCluster.lrange(key, start, end);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "start", start +"", "end", end +""));
			return null;
		}
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
			return this.jedisCluster.ltrim(key, start, end);
		}
		try {
			return this.jedisCluster.ltrim(key, start, end);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "start", start +"", "end", end +""));
			return null;
		}
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
			return this.jedisCluster.lindex(key, index);
		}
		try {
			return this.jedisCluster.lindex(key, index);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "index", index +""));
			return null;
		}
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
			return this.jedisCluster.lset(key, index, value);
		}
		try {
			return this.jedisCluster.lset(key, index, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "index", index +"", "value", value));
			return null;
		}
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
			return this.jedisCluster.lrem(key, count, value);
		}
		try {
			return this.jedisCluster.lrem(key, count, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "count", count +"", "value", value));
			return null;
		}
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
			return this.jedisCluster.lpop(key);
		}
		try {
			return this.jedisCluster.lpop(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
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
			return this.jedisCluster.rpop(key);
		}
		try {
			return this.jedisCluster.rpop(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
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
			return this.jedisCluster.sadd(key, member);
		}
		try {
			return this.jedisCluster.sadd(key, member);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJsonByArray(key, "member", member));
			return null;
		}
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
			return this.jedisCluster.smembers(key);
		}
		try {
			return this.jedisCluster.smembers(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
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
			return this.jedisCluster.srem(key, member);
		}
		try {
			return this.jedisCluster.srem(key, member);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJsonByArray(key, "member", member));
			return null;
		}
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
			return this.jedisCluster.spop(key);
		}
		try {
			return this.jedisCluster.spop(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
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
			return this.jedisCluster.spop(key, count);
		}
		try {
			return this.jedisCluster.spop(key, count);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "count", count +""));
			return null;
		}
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
			return this.jedisCluster.scard(key);
		}
		try {
			return this.jedisCluster.scard(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
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
			return this.jedisCluster.sismember(key, member);
		}
		try {
			return this.jedisCluster.sismember(key, member);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "member", member));
			return null;
		}
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
			return this.jedisCluster.srandmember(key);
		}
		try {
			return this.jedisCluster.srandmember(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
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
			return this.jedisCluster.srandmember(key, count);
		}
		try {
			return this.jedisCluster.srandmember(key, count);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "count", count +""));
			return null;
		}
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
			return this.jedisCluster.strlen(key);
		}
		try {
			return this.jedisCluster.strlen(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
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
			return this.jedisCluster.zadd(key, score, member);
		}
		try {
			return this.jedisCluster.zadd(key, score, member);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "score", score +"", "member", member));
			return null;
		}
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
			return this.jedisCluster.zadd(key, scoreMembers);
		}
		try {
			return this.jedisCluster.zadd(key, scoreMembers);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "scoreMembers", scoreMembers));
			return null;
		}
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
			return this.jedisCluster.zrange(key, start, end);
		}
		try {
			return this.jedisCluster.zrange(key, start, end);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "start", start +"", "end", end +""));
			return null;
		}
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
			return this.jedisCluster.zrem(key, member);
		}
		try {
			return this.jedisCluster.zrem(key, member);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJsonByArray(key, "member", member));
			return null;
		}
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
			return this.jedisCluster.zincrby(key, score, member);
		}
		try {
			return this.jedisCluster.zincrby(key, score, member);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "score", score +"", "member", member));
			return null;
		}
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
			return this.jedisCluster.zrank(key, member);
		}
		try {
			return this.jedisCluster.zrank(key, member);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "member", member));
			return null;
		}
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
		return this.jedisCluster.zrevrank(key, member);
		}
		try {
			return this.jedisCluster.zrevrank(key, member);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "member", member));
			return null;
		}
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
			return this.jedisCluster.zrevrange(key, start, end);
		}
		try {
			return this.jedisCluster.zrevrange(key, start, end);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "start", start +"", "end", end +""));
			return null;
		}
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
			return this.jedisCluster.zrangeWithScores(key, start, end);
		}
		try {
			return this.jedisCluster.zrangeWithScores(key, start, end);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "start", start +"", "end", end +""));
			return null;
		}
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
			return this.jedisCluster.zrevrangeWithScores(key, start, end);
		}
		try {
			return this.jedisCluster.zrevrangeWithScores(key, start, end);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "start", start +"", "end", end +""));
			return null;
		}
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
			return this.jedisCluster.zcard(key);
		}
		try {
			return this.jedisCluster.zcard(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
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
			return this.jedisCluster.zscore(key, member);
		}
		try {
			return this.jedisCluster.zscore(key, member);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "member", member));
			return null;
		}
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
			return this.jedisCluster.sort(key);
		}
		try {
			return this.jedisCluster.sort(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));
			return null;
		}
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
			return this.jedisCluster.sort(key, sortingParameters);
		}
		try {
			return this.jedisCluster.sort(key, sortingParameters);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, sortingParameters));
			return null;
		}
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
			return this.jedisCluster.zcount(key, min, max);
		}
		try {
			return this.jedisCluster.zcount(key, min, max);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min +"", "max", max +""));
			return null;
		}
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
			return this.jedisCluster.zcount(key, min, max);
		}
		try {
			return this.jedisCluster.zcount(key, min, max);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min, "max", max));
			return null;
		}
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
			return this.jedisCluster.zrangeByScore(key, min, max);
		}
		try {
			return this.jedisCluster.zrangeByScore(key, min, max);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min +"", "max", max +""));
			return null;
		}
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
			return this.jedisCluster.zrangeByScore(key, min, max);
		}
		try {
			return this.jedisCluster.zrangeByScore(key, min, max);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min, "max", max));
			return null;
		}
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
			return this.jedisCluster.zrevrangeByScore(key, max, min);
		}
		try {
			return this.jedisCluster.zrevrangeByScore(key, max, min);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min +"", "max", max +""));
			return null;
		}
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
			return this.jedisCluster.zrangeByScore(key, min, max, offset, count);
		}
		try {
			return this.jedisCluster.zrangeByScore(key, min, max, offset, count);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min +"", "max", max +"", "offset", offset +"", "count", count +""));
			return null;
		}
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
			return this.jedisCluster.zrevrangeByScore(key, max, min);
		}
		try {
			return this.jedisCluster.zrevrangeByScore(key, max, min);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "max", max, "min", min));
			return null;
		}
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
			return this.jedisCluster.zrangeByScore(key, min, max, offset, count);
		}
		try {
			return this.jedisCluster.zrangeByScore(key, min, max, offset, count);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min +"", "max", max +"", "offset", offset +"", "count", count +""));
			return null;
		}
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
			return this.jedisCluster.zrevrangeByScore(key, max, min, offset, count);
		}
		try {
			return this.jedisCluster.zrevrangeByScore(key, max, min, offset, count);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "max", max +"", "min", min +"", "offset", offset +"", "count", count +""));
			return null;
		}
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
			return this.jedisCluster.zrangeByScoreWithScores(key, min, max);
		}
		try {
			return this.jedisCluster.zrangeByScoreWithScores(key, min, max);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min +"", "max", max +""));
			return null;
		}
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
			return this.jedisCluster.zrevrangeByScoreWithScores(key, max, min);
		}
		try {
			return this.jedisCluster.zrevrangeByScoreWithScores(key, max, min);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "max", max +"", "min", min +""));
			return null;
		}
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
			return this.jedisCluster.zrangeByScoreWithScores(key, min, max, offset, count);
		}
		try {
			return this.jedisCluster.zrangeByScoreWithScores(key, min, max, offset, count);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min +"", "max", max +"", "offset", offset +"", "count", count +""));
			return null;
		}
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
			return this.jedisCluster.zrevrangeByScore(key, max, min, offset, count);
		}
		try {
			return this.jedisCluster.zrevrangeByScore(key, max, min, offset, count);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "max", max +"", "min", min +"", "offset", offset +"", "count", count +""));
			return null;
		}
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
			return this.jedisCluster.zrangeByScoreWithScores(key, min, max);
		}
		try {
			return this.jedisCluster.zrangeByScoreWithScores(key, min, max);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min +"", "max", max +""));
			return null;
		}
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
			return this.jedisCluster.zrevrangeByScoreWithScores(key, max, min);
		}
		try {
			return this.jedisCluster.zrevrangeByScoreWithScores(key, max, min);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "max", max +"", "min", min +""));
			return null;
		}
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
			return this.jedisCluster.zrangeByScoreWithScores(key, min, max, offset, count);
		}
		try {
			return this.jedisCluster.zrangeByScoreWithScores(key, min, max, offset, count);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min +"", "max", max +"", "offset", offset +"", "count", count +""));
			return null;
		}
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
			return this.jedisCluster.zrevrangeByScoreWithScores(key, max, min, offset, count);
		}
		try {
			return this.jedisCluster.zrevrangeByScoreWithScores(key, max, min, offset, count);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "max", max +"", "min", min +"", "offset", offset +"", "count", count +""));
			return null;
		}
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
			return this.jedisCluster.zrevrangeByScoreWithScores(key, max, min, offset, count);
		}
		try {
			return this.jedisCluster.zrevrangeByScoreWithScores(key, max, min, offset, count);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "max", max +"", "min", min +"", "offset", offset +"", "count", count +""));
			return null;
		}
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
			return this.jedisCluster.zremrangeByRank(key, start, end);
		}
		try {
			return this.jedisCluster.zremrangeByRank(key, start, end);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "start", start +"", "end", end +""));
			return null;
		}
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
			return this.jedisCluster.zremrangeByScore(key, start, end);
		}
		try {
			return this.jedisCluster.zremrangeByScore(key, start, end);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "start", start +"", "end", end +""));
			return null;
		}
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
			return this.jedisCluster.zremrangeByScore(key, start, end);
		}
		try {
			return this.jedisCluster.zremrangeByScore(key, start, end);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "start", start, "end", end));
			return null;
		}
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
			return this.jedisCluster.zlexcount(key, min, max);
		}
		try {
			return this.jedisCluster.zlexcount(key, min, max);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min, "max", max));
			return null;
		}
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
			return this.jedisCluster.zrangeByLex(key, min, max);
		}
		try {
			return this.jedisCluster.zrangeByLex(key, min, max);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min, "max", max));
			return null;
		}
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
			return this.jedisCluster.zrangeByLex(key, min, max, offset, count);
		}
		try {
			return this.jedisCluster.zrangeByLex(key, min, max, offset, count);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min, "max", max, "offset", offset +"", "count", count +""));
			return null;
		}
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
			return this.jedisCluster.zrevrangeByLex(key, max, min);
		}
		try {
			return this.jedisCluster.zrevrangeByLex(key, max, min);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "max", max, "min", min));
			return null;
		}
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
			return this.jedisCluster.zrevrangeByLex(key, max, min, offset, count);
		}
		try {
			return this.jedisCluster.zrevrangeByLex(key, max, min, offset, count);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "max", max, "min", min, "offset", offset +"", "count", count +""));
			return null;
		}
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
			return this.jedisCluster.zremrangeByLex(key, min, max);
		}
		try {
			return this.jedisCluster.zremrangeByLex(key, min, max);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "min", min, "max", max));
			return null;
		}
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
			return this.jedisCluster.linsert(key, where, pivot, value);
		}
		try {
			return this.jedisCluster.linsert(key, where, pivot, value);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key, where, pivot, value));
			return null;
		}
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
			return this.jedisCluster.lpushx(key, string);
		}
		try {
			return this.jedisCluster.lpushx(key, string);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJsonByArray(key, "string", string));
			return null;
		}
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
			return this.jedisCluster.rpushx(key, string);
		}
		try {
			return this.jedisCluster.rpushx(key, string);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJsonByArray(key, "string", string));
			return null;
		}
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
			return this.jedisCluster.blpop(arg);
		}
		try {
			return this.jedisCluster.blpop(arg);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJsonByOthers("arg", arg));  
			return null;
		}
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
			return this.jedisCluster.blpop(timeout, key);
		}
		try {
			return this.jedisCluster.blpop(timeout, key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("timeout", timeout +"", "key", key));  
			return null;
		}
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
			return this.jedisCluster.brpop(arg);
		}
		try {
			return this.jedisCluster.brpop(arg);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJsonByOthers("arg", arg));  
			return null;
		}
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
			return this.jedisCluster.brpop(timeout, key);
		}
		try {
			return this.jedisCluster.brpop(timeout, key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("timeout", timeout +"", "key", key));  
			return null;
		}
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
			return this.jedisCluster.del(key);
		}
		try {
			return this.jedisCluster.del(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));  
			return null;
		}
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
			return this.jedisCluster.echo(string);
		}
		try {
			return this.jedisCluster.echo(string);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJsonByOthers("string", string));  
			return null;
		}
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
			return this.jedisCluster.move(key, dbIndex);
		}
		try {
			return this.jedisCluster.move(key, dbIndex);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "dbIndex", dbIndex +""));  
			return null;
		}
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
			return this.jedisCluster.bitcount(key);
		}
		try {
			return this.jedisCluster.bitcount(key);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson(key));  
			return null;
		}
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
			return this.jedisCluster.bitcount(key, start, end);
		}
		try {
			return this.jedisCluster.bitcount(key, start, end);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "start", start +"", "end", end +""));
			return null;
		}
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
			return this.jedisCluster.hscan(key, cursor);
		}
		try {
			return this.jedisCluster.hscan(key, cursor);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "cursor", cursor +""));
			return null;
		}
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
			return this.jedisCluster.sscan(key, cursor);
		}
		try {
			return this.jedisCluster.sscan(key, cursor);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "cursor", cursor +""));
			return null;
		}
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
			return this.jedisCluster.zscan(key, cursor);
		}
		try {
			return this.jedisCluster.zscan(key, cursor);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "cursor", cursor +""));
			return null;
		}
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
			return this.jedisCluster.hscan(key, cursor);
		}
		try {
			return this.jedisCluster.hscan(key, cursor);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "cursor", cursor));
			return null;
		}
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
			return this.jedisCluster.sscan(key, cursor);
		}
		try {
			return this.jedisCluster.sscan(key, cursor);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "cursor", cursor));
			return null;
		}
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
			return this.jedisCluster.zscan(key, cursor);
		}
		try {
			return this.jedisCluster.zscan(key, cursor);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJson("key", key, "cursor", cursor));
			return null;
		}
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
			return this.jedisCluster.pfadd(key, elements);
		}
		try {
			return this.jedisCluster.pfadd(key, elements);
		} catch (Exception ex) {
			this.logAnalyzer.analyze(ex, LogAnalyzerUtils.getLogAnalyzerJsonByArray(key, "elements", elements));
			return null;
		}
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
			return this.jedisCluster.pfcount(key);
		}
		try {
			return this.jedisCluster.pfcount(key);
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
