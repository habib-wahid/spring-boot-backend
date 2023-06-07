package com.usb.pss.ipaservice.inventory.repository;

import com.usb.pss.ipaservice.utils.Utils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class BaseRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseRepository.class);
    @Autowired
    private EntityManager em;

    @Transactional
    public <T> T persist(T entity) {
        em.persist(entity);
        em.flush();
        return entity;
    }

    @Transactional
    public <T> T merge(T entity) {
        em.merge(entity);
        em.flush();
        return entity;
    }

    public <T> List<T> findByNativeQuery(String sql, Map<String, Object> params, Integer startIndex, Integer limit) {
        if (!Utils.isOk(sql)) {
            return new ArrayList<T>();
        }

        if (startIndex == null || startIndex < 0) {
            startIndex = 0;
        }

        if (limit == null || limit < 0) {
            limit = 100;
        }

        printSQL(sql, params);
        Query query = em.createNativeQuery(sql).setFirstResult(startIndex).setMaxResults(limit);
        if (params != null && params.size() > 0) {
            params.forEach((k, v) -> {
                query.setParameter(k, v);
            });
        }

        return query.getResultList();
    }


    public <T> List<T> findByNativeQuery(String sql, Map<String, Object> params) {
        if (!Utils.isOk(sql)) {
            return new ArrayList<>();
        }
        printSQL(sql, params);
        Query query = em.createNativeQuery(sql);
        if (params != null && params.size() > 0) {
            params.forEach((k, v) -> query.setParameter(k, v));
        }

        return query.getResultList();
    }

    public Integer findCountByNativeQuery(String sql, Map<String, Object> params) {
        if (!Utils.isOk(sql)) {
            return 0;
        }
        printSQL(sql, params);
        Query query = em.createNativeQuery(sql);
        if (params != null && params.size() > 0) {
            params.forEach((k, v) -> {
                query.setParameter(k, v);
            });
        }

        return Utils.getIntegerFromObject(query.getSingleResult());
    }

    public <T> List<T> findByNativeQuery(String sql, Class<T> entity, Map<String, Object> params) {
        if (!Utils.isOk(sql)) {
            return new ArrayList<>();
        }
        printSQL(sql, params);
        Query query = em.createNativeQuery(sql, entity);
        if (params != null && params.size() > 0) {
            params.forEach((k, v) -> {
                query.setParameter(k, v);
            });
        }

        return query.getResultList();
    }

    private void printSQL(String sql, Map<String, Object> params) {
        if (true) {
            LOGGER.info("===RAW SQL:{}", sql);
            if (params != null && params.size() > 0) {
                LOGGER.info("==PARAM START==");
                params.forEach((k, v) -> LOGGER.info("==KEY:{},VALUE:{}", k, v));
                LOGGER.info("==PARAM END==");
            }

        }
    }
}
