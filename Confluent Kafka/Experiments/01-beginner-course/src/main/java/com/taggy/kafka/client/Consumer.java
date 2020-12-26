package com.taggy.kafka.client;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Objects;
import java.util.Properties;

public class Consumer {

    public static final String BOOT_STRAP_SERVER = "localhost:9092";
    public static final String FIRST_TOPIC = "first_topic";


    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Consumer.class);
        logger.info("Start of Consumer ");

        // create properties
        Properties consumerProperties = new Properties();
        consumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOT_STRAP_SERVER);
        consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "my-custom-consumer-group");
        consumerProperties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String , String> kafkaConsumer = new KafkaConsumer<String, String>(consumerProperties);

        kafkaConsumer.subscribe(Collections.singleton(FIRST_TOPIC));

        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));

            for(ConsumerRecord record : records){
                logger.info("Topic : "+record.topic());
                logger.info("key : "+record.key());
                logger.info("value : "+record.value());
                logger.info("partition : "+record.partition());
                logger.info("offset : "+record.offset());
            }
        }
    }
}
