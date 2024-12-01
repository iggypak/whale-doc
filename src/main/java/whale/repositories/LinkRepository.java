package whale.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import whale.entity.Link;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {}
