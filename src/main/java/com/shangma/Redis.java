package com.shangma;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Set;

public class Redis {

    /**
     * Jedis的初体验
     */

    //@Test的注解方法不能有参数，不能有返回值，修饰符必须为public
    @Test
    public void fun() {
        //如果不写 域名端口 默认就是 本机 默认端口就是6379
        //Jedis jedis  = new Jedis();
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.set("name", "huige");
        String name = jedis.get("name");
        System.out.println(name);

        jedis.close();//释放资源
    }


    /**
     * 对hash的操作
     */
    @Test
    public void fun1() {
        Jedis jedis = new Jedis();
        jedis.hset("wife", "name", "高圆圆");
        String name = jedis.hget("wife", "name");
        System.out.println(name);
        jedis.close();//释放资源
    }


    /**从自定义连接池类Pool，调用创建销毁jedis方法
     * 对list 的操作
     */
    @Test
    public void fun2() {
        Jedis jedis = Pool.getJedis();
        //Jedis jedis = new Jedis();

        //jedis.lpush("s1", "aaa", "bbb", "cccc");
        jedis.rpush("s1", "ddd", "eee");
        //0，-1包含全部元素
        List<String> list = jedis.lrange("s1", 0, -1);
        list.forEach(System.out::println);

        Pool.releaseJedis(jedis);
        //jedis.close();//释放资源

    }

    /**
     * 从连接池类JedisPoolConfig，调用创建销毁jedis方法
     */
    @Test
    public void pool() {
        //创建JedisPoolConfig配置类
        JedisPoolConfig config  = new JedisPoolConfig();
        // 设置最大空闲数
        config.setMaxIdle(10);
        // 设置最大连接数
        config.setMaxTotal(50);

        //通过配置类创建连接池
        JedisPool pool = new JedisPool(config,"localhost",6379);
        // 在连接池中取出Jedis连接
        Jedis jedis = pool.getResource();


        jedis.set("haha","窗前明月光");
        String ss = jedis.get("haha");
        System.out.println(ss);

    }

    /**
     * 从自定义的连接池工具类JedisUtil，调用创建销毁jedis方法
     */
    @Test
    public void pool1() {
        Jedis jedis = JedisUtil.getJedis();
        jedis.set("haha", "窗前明月光");
        String haha = jedis.get("haha");
        System.out.println(haha);
        JedisUtil.release(jedis);
    }

    /**
     * 对set 的操作
     */
    @Test
    public void fun3() {
        Jedis jedis = new Jedis();
        jedis.sadd("s2", "zs", "ls", "ww");
        Set<String> s2 = jedis.smembers("s2");
        s2.forEach(System.out::println);
        jedis.close();//释放资源
    }


    /**
     * 对sortSet 集合
     */
    @Test
    public void fun4() {
        Jedis jedis = new Jedis();
        jedis.zadd("mv",20,"如花");
        Set<String> mv = jedis.zrange("mv", 0, -1);
        mv.forEach(System.out::println);
        jedis.close();//释放资源
    }



}
