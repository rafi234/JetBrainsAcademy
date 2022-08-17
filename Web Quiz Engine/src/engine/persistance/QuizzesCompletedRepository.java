package engine.persistance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizzesCompletedRepository
        extends PagingAndSortingRepository<QuizzesCompleted, Integer> {
    Slice<QuizzesCompleted> findAllByUserCompleted(Pageable pageable, User user);
}
