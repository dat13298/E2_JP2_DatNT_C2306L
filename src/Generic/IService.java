package Generic;

import java.util.List;

public interface IService<T> {
    T add();
    T update(T t);
    void delete(T t);
    T findById(String id);
}
