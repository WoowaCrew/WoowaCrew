package woowacrew.degree.controller;

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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import woowacrew.degree.dto.DegreeResponseDto;
import woowacrew.degree.dto.DegreeWithUserCountResponseDto;
import woowacrew.degree.service.DegreeService;
import woowacrew.security.SecurityContextSupport;
import woowacrew.user.domain.UserRole;
import woowacrew.user.dto.UserContext;
import woowacrew.user.dto.UserResponseDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacrew.common.document.ApiDocumentUtils.getDocumentRequest;
import static woowacrew.common.document.ApiDocumentUtils.getDocumentResponse;
import static woowacrew.common.document.SnippetUtils.userResponseDtoListSnippets;

@WebMvcTest(controllers = AdminDegreeApiController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "woowacrew.security.*")})
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
class AdminDegreeApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DegreeService degreeService;

    private UserContext userContext;

    @BeforeEach
    void setUp() {
        userContext = new UserContext(1L, "12345", "admin", UserRole.ROLE_ADMIN);
        SecurityContextSupport.updateContext(userContext);
    }

    @Test
    void showDegrees() throws Exception {
        List<DegreeWithUserCountResponseDto> degrees = new ArrayList<>();
        degrees.add(new DegreeWithUserCountResponseDto(1L, 1, 45));
        degrees.add(new DegreeWithUserCountResponseDto(2L, 2, 44));
        degrees.add(new DegreeWithUserCountResponseDto(3L, 3, 47));

        given(degreeService.findAllWithUserCount()).willReturn(degrees);

        mockMvc.perform(get("/api/degrees"))
                .andExpect(status().isOk())
                .andDo(document("degree/show",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("기수의 id"),
                                fieldWithPath("[].degreeNumber").type(JsonFieldType.NUMBER).description("기수"),
                                fieldWithPath("[].userCount").type(JsonFieldType.NUMBER).description("기수별 유저수")
                        )));
    }

    @Test
    void showDetailUsersOfDegree() throws Exception {
        List<UserResponseDto> users = new ArrayList<>();
        users.add(generateFirstDegreeCrew(1L, "123456", "woowa1"));
        users.add(generateFirstDegreeCrew(2L, "234567", "woowa2"));
        users.add(generateFirstDegreeCrew(3L, "345678", "woowa3"));

        given(degreeService.findUserByDegreeId(1L)).willReturn(users);

        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/degrees/{degreeId}/users", 1L))
                .andExpect(status().isOk())
                .andDo(document("degree/showDetail",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("degreeId").description("조회할 기수의 id")
                        ),
                        userResponseDtoListSnippets));
    }

    private UserResponseDto generateFirstDegreeCrew(Long id, String oauthId, String nickName) {
        LocalDate birthdayDate = LocalDate.of(2019, 1, 1);
        UserResponseDto userResponseDto = new UserResponseDto(id, oauthId, nickName, birthdayDate);
        userResponseDto.setUserRole(UserRole.ROLE_CREW);
        userResponseDto.setDegreeResponseDto(new DegreeResponseDto(1L, 1));
        return userResponseDto;
    }
}