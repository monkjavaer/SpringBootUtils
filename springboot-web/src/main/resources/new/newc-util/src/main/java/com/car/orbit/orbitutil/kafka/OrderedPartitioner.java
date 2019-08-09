package com.car.orbit.orbitutil.kafka;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CreateDate: 2019-4-1 <br/>
 * Author: monkjavaer <br/>
 * Description: 顺序分区的Partitioner，与传入的key无关
 * Version: 1.0
 **/
public class OrderedPartitioner implements Partitioner {
    private final AtomicInteger counter = new AtomicInteger((new Random()).nextInt());

    public OrderedPartitioner() {
    }

    private static int toPositive(int number) {
        return number & 2147483647;
    }

    public void configure(Map<String, ?> configs) {
    }

    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
        int numPartitions = partitions.size();
        int nextValue = this.counter.getAndIncrement();
        List<PartitionInfo> availablePartitions = cluster.availablePartitionsForTopic(topic);
        if (availablePartitions.size() > 0) {
            int part = toPositive(nextValue) % availablePartitions.size();
            return ((PartitionInfo)availablePartitions.get(part)).partition();
        } else {
            return toPositive(nextValue) % numPartitions;
        }
    }

    public void close() {
    }
}
