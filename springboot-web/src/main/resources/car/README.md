
###部署数据流程
1、	导入基础数据（Trunk.sql）
2、	和辰安统一认证系统联系，获取统一认证证书，接口地址（auth.properties，web配置文件），分配交通执法超级管理员账户superAdmin,密码superAdmin
3、	拿到当地机构数据、城市数据、区域数据、设备安装路口数据；superAdmin登录系统录入数据
4、	添加智能主机
5、	拿到视频平台录入的电子警察相机数据后，将向相应的主键告诉工作人员配置为相机的设备号deviceid。登录系统从视频平台同步设备数据。
6、	同步设备后，将设备关联到具体路口和智能主机
7、	数据库表MONITOR_CENTER添加相应配置数据（根据现场部署地址修改）：
一键报警地址：UNIFIED_ALARM_URL：http://10.5.30.21:10003/access/accessin
视频地址VIDEO_SERVER_IP：http://192.168.19.236:8088/vision
视频地址：VIDEO_SDK：192.168.19.236,9999,192.168.19.236,6543,ttt,ttt（SDKvideoServerIP，videoServerPort，videoServerTcpIP，videoServerTcpPort，videoServerUserName，videoServerPassword）
8、MAP，地图接口地址获取，配置system.properties
9、地图撒点服务部署后将地址配置system.properties的HEATMAP_STR字段
10、安装部署时，添加完机构必须配置相应的视频服务器和接处警服务器地址,然后重启系统
