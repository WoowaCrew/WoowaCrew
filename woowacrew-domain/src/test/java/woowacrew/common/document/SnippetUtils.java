package woowacrew.common.document;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

public class SnippetUtils {
    public static ResponseFieldsSnippet articleResponseDtoSnippets = responseFields(
            fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
            fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
            fieldWithPath("content").type(JsonFieldType.STRING).description("게시글"),
            fieldWithPath("userResponseDto.id").type(JsonFieldType.NUMBER).description("작성자 id"),
            fieldWithPath("userResponseDto.oauthId").type(JsonFieldType.STRING).description("작성자 github ouath id"),
            fieldWithPath("userResponseDto.nickname").type(JsonFieldType.STRING).description("작성자 닉네임"),
            fieldWithPath("userResponseDto.birthday").type(JsonFieldType.STRING).description("작성자 생일"),
            fieldWithPath("userResponseDto.userRole").type(JsonFieldType.STRING).description("작성자 Role"),
            fieldWithPath("userResponseDto.degreeResponseDto.id").type(JsonFieldType.NUMBER).description("작성자 기수 id"),
            fieldWithPath("userResponseDto.degreeResponseDto.degreeNumber").type(JsonFieldType.NUMBER).description("작성자 기수"),
            fieldWithPath("createdDate").type(JsonFieldType.STRING).description("게시글 생성일"),
            fieldWithPath("lastModifiedDate").type(JsonFieldType.STRING).description("게시글 수정일")
    );

    public static ResponseFieldsSnippet articleResponseDtosSnippets = responseFields(
            fieldWithPath("pageNumber").type(JsonFieldType.NUMBER).description("페이지 번호"),
            fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수"),
            fieldWithPath("articles.[].id").type(JsonFieldType.NUMBER).description("게시글 id"),
            fieldWithPath("articles.[].title").type(JsonFieldType.STRING).description("제목"),
            fieldWithPath("articles.[].content").type(JsonFieldType.STRING).description("게시글"),
            fieldWithPath("articles.[].userResponseDto.id").type(JsonFieldType.NUMBER).description("작성자 id"),
            fieldWithPath("articles.[].userResponseDto.oauthId").type(JsonFieldType.STRING).description("작성자 github ouath id"),
            fieldWithPath("articles.[].userResponseDto.nickname").type(JsonFieldType.STRING).description("작성자 닉네임"),
            fieldWithPath("articles.[].userResponseDto.birthday").type(JsonFieldType.STRING).description("작성자 생일"),
            fieldWithPath("articles.[].userResponseDto.userRole").type(JsonFieldType.STRING).description("작성자 Role"),
            fieldWithPath("articles.[].userResponseDto.degreeResponseDto.id").type(JsonFieldType.NUMBER).description("작성자 기수 id"),
            fieldWithPath("articles.[].userResponseDto.degreeResponseDto.degreeNumber").type(JsonFieldType.NUMBER).description("작성자 기수"),
            fieldWithPath("articles.[].createdDate").type(JsonFieldType.STRING).description("게시글 생성일"),
            fieldWithPath("articles.[].lastModifiedDate").type(JsonFieldType.STRING).description("게시글 수정일")
    );

    public static ResponseFieldsSnippet userResponseDtoListSnippets = responseFields(
            fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("유저 id"),
            fieldWithPath("[].oauthId").type(JsonFieldType.STRING).description("유저 github ouath id"),
            fieldWithPath("[].nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
            fieldWithPath("[].birthday").type(JsonFieldType.STRING).description("유저 생일"),
            fieldWithPath("[].userRole").type(JsonFieldType.STRING).description("유저 Role"),
            fieldWithPath("[].degreeResponseDto.id").type(JsonFieldType.NUMBER).description("유저 기수 id"),
            fieldWithPath("[].degreeResponseDto.degreeNumber").type(JsonFieldType.NUMBER).description("유저 기수")
    );
}
