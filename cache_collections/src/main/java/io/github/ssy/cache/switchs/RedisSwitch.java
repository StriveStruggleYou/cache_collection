package io.github.ssy.cache.switchs;

public class RedisSwitch implements Switch {


  SwitchFactory switchFactory;

  public RedisSwitch(SwitchFactory switchFactory) {
    this.switchFactory = switchFactory;
  }

  private String relativePath;

  private String switchName;

  //默认关闭，0表示没有打开
  private volatile int switchNum = 0;

  @Override
  public boolean isOn() {
    if (switchFactory.getSwitch(SwitchEnum.CDN_REDIS1_ENUM.getRelativePath()) == 0) {
      return false;
    }
    return true;
  }

  @Override
  public boolean switchOn() {
    return false;
  }

  @Override
  public boolean switchOff() {
    return false;
  }


  public void setSwitchNum(int switchNum) {
    this.switchNum = switchNum;
  }

  public void setRelativePath(String relativePath) {
    this.relativePath = relativePath;
  }

  public void setSwitchName(String switchName) {
    this.switchName = switchName;
  }
}
