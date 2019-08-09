package com.car.trunk.dal.model;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * Created by monkjavaer on 2017/11/29 0029.
 */
@Entity
@Table(name = "AUTHORITY")
public class AuthorityEntity {
    private BigInteger id;
    private String name;
    private Byte enable;
    private String levelCode;
    private Integer positionInGroup;
    private String url;
    private String matchUrl;
    private Byte isDirectUrl;
    private BigInteger parentId;

    @Id
    @Column(name = "ID", nullable = false)
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME", nullable = true, length = 48)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "ENABLE", nullable = true)
    public Byte getEnable() {
        return enable;
    }

    public void setEnable(Byte enable) {
        this.enable = enable;
    }

    @Basic
    @Column(name = "LEVEL_CODE", nullable = true, length = 512)
    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    @Basic
    @Column(name = "POSITION_IN_GROUP", nullable = true)
    public Integer getPositionInGroup() {
        return positionInGroup;
    }

    public void setPositionInGroup(Integer positionInGroup) {
        this.positionInGroup = positionInGroup;
    }

    @Basic
    @Column(name = "URL", nullable = true, length = 256)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "MATCH_URL", nullable = true, length = 256)
    public String getMatchUrl() {
        return matchUrl;
    }

    public void setMatchUrl(String matchUrl) {
        this.matchUrl = matchUrl;
    }

    @Basic
    @Column(name = "IS_DIRECT_URL", nullable = true)
    public Byte getIsDirectUrl() {
        return isDirectUrl;
    }

    public void setIsDirectUrl(Byte isDirectUrl) {
        this.isDirectUrl = isDirectUrl;
    }

    @Basic
    @Column(name = "PARENT_ID", nullable = true)
    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}

        AuthorityEntity that = (AuthorityEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {return false;}
        if (name != null ? !name.equals(that.name) : that.name != null) {return false;}
        if (enable != null ? !enable.equals(that.enable) : that.enable != null) {return false;}
        if (levelCode != null ? !levelCode.equals(that.levelCode) : that.levelCode != null) {return false;}
        if (positionInGroup != null ? !positionInGroup.equals(that.positionInGroup) : that.positionInGroup != null)
        {return false;}
        if (url != null ? !url.equals(that.url) : that.url != null) {return false;}
        if (matchUrl != null ? !matchUrl.equals(that.matchUrl) : that.matchUrl != null) {return false;}
        if (isDirectUrl != null ? !isDirectUrl.equals(that.isDirectUrl) : that.isDirectUrl != null) {return false;}
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) {return false;}

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (enable != null ? enable.hashCode() : 0);
        result = 31 * result + (levelCode != null ? levelCode.hashCode() : 0);
        result = 31 * result + (positionInGroup != null ? positionInGroup.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (matchUrl != null ? matchUrl.hashCode() : 0);
        result = 31 * result + (isDirectUrl != null ? isDirectUrl.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        return result;
    }
}
