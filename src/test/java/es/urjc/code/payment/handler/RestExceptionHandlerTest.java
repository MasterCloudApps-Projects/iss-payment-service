package es.urjc.code.payment.handler;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@ContextConfiguration(classes = { TestController.class, RestExceptionHandler.class })
@WebAppConfiguration
class RestExceptionHandlerTest {

    private TestController testController;

    private RestExceptionHandler restExceptionHandler;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
    	this.testController = new TestController();
    	this.restExceptionHandler = new RestExceptionHandler();
        mockMvc = MockMvcBuilders.standaloneSetup(testController)
                .setControllerAdvice(restExceptionHandler).build();
    }

    @Test
    void testNotFoundException() throws Exception {
        mockMvc.perform(get("/test/not-found")).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("entity not found"));
    }

    @Test
    void testNotFoundExceptionWithThrowable() throws Exception {
        mockMvc.perform(get("/test/not-found/iae")).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("java.lang.IllegalArgumentException: illegal argument"));
    }

    
    @Test
    void testNotFoundExceptionWithThrowableAndMessage() throws Exception {
        mockMvc.perform(get("/test/not-found/iae-with-message")).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("entity not found"));
    }
    
    @Test
    void testBusinessException() throws Exception {
        mockMvc.perform(get("/test/business-exception")).andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").value("business error"));
    }

    @Test
    void testCommunicationException() throws Exception {
        mockMvc.perform(get("/test/communication-exception")).andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("communication error"));
    }
    
    @Test
    void testNotAvailableException() throws Exception {
        mockMvc.perform(get("/test/notavailable-exception")).andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.message").value("notavailable error"));
    }
    
    @Test
    void testIOException() throws Exception {
        mockMvc.perform(get("/test/io-exception")).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Bad Request"));
    }
    
    @Test
    public void testValidationError() throws Exception {
    	String content = new ObjectMapper().writeValueAsString( new es.urjc.code.payment.handler.TestController.TestFieldValidation());
    	MvcResult result = mockMvc.perform(post("/test/validation-exception").contentType(MediaType.APPLICATION_JSON).content(content))
        .andExpect(status().isBadRequest())
        .andReturn();
    	String body = result.getResponse().getContentAsString();
    	assertTrue(body.contains("must not be null"));
    }

}
