package com.liuming.springandlangchain4j.generator.service.impl;

import static com.liuming.springandlangchain4j.generator.DTO数据模型.枚举.用户常量.USER_LOGIN_STATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;

import com.liuming.springandlangchain4j.exception.业务异常;
import com.liuming.springandlangchain4j.exception.错误代码;
import com.liuming.springandlangchain4j.generator.DTO数据模型.UserQueryRequest;
import com.liuming.springandlangchain4j.generator.DTO数据模型.UserVO;
import com.liuming.springandlangchain4j.generator.DTO数据模型.用户登录脱敏数据类型VO;
import com.liuming.springandlangchain4j.generator.DTO数据模型.枚举.用户角色枚举;
import com.liuming.springandlangchain4j.generator.entity.User;
import com.liuming.springandlangchain4j.generator.mapper.UserMapper;
import com.mybatisflex.core.query.QueryWrapper;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTests {

    @Spy
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(userService, "mapper", userMapper);
    }

    @Test
    void userRegisterRejectsBlankInput() {
        业务异常 exception = assertThrows(业务异常.class,
                () -> userService.userRegister("", "12345678", "12345678"));

        assertEquals(错误代码.PARAMS_ERROR.getCode(), exception.getCode());
    }

    @Test
    void userRegisterRejectsDuplicateAccount() {
        when(userMapper.selectCountByQuery(any(QueryWrapper.class))).thenReturn(1L);

        业务异常 exception = assertThrows(业务异常.class,
                () -> userService.userRegister("tester", "12345678", "12345678"));

        assertEquals("账号重复", exception.getMessage());
    }

    @Test
    void userRegisterReturnsGeneratedIdWhenSaveSucceeds() {
        when(userMapper.selectCountByQuery(any(QueryWrapper.class))).thenReturn(0L);
        doAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(12345L);
            return true;
        }).when(userService).save(any(User.class));

        long result = userService.userRegister("tester", "12345678", "12345678");

        assertEquals(12345L, result);
        verify(userService).save(argThat(user -> "tester".equals(user.getUserAccount())
                && "无名".equals(user.getUserName())
                && 用户角色枚举.USER.getValue().equals(user.getUserRole())));
    }

    @Test
    void getLoginUserVoReturnsNullWhenUserMissing() {
        assertNull(userService.getLoginUserVO(null));
    }

    @Test
    void getLoginUserVoCopiesSafeFields() {
        User user = new User();
        user.setId(8L);
        user.setUserAccount("tester");
        user.setUserName("name");

        用户登录脱敏数据类型VO loginUserVO = userService.getLoginUserVO(user);

        assertNotNull(loginUserVO);
        assertEquals(8L, loginUserVO.getId());
        assertEquals("tester", loginUserVO.getUserAccount());
        assertEquals("name", loginUserVO.getUserName());
    }

    @Test
    void userLoginStoresSessionState() {
        User storedUser = new User();
        storedUser.setId(9L);
        storedUser.setUserAccount("tester");
        when(userMapper.selectOneByQuery(any(QueryWrapper.class))).thenReturn(storedUser);
        MockHttpServletRequest request = new MockHttpServletRequest();

        用户登录脱敏数据类型VO result = userService.userLogin("tester", "12345678", request);

        assertEquals(9L, result.getId());
        assertEquals(storedUser, request.getSession().getAttribute(USER_LOGIN_STATE));
    }

    @Test
    void getLoginUserRejectsMissingSessionUser() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        业务异常 exception = assertThrows(业务异常.class, () -> userService.getLoginUser(request));

        assertEquals(错误代码.NOT_LOGIN_ERROR.getCode(), exception.getCode());
    }

    @Test
    void getLoginUserLoadsLatestUserFromPersistence() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        User sessionUser = new User();
        sessionUser.setId(77L);
        request.getSession().setAttribute(USER_LOGIN_STATE, sessionUser);
        User dbUser = new User();
        dbUser.setId(77L);
        dbUser.setUserName("latest");
        doReturn(dbUser).when(userService).getById(77L);

        User result = userService.getLoginUser(request);

        assertEquals("latest", result.getUserName());
    }

    @Test
    void userLogoutRemovesSessionState() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.getSession().setAttribute(USER_LOGIN_STATE, new User());

        assertTrue(userService.userLogout(request));
        assertNull(request.getSession().getAttribute(USER_LOGIN_STATE));
    }

    @Test
    void userLogoutRejectsMissingSessionState() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        业务异常 exception = assertThrows(业务异常.class, () -> userService.userLogout(request));

        assertEquals(错误代码.PARAMS_ERROR.getCode(), exception.getCode());
    }

    @Test
    void getUserVoListReturnsEmptyListForEmptyInput() {
        List<UserVO> result = userService.getUserVOList(List.of());

        assertTrue(result.isEmpty());
    }

    @Test
    void getQueryWrapperRejectsNullRequest() {
        业务异常 exception = assertThrows(业务异常.class, () -> userService.getQueryWrapper(null));

        assertEquals(错误代码.PARAMS_ERROR.getCode(), exception.getCode());
    }

    @Test
    void getQueryWrapperBuildsConditionsFromRequest() {
        UserQueryRequest request = new UserQueryRequest();
        request.setId(1L);
        request.setUserAccount("tester");
        request.setUserName("name");
        request.setUserProfile("profile");
        request.setUserRole("admin");
        request.setSortField("createTime");
        request.setSortOrder("ascend");

        QueryWrapper wrapper = userService.getQueryWrapper(request);

        assertNotNull(wrapper);
        assertTrue(wrapper.toSQL().contains("userAccount"));
        assertTrue(wrapper.toSQL().contains("createTime"));
    }
}