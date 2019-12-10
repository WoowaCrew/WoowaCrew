package woowacrew.user.service;

import org.springframework.stereotype.Service;
import woowacrew.user.domain.Degree;
import woowacrew.user.domain.DegreeRepository;
import woowacrew.user.domain.exception.DegreeBoundException;

@Service
public class DegreeInternalService {
    private DegreeRepository degreeRepository;

    public DegreeInternalService(DegreeRepository degreeRepository) {
        this.degreeRepository = degreeRepository;
    }

    public Degree findDegreeByNumber(int numberOfDegree) {
        return degreeRepository.findByNumber(numberOfDegree)
                .orElseThrow(DegreeBoundException::new);
    }
}
