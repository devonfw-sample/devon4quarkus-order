package com.devonfw.quarkus.ordermanagement.domain.repo;

import com.devonfw.quarkus.ordermanagement.domain.model.ItemEntity;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ItemFragmentImpl implements ItemFragment {
    @Inject
    EntityManager em;

    @Override
    public void deleteByOrderId(Long id) {
        Query query = this.em.createQuery("delete from ItemEntity where productorder_id = :id").setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<ItemEntity> findItemsByOrderId(Long id) {
        Query query = this.em.createQuery("select a from ItemEntity a where productorder_id = :id").setParameter("id", id);
        return query.getResultList();
    }
}
