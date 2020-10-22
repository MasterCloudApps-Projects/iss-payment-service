package es.urjc.code.payment.exception;

public class CommunicationException extends RuntimeException {

	private static final long serialVersionUID = -2003244849099044564L;

	public CommunicationException(String msg) {
        super(msg);
    }
}
