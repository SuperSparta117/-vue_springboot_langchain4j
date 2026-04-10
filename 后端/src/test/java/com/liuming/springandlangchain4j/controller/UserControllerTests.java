package com.liuming.springandlangchain4j.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.liuming.springandlangchain4j.exception.业务异常;
import com.liuming.springandlangchain4j.exception.错误代码;
import com.liuming.springandlangchain4j.exception.前后端异常统一.全局异常管理;
import com.liuming.springandlangchain4j.generator.DTO数据模型.用户登录脱敏数据类型VO;
import com.liuming.springandlangchain4j.generator.entity.User;
import com.liuming.springandlangchain4j.generator.service.UserService;

class UserControllerTests {

    private MockMvc mockMvc;

    private UserService userService;

        @BeforeEach
        void setUp() {
                userService = mock(UserService.class);
                UserController userController = new UserController();
                ReflectionTestUtils.setField(userController, "userService", userService);

                ObjectMapper objectMapper = new ObjectMapper();
                SimpleModule module = new SimpleModule();
                module.addSerializer(Long.class, ToStringSerializer.instance);
                module.addSerializer(Long.TYPE, ToStringSerializer.instance);
                objectMapper.registerModule(module);
                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper);

                mockMvc = MockMvcBuilders.standaloneSetup(userController, new HealthController())
                                .setControllerAdvice(new 全局异常管理())
                                .setMessageConverters(converter)
                                .build();
        }

    @Test
    void healthEndpointReturnsOkPayload() throws Exception {
        mockMvc.perform(get("/health/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(0)))
                .andExpect(jsonPath("$.data", is("ok")));
    }

    @Test
    void registerEndpointReturnsCreatedUserId() throws Exception {
        when(userService.userRegister("tester", "12345678", "12345678")).thenReturn(66L);

        mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          \"userAccount\": \"tester\",
                          \"userPassword\": \"12345678\",
                          \"checkPassword\": \"12345678\"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(0)))
                .andExpect(jsonPath("$.data", is("66")));

        verify(userService).userRegister("tester", "12345678", "12345678");
    }

    @Test
    void registerEndpointUsesGlobalExceptionHandlerForBusinessError() throws Exception {
        when(userService.userRegister("tester", "12345678", "12345678"))
                .thenThrow(new 业务异常(错误代码.PARAMS_ERROR, "账号重复"));

        mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          \"userAccount\": \"tester\",
                          \"userPassword\": \"12345678\",
                          \"checkPassword\": \"12345678\"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(40000)))
                .andExpect(jsonPath("$.message", is("账号重复")));
    }

    @Test
    void loginEndpointReturnsSanitizedUser() throws Exception {
        用户登录脱敏数据类型VO loginUser = new 用户登录脱敏数据类型VO();
        loginUser.setId(88L);
        loginUser.setUserAccount("tester");
        when(userService.userLogin(eq("tester"), eq("12345678"), any())).thenReturn(loginUser);

        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          \"userAccount\": \"tester\",
                          \"userPassword\": \"12345678\"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(0)))
                .andExpect(jsonPath("$.data.id", is("88")))
                .andExpect(jsonPath("$.data.userAccount", is("tester")));
    }

    @Test
    void getLoginUserEndpointWrapsServiceResult() throws Exception {
        User loginUser = new User();
        loginUser.setId(9L);
        用户登录脱敏数据类型VO loginUserVO = new 用户登录脱敏数据类型VO();
        loginUserVO.setId(9L);
        loginUserVO.setUserAccount("tester");
        when(userService.getLoginUser(any())).thenReturn(loginUser);
        when(userService.getLoginUserVO(loginUser)).thenReturn(loginUserVO);

        mockMvc.perform(get("/user/get/login"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(0)))
                .andExpect(jsonPath("$.data.id", is("9")))
                .andExpect(jsonPath("$.data.userAccount", is("tester")));
    }

    @Test
    void logoutEndpointReturnsTrueWhenServiceSucceeds() throws Exception {
        when(userService.userLogout(any())).thenReturn(true);

        mockMvc.perform(post("/user/logout"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(0)))
                .andExpect(jsonPath("$.data", is(true)));
    }
}