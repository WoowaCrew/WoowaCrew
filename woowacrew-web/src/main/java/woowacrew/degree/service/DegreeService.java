package woowacrew.degree.service;

import org.springframework.stereotype.Service;
import woowacrew.degree.dto.DegreeWithUserCountResponseDto;
import woowacrew.degree.utils.DegreeConverter;
import woowacrew.user.dto.UserResponseDto;
import woowacrew.user.service.UserInternalService;
import woowacrew.user.utils.UserConverter;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DegreeService {
    private final DegreeInternalService degreeInternalService;
    private final UserInternalService userInternalService;

    public DegreeService(DegreeInternalService degreeInternalService, UserInternalService userInternalService) {
        this.degreeInternalService = degreeInternalService;
        this.userInternalService = userInternalService;
    }

    public List<DegreeWithUserCountResponseDto> findAllWithUserCount() {
        return degreeInternalService.findAll().stream()
                .map(degree -> {
                    int userCount = userInternalService.countByDegreeId(degree.getId());
                    return DegreeConverter.degreeToWithUserCountResponseDto(degree, userCount);
                })
                .collect(Collectors.toList());
    }

    public List<UserResponseDto> findUserByDegreeId(Long degreeId) {
        return userInternalService.findByDegreeId(degreeId).stream()
                .map(UserConverter::toDto)
                .collect(Collectors.toList());
    }
}
