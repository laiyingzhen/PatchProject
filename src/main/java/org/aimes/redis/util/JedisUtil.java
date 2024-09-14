package org.aimes.redis.util;

import lombok.Data;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Data
public class JedisUtil {
    private static JedisPool jedisPool;
    private static int defaultRedisDB = 0;
    public static void setJedisPool(JedisPool paramsJedisPool){
        jedisPool = paramsJedisPool;
    }
    public static Jedis getJedis(){
        Jedis jedis = jedisPool.getResource();
        String connStatus = jedis.ping();
        System.out.println("connStatus = " + connStatus);
        return jedis;
    }
    public static Jedis getJedis(int redisDb){
        Jedis jedis = jedisPool.getResource();
        String connStatus = jedis.ping();
        System.out.println("connStatus = " + connStatus);
        // index = db name
        jedis.select(redisDb);
        return jedis;
    }
    public static JedisPool getJedisPool(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxWaitMillis(3000);
        jedisPoolConfig.setMaxTotal(1000);
        jedisPoolConfig.setMaxIdle(500);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1",6379);
        return jedisPool;
    }
}
