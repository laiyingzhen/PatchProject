package org.aimes.redis.app;

import org.aimes.redis.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisMain {
    public static void main(String[] args) {
        int redisDb = 1;
        JedisPool jedisPool = JedisUtil.getJedisPool();
        JedisUtil.setJedisPool(jedisPool);
        Jedis jedis = JedisUtil.getJedis(redisDb);
        jedis.set("qatest009@JC","mongoDbPaging");
        System.out.println(jedis.get("qatest009@JC"));
        jedis.close();
    }
}
