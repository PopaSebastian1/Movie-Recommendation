package Database.dao;

import Database.model.DatabaseConnection;
import Database.model.LikedEntity;

import java.util.List;

public class LikedDaoi {
    DatabaseConnection connection = new DatabaseConnection();
    public void create(LikedEntity likedEntity) {
        connection.executeTransaction(entityManager -> entityManager.persist(likedEntity));
    }
    public void getAll() {
        connection.executeTransaction(entityManager -> entityManager.createQuery("SELECT a FROM LikedEntity a", LikedEntity.class));
    }
    public List<LikedEntity> getAllByUserId(long id)
    {
        return connection.getEntityManager().createQuery("SELECT a FROM LikedEntity a WHERE a.userId = :id", LikedEntity.class).setParameter("id", id).getResultList();
    }
}
