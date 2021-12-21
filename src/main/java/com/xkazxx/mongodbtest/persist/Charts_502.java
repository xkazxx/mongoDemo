package com.xkazxx.mongodbtest.persist;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

/**
 * @author created by xkazxx
 * @version v0.0.1
 * description: com.xkazxx.mongodbtest
 * date:2021/11/24
 */
@Document(collection = "charts_502")
public class Charts_502 implements Serializable {
    @Id
    private String id;
    @Field(value = "address")
    private String address;
    @Field(value = "appId")
    private String appId;
    @Field(value = "case_type")
    private List<String> caseType;
    @Field(value = "createdAt")
    private Long createdAt;
    @Field(value = "createdBy")
    private Integer createdBy;
    @Field(value = "description")
    private String description;
    @Field(value = "images1")
    private List<String> images1;
    @Field(value = "images2")
    private List<String> images2;
    @Field(value = "images3")
    private List<String> images3;
    @Field(value = "isDeleted")
    private String isDeleted;
    @Field(value = "lnglat")
    private List<String> lngLat;
    @Field(value = "partiedBy")
    private List<String> partiedBy;
    @Field(value = "partiedOrg")
    private List<String> partiedOrg;
    @Field(value = "status")
    private List<String> status;
    @Field(value = "tableDataId")
    private String tableDataId;
    @Field(value = "updatedAt")
    private Long updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public List<String> getCaseType() {
        return caseType;
    }

    public void setCaseType(List<String> caseType) {
        this.caseType = caseType;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages1() {
        return images1;
    }

    public void setImages1(List<String> images1) {
        this.images1 = images1;
    }

    public List<String> getImages2() {
        return images2;
    }

    public void setImages2(List<String> images2) {
        this.images2 = images2;
    }

    public List<String> getImages3() {
        return images3;
    }

    public void setImages3(List<String> images3) {
        this.images3 = images3;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public List<String> getLngLat() {
        return lngLat;
    }

    public void setLngLat(List<String> lngLat) {
        this.lngLat = lngLat;
    }

    public List<String> getPartiedBy() {
        return partiedBy;
    }

    public void setPartiedBy(List<String> partiedBy) {
        this.partiedBy = partiedBy;
    }

    public List<String> getPartiedOrg() {
        return partiedOrg;
    }

    public void setPartiedOrg(List<String> partiedOrg) {
        this.partiedOrg = partiedOrg;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public String getTableDataId() {
        return tableDataId;
    }

    public void setTableDataId(String tableDataId) {
        this.tableDataId = tableDataId;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Charts_502{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", appId='" + appId + '\'' +
                ", caseType=" + caseType +
                ", createdAt=" + createdAt +
                ", createdBy=" + createdBy +
                ", description='" + description + '\'' +
                ", images1=" + images1 +
                ", images2=" + images2 +
                ", images3=" + images3 +
                ", isDeleted='" + isDeleted + '\'' +
                ", lngLat=" + lngLat +
                ", partiedBy=" + partiedBy +
                ", partiedOrg=" + partiedOrg +
                ", status=" + status +
                ", tableDataId='" + tableDataId + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
