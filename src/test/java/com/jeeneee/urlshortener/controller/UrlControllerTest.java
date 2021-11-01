package com.jeeneee.urlshortener.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.removeHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeeneee.urlshortener.model.Url;
import com.jeeneee.urlshortener.model.UrlRequest;
import com.jeeneee.urlshortener.service.UrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@WebMvcTest(UrlController.class)
@ExtendWith(RestDocumentationExtension.class)
class UrlControllerTest {

    private static final String LONG_URL = "https://github.com/jeeneee";
    private static final String SHORT_URL = "A3ec81D";

    @MockBean
    private UrlService urlService;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext,
        RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .apply(documentationConfiguration(restDocumentation).operationPreprocessors()
                .withRequestDefaults(prettyPrint(), removeHeaders("Content-Length", "Host"))
                .withResponseDefaults(prettyPrint(), removeHeaders("Content-Length", "Vary"))
            )
            .alwaysDo(print())
            .build();
    }

    @DisplayName("short url을 생성한다.")
    @Test
    void createShortUrl() throws Exception {
        String request = objectMapper.writeValueAsString(
            new UrlRequest("https://github.com/jeeneee"));
        Url response = new Url(LONG_URL, SHORT_URL);
        given(urlService.saveIfAbsent(any(UrlRequest.class))).willReturn(response);

        ResultActions result = mockMvc.perform(
            post("/")
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .content(request)
        );

        result.andExpect(status().isCreated())
            .andDo(
                document("url/create",
                    requestFields(
                        fieldWithPath("longUrl").type(JsonFieldType.STRING).description("URL")
                    ),
                    responseHeaders(
                        headerWithName("Location").description("Created URL")
                    )
                )
            );
    }

    @DisplayName("해당 long url로 리다이렉트한다.")
    @Test
    void redirect() throws Exception {
        Url response = new Url(LONG_URL, SHORT_URL);
        given(urlService.findByShortUrl(any())).willReturn(response);

        ResultActions result = mockMvc.perform(
            get("/" + SHORT_URL)
        );

        result.andExpect(status().is3xxRedirection())
            .andDo(
                document("url/redirect",
                    responseHeaders(
                        headerWithName("Location").description("Redirected URL")
                    )
                )
            );
    }
}