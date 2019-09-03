package com.car.orbit.orbitservice.test;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaUtil {
    private String topic;

    private Producer<String, String> producer;

    public KafkaUtil(String topic){
        this.topic=topic;
        this.producer=new KafkaProducer<String, String>(getConfig());
    }

    public static Properties getConfig() {
        Properties props =new Properties();
        props.put(Constants.BOOKERSTRAP_SERVERS, Constants.BOOKER_HOST);
        props.put(Constants.KEY_SERIALIZER, Constants.KEY_SERIALIZER_VALUE);
        props.put(Constants.VALUE_SERIALIZER, Constants.VALUE_SERIALIZER_VALUE);
        return props;
    }


    public void sendMessage(String key,String message){
        try {
            producer.send(new ProducerRecord<String, String>(topic,key,message));
        }
        catch (Exception e){
            System.out.println("kafka insert exception");
        }finally {
            producer.close();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        KafkaUtil myTestProducer = new KafkaUtil("blacklist_alarm1");
//        WarningOutput warningOutput = new WarningOutput();
//        warningOutput.setId(InfoIdUtils.get32UUID());
//        warningOutput.setWarning_conf_id(1);
//        warningOutput.setWarning_module_id(1);
//        warningOutput.setWarning_entity_id("1");
//        warningOutput.setWarning_entity_name("trump");
//        warningOutput.setWarning_level(1);
//        warningOutput.setInfo_id("965DC00586AD847C90C27B56E86D1DCA");
//        warningOutput.setUser_id("123456");
//        warningOutput.setMatch_words("trump");
//        warningOutput.setWarning_type_id(1);
//        warningOutput.setAuthor("Ricky Davila");
//        warningOutput.setSite_id(301);
//        warningOutput.setStatus(1);
//        //warningOutput.setSensitive_id(warningInfoMapping);
//        //warningOutput.setSummary();推特无摘要
//        warningOutput.setWarning_content("After he failed to complete his military service in Germany, donald trump’s grandfather, Friedrich Trump wrote a letter begging not to be deported. Here it is:\n" +
//                "https://t.co/S3xIA7i07x");
//        warningOutput.setWarning_time(TimeUtils.getTime());
//        warningOutput.setCreate_post_time("2018-05-28 23:57:18");
//        warningOutput.setImage_urls("https://twitter.com/TheRickyDavila/status/1001251096277417984");
//        String str = JSON.toJSONString(warningOutput);

        for (int i = 0; i < 1; i++) {
//            myTestProducer.sendMessage(null, str);
            myTestProducer.sendMessage(null,"{\n" +
                    "    \"CAPTURE_TIME\": \"2019-04-08 08:10:04\",\n" +
                    "    \"PHOTO_FASTDFS_URL\": \"http://172.16.3.31:8888/group1/M00/00/74/rBADH1yrOGqAMSWwAABl-25Z3Jo810.jpg\",\n" +
                    "    \"PLATE_NUMBER\": \"京M38102\",\n" +
                    "    \"PLATE_COLOR\":\"蓝\"\n" +
                    " \n" +
                    "}");
        }
        myTestProducer.producer.close();
    }
}
