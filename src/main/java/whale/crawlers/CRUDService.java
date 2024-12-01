package whale.crawlers;

import java.util.List;

public interface CRUDService<T> {
    List<T> getAllLinks(String source);
    void saveAll(List<T> links);
    void save(T link);
    void deleteById(Long id);
    T findByUrl(String url);
    T findByid(Long id);
}
