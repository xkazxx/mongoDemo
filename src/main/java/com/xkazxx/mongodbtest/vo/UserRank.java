package com.xkazxx.mongodbtest.vo;

import java.io.Serializable;

/**
 * @author created by xkazxx
 * @version v0.0.1
 * description: com.big.vo
 * date:2021/9/29
 */

public class UserRank implements Serializable {
  private static final long serialVersionUID = 8679703980211589340L;
  
  private Long id;
  private Double point;

  public UserRank() {
  }



  @Override
  public String toString() {
    return "UserRank{" +
            "id=" + id +
            ", point=" + point +
            '}';
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Double getPoint() {
    return point;
  }

  public void setPoint(Double point) {
    this.point = point;
  }
}
