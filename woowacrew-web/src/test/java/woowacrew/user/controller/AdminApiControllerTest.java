package woowacrew.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import woowacrew.common.controller.JsonConverter;
import woowacrew.degree.dto.DegreeResponseDto;
import woowacrew.security.SecurityContextSupport;
import woowacrew.user.domain.UserRole;
import woowacrew.user.dto.UserApproveDto;
import woowacrew.user.dto.UserContext;
import woowacrew.user.dto.UserResponseDto;
import woowacrew.user.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacrew.common.document.ApiDocumentUtils.getDocumentRequest;
import static woowacrew.common.document.ApiDocumentUtils.getDocumentResponse;
import static woowacrew.common.document.SnippetUtils.userResponseDtoListSnippets;

@WebMvcTest(controllers = AdminApiController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "woowacrew.security.*")})
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
class AdminApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private UserContext userContext;

    @BeforeEach
    void setUp() {
        userContext = new UserContext(1L, "12345", "admin", UserRole.ROLE_ADMIN);
        SecurityContextSupport.updateContext(userContext);
    }

    @Test
    void approveUserList() throws Exception {
        List<UserResponseDto> users = new ArrayList<>();
        users.add(generateUser(1L, "123456", "woowa1", UserRole.ROLE_CREW));
        users.add(generateUser(2L, "234567", "woowa2", UserRole.ROLE_COACH));
        users.add(generateUser(3L, "345678", "woowa3", UserRole.ROLE_ADMIN));

        given(userService.findApprovedUser()).willReturn(users);

        mockMvc.perform(get("/api/users/approve"))
                .andExpect(status().isOk())
                .andDo(document("user/approve/list",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        userResponseDtoListSnippets));
    }

    @Test
    void disapproveUserList() throws Exception {
        List<UserResponseDto> users = new ArrayList<>();
        users.add(generateUser(4L, "456789", "woowa4", UserRole.ROLE_PRECOURSE));
        users.add(generateUser(5L, "567891", "woowa5", UserRole.ROLE_PRECOURSE));
        users.add(generateUser(6L, "678912", "woowa6", UserRole.ROLE_PRECOURSE));

        given(userService.findDisapprovedUser()).willReturn(users);

        mockMvc.perform(get("/api/users/disapprove"))
                .andExpect(status().isOk())
                .andDo(document("user/disapprove/list",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        userResponseDtoListSnippets));
    }

    @Test
    void approveUser() throws Exception {
        UserApproveDto userApproveDto = new UserApproveDto(UserRole.ROLE_CREW, 1);

        mockMvc.perform(
                RestDocumentationRequestBuilders.put("/api/users/{userId}/approve", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.convert(userApproveDto)))
                .andExpect(status().isOk())
                .andDo(document("user/approve",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("userId").description("승인할 유저의 id")
                        )
                ));

        verify(userService).approveUserFor(1L, userContext, userApproveDto);
    }

    private UserResponseDto generateUser(Long id, String oauthId, String nickName, UserRole userRole) {
        LocalDate birthdayDate = LocalDate.of(2019, 1, 1);
        UserResponseDto userResponseDto = new UserResponseDto(id, oauthId, nickName, birthdayDate);
        userResponseDto.setUserRole(userRole);
        userResponseDto.setDegreeResponseDto(new DegreeResponseDto(1L, 1));
        return userResponseDto;
    }
}