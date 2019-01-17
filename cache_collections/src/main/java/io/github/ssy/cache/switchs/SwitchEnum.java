package io.github.ssy.cache.switchs;

public enum SwitchEnum {
  CDN_REDIS1_ENUM("/cdn/redis1/switch","cdn_redis1");

  private String relativePath;

  private String switchName;

  SwitchEnum(String relativePath, String switchName) {
    this.relativePath = relativePath;
    this.switchName = switchName;
  }



}
