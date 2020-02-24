package com.example.jmsdemo;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.jmsdemo.constant.IKafkaConstants;
import com.example.jmsdemo.kafka.ConsumerCreator;
import com.example.jmsdemo.kafka.CustomObject;
import com.example.jmsdemo.kafka.ProducerCreator;

@SpringBootApplication
public class JmsdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JmsdemoApplication.class, args);
		  //runProducer();
		  runConsumer();
	}
	
	static void runConsumer() {
        Consumer<Long, CustomObject> consumer = ConsumerCreator.createConsumer();
        int noMessageFound = 0;
        while (true) {
          ConsumerRecords<Long, CustomObject> consumerRecords = consumer.poll(Duration.ofMillis(1)); // Duration.ofMillis(1000)
          // 1000 is the time in milliseconds consumer will wait if no record is found at broker.
          if (consumerRecords.count() == 0) {
              noMessageFound++;
              if (noMessageFound > IKafkaConstants.MAX_NO_MESSAGE_FOUND_COUNT)
                // If no message found count is reached to threshold exit loop.  
                break;
              else
                  continue;
          }
          //print each record. 
          consumerRecords.forEach(record -> {
              System.out.println("Record Key " + record.key());
              System.out.println("Record value " + record.value().getId());
              System.out.println("Record partition " + record.partition());
              System.out.println("Record offset " + record.offset());
           });
          // commits the offset of record to broker. 
           consumer.commitAsync();
        }
    consumer.close();
    }
    static void runProducer() {
Producer<Long, CustomObject> producer = ProducerCreator.createProducer();
        for (int index = 0; index < IKafkaConstants.MESSAGE_COUNT; index++) {
        	CustomObject obj = new CustomObject();
        	obj.setId("Index: " + index);
            ProducerRecord<Long, CustomObject> record = new ProducerRecord<Long, CustomObject>(IKafkaConstants.TOPIC_NAME,
            obj);
            try {
            RecordMetadata metadata = producer.send(record).get();
                        System.out.println("Record sent with key " + index + " to partition " + metadata.partition()
                        + " with offset " + metadata.offset());
                 } 
            catch (ExecutionException e) {
                     System.out.println("Error in sending record");
                     System.out.println(e);
                  } 
             catch (InterruptedException e) {
                      System.out.println("Error in sending record");
                      System.out.println(e);
                  }
         }
    }

}
