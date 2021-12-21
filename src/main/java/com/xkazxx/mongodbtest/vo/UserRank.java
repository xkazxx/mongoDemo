package com.xkazxx.mongodbtest.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author created by xkazxx
 * @version v0.0.1
 * description: com.big.vo
 * date:2021/9/29
 */
@Data
public class UserRank implements Serializable {
  /**
   * 用户id
   */
  private Long id;
  /**
   * 用户案例数
   */
  private Long count;

}
