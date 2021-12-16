package com.plamena.findahomeapi.collections;

import com.plamena.findahomeapi.utils.Page;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public abstract class ObjCollection<T> {

  private Page page;
  private List<T> data = new ArrayList<>();

  public Page getPage() {
    return page;
  }

  public void setPage(Page page) {
    this.page = page;
  }

  public List<T> getData() {
    return data;
  }

  public void setData(List<T> data) {
    this.data = data;
  }
}