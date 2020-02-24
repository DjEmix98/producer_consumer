package com.example.jmsdemo.kafka;

import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.example.jmsdemo.constant.IKafkaConstants;

public class ConsumerCreator {

	public static Consumer<Long, CustomObject> createConsumer() {
		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.KAFKA_BROKERS);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, IKafkaConstants.GROUP_ID_CONFIG);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomObjectDeserializer.class.getName());
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, IKafkaConstants.MAX_POLL_RECORDS);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, IKafkaConstants.OFFSET_RESET_EARLIER);
		Consumer<Long, CustomObject> consumer = new KafkaConsumer<>(props);
		consumer.subscribe(Collections.singletonList(IKafkaConstants.TOPIC_NAME));
		return consumer;
	}
	
	/*
	 * MAX_POLL_RECORDS_CONFIG: Il numero massimo di 
	 * record che il modulo in coda può tirarsi su da una coda
	 * 
	 * OFFSET: è un tipo long che permette di identificare in modo univoco i messaggi
	 * 
	 * ENABLE_AUTO_COMMIT_CONFIG: Impostazione per che permette di autocommittare 
	 * gli ofstet quando questo viene consumato dal modulo chiamate
	 * se è a true committerà periodicamente gli offset
	 * 
	 * AUTO_OFFSET_RESET_CONFIG: per ogni consumatore memorizza l'ultimo valore dell'offset
	 * questa funziona può tornare utile se non si configura l'autocommit degli offset
	 * 
	 * */
}
