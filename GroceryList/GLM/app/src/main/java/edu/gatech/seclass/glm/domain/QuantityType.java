package edu.gatech.seclass.glm.domain;

/**
 * Represents a Quantity Type.
 *
 * @author Team 02
 * @since 10/09/2016
 * @version 1.0
 */
public final class QuantityType {

  private long id;

  private String type;

  public QuantityType(final String type) {
    this.id = id;
    this.type = type;
  }

  public QuantityType(final long id, final String type) {
    this.id = id;
    this.type = type;
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

  @Override
  public String toString() {
    return "QuantityType{" +
            "id=" + id +
            ", type='" + type + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    QuantityType that = (QuantityType) o;

    if (id != that.id) return false;
    return type != null ? type.equals(that.type) : that.type == null;
  }

  @Override
  public int hashCode() {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }
}