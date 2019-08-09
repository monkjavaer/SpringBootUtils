package com.car.orbit.orbitutil.es;

import com.car.orbit.orbitutil.tools.DateUtils;
import com.car.orbit.orbitutil.tools.PropertyReaderUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.stats.IndicesStatsRequest;
import org.elasticsearch.action.admin.indices.stats.IndicesStatsResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

/**
 * @Title: ESUtils
 * @Package: com.car.orbit.orbitutil.es
 * @Description:
 * @Author: monkjavaer
 * @Date: 2019/03/15 11:48
 * @Version: V1.0
 */
public class ESUtils {

    /**
     * ES集群名称
     */
    private static String clusterName = PropertyReaderUtils.getProValue("es.cluster.name");

    /**
     * ES服务器IP
     */
    private static String ips = PropertyReaderUtils.getProValue("es.host.ip");

    /**
     * ES服务器端口
     */
    private static int port = Integer.parseInt(PropertyReaderUtils.getProValue("es.host.port"));

    /**
     * 连接设置对象
     */
    private static Settings settings;

    /**
     * ES连接对象
     */
    private static TransportClient client;

    /**
     * 索引缓存
     */
    public static Set<String> indexSet = new TreeSet<>(Comparator.naturalOrder());

    /**
     * 初始化ES连接池
     */
    public static void initClient() {
        try {
            settings = Settings.builder().put("cluster.name", clusterName).build(); //设置集群名称
            client = new PreBuiltTransportClient(settings);
            //添加集群连接
            String[] ipArr = ips.split(",");
            for (String ip : ipArr) {
                if (StringUtils.isNotBlank(ip)) {
                    client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip.trim()), port));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("unable to init es-cluster connection");
        }
    }

    /**
     * 获取ES连接客户端
     *
     * @return TransportClient ES连接客户端
     */
    public static TransportClient getClient() {
        return client;
    }

    /**
     * 通过缓存判断索引是否存在
     *
     * @param index
     * @return
     */
    public static boolean isIndexExist(String index) {
        return indexSet.contains(index);
    }

    /**
     * 刷新索引缓存
     */
    public static void refreshIndexCache() {
        ActionFuture<IndicesStatsResponse> isr = client.admin().indices().stats(new IndicesStatsRequest().all());
        Set<String> set = isr.actionGet().getIndices().keySet();

        /** 筛选出orbit的过车记录表 **/
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().matches("orbit_pass_vehicle_record_\\d{4}_\\d{2}")) {
                iterator.remove();
            }
        }

        /** 排序 **/
        indexSet.clear();
        indexSet.addAll(set);
    }

    /**
     * 获取ES返回结果中的字段值(仅能处理int,long,String三种参数类型)
     *
     * @param hit       ES命中数据
     * @param fieldName 待获取字段名
     * @param clazz     字段值的类型
     * @return T 待查询的字段值
     */
    @SuppressWarnings("unchecked")
    public static <T> T getField(SearchHit hit, String fieldName, Class<T> clazz) {
        //获取命中字段
        Map<String, Object> fields = hit.getSource();
        Object obj = fields.get(fieldName);

        //转换为给定格式并返回
        if (clazz == Integer.class) {
            if (obj == null) {
                return (T) new Integer(0);
            }
            return (T) (Integer.valueOf(obj.toString()));
        }
        if (clazz == Long.class) {
            if (obj == null) {
                return (T) new Long(0L);
            }
            return (T) (Long.valueOf(obj.toString()));
        }
        if (clazz == String.class) {
            if (obj == null) {
                return (T) "";
            }
            return (T) (obj.toString());
        }
        if (clazz == Boolean.class) {
            if (obj == null) {
                return (T) Boolean.FALSE;
            }
            String bolStr = obj.toString();
            if ("true".equalsIgnoreCase(bolStr)) {
                return (T) Boolean.TRUE;
            } else if ("false".equalsIgnoreCase(bolStr)) {
                return (T) Boolean.FALSE;
            }
        }

        //仅能处理以上几种类型，其他类型不处理
        throw new RuntimeException("unhandled class " + clazz.getName());
    }

    /**
     * 准备索引数据
     *
     * @param builder 索引数据创建对象
     * @param field   文档field名
     * @param value   文档field值
     * @param key     仅当value为List<String>时需要传入该值，值为ES中元素的key值
     * @throws IOException 索引IO异常
     */
    public static void prepareSource(XContentBuilder builder, String field, Object value, String... key) throws IOException {
        //空数据不处理
        if (Objects.isNull(value)) {
            return;
        }

        //处理字符串型数据
        if (value instanceof String) {
            if (value.toString().trim().length() > 0) { //无内容的字符串不处理
                builder.field(field, value.toString());
            }
            return;
        }

        //处理日期数据
        if (value instanceof Date) {
            builder.field(field, DateUtils.format((Date) value));
            return;
        }

        //处理List类型数据
        if (value instanceof List) {
            List<Object> listVal = (List<Object>) value;
            if (listVal.size() > 0) { //空容器不处理
                Object item = listVal.get(0);
                if (item instanceof String) { //现在仅处理List<String>类型
                    if (ArrayUtils.isEmpty(key) || (key.length > 1)) {
                        throw new RuntimeException("unique key is needed");
                    }
                    String esKey = key[0];
                    List<Map<String, String>> values = new ArrayList<>();
                    for (Object esValue : listVal) {
                        Map<String, String> curItem = new HashMap<>();
                        curItem.put(esKey, (String) esValue);
                        values.add(curItem);
                    }
                    builder.field(field, values);
                }
            }
            return;
        }

        //其他未处理类型直接索引并返回
        builder.field(field, value);
        return;
    }
}
