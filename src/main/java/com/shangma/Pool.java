package com.shangma;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.LinkedList;

public class Pool {

    /**
     * 连接池的使用
     */

    //连接池，LinkedList集合，便于回收
    public static LinkedList<Jedis> list=new LinkedList<>();
    static {
        for(int i=0;i<10;i++){
            Jedis jedis=new Jedis();
            list.add(jedis);
        }
    }

    //从连接池获取jedis
    public static Jedis getJedis(){
        return list.removeFirst();
    }

    //归还jedis到连接池
    public static void releaseJedis(Jedis jedis){
       list.addLast(jedis);
    }



}
