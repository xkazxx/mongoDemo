package com.xkazxx.mongodbtest.service;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.xkazxx.mongodbtest.vo.UserRank;
import com.xkazxx.mongodbtest.vo.UserRankStatVO;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author created by xkazxx
 * @version v0.0.1
 * description: com.big.services
 * date:2021/9/23
 */
@Service
public class TakePicturesEverywhereService {

  @Autowired
  private MongoTemplate mongoService;

  public UserRankStatVO getUserRanks(Integer userId) {
    UserRankStatVO statVO = new UserRankStatVO();
    getUserInfo(userId, statVO);
    // 获取榜单
    LinkedHashMap<Long, Integer> userRank = getUserRank();
    // 获取积分
    ArrayList<UserRank> userRanks = convertCaseNumToPoint(userRank);
    // 获取个人统计信息
    statVO.setRanks(userRanks);
    return statVO;
  }

  /**
   * 获取用户排行榜
   *
   * @return key-用户id，value-审核通过且未删除的案件数
   */
  public LinkedHashMap<Long, Integer> getUserRank() {
    MongoCollection<Document> collection = mongoService.getCollection("charts_502");
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

  /**
   * 将用户案例转换为积分
   *
   * @param target 转换对象
   * @return key-用户id，value-积分
   */
  public ArrayList<UserRank> convertCaseNumToPoint(LinkedHashMap<Long, Integer> target) {
    if (target == null || target.isEmpty()) {
      return new ArrayList<>(0);
    }
    ArrayList<UserRank> result = new ArrayList<>(target.size());
    for (Map.Entry<Long, Integer> entry : target.entrySet()) {
      Long key = entry.getKey();
      UserRank userRank = new UserRank();
      userRank.setId(key);
      userRank.setCount(Long.valueOf(entry.getValue()));
      result.add(userRank);
    }
    return result;
  }

  /**
   * 获取当前人排名信息
   *
   * @param userId 用户id
   * @param statVO 统计结果
   */
  public void getUserInfo(Integer userId, UserRankStatVO statVO) {
    MongoCollection<Document> collection = mongoService.getCollection("charts_502");

    final Criteria criteria = Criteria.where("createdBy").is(userId).and("isDeleted").ne("true").and("status").is(Collections.singletonList("审核通过"));
//    long caseNum = collection.countDocuments(filter);
    long caseNum = collection.countDocuments(criteria.getCriteriaObject());
    statVO.setPoint(caseNum);
    // 统计个人排名
    // 对createdBy的数量进行分组，并且统计出个数
    Document doc2 = new Document();
    doc2.append("_id", "$createdBy");
    doc2.append("count", new Document("$sum", 1));
    Document group2 = new Document("$group", doc2);
    //查找未删除，审核通过的
    Document matchDoc2 = new Document();
    matchDoc2.put("status", Collections.singletonList("审核通过"));
    matchDoc2.put("isDeleted", new Document("$ne", "true"));
    Document match2 = new Document("$match", matchDoc2);
    // 根据count字段进行降序排序
    Document match3 = new Document("$match", new Document("count", new Document("$gt", caseNum)));
    Document sort2 = new Document("$sort", new Document("count", 1));

    // 将筛选条件添加到文本集合中
    List<Document> docList = new ArrayList<>();
    docList.add(match2);
    docList.add(group2);
    docList.add(sort2);
    docList.add(match3);
    // 聚合查询并输出
    AggregateIterable<Document> aggregate = collection.aggregate(docList);
    Document first = aggregate.first();
    int total = 1;
    if (first != null) {
      for (Document ignored : aggregate) {
        total++;
      }
      statVO.setGap(first.getInteger("count") - caseNum);
    } else {
      statVO.setGap(0L);
    }
    statVO.setLevel(total);
  }
}
