package com.unit.tests;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextLoader;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created with IntelliJ IDEA.
 * User: tnazar
 * Date: 3/5/13
 * Time: 11:02 AM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader =  ContextLoader.class, locations = {
        "classpath:applicationContext.xml",
        "file:WebContent/WEB-INF/login-security.xml",
        "file:WebContent/WEB-INF/dispatcher-servlet.xml"})
public class SecurityTest {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private WebApplicationContext cxt;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    private MockMvc mockMvc;

    private static Authentication userAuthentication;
    private static Authentication adminAuthentication;

    @BeforeClass
    public static void beforeClass() {
        List<GrantedAuthority> userAuthorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        userAuthentication = new UsernamePasswordAuthenticationToken("user", "user", userAuthorities);

        List<GrantedAuthority> adminAuthorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN");
        adminAuthentication = new UsernamePasswordAuthenticationToken("admin", "admin", adminAuthorities);
    }

    @Before
    public void setupContainer() throws Exception
    {
        System.out.println("cxt " + cxt);
        System.out.println("springSecurityFilterChain " + springSecurityFilterChain);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(cxt).addFilters(springSecurityFilterChain).build();
    }

    @Test
    public void testShouldRedirectToLoginPage() throws Exception {
        mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("http://localhost/login")) ;
    }

    @Test
    public void testLoginPageAvailable() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login")) ;

        mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login")) ;
    }

    @Test
    public void testAdminAuthority() throws Exception {

        SecurityContextHolder.getContext().setAuthentication(userAuthentication);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        mockMvc.perform(get("/admin").session(session))
                .andDo(print())
                .andExpect(status().isForbidden()) ;

        mockMvc.perform(get("/accounts").session(session))
                .andDo(print())
                .andExpect(status().isForbidden());

        SecurityContextHolder.getContext().setAuthentication(adminAuthentication);
        session = new MockHttpSession();
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        mockMvc.perform(get("/admin").session(session))
                .andDo(print())
                .andExpect(status().isOk()) ;

    }

    @After
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }
}
