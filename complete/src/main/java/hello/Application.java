package hello;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@SpringBootConfiguration
public class Application {

    static final String topicExchangeName = "Integration";

    static final String queueName = "Integration_All_Queue";

    @Bean
    Queue queue() {
        return new Queue(queueName, true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("Integration.#");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);              
        container.setMessageListener(listenerAdapter);        
        return container;
    }           
        
    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
    	MessageListenerAdapter adapter = new MessageListenerAdapter(receiver, "receiveMessage"); 
    	//adapter.setMessageConverter(jsonMessageConverter());  	
        return adapter;
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);        
        System.out.println("All Integration (Micro) Service Started"); 
        System.out.println("------------------------------------------------------------"); 
    }

}
