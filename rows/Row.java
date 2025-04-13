package rows;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Row {
  private List<String> columns = new ArrayList<>();
  private List<Object> values = new ArrayList<>();

  public Row() {
  }

  public Row(Row row) {
    this.columns = new ArrayList<>(row.columns);
    this.values = new ArrayList<>(row.values);
  }

  public Row(List<String> columns) {
    this.columns = new ArrayList<>(columns);
    this.values = new ArrayList<>(columns.size());
  }

  public Row(String name, Object value) {
    this.columns.add(name);
    this.values.add(value);
  }

  public Row(String name1, Object value1, String name2, Object value2) {
    this.columns.add(name1);
    this.values.add(value1);
    this.columns.add(name2);
    this.values.add(value2);
  }

  public String getColumn(int idx) {
    return columns.get(idx);
  }

  public void setColumn(int idx, String name) {
    columns.set(idx, name);
  }

  public Object getValue(int idx) {
    return values.get(idx);
  }

  public Object getValue(String col) {
    int idx = find(col);
    return idx != -1 ? values.get(idx) : null;
  }

  public Row setValue(int idx, Object value) {
    values.set(idx, value);
    return this;
  }

  public Row add(String name, Object value) {
    columns.add(name);
    values.add(value);
    return this;
  }

  public Row remove(int idx) {
    columns.remove(idx);
    values.remove(idx);
    return this;
  }

  public int find(String col) {
    for (int i = 0; i < columns.size(); i++) {
      if (columns.get(i).equalsIgnoreCase(col)) {
        return i;
      }
    }
    return -1;
  }

  public int width() {
    return columns.size();
  }

  public void addOrSet(String name, Object value) {
    int idx = find(name);
    if (idx != -1) {
      setValue(idx, value);
    } else {
      add(name, value);
    }
  }

  public void addOrSetAtIndex(int index, String name, Object value) {
    int idx = find(name);
    if (idx != -1) {
      setValue(idx, value);
    } else if (index <= columns.size()) {
      columns.add(index, name);
      values.add(index, value);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Row)) return false;
    Row row = (Row) o;
    return Objects.equals(columns, row.columns) &&
           Objects.equals(values, row.values);
  }

  @Override
  public int hashCode() {
    return Objects.hash(columns, values);
  }
}
