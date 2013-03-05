package com.unit.tests.controllers;

import com.controllers.rest.AccountRestController;
import com.exceptions.DataException;
import com.exceptions.NotFoundException;
import com.model.Client;
import com.model.ClientStatus;
import com.services.AppServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created with IntelliJ IDEA.
 * User: tnazar
 * Date: 3/4/13
 * Time: 10:16 AM
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration
public class AccountRestControllerTest {

    @InjectMocks
    AccountRestController controller;
    @Mock
    AppServiceImpl appService;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

    @Test
    public void testAccountUpdateStatus() throws Exception {
        String number = "1234567890";
        when(appService.updateClientStatus(number, ClientStatus.ACTIVE)).thenReturn(ClientStatus.BLOCKED);

        mockMvc.perform(post("/accountUpdateStatus")
                .param("number", number)
                .param("status", ClientStatus.ACTIVE.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(ClientStatus.BLOCKED.toString()));
    }

    @Test
    public void testAccountActive() throws Exception {
        Client client = new Client(1);
        client.setNumber("1234567890");
        client.setStatus(ClientStatus.ACTIVE);
        client.setFirstname("First");
        client.setLastname("Last");

        String exceptionMessage = "Exception message";
        when(appService.findActiveClient("null")).thenThrow(new NotFoundException(exceptionMessage));
        when(appService.findActiveClient("inactive")).thenThrow(new DataException(exceptionMessage));
        when(appService.findActiveClient(client.getNumber())).thenReturn(client);

        mockMvc.perform(get("/accountActive/null"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("error").value(exceptionMessage));

        mockMvc.perform(get("/accountActive/inactive"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("error").value(exceptionMessage));

        mockMvc.perform(get("/accountActive/" + client.getNumber()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(client.getFirstname() + " " + client.getLastname()))
                .andExpect(jsonPath("number").value(client.getNumber()))
                .andExpect(jsonPath("status").value(client.getStatus().toString()));
    }
}
