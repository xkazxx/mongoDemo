package com.xkazxx.mongodbtest.controller;

import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReadConfig;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.result.InsertManyResult;
import com.xkazxx.mongodbtest.persist.Charts_502;
import com.xkazxx.mongodbtest.vo.UserRank;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;

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
  public List<UserRank> getUserRank() {
    // 对createdBy的数量进行分组，并且统计出个数
    // 查找没有删除，审核通过的
    // 根据count字段进行降序排序
    // 限制查询个数
    // 将筛选条件添加到文本集合中
//  where ($match) 、group by ($group) 、having($match)、select($project)、order by($sort)、limit($limit)  sum($sum)、count($sum)、join($lookup)
    final TypedAggregation<Charts_502> newAggregation = Aggregation.newAggregation(
            Charts_502.class,
            Aggregation.match(Criteria.where("status").is(Collections.singletonList("审核通过")).and("isDeleted").ne("true")),
            Aggregation.group("createdBy").count().as("count"),
            Aggregation.sort(Sort.by(Sort.Direction.DESC, "count")),
            Aggregation.limit(5)
    );
    final AggregationResults<UserRank> results = mongoTemplate.aggregate(newAggregation, UserRank.class);
    return results.getMappedResults();
  }

  @GetMapping("/init")
  public String readFileAndInsert() throws IOException {
    File file = ResourceUtils.getFile("classpath:template/charts_502.csv");
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
        List<String> case_type1 = (List<String>) objectMapper.readValue(case_type, new TypeReference<List<String>>() {});
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
        final List<String> strings = objectMapper.readValue(images1, new TypeReference<List<String>>() {});
        document.put("images1", strings);
      }
      final String images2 = line.getByName("images2");
      if (images2 != null) {
        final List<String> strings = objectMapper.readValue(images2, new TypeReference<List<String>>() {});
        document.put("images2", strings);
      }
      final String images3 = line.getByName("images3");
      if (images3 != null) {
        final List<String> strings = objectMapper.readValue(images3, new TypeReference<List<String>>() {});
        document.put("images3", strings);
      }
      final String isDeleted = line.getByName("isDeleted");
      if (isDeleted != null) {
        document.put("isDeleted", isDeleted);
      }
      final String lngLat = line.getByName("lngLat");
      if (lngLat != null) {
        final List<String> strings = objectMapper.readValue(lngLat, new TypeReference<List<String>>() {});
        document.put("lngLat", strings);
      }

      final String partiedBy = line.getByName("partiedBy");
      if (partiedBy != null) {
        final List<String> strings = objectMapper.readValue(partiedBy, new TypeReference<List<String>>() {});
        document.put("partiedBy", strings);
      }
      final String partiedOrg = line.getByName("partiedOrg");
      if (partiedOrg != null) {
        final List<String> strings = objectMapper.readValue(partiedOrg, new TypeReference<List<String>>() {});
        document.put("partiedOrg", strings);
      }

      final String status = line.getByName("status");
      if (status != null) {
        final List<String> strings = objectMapper.readValue(status, new TypeReference<List<String>>() {});
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
    mongoTemplate.getCollection("charts_502").insertMany(documents);
    return "插入成功";
  }
}
