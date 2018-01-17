package com.lk.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.*;
import java.util.concurrent.Callable;

/**
 * redis缓存工具类
 * @author likun
 * @date 2018-01-14
 */
public class RedisCache implements Cache {

    @Value("${cacheTime}")
    private long liveTime;

    private RedisTemplate<String, Object> redisTemplate;
    private String name;
    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this.redisTemplate;
    }

    @Override
    public ValueWrapper get(Object key) {
        System.out.println("从redis中取值...");
        final String keyf =  key.toString();
        Object object = null;
        object = redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] key1 = keyf.getBytes();
            byte[] value = connection.get(key1);
            if (value == null) {
                return null;
            }
            return toObject(value);
        });
        return (object != null ? new SimpleValueWrapper(object) : null);
    }

    @Override
    public void put(Object key, Object value) {
        System.out.println("put key");
        final String keyf = key.toString();
        final Object valuef = value;
        redisTemplate.execute((RedisCallback<Long>) connection -> {
            byte[] keyb = keyf.getBytes();
            byte[] valueb = toByteArray(valuef);
            connection.set(keyb, valueb);
            if (liveTime > 0) {
                connection.expire(keyb, liveTime);
            }
            return 1L;
        });
    }

    private byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        }catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }

    private Object toObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    @Override
    public void evict(Object key) {
        System.out.println("del key");
        final String keyf = key.toString();
        redisTemplate.execute((RedisCallback<Long>) connection -> connection.del(keyf.getBytes()));
    }

    @Override
    public void clear() {
        System.out.println("clear key");
        redisTemplate.execute((RedisCallback<String>) connection -> {
            connection.flushDb();
            return "ok";
        });
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        return null;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return null;
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        return null;
    }
}
