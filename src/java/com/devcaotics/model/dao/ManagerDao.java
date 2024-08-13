package com.devcaotics.model.dao;

import com.devcaotics.model.negocio.Mercadinho;
import com.devcaotics.model.negocio.ONG;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagerDao {

    private static ManagerDao myself = null;

    private EntityManager manager;

    private ManagerDao() {
        this.manager = Persistence.createEntityManagerFactory("menoscommaisPU").createEntityManager();
    }

    public static ManagerDao getCurrentInstance() {
        if (myself == null) {
            myself = new ManagerDao();
        }
        return myself;
    }

    public void insert(Object o) {
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            manager.persist(o);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }

    public <T> T readById(int id, Class<T> clazz) {
        return manager.find(clazz, id);
    }

    public <T> List<T> readAll(String jpql, Class<T> clazz) {
        Query query = manager.createQuery(jpql, clazz);
        return query.getResultList();
    }

    public void update(Object o) {
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            manager.merge(o);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }

    public void delete(Object o) {
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            manager.remove(manager.contains(o) ? o : manager.merge(o));
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }
    
    public <T> List<T> readWithParameters(String jpql, Class<T> clazz, Map<String, Object> parameters) {
        Query query = manager.createQuery(jpql, clazz);
        
        // Adiciona os parâmetros à consulta
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        
        return query.getResultList();
    }

    public List<Mercadinho> autenticarMercadinho(String login, String senha) {
        String query = "select m from Mercadinho m where m.login = :login and m.senha = :senha";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("login", login);
        parameters.put("senha", senha);
        return readWithParameters(query, Mercadinho.class, parameters);
    }

    public List<ONG> autenticarONG(String login, String senha) {
        String query = "select o from ONG o where o.login = :login and o.senha = :senha";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("login", login);
        parameters.put("senha", senha);
        return readWithParameters(query, ONG.class, parameters);
    }
}
