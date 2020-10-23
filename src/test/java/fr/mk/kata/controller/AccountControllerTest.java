package fr.mk.kata.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.mk.kata.dto.AccountResponsePayload;
import fr.mk.kata.dto.OperationDto;
import fr.mk.kata.model.Operation;
import fr.mk.kata.service.AccountServiceImpl;
import fr.mk.kata.service.OperationServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)

public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountServiceImpl accountServiceImpl;

    @MockBean
    private OperationServiceImpl operationServiceImpl;


    private ObjectMapper mapper = new ObjectMapper();
    private OperationDto operationDto;

    @Before
    public void init() {
        operationDto = OperationDto.builder().amount(100L).operationType("DEPOSIT").build();
    }

    @Test
    public void should_display_operations() throws Exception {
        doReturn(new ArrayList<Operation>()).when(operationServiceImpl).fetchBy(1L);

        mockMvc.perform(get("/accounts/1/operations")
                .content(mapper.writeValueAsString(operationDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void should_response_with_status_200() throws Exception {
        doReturn(AccountResponsePayload.builder().build()).when(accountServiceImpl).updateBalance(operationDto, 1L);

        mockMvc.perform(post("/accounts/1/balance/update")
                .content(mapper.writeValueAsString(operationDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

}
