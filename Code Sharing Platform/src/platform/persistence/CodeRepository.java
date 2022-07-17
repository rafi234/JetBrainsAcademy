package platform.persistence;

import platform.buissnes.Code;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CodeRepository extends CrudRepository<Code, UUID> {
    List<Code> findFirst10ByTimeDependentFalseAndViewsDependentFalseOrderByDateDesc();
}
