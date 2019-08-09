package com.car.orbit.orbitservice.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "orbit_sys_message")
public class OrbitSysMessage {
    /**
     * 消息id
     */
    @Id
    private String id;

    /**
     * 业务id
     */
    @Column(name = "business_id")
    private String businessId;

    /**
     * 消息内容
     */
    @Column(name = "message_comment")
    private String messageComment;

    /**
     * 消息类型（1审批）
     */
    @Column(name = "message_type")
    private Integer messageType;

    /**
     * 消息状态（1已读，2未读）初始时为未读
     */
    @Column(name = "message_status")
    private Integer messageStatus;

    /**
     * 消息时间
     */
    @Column(name = "message_time")
    private Date messageTime;

    /**
     * 已读时间
     */
    @Column(name = "read_time")
    private Date readTime;

    /**
     * 已读人
     */
    private String reader;

    /**
     * 已读人id
     */
    @Column(name = "reader_id")
    private String readerId;

    /**
     * 获取消息id
     *
     * @return id - 消息id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置消息id
     *
     * @param id 消息id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取业务id
     *
     * @return business_id - 业务id
     */
    public String getBusinessId() {
        return businessId;
    }

    /**
     * 设置业务id
     *
     * @param businessId 业务id
     */
    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    /**
     * 获取消息内容
     *
     * @return message_comment - 消息内容
     */
    public String getMessageComment() {
        return messageComment;
    }

    /**
     * 设置消息内容
     *
     * @param messageComment 消息内容
     */
    public void setMessageComment(String messageComment) {
        this.messageComment = messageComment;
    }

    /**
     * 获取消息类型（1审批）
     *
     * @return message_type - 消息类型（1审批）
     */
    public Integer getMessageType() {
        return messageType;
    }

    /**
     * 设置消息类型（1审批）
     *
     * @param messageType 消息类型（1审批）
     */
    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    /**
     * 获取消息状态（1已读，2未读）初始时为未读
     *
     * @return message_status - 消息状态（1已读，2未读）初始时为未读
     */
    public Integer getMessageStatus() {
        return messageStatus;
    }

    /**
     * 设置消息状态（1已读，2未读）初始时为未读
     *
     * @param messageStatus 消息状态（1已读，2未读）初始时为未读
     */
    public void setMessageStatus(Integer messageStatus) {
        this.messageStatus = messageStatus;
    }

    /**
     * 获取消息时间
     *
     * @return message_time - 消息时间
     */
    public Date getMessageTime() {
        return messageTime;
    }

    /**
     * 设置消息时间
     *
     * @param messageTime 消息时间
     */
    public void setMessageTime(Date messageTime) {
        this.messageTime = messageTime;
    }

    /**
     * 获取已读时间
     *
     * @return read_time - 已读时间
     */
    public Date getReadTime() {
        return readTime;
    }

    /**
     * 设置已读时间
     *
     * @param readTime 已读时间
     */
    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    /**
     * 获取已读人
     *
     * @return reader - 已读人
     */
    public String getReader() {
        return reader;
    }

    /**
     * 设置已读人
     *
     * @param reader 已读人
     */
    public void setReader(String reader) {
        this.reader = reader;
    }

    /**
     * 获取已读人id
     *
     * @return reader_id - 已读人id
     */
    public String getReaderId() {
        return readerId;
    }

    /**
     * 设置已读人id
     *
     * @param readerId 已读人id
     */
    public void setReaderId(String readerId) {
        this.readerId = readerId;
    }
}