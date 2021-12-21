package com.xkazxx.mongodbtest.controller;

import com.xkazxx.mongodbtest.service.TakePicturesEverywhereService;
import com.xkazxx.mongodbtest.vo.UserRankStatVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author created by xkazxx
 * @version v0.0.1
 * description: com.big.controllers
 * date:2021/9/22
 */
@RestController
public class TakePicturesEverywhereController {

  @Autowired
  private TakePicturesEverywhereService takePicturesEverywhereService;

  /**
   * 随手拍小程序-用户排行榜
   *
   * @param userId 当前用户id
   * @return 统计信息
   */
  @GetMapping("/user/rank/{userId}")
  public UserRankStatVO getUserRank(@PathVariable(required = true) Integer userId) {
    return takePicturesEverywhereService.getUserRanks(userId);

  }

}
