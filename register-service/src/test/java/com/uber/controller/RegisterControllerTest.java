package com.uber.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uber.entity.DriverDto;
import com.uber.entity.user.Role;
import com.uber.service.RegisterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolationException;
import java.io.File;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class RegisterControllerTest {

    @InjectMocks
    private RegisterController registerController;

    @Mock
    private RegisterService registerService;

    private static String USER_VALID_FILE_NAME = "src/test/resources/register/user_valid.json";

    @Test
    public void registerUser() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            DriverDto driverDto = objectMapper.readValue(new File(USER_VALID_FILE_NAME), DriverDto.class);

            BindingResult result = Mockito.mock(BindingResult.class);
            Mockito.when(result.hasErrors()).thenReturn(false);

            Mockito.doNothing().when(registerService).registerDriver(Mockito.any());

            registerController.registerDriver(driverDto,result);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void updateUser() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            DriverDto driverDto = objectMapper.readValue(new File(USER_VALID_FILE_NAME), DriverDto.class);

            BindingResult result = Mockito.mock(BindingResult.class);
            Mockito.when(result.hasErrors()).thenReturn(false);

            Mockito.doNothing().when(registerService).updateDriver(Mockito.any());

            registerController.updateDriver(driverDto,result);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
