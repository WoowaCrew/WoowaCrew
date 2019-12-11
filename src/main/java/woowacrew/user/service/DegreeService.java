package woowacrew.user.service;

import org.springframework.stereotype.Service;
import woowacrew.user.dto.DegreeResponseDto;
import woowacrew.user.utils.DegreeConverter;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DegreeService {
    private final DegreeInternalService degreeInternalService;

    public DegreeService(DegreeInternalService degreeInternalService) {
        this.degreeInternalService = degreeInternalService;
    }


    public List<DegreeResponseDto> findAll() {
        return degreeInternalService.findAll().stream()
                .map(DegreeConverter::degreeToReponseDto)
                .collect(Collectors.toList());
    }
}
