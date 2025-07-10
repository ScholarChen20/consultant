package com.itheima.consultant.repository;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;

@Repository  // 声明为Repository为Spring Bean
public class RedisChatMemoryStore implements ChatMemoryStore {
    //注入RedisTemplate
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 获取会话消息，默认使用Redis存储会话消息
     * @param memoryId
     * @return
     */
    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        //获取会话消息
        String json = redisTemplate.opsForValue().get(memoryId);
        //把json字符串转化成List<ChatMessage>,反序列化操作
        List<ChatMessage> list = ChatMessageDeserializer.messagesFromJson(json);
        return list;
    }

    /**
     * 更新会话消息，默认使用Redis存储会话消息
     * @param memoryId
     * @param list
     */
    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> list) {
        //更新会话消息
        //1.把list转换成json数据
        String json = ChatMessageSerializer.messagesToJson(list);
        //2.把json数据存储到redis中
        redisTemplate.opsForValue().set(memoryId.toString(),json, Duration.ofDays(1));
    }

    /**
     *  删除会话消息，默认使用Redis存储会话消息
     * @param memoryId
     */
    @Override
    public void deleteMessages(Object memoryId) {
        redisTemplate.delete(memoryId.toString());
    }
}
