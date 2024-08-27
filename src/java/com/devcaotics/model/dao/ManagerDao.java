package com.devcaotics.model.dao;

import com.devcaotics.model.negocio.Mercadinho;
import com.devcaotics.model.negocio.ONG;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagerDao {

    private static ManagerDao myself = null;

    private EntityManagerFactory manager =null;

    private ManagerDao() {
        this.manager = Persistence.createEntityManagerFactory("menoscommaisPU");
    }

    public static ManagerDao getCurrentInstance() {
        if (myself == null) {
            myself = new ManagerDao();
        }
        return myself;
    }

    public void insert(Object o) {
        EntityManager em = manager.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(o);
            em.flush();
            em.getTransaction().commit();
            
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        }
            em.close();
    }

    public <T> T readById(int id, Class<T> clazz) {
        EntityManager em = manager.createEntityManager();
        return em.find(clazz, id);
    }

    public <T> List<T> readAll(String jpql, Class<T> clazz) {
        EntityManager em = manager.createEntityManager();
        Query query = em.createQuery(jpql, clazz);
        return query.getResultList();
    }
    
    public List readProdutosByMercadinho(String query,Class c){
        
        EntityManager em = manager.createEntityManager();
        
        List returnedList = em.createQuery(query,c).getResultList();
        
        em.close();
        
        return returnedList;
    }

    public void update(Object o) {
        EntityManager em = manager.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(o);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        }
    }

    public void delete(Object o) {
        EntityManager em = manager.createEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.contains(o) ? o : em.merge(o));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        }
    }
    
    public <T> List<T> readWithParameters(String jpql, Class<T> clazz, Map<String, Object> parameters) {
        EntityManager em = manager.createEntityManager();
        Query query = em.createQuery(jpql, clazz);
        
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
