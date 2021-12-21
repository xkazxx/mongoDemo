package com.xkazxx.mongodbtest.vo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author created by xkazxx
 * @version v0.0.1
 * description: com.big.vo
 * date:2021/9/23
 */
public class UserRankStatVO implements Serializable {

  private static final long serialVersionUID = -2216526866173027230L;
  private ArrayList<UserRank> ranks; // key-用户id，value-审核通过的用户上报案件数
  private Integer level; // 当前用户排名
  private double point; // 当前用户积分
  private double gap; // 当前用户与前一名积分差值

  public UserRankStatVO() {

  }

  @Override
  public String toString() {
    return "UserRankStatVO{" +
            "ranks=" + ranks +
            ", level=" + level +
            ", point=" + point +
            ", gap=" + gap +
            '}';
  }

  public ArrayList<UserRank> getRanks() {
    return ranks;
  }

  public void setRanks(ArrayList<UserRank> ranks) {
    this.ranks = ranks;
  }

  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }

  public double getPoint() {
    return point;
  }

  public void setPoint(double point) {
    this.point = point;
  }

  public double getGap() {
    return gap;
  }

  public void setGap(double gap) {
    this.gap = gap;
  }
}
