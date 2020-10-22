package es.urjc.code.payment.handler;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import es.urjc.code.payment.exception.BusinessException;
import es.urjc.code.payment.exception.CommunicationException;
import es.urjc.code.payment.exception.EntityNotFoundException;
import es.urjc.code.payment.exception.NotAvailableException;

@RestController
public class TestController {

    @GetMapping("/test/not-found")
    public void entityNotFound() {
        throw new EntityNotFoundException("entity not found");
    }

    @GetMapping("/test/not-found/iae-with-message")
    public void entityNotFoundNonUniqueResultWithMessage() {
        throw new EntityNotFoundException("entity not found", new IllegalArgumentException());
    }

    @GetMapping("/test/not-found/iae")
    public void entityNotFoundNonUniqueResult() {
        throw new EntityNotFoundException(new IllegalArgumentException("illegal argument"));
    }

    @GetMapping("/test/business-exception")
    public void business() {
        throw new BusinessException("business error");
    }
    
    @GetMapping("/test/communication-exception")
    public void communication() {
        throw new CommunicationException("communication error");
    }

    @GetMapping("/test/notavailable-exception")
    public void notavailable() {
        throw new NotAvailableException("notavailable error");
    }
    
    @GetMapping("/test/io-exception")
    public void io() throws IOException {
        throw new IOException("io error");
    }
}
