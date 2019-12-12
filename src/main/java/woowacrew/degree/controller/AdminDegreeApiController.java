package woowacrew.degree.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacrew.degree.dto.DegreeWithUserCountResponseDto;
import woowacrew.degree.service.DegreeService;
import woowacrew.user.dto.UserResponseDto;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/degrees")
public class AdminDegreeApiController {
    private final DegreeService degreeService;

    public AdminDegreeApiController(DegreeService degreeService) {
        this.degreeService = degreeService;
    }

    @GetMapping
    public ResponseEntity<List<DegreeWithUserCountResponseDto>> showDegrees() {
        return ResponseEntity.ok(degreeService.findAllWithUserCount());
    }

    @GetMapping("/{degreeId}/users")
    public ResponseEntity<List<UserResponseDto>> showDetailUsersOfDegree(@PathVariable Long degreeId) {
        return ResponseEntity.ok(degreeService.findUserByDegreeId(degreeId));
    }
}
