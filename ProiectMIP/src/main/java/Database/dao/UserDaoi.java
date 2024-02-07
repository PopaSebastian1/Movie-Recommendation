package Database.dao;
import Database.model.DatabaseConnection;
import Database.model.UserEntity;
import Database.model.MovieTableEntity;
import javax.persistence.TypedQuery;
import java.security.PublicKey;
import java.util.List;
public class UserDaoi {

    private DatabaseConnection connection = new DatabaseConnection();

    public UserEntity get(int id) {
        return connection.getEntityManager().find(UserEntity.class, id);
    }

    public List<UserEntity> getAll() {
        TypedQuery<UserEntity> query = connection.getEntityManager().createQuery("SELECT a FROM UserEntity a", UserEntity.class);
        return query.getResultList();
    }
    public void create(UserEntity user) {
        connection.executeTransaction(entityManager -> entityManager.persist(user));
    }
    public void add(UserEntity user) {
        connection.executeTransaction(entityManager -> entityManager.merge(user));
    }

    public boolean searchUser(String username,String passowrd)
    {
        List<UserEntity> users = getAll();
        users.indexOf(username);
        for (UserEntity user : users) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(passowrd)) {
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        return false;
    }
    public UserEntity searchUserById(int id)
    {
        TypedQuery<UserEntity> query = connection.getEntityManager().createQuery("SELECT a FROM UserEntity a WHERE a.id = :id", UserEntity.class);
        return query.getSingleResult();
    }
    public UserEntity searchUserByUsername(String username)
    {
        List<UserEntity> users = getAll();
        for (UserEntity user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

}
