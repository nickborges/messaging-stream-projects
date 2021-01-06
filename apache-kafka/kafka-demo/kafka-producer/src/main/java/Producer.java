import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class Producer {

    public static void main(String[] args) throws Exception{

        var producer = new KafkaProducer<String, String>(properties());

        producerMeuTopico(producer);

        producerOutroTopico(producer);
    }

    private static void producerMeuTopico(KafkaProducer producer) throws ExecutionException, InterruptedException{
        var userId = UUID.randomUUID().toString();
        var orderId = UUID.randomUUID().toString();
        var amount = BigDecimal.valueOf(Math.random() * 9999 + 1.9).setScale(2, RoundingMode.HALF_EVEN);
        var order = new Order(userId, orderId, amount);

        var record1 = new ProducerRecord<>("MEU_TOPICO", userId, order);
        producer.send(record1, getCallback()).get(); //get() confirma o envio da mensagem para o tópico
    }

    private static void producerOutroTopico(KafkaProducer producer) throws ExecutionException, InterruptedException{
        var key = UUID.randomUUID().toString();
        var value = key +"Mensagem do outro tópco!!!";
        var record2 = new ProducerRecord<>("MEU_OUTRO_TOPICO", key, value);
        producer.send(record2, getCallback()).get(); //get() confirma o envio da mensagem para o tópico
    }

    private static Callback getCallback() {
        Callback callback = (data, exception) -> {
            if(exception != null){
                exception.printStackTrace();
                return;
            }
            System.out.println("Mensagem enviada com sucesso ==> ");
            System.out.println("tópico = " + data.topic()
                    + " / partition = " + data.partition()
                    + " / offset = " + data.offset()
                    + " / timestamp = " + data.timestamp());
        };
        return callback;
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all"); //garante a reliability, espera que a informção enviada seja replicada para os demais brokers
        return properties;
    }
}
