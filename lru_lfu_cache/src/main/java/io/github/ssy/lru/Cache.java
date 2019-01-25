package io.github.ssy.lru;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Cache {

  //
  public static void main(String args[]) {
    LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap(10, 0.75F, true);
    linkedHashMap.put("1", "a");
    linkedHashMap.put("2", "b");
    linkedHashMap.put("3", "c");
    linkedHashMap.get("2");
    linkedHashMap.put("4","d");
    Iterator<Entry<String, String>> iterator = linkedHashMap.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry entry = iterator.next();
      System.out.println(entry.getKey() + ":" + entry.getValue());
    }


  }

}
