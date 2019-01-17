package io.github.ssy.cache.switchs.Listener;

import io.github.ssy.cache.switchs.SwitchEnum;
import io.github.ssy.cache.switchs.SwitchFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;

public class RedisNodeCacheListener implements NodeCacheListener {

  CuratorFramework client;

  SwitchEnum switchEnum;


  SwitchFactory switchFactory;

  public RedisNodeCacheListener(CuratorFramework client, SwitchEnum switchEnum,
      SwitchFactory switchFactory) {
    this.switchEnum = switchEnum;
    this.client = client;
    this.switchFactory = switchFactory;
  }

  @Override
  public void nodeChanged() throws Exception {
    String nodeData = new String(client.getData().forPath(switchEnum.getRelativePath()));
    //如果有修改就扔回去
    switchFactory.putSwitch(switchEnum.getRelativePath(), Integer.valueOf(nodeData));
  }





}
