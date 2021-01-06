import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Properties;
import java.util.regex.Pattern;

public class ConsumerTodosTopicos {

    public static void main(String[] args) {
        //propriedades de conexão
        var consumer = new KafkaConsumer(properties());

        //record ou message
        consumer.subscribe(Pattern.compile("MEU.*")); //consome todos os tópico que começam com MEU

        while (true) {
            ConsumerRecords records = consumer.poll(Duration.ofMillis(100));
            if(!records.isEmpty()){
                records.forEach(r -> {
                    var record = (ConsumerRecord) r;
                    System.out.println("---------------------------------");
                    System.out.println(">>> Lendo mensagens de TODOS OS TÓPICOS >>>");
                    System.out.println("topic = "+ record.topic());
                    System.out.println("partition = "+ record.partition());
                    System.out.println("offset = "+ record.offset());
                    System.out.println("key = "+ record.key());
                    System.out.println("value = "+ record.value());
                });
                continue;
            }
        }
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, ConsumerTodosTopicos.class.getName());
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        return properties;
    }
}
