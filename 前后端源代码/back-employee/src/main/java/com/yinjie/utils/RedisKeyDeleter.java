package com.yinjie.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.List;
/*
*
* 用来批量删除key
* */
public class RedisKeyDeleter {
    private Jedis jedis;

    public RedisKeyDeleter(String host, int port, int db) {
        this.jedis = new Jedis(host, port, db);
    }

    public int deleteKeysWithPrefix(String prefix) {
        // 使用SCAN命令遍历所有匹配的key
        String cursor = "0";
        ScanParams scanParams = new ScanParams().match(prefix + "*").count(100); // 假设每次迭代返回100个key
        int deletedCount = 0;

        do {
            ScanResult<String> scanResult = jedis.scan(cursor, scanParams);
            cursor = scanResult.getCursor();
            List<String> keys = scanResult.getResult();

            if (!keys.isEmpty()) {
                // 批量删除key
                deletedCount += jedis.del(keys.toArray(new String[0]));
            }
        } while (!"0".equals(cursor));

        return deletedCount;
    }

    // 其他方法...

    // 关闭连接的方法（可选，根据具体设计）
    public void close() {
        if (jedis != null) {
            jedis.close();
        }
    }
}