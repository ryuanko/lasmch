package com.lasmch.pager;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Slf4j
public class Pagination {

  @Getter
  @Builder
  static public class Offset {
    Integer start;
    Integer fetchSize;
  }


  int currentPage;
  int fetchSize;
  int total;
  List<?> records;
  Navigation navigation;

  final int PAGE_GROUP_SIZE  = 10;

  public Pagination(int currentPage, int fetchSize, int total, List<?> records, Map<String, Object> kwds) {
    this.currentPage = currentPage;
    this.fetchSize = fetchSize;
    this.total = total;
    this.records = records;
    this.navigation = new Navigation(total, currentPage, fetchSize, PAGE_GROUP_SIZE, kwds);
  }

  public Pagination(int total, List<?> records, Map<String, Object> kwds) {
    int currentPage = 1;
    int fetchSize = 13;
    if (kwds.get("page") != null && !kwds.get("page").equals("")) currentPage = Integer.parseInt(kwds.get("page").toString());
    if (kwds.get("fetchSize") != null && !kwds.get("fetchSize").equals("")) fetchSize = Integer.parseInt(kwds.get("fetchSize").toString());

    this.currentPage = currentPage;
    this.fetchSize = fetchSize;
    this.total = total;
    this.records = records;
    this.navigation = new Navigation(total, currentPage, fetchSize, PAGE_GROUP_SIZE, kwds);
  }

  public Pagination(int currentPage, int fetchSize, int total, List<?> records) {
    this(currentPage, fetchSize, total, records, Collections.EMPTY_MAP);
  }


  @Getter
  @ToString
  class Navigation {
    int currentPage;
    int totalPage;
    int startRange;
    int endRange;
    boolean hasNext;
    boolean hasBefore;
    String keyword;
    Map<String, Object> keywords = new HashMap<>();
    List<NavigationLink> pages = new ArrayList();


    Navigation(int totalRecords, int currentPage, int pageSize, int pageGroup, Map<String, Object> kwds) {
      this.currentPage = currentPage;

      totalPage = (int) Math.ceil(totalRecords / (double) pageSize);

      if (totalPage <= 0) {
        totalPage = 1;
      }

      if (totalPage >= currentPage) {
        int groupNo = currentPage / pageGroup + (currentPage % pageGroup > 0 ? 1 : 0);
        endRange = groupNo * pageGroup;
        startRange = endRange - (pageGroup - 1);

        hasBefore = currentPage > 1 && currentPage <= totalPage;
        hasNext = currentPage > 0 && currentPage < totalPage;
      }

      if (totalPage <= endRange)
        endRange = totalPage;

      List<String> kws =
              kwds
                      .keySet()
                      .stream()
                      // .filter(i -> !(i.equals("page") || i.equals("limit")))
                      .filter(i -> !(i.equals("limit")))
                      .map(i -> {
                        Object t = kwds.get(i);
                        if(t == null)
                          return null;

                        if( !(t instanceof  Offset)){
                          keywords.put(i, t);
                          return String.format("%s=%s", i, t);
                        }

                        return null;
                      }).filter(i -> i != null).collect(Collectors.toList());


      keyword = StringUtils.collectionToDelimitedString(kws, "&");

      if(!StringUtils.isEmpty(keyword))
        keyword = "&" + keyword;

      for (int i = startRange; i <= endRange; i++) {
        pages.add( new NavigationLink(i, keyword));
      }
    }
  }


  @Getter
  @ToString
  class NavigationLink {
    int page;
    String keyword;

    public NavigationLink(int page, String keyword) {
      this.page = page;
      this.keyword = keyword;
    }
  }



  public ModelAndView asModel(String viewName) {
    ModelAndView mv = asModel();
    mv.setViewName(viewName);
    return mv;
  }


  public ModelAndView asModel() {
    return new ModelAndView()
      .addObject("currentPage", currentPage)
      .addObject("fetchSize", fetchSize)
      .addObject("total", total)
      .addObject("records",  records)
      .addObject("navigation", navigation);
  }


  public static Offset offset(int page, int fetchSize) {
    return Offset.builder()
            .start(page > 0 ? fetchSize * (page - 1) : page <= 0 ? 1 : page)
            .fetchSize(fetchSize)
            .build();
  }

  public static Offset offset_map(Map<String, Object> map) {
    int page = 1;
    int fetchSize = 13;

    if (map.get("page") != null && !map.get("page").equals("")) page = Integer.parseInt(map.get("page").toString());
    if (map.get("fetchSize") != null && !map.get("fetchSize").equals("")) fetchSize = Integer.parseInt(map.get("fetchSize").toString());

    return Offset.builder()
            .start(page > 0 ? fetchSize * (page - 1) : page <= 0 ? 1 : page)
            .fetchSize(fetchSize)
            .build();
  }
}
