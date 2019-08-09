package com.car.orbit.orbitservice.controller;

import com.car.orbit.orbitservice.kafka.AlarmConsumerService;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResultUtil;
import com.car.orbit.orbitutil.tools.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// import org.springframework.mail.javamail.JavaMailSender;

/**
 * CreateDate：2019/3/5 <br/>
 * Author：monkjavaer <br/>
 * Description:
 **/
@RestController
public class TestController {

    public static final String TEXT = "所有 API 使用 REST 架构约定形式命名。REST架构的思想是将 API 请求对象看成一个个资源，实现者使用相应的 HTTP 的动词（POST, POST, PUT, PATCH, DELETE）来访问和操作这些资源。接口中所有参数均大小写敏感。对于这些动词的解释如下表所示";

    @Autowired
    AlarmConsumerService alarmConsumerService;

    @Autowired
    private RedisClient redisClient;

    @GetMapping("test")
    public void test() {
        System.out.println("test...");
    }

    // @Autowired
    // JavaMailSender javaMailSender;

    // @Autowired
    // private TobatoFdfsClient tobatoFdfsClient;


    @RequestMapping(value = "/alarm", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult sendEmail() {
//        AlarmMessage alarmMessage = new AlarmMessage();
//        alarmMessage.setPlateNumber("津DXY801");
//        alarmMessage.setBlacklistId("afc2191377e8f4c26aa2835de674d9ff");
//        alarmMessage.setCaptureTime(DateUtils.format(new Date()));
//        alarmMessage.setAlarmTime(DateUtils.format(new Date()));
//
//        alarmConsumerService.consumer(JsonUtils.toJSONString(alarmMessage));
//
//        return ResultUtil.success();

        // AlarmMessage alarmMessage = new AlarmMessage();
        // alarmMessage.setPlateNumber("津DXY801");
        // alarmMessage.setTaskId("F53E824CAB0946BDBD62B042F97B9263");
        // alarmMessage.setCaptureTime(DateUtils.format(new Date()));
        // alarmMessage.setAlarmTime(DateUtils.format(new Date()));
        // alarmMessage.setPhotoFastdfsUrl("http://172.16.3.31:8888/group1/M00/00/69/rBADH1yd4z2AaCnrAAEFEdl58C4416.jpg");

//        String s = "{\"alarmId\":\"622626F137DC46FBB8B57A20398A293C\",\"alarmTime\":\"2019-05-09 17:49:30\",\"areaId\":\"B82368E24457489A80CA66E6D2C6F257\",\"blacklistId\":\"4C6A97E66CAC8D7BA06996D74A73829A\",\"captureTime\":\"2019-04-23 08:21:25\",\"cityId\":\"E62C651C91A544919B72C317E84537A0\",\"deviceId\":\"CAFB3CBD42B4442EA675BB700B4E34E9\",\"passVehicleId\":\"D20AF77853EE4E63A9AFFCF30376C7D3\",\"photoFastdfsUrl\":\"http://172.16.3.31:8888/group1/M00/02/43/rBADH1zANmiAb2fRAAGAkgd2U1Y899.jpg\",\"plateNumber\":\"津LF9793\",\"roadCrossPointId\":\"B177AD0AE3E7402983E56CAB0A9430D6\",\"vehicleBrand\":\"0123\",\"vehicleBrandChild\":\"01230000\",\"vehicleColor\":\"10\",\"vehicleType\":\"3\"}";
//        alarmConsumerService.consumer(s);



        String s = "{\"alarmId\":\"90370487E587436EA0B18EFCDA4BC468\",\"alarmTime\":\"2019-05-29 10:19:36\",\"areaId\":\"B82368E24457489A80CA66E6D2C6F257\",\"captureTime\":\"2019-05-29 10:19:30\",\"cityId\":\"E62C651C91A544919B72C317E84537A0\",\"deviceId\":\"553D5ACDFCAB45CE8D5AA39E1B1FF267\",\"passVehicleId\":\"AE5FB44D90B5468596BD85FC300F7500\",\"photoFastdfsUrl\":\"http://192.168.20.84:8888/group1/M00/72/AF/wKgUVFzs5QSAGe7GAABdWN_skSw976.jpg\",\"plateNumber\":\"浙CG5588\",\"roadCrossPointId\":\"3FC3D2CDE2E94718A6AB28B275BE7CC5\",\"taskId\":\"27AB9D90A9AB433699E46158F2535710\",\"vehicleBrand\":\"0045\",\"vehicleBrandChild\":\"00450013\",\"vehicleColor\":\"8\",\"vehicleType\":\"3\"}";
        alarmConsumerService.consumer(s);
//        EmailUtil.sendHtmlMail2();

        //EmailUtil.sendSimpleEmail("zhengl", "车辆大数据", "车辆大数据HHHHH");

        return ResultUtil.success();
    }

    // @GetMapping("pic-test")
    // public OrbitResult uploadPic() {
    //     for (int i = 0; i < 30; i++) {
    //         int finalI = i;
    //         new Thread(() -> {
    //             for (int j = 0; j < 100; j++) {
    //                 int finalJ = j;
    //                 String path = null;
    //                 try {
    //                     path = tobatoFdfsClient.uploadFile(new File("C:\\Users\\monkjavaer\\Pictures\\Saved Pictures\\test\\10.jpg"));
    //                 } catch (IOException e) {
    //                     e.printStackTrace();
    //                 }
    //                 System.out.println(String.format("第%s个fastdfs线程存入第%s次，路径：%s", finalI, finalJ, path));
    //             }
    //         }).start();
    //     }
    //     return ResultUtil.success();
    // }

    // @GetMapping("redis-test")
    // public OrbitResult redisTest(@RequestParam String token) {
    //     redisClient.save(BaseBusinessRedis.LOGIN_USER, token, "test-data");
    //     return ResultUtil.success();
    // }
}