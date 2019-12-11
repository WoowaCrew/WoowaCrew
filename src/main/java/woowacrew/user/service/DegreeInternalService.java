package woowacrew.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.user.domain.Degree;
import woowacrew.user.domain.DegreeRepository;
import woowacrew.user.domain.exception.DegreeBoundException;

import java.util.List;

@Service
@Transactional
public class DegreeInternalService {
    private DegreeRepository degreeRepository;

    public DegreeInternalService(DegreeRepository degreeRepository) {
        this.degreeRepository = degreeRepository;
    }

    @Transactional(readOnly = true)
    public Degree findDegreeByNumber(int numberOfDegree) {
        return degreeRepository.findByNumber(numberOfDegree)
                .orElseThrow(DegreeBoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Degree> findAll() {
        return degreeRepository.findAll();
    }
}
