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

    public static boolean putRedisCache(String key, Object obj) {
        return putRedisCache(defaultRedisDB, key, obj);
    }

    public static boolean putRedisCache(String key, Object obj, int expireTime) {
        return putRedisCache(defaultRedisDB, key, obj, expireTime);
    }

    public static boolean putRedisCache(int redisDB, String key, Object obj) {
        return putRedisCache(redisDB, key, obj, -1);
    }

    public static boolean putRedisCache(Jedis jedis, int redisDB, String key, Object obj) {
        return putRedisCache(jedis, redisDB, key, obj, -1);
    }

    public static boolean putRedisCache(int redisDB, String key, Object obj, int expireTime) {
        Jedis jedis = null;
        boolean result = false;

        try {
            jedis = getJedis();
            result = putRedisCache(jedis, redisDB, key, obj, expireTime);
        } finally {
            returnJedis2Pool(jedis);
        }

        return result;
    }

    public static boolean putRedisCache(Jedis jedis, int redisDB, String key, Object obj, int expireTime) {
        String result = "";
        jedis.select(redisDB);
        if (expireTime > 0) {
            result = jedis.setex(key.getBytes(), expireTime, SerializeUtil.serialize(obj));
        } else {
            result = jedis.set(key.getBytes(), SerializeUtil.serialize(obj));
        }

        return "OK".equals(result);
    }

    public static boolean putRedisCacheIncrBy(int redisDB, String key, int incrBy, int expireTime) {
        Jedis jedis = null;
        boolean result = false;

        try {
            jedis = getJedis();
            result = putRedisCacheIncrBy(jedis, redisDB, key, incrBy, expireTime);
        } finally {
            returnJedis2Pool(jedis);
        }

        return result;
    }

    public static boolean putRedisCacheIncrBy(Jedis jedis, int redisDB, String key, int incrBy, int expireTime) {
        jedis.select(redisDB);
        Long result = jedis.incrBy(key, (long)incrBy);
        if (expireTime > 0) {
            jedis.expire(key, expireTime);
        }

        return result > 0L;
    }

    public static Object getRedisObject(String key) {
        return getRedisObject(defaultRedisDB, key);
    }

    public static Object getRedisObject(int redisDB, String key) {
        Jedis jedis = null;

        Object var5;
        try {
            jedis = getJedis(redisDB);
            byte[] bytes = jedis.get(key.getBytes());
            Object var4;
            if (bytes != null) {
                var4 = SerializeUtil.unserialize(bytes);
                return var4;
            }

            var4 = null;
            var5 = var4;
        } finally {
            returnJedis2Pool(jedis);
        }

        return var5;
    }

    public static boolean removeRedisObject(String key) {
        return removeRedisObject(defaultRedisDB, key);
    }

    public static boolean removeRedisObject(int redisDB, String key) {
        Jedis jedis = null;
        boolean result = Boolean.FALSE;

        try {
            jedis = getJedis(redisDB);
            result = jedis.del(key) > 0L;
        } finally {
            returnJedis2Pool(jedis);
        }

        return result;
    }
    public static void returnJedis2Pool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }

    }
}
