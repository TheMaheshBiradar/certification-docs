package com.taggy.twitter.client.producer;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.event.Event;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TwitterProducer {
    public static final String BOOT_STRAP_SERVER = "localhost:9092";

    Logger logger = LoggerFactory.getLogger(TwitterProducer.class.getName());

    private String consumerKey="7logPR46J5n05gDn5gxmueO7M";
    private String consumerKeySecret= "aXeR4Tvp2pLS79itcfv1luMGAzV9GF0i0ifMCF2v9fLUegqUAS";

    private String accessToken="1045361442-MUedsHACUT1ZftML5pNTHgDeXpeWCDGS4deNumZ";
    private String accessTokenSecret = "VwS71t4XqvWQXqCEyUDfZv4ripqtTFs6PBZJj8d5JDxom";

    public static void main(String[] args) {
        new TwitterProducer().run();
    }

    public void run () {

        // receive tweets

        BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100000);
        Client hosebirdClient = this.createTwitterClient(msgQueue);
        hosebirdClient.connect();

        KafkaProducer producer = getProducer();

        // on a different thread, or multiple different threads....
        while (!hosebirdClient.isDone()) {
            String msg = null;
            try {
                msg = msgQueue.poll(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                hosebirdClient.stop();
            }

            if (Objects.nonNull(msg)){
                producer.send(new ProducerRecord("twitter_tweets",null,msg), new Callback() {
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        if(Objects.isNull(e)) {
                            logger.info("Topic : "+recordMetadata.topic());
                            logger.info("Partition : "+recordMetadata.partition());
                            logger.info("Offset : "+recordMetadata.offset());
                        } else {
                            logger.error("An exception occurred", e);
                        }
                    }
                }
                );
    }
        }
    }

    public BasicClient createTwitterClient(BlockingQueue<String> msgQueue){
        /** Set up your blocking queues: Be sure to size these properly based on expected TPS of your stream */
        BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<Event>(1000);

    /** Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
    // Optional: set up some followings and track terms
        List<String> terms = Lists.newArrayList("bitcoin");
        hosebirdEndpoint.trackTerms(terms);

    // These secrets should be read from a config file
        Authentication hosebirdAuth = new OAuth1(consumerKey, consumerKeySecret, accessToken,accessTokenSecret);

        ClientBuilder builder = new ClientBuilder()
                .name("Hosebird-Client-01")                              // optional: mainly for the logs
                .hosts(hosebirdHosts)
                .authentication(hosebirdAuth)
                .endpoint(hosebirdEndpoint)
                .processor(new StringDelimitedProcessor(msgQueue))
                .eventMessageQueue(eventQueue);                          // optional: use this if you want to process client events

        return builder.build();
// Attempts to establish a connection.

    }

    public KafkaProducer getProducer () {
        // create properties
        Properties producerProperties = new Properties();
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOT_STRAP_SERVER);
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        producerProperties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");

        producerProperties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        producerProperties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        producerProperties.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5");
        producerProperties.setProperty(ProducerConfig.RETRIES_CONFIG, Integer.toString(Integer.MAX_VALUE));


        KafkaProducer<String , String> producer = new KafkaProducer<String, String>(producerProperties);
        return  producer;
    }

}
