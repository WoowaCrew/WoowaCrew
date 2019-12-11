package woowacrew.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacrew.user.dto.DegreeResponseDto;
import woowacrew.user.service.DegreeService;

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
    public ResponseEntity<List<DegreeResponseDto>> showDegrees() {
        return ResponseEntity.ok(degreeService.findAll());
    }
}
