package Repository;

import java.util.*;

public abstract class BaseRepository<T, ID> {
    protected Map<ID, T> dataMap = new HashMap<>();
    protected List<T> allData = new ArrayList<>();

    public Optional<T> findById(ID id) {
        return Optional.ofNullable(dataMap.get(id));
    }

    public List<T> findAll() {
        return new ArrayList<>(allData);
    }

    public void deleteById(ID id) {
        T removed = dataMap.remove(id);
        if (removed != null) {
            allData.remove(removed);
        }
    }

    public abstract void save(T entity);
    public abstract ID getId(T entity);
}
