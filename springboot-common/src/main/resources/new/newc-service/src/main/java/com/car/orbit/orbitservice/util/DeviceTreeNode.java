package com.car.orbit.orbitservice.util;

import java.util.List;

/**
 * @Title: DeviceTreeNode
 * @Package: com.car.orbit.orbitservice.util
 * @Description: 组织结构和设备信息树形工具
 * @Author: monkjavaer
 * @Data: 2019/3/22 13:48
 * @Version: V1.0
 */
public class DeviceTreeNode<T> {
    private int count;
    private String id;
    private String parentId;
    private String label;
    private List<T> children;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }
}
