package Generic;

import java.util.List;
import java.util.Optional;

public interface IService<T> {
    T add();
    T update(T t);
    void delete(T t);
    Optional<T> findById(String id);
}
