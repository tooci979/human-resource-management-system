package com.yinjie.VO;

import com.yinjie.utils.RedisKeyDeleter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class DeleteRedis {
    public void deleter(){
        RedisKeyDeleter deleter = new RedisKeyDeleter("localhost", 6379, 0); // 假设Redis在本地运行，端口6379，使用数据库0
        try {
            int deletedCount = deleter.deleteKeysWithPrefix("employees_page_");

        } finally {
            deleter.close();
        }
    }

}
