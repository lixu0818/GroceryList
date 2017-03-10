package edu.gatech.seclass.glm.domain;

/**
 * Represents a GroceryItem.
 *
 * @author Team 02
 * @since 10/09/2016
 * @version 1.0
 */
public final class GroceryItem {

  private long id;

  private float quantity;

  private boolean checkoff;

  private GrocerySet grocerySet;

  private QuantityType quantityType;

  private Item item;

  public GroceryItem() {

  }

  public GroceryItem(final long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public float getQuantity() {
    return quantity;
  }

  public void setQuantity(final float quantity) {
    this.quantity = quantity;
  }

  public boolean isCheckoff() {
    return checkoff;
  }

  public void setCheckoff(final boolean checkoff) {
    this.checkoff = checkoff;
  }

  public GrocerySet getGrocerySet() {
    return grocerySet;
  }

  public void setGrocerySet(final GrocerySet grocerySet) {
    this.grocerySet = grocerySet;
  }

  public QuantityType getQuantityType() {
    return quantityType;
  }

  public void setQuantityType(QuantityType quantityType) {
    this.quantityType = quantityType;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(final Item item) {
    this.item = item;
  }

  @Override
  public String toString() {
    return "GroceryItem{" +
            "id=" + id +
            ", quantity=" + quantity +
            ", checkoff=" + checkoff +
            ", quantityType=" + quantityType.toString() +
            ", item= " + item.toString() +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    GroceryItem that = (GroceryItem) o;

    if (id != that.id) return false;
    if (quantity != that.quantity) return false;
    if (checkoff != that.checkoff) return false;
    if (quantityType != null ? !quantityType.equals(that.quantityType) : that.quantityType != null)
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + (quantity != +0.0f ? Float.floatToIntBits(quantity) : 0);
    result = 31 * result + (checkoff ? 1 : 0);
    result = 31 * result + (grocerySet != null ? grocerySet.hashCode() : 0);
    result = 31 * result + (quantityType != null ? quantityType.hashCode() : 0);
    result = 31 * result + (item != null ? item.hashCode() : 0);
    return result;
  }
}