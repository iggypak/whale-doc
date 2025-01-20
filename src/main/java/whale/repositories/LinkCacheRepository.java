package whale.repositories;

import org.springframework.data.repository.CrudRepository;
import whale.entity.Link;

public interface LinkCacheRepository extends CrudRepository<Link, String> {
}
