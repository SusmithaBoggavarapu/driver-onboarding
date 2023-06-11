package com.uber.controller;

import com.uber.client.AuthClient;
import com.uber.service.OnboardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class OnboardControllerTest {

    @InjectMocks
    private OnboardController onboardController;

    @Mock
    private OnboardService onboardService;

    @Mock
    private AuthClient authClient;

    @Test
    public void onboard(){
        Mockito.doNothing().when(onboardService).triggerOnboarding(Mockito.anyString());
        Mockito.when(authClient.validateUser(Mockito.any())).thenReturn("test");
        onboardController.onboard("auth_token");
    }

    @Test
    public void activate(){
        Mockito.doNothing().when(onboardService).activateDriver(Mockito.any());
        Mockito.when(authClient.validateUser(Mockito.any())).thenReturn("test");
        onboardController.activateOnboard("auth_token");
    }
}
