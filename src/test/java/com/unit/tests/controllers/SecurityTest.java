package com.unit.tests.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextLoader;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Qualifier("")
    @Resource
    private FilterChainProxy springSecurityFilterChain;

    private MockMvc mockMvc;

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
                .andExpect(forwardedUrl("app/login")) ;
    }
}
