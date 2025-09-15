package Repository;

import java.util.*;


public abstract class BaseRepository<T, ID> {
    
    protected Map<ID, T> storage = new HashMap<>();

    
    protected List<T> entities = new ArrayList<>();

    
    public T findById(ID id) {
        return storage.get(id);
    }

    
    public List<T> findAll() {
        return new ArrayList<>(entities);
    }

    
    public abstract void save(T entity);

    public abstract ID getId(T entity);
}
