//package com.car.vehicle.service;
//
//import com.car.base.common.constant.EnvConstant;
//import com.car.base.kafka.KafkaProducerUtils2;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//public class TestKafka {
//    public static void main(String[] args) {
//        KafkaProducerUtils2.init(EnvConstant.METADATA_BROKER_LIST);
//        for (int i = 0 ; i <1 ; i++) {
//            List<String> lists = new ArrayList<String>();
//            String msg = "{\n" +
//                    "\"id\":\""+200000+i+"\",\n" +
//                    "\"drive_status\":\"0\",\n" +
//                    "\"capture_time\":\"2019-07-13 16:49:32\",\n" +
//                    "\"plate_number\":\"LD8403BZ\",\n" +
//                    "\"vehicle_color\": \"Z\",\n" +
//                    "\"plate_color\": \"0\",\n" +
//                    "\"vehicle_brand\":\"98\",\n" +
//                    "\"vehicle_type\":0,\n" +
//                    "\"speed\":\"87\",\n" +
//                    "\"roadway_no\":\"2\",\n" +
//                    "\"roadway_name\":\"Estr.de Cateta\",\n" +
//                    "\"road_cross_point_id\":\"2019014\",\n" +
//                    "\"device_id\":\"100000244\",\n" +
//                    "\"direction_code\":\"Para o sul\",\n" +
//                    "\"plate_photo_id\":\"4325046\",\n" +
//                    "\"photo_fastdfs_id\":\"photo/32gs0.jpg\",\n" +
//                    "\"city_name\":\"Luanda\",\n" +
//                    "\"area_name\":\"Angola\",\n" +
//                    "\"road_name\":\"R. Am¨Şlcar Cabral\",\n" +
//                    "\"city_id\":\"306504177140000\",\n" +
//                    "\"area_id\":\"306505411530000\",\n" +
//                    "\"device_name\":\"device_2019004\"\n" +
//                    "}";
//            System.out.println(msg);
//            lists.add(msg);
//            try {
//                if (lists.size() > 0) {
//                    System.out.println("batch send  passVehicle Data to kafak start=");
//                    KafkaProducerUtils2.sendMsgs(EnvConstant.KAFKA_TOPIC_PASSEDBYVEHICLE,lists);
//                    System.out.println("batch send passVehicle Data to kafak  end=");
//                }else{
//                    TimeUnit.MILLISECONDS.sleep(500);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                KafkaProducerUtils2.close();
//            }
//
//        }
//    }
//}
