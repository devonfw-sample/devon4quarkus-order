package com.devonfw.quarkus.general.domain.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Abstract base class for all persistence entities with an {@link #getId() id} and a
 * {@link #getModificationCounter() modificationCounter} (version) field. All persistence entities of this application
 * should inherit from this class. It is using JPA annotations at the getters what has several advantages but also
 * implies that you have to annotate transient getter methods with the {@link Transient} annotation.
 */
@MappedSuperclass
public abstract class ApplicationPersistenceEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer modificationCounter;

    public ApplicationPersistenceEntity() {

        super();
    }

    public Long getId() {

        return this.id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    @Version
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
     *               representation.
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
