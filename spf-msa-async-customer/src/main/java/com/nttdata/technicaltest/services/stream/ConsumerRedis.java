package com.nttdata.technicaltest.services.stream;

import com.google.gson.Gson;
import com.nttdata.technicaltest.services.exception.ClientAccountOperationException;
import com.nttdata.technicaltest.services.service.AccountClient;
import com.nttdata.technicaltest.services.service.dto.CustomerCacheDto;
import com.nttdata.technicaltest.services.service.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsumerRedis {
    private final RedisConnectionFactory redisConnectionFactory;
    private final AccountClient accountClient;

    @Bean
    public RedisMessageListenerContainer redisContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(messageListener(), topic());
        return container;
    }

    @Bean
    public MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter((MessageListener) (message, bytes) -> {
            log.info("Message received: {}", message);
            Gson gson = new Gson();
            CustomerCacheDto customerCacheDto;
            customerCacheDto = gson.fromJson(message.toString(), CustomerCacheDto.class);
            accountClient.postSaveAccount(Mapper.INSTANCE.mapperToPostCustomerAccountRequest(customerCacheDto))
                    .doOnSuccess(success -> log.info("Success to save account!"))
                    .doOnError(error -> log.error("Error to save account! -> {}", error.getMessage()))
                    .onErrorMap(error -> new ClientAccountOperationException())
                    .subscribe();
        });
    }

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("message");
    }
}
