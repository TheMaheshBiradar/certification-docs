package com.taggy.kafka.client;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Properties;

public class Producer {

    public static final String BOOT_STRAP_SERVER = "localhost:9092";
    public static final String FIRST_TOPIC = "first_topic";


    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Producer.class);

        logger.info("Start of Producer ");

        // create properties
        Properties producerProperties = new Properties();
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOT_STRAP_SERVER);
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String , String> producer = new KafkaProducer<String, String>(producerProperties);

        ProducerRecord producerRecord = new ProducerRecord(FIRST_TOPIC, "key-1","producer message from IDE");

        producer.send(producerRecord, new Callback() {
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if(Objects.isNull(e)) {
                    logger.info("Topic : "+recordMetadata.topic());
                    logger.info("Partition : "+recordMetadata.partition());
                    logger.info("Offset : "+recordMetadata.offset());
                } else {
                    logger.error("An exception occurred", e);
                }
            }
        });
        producer.flush();
        producer.close();

    }
}
