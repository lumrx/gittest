package com.taotao.manage.service.redis.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.manage.service.redis.RedisFunction;
import com.taotao.manage.service.redis.RedisService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisPoolService implements RedisService {
	
	@Autowired
	private JedisPool jedisPool;
	
	private  <T> T execute(RedisFunction<T, Jedis> fun) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return fun.callback(jedis);
		} finally {
			jedis.close();
		}
	}

	@Override
	public String set(final String key, final String value) {
		
		return execute(new RedisFunction<String, Jedis>() {

			@Override
			public String callback(Jedis jedis) {
				return jedis.set(key, value);
			}
		});
	}

	@Override
	public String setex(String key, int seconds, String value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String str = jedis.setex(key, seconds, value);
			return str;
		} finally {
			jedis.close();
		}
	}

	@Override
	public Long expire(String key, int seconds) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Long expire = jedis.expire(key, seconds);
			return expire;
		} finally {
			jedis.close();
		}
	}

	@Override
	public String get(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String str = jedis.get(key);
			return str;
		} finally {
			jedis.close();
		}
	}

	@Override
	public Long del(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Long expire = jedis.del(key);
			return expire;
		} finally {
			jedis.close();
		}
	}

	@Override
	public Long incr(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Long expire = jedis.incr(key);
			return expire;
		} finally {
			jedis.close();
		}
	}
}
