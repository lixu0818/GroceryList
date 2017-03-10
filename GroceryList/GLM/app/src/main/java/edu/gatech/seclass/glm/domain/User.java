package edu.gatech.seclass.glm.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an User.
 *
 * @author Team 02
 * @since 10/09/2016
 * @version 1.0
 */
public final class User {

  private long id;

  private String name;

  private String email;

  private List<GrocerySet>  grocerySet;

  public void User(long id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.grocerySet = new ArrayList<GrocerySet>();
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

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public List<GrocerySet> getGrocerySet() {
    return grocerySet;
  }

  public void setGrocerySet(final List<GrocerySet> grocerySet) {
    this.grocerySet = grocerySet;
  }

  public boolean addGrocerySet(final GrocerySet grocerySet) {
    if (this.grocerySet != null) {
      this.grocerySet.add(grocerySet);
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    User user = (User) o;

    if (id != user.id) return false;
    if (name != null ? !name.equals(user.name) : user.name != null) return false;
    if (email != null ? !email.equals(user.email) : user.email != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (grocerySet != null ? grocerySet.hashCode() : 0);
    return result;
  }
}