package com.example.jmsdemo.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;

import com.example.jmsdemo.constant.IKafkaConstants;

public class ProducerCreator {

	  public static Producer<Long, CustomObject> createProducer() {
	        Properties props = new Properties();
	        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.KAFKA_BROKERS);
	        props.put(ProducerConfig.CLIENT_ID_CONFIG, IKafkaConstants.CLIENT_ID);
	        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
	        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CustomSerializer.class.getName());
	        //props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class.getName());
	        return new KafkaProducer<>(props);
	    }
	  
	  /*
		 *BOOTSTRAP_SERVERS_CONFIG:  Indirizzo del broker di kafka, se il broker è su cluster 
		 * si possono inserire più indirizzi separandoli con la virgola
		 * 
		 * CLIENT_ID_CONFIG: Id del produttore in modo tale che il broker possa determiare
		 * l'origine della richiesta
		 * 
		 * KEY_DESERIALIZER_CLASS_CONFIG: Costante utilizzata per serializzare la chiave
		 * in questo caso stiamo serializzando un long quindi la classe LongDeserializer 
		 * ci serve per serializzare il nostro oggetto, in caso ci fosse la necessità di serializzare
		 * un'oggetto più complesso bisognerà usare una classe customizzata 
		 * che implementi l'interfaccia "Serializer"
		 * 
		 * VALUE_DESERIALIZER_CLASS_CONFIG: Usato per serializzare il nostro oggetto valore */
}
