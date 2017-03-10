package edu.gatech.seclass.glm.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a GrocerySet.
 *
 * @author Team 02
 * @since 10/09/2016
 * @version 1.0
 */
public final class GrocerySet {

  private long id;

  private String name;

  private List<GroceryItem>  groceryItems;

  public GrocerySet(final long id) {
    this.id = id;
    this.groceryItems = new ArrayList<GroceryItem>();
  }

  public GrocerySet(final String name) {
    this.name = name;
    this.groceryItems = new ArrayList<GroceryItem>();
  }

  public GrocerySet(final long id, final String name) {
    this(name);
    this.id = id;
  }

  public boolean addGroceryItem(final GroceryItem groceryItem) {
    if (groceryItems != null) {
      this.groceryItems.add(groceryItem);
      return true;
    }
    return false;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public List<GroceryItem> getGroceryItems() {
    return groceryItems;
  }

  public void setGroceryItems(final List<GroceryItem> groceryItems) {
    this.groceryItems = groceryItems;
  }

  @Override
  public String toString() {
    return "GrocerySet{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    GrocerySet that = (GrocerySet) o;

    if (id != that.id) return false;
    if (name != null ? !name.equals(that.name) : that.name != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (groceryItems != null ? groceryItems.hashCode() : 0);
    return result;
  }
}