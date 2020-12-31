package com.taggy.kafka.client.elasticsearch;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ElasticSearchConsumer {
    public static final String BOOT_STRAP_SERVER = "localhost:9092";
    public static final String FIRST_TOPIC = "twitter_tweets";
   public static Logger logger = LoggerFactory.getLogger(ElasticSearchConsumer.class.getName());
    public static void main(String[] args) throws IOException, InterruptedException {


        RestHighLevelClient client = createClient();
        String jsonString = "{\"foo\":\"bar\"}";
        KafkaConsumer consumer = getKafkaConsumer();
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            for(ConsumerRecord record : records){
                IndexRequest indexRequest = new IndexRequest("twitter","tweets").source(record.value(), XContentType.JSON);
                IndexResponse response =  client.index(indexRequest, RequestOptions.DEFAULT);
                logger.info("response : "+response.getId());
                Thread.sleep(1000);
            }

        }
    }



    public static RestHighLevelClient createClient () {
        String host ="kafka-course-2728640243.eu-central-1.bonsaisearch.net";
        String user ="6taz34lhk9";
        String pass ="cmpxka95og";


        CredentialsProvider  credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(user,pass));

        RestClientBuilder builder = RestClient.builder(new HttpHost(host, 443,"https"))
                                    .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                                        @Override
                                        public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                                            return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                                        }
                                    });
        return new RestHighLevelClient(builder);
    }

   public static KafkaConsumer getKafkaConsumer () {
        // create properties
        Properties consumerProperties = new Properties();
        consumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOT_STRAP_SERVER);
        consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "my-elastic-consumer-group");
        consumerProperties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String , String> kafkaConsumer = new KafkaConsumer<String, String>(consumerProperties);

        kafkaConsumer.subscribe(Collections.singleton(FIRST_TOPIC));

       return kafkaConsumer;
    }
}
