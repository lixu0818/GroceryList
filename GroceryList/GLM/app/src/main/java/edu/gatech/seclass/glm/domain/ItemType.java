package edu.gatech.seclass.glm.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Item Type.
 *
 * @author Team 02
 * @since 10/09/2016
 * @version 1.0
 */
public final class ItemType {

  private long id;

  private String type;

  private List<Item> items;

  public ItemType(final String type) {
    this.type = type;
    this.items = new ArrayList<Item>();
  }

  public ItemType(final long id, final String type) {
    this.id = id;
    this.type = type;
    this.items = new ArrayList<Item>();
  }

  public long getId() {
    return id;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public List<Item> getItems() {
    return items;
  }

  public void setItems(final List<Item> items) {
    this.items = items;
  }

  public boolean addItem(final Item item) {
    if (this.items != null) {
      this.items.add(item);
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    return "ItemType{" +
            "id=" + id +
            ", type='" + type + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ItemType itemType = (ItemType) o;

    if (id != itemType.id) return false;
    if (type != null ? !type.equals(itemType.type) : itemType.type != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (items != null ? items.hashCode() : 0);
    return result;
  }
}