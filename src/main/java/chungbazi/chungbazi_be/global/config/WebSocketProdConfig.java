package chungbazi.chungbazi_be.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@Profile("prod")
public class WebSocketProdConfig implements WebSocketMessageBrokerConfigurer{

    @Value("${spring.rabbitmq.host}")
    private String rabbitHost;

    @Value("${spring.rabbitmq.username}")
    private String rabbitUsername;

    @Value("${spring.rabbitmq.password}")
    private String rabbitPassword;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/pub");

        config.enableStompBrokerRelay("/queue", "/topic", "/exchange", "/amq/queue")
                .setRelayHost(rabbitHost)
                .setRelayPort(61613) // STOMP port
                .setClientLogin(rabbitUsername)
                .setClientPasscode(rabbitPassword)
                .setSystemLogin(rabbitUsername)
                .setSystemPasscode(rabbitPassword)
                .setVirtualHost("/");
    }
}
