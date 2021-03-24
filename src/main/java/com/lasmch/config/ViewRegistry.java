package com.lasmch.config;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ViewRegistry implements Iterable<ViewRegistry.View> {

  final List<View> views = new ArrayList();

  public ViewRegistry add(String path, String viewName) {
    views.add(View.builder().path(path).viewName(viewName).build());
    return this;
  }


  @Override
  public Iterator<View> iterator() {
    return views.iterator();
  }


  @Data
  @Builder
  static class View {

    String path;
    String viewName;
  }

}
