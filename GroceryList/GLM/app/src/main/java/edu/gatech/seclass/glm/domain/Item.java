package edu.gatech.seclass.glm.domain;

/** 
 * Represents an Item.
 *
 * @author Team 02
 * @since 10/09/2016
 * @version 1.0
 */
public final class Item {

  private long id;

  private String name;

  private ItemType itemType;

  public Item(final long id) {
    this.id = id;
  }

  public Item(final String name) {
    this.id = id;
    this.name = name;
  }

  public Item(final long id, final String name) {
    this.id = id;
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public ItemType getItemType() {
    return itemType;
  }

  public void setItemType(final ItemType itemType) {
    this.itemType = itemType;
  }

  @Override
  public String toString() {
    return "Item{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", itemType=" + itemType +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Item item = (Item) o;

    if (id != item.id) return false;
    if (name != null ? !name.equals(item.name) : item.name != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (itemType != null ? itemType.hashCode() : 0);
    return result;
  }
}