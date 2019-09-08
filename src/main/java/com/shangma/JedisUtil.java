package com.shangma;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JedisUtil {


    private static JedisPool jedisPool;

    static {
        //从配置文件获得输入流
        InputStream in = JedisUtil.class.getClassLoader().getResourceAsStream("redis.properties");

        //properties对象得到配置信息
        Properties properties = new Properties();
        try {
            properties.load(in);
            //创建JedisPoolConfig配置类
            JedisPoolConfig config = new JedisPoolConfig();
            // 最大空闲数
            config.setMaxIdle(Integer.parseInt(properties.getProperty("maxIdle")));
            // 最大连接数
            config.setMaxTotal(Integer.parseInt(properties.getProperty("maxTotal")));
            //ip地址
            String host = properties.getProperty("host");
            //端口号
            int port = Integer.parseInt(properties.getProperty("port"));

            jedisPool = new JedisPool(config, host, port);

        } catch (IOException e) {
            throw new RuntimeException("配置文件加载失败");
        }

    }

    /**
     * 获得Jedis对象方法
     */
    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    /**
     * 释放资源
     */
    public static void release(Jedis jedis) {
        //这个表示当前jedis链接 放到我们链接池中  并不是关闭了
        jedis.close();
    }

}
