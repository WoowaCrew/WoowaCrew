package woowacrew.degree.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DegreeRepository extends JpaRepository<Degree, Long> {

    Optional<Degree> findByDegreeNumber(int degreeNumber);
}
