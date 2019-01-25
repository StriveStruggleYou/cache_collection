package io.github.ssy.cache.switchs;

public interface Switch {

  String ROOT_DIR = "/sw";

  boolean isOn();

  boolean switchOn();

  boolean switchOff();

}
