package be.uantwerpen.iw.ei.se.controllers;

import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by Thomas on 15/11/2015.
 */
public class FittsTestControllerTests
{
    @Mock
    private UserService userService;

    @InjectMocks
    private FittsTestController fittsTestController;

    private MockMvc mockMvc;

    User principalUser;

    @Before
    public void setup()
    {
        principalUser = new User("Test", "User");

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(fittsTestController).build();
    }

    @Test
    public void viewFittsTestPageTest() throws Exception
    {
        when(userService.getPrincipalUser()).thenReturn(principalUser);

        mockMvc.perform(get("/TestPortal/001")).andExpect(view().name("testPortal/fittsTest"));
    }
}