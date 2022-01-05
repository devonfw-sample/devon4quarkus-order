package com.devonfw.quarkus.general.domain.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Abstract base class for all {@link PersistenceEntity persistence entities} with an {@link #getId() id} and a
 * {@link #getModificationCounter() modificationCounter} (version) field. All persistence entities of this application
 * should inherit from this class.
 */
@MappedSuperclass
public abstract class ApplicationPersistenceEntity {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Version
  private Integer modificationCounter;

  /**
   * The constructor.
   */
  public ApplicationPersistenceEntity() {

    super();
  }

  public Long getId() {

    return this.id;
  }

  public void setId(Long id) {

    this.id = id;
  }

  public Integer getModificationCounter() {

    return this.modificationCounter;
  }

  public void setModificationCounter(Integer version) {

    this.modificationCounter = version;
  }

  @Override
  public String toString() {

    StringBuilder buffer = new StringBuilder();
    toString(buffer);
    return buffer.toString();
  }

  /**
   * Method to extend {@link #toString()} logic.
   *
   * @param buffer is the {@link StringBuilder} where to {@link StringBuilder#append(Object) append} the string
   *        representation.
   */
  protected void toString(StringBuilder buffer) {

    buffer.append(getClass().getSimpleName());
    if (this.id != null) {
      buffer.append("[id=");
      buffer.append(this.id);
      buffer.append("]");
    }
  }
}
