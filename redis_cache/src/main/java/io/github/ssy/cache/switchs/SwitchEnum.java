package io.github.ssy.cache.switchs;

public enum SwitchEnum {
  CDN_REDIS1_ENUM("/sw/cdn/redis1/switch","cdn_redis1");

  private String relativePath;

  private String switchName;

  SwitchEnum(String relativePath, String switchName) {
    this.relativePath = relativePath;
    this.switchName = switchName;
  }

  public String getRelativePath() {
    return relativePath;
  }

  public void setRelativePath(String relativePath) {
    this.relativePath = relativePath;
  }

  public String getSwitchName() {
    return switchName;
  }

  public void setSwitchName(String switchName) {
    this.switchName = switchName;
  }
}
