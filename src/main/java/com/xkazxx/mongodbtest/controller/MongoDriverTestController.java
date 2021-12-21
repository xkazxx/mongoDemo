package com.xkazxx.mongodbtest.controller;

import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReadConfig;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertManyResult;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author created by xkazxx
 * @version v0.0.1
 * description: com.xkazxx.mongodbtest.controller
 * date:2021/11/24
 */
@RestController
public class MongoDriverTestController {

  @Autowired
  private MongoTemplate mongoTemplate;

  /**
   * 获取用户排行榜
   *
   * @return key-用户id，value-审核通过且未删除的案件数
   */
  @GetMapping("/rank")
  public LinkedHashMap<Long, Integer> getUserRank() {
    MongoCollection<Document> collection = mongoTemplate.getCollection("charts_502");
    final long l = collection.countDocuments();
    System.out.println("all data " + l);
    // 对createdBy的数量进行分组，并且统计出个数
    Document doc = new Document();
    doc.append("_id", "$createdBy");
    doc.append("count", new Document("$sum", 1));
    //查找没有删除，审核通过的
    Document matchDoc = new Document();
    matchDoc.put("status", Collections.singletonList("审核通过"));
    matchDoc.put("isDeleted", new Document("$ne", "true"));
    Document group = new Document("$group", doc);
    Document match = new Document("$match", matchDoc);
    // 根据count字段进行降序排序
    Document sort = new Document("$sort", new Document("count", -1));
    //限制查询个数
//    Document limit = new Document("$limit", 5);
    // 将筛选条件添加到文本集合中
    List<Document> docList = new ArrayList<Document>();
    docList.add(match);
    docList.add(group);
    docList.add(sort);
//    docList.add(limit);
    // 聚合查询并输出
    AggregateIterable<Document> aggregate = collection.aggregate(docList);
    LinkedHashMap<Long, Integer> result = new LinkedHashMap<>();
    for (Document next : aggregate) {
      Long createdBy = next.getLong("_id");
      int count = next.getInteger("count", 0);
      result.put(createdBy, count);
    }
    return result;
  }

  @GetMapping("/init")
  public void readFileAndInsert() throws IOException {

    File file = new File("D:\\Edge_download\\charts_502.csv");
    final CsvReadConfig readConfig = new CsvReadConfig();
    readConfig.setContainsHeader(true);
    readConfig.setErrorOnDifferentFieldCount(false);
    readConfig.setCommentCharacter('/');
    final CsvReader reader = CsvUtil.getReader(readConfig);
    final CsvData read = reader.read(file, Charset.forName("GB2312"));
    final ObjectMapper objectMapper = new ObjectMapper();
    List<Document> documents = new ArrayList<>();

    for (CsvRow line : read.getRows()) {
      final Document document = new Document();
      final String address = line.getByName("address");
      if (address != null) {
        document.put("address", address);
      }
      final String appId = line.getByName("appId");
      if (appId != null) {
        document.put("appId", appId);
      }
      final String case_type = line.getByName("case_type");
      if (case_type != null) {
        List<String> case_type1 = (List<String>) objectMapper.readValue(case_type, List.class);
        document.put("case_type", case_type1);
      }
      final String createdAt = line.getByName("createdAt");
      if (createdAt != null) {
        document.put("createdAt", Long.valueOf(appId.trim()));
      }
      final String createdBy = line.getByName("createdBy");
      if (createdBy != null) {
        document.put("createdBy", Integer.valueOf(createdBy));
      }
      final String description = line.getByName("description");
      if (description != null) {
        document.put("description", description);
      }
      final String images1 = line.getByName("images1");
      if (images1 != null) {
        final List<String> strings = objectMapper.readValue(images1, List.class);
        document.put("images1", strings);
      }
      final String images2 = line.getByName("images2");
      if (images2 != null) {
        final List<String> strings = objectMapper.readValue(images2, List.class);
        document.put("images2", strings);
      }
      final String images3 = line.getByName("images3");
      if (images3 != null) {
        final List<String> strings = objectMapper.readValue(images3, List.class);
        document.put("images3", strings);
      }
      final String isDeleted = line.getByName("isDeleted");
      if (isDeleted != null) {
        document.put("isDeleted", isDeleted);
      }
      final String lngLat = line.getByName("lngLat");
      if (lngLat != null) {
        final List<String> strings = objectMapper.readValue(lngLat, List.class);
        document.put("lngLat", strings);
      }

      final String partiedBy = line.getByName("partiedBy");
      if (partiedBy != null) {
        final List<String> strings = objectMapper.readValue(partiedBy, List.class);
        document.put("partiedBy", strings);
      }
      final String partiedOrg = line.getByName("partiedOrg");
      if (partiedOrg != null) {
        final List<String> strings = objectMapper.readValue(partiedOrg, List.class);
        document.put("partiedOrg", strings);
      }

      final String status = line.getByName("status");
      if (status != null) {
        final List<String> strings = objectMapper.readValue(status, List.class);
        document.put("status", strings);
      }
      final String tableDataId = line.getByName("tableDataId");
      if (tableDataId != null) {
        document.put("tableDataId", tableDataId);
      }
      final String updatedAt = line.getByName("updatedAt");
      if (updatedAt != null) {
        document.put("updatedAt", Long.valueOf(updatedAt.trim()));
      }

      documents.add(document);
    }

    final InsertManyResult charts_502 = mongoTemplate.getCollection("charts_502").insertMany(documents);
    System.out.println("插入成功：" + charts_502);
  }
}
