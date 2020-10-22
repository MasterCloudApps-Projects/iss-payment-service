package es.urjc.code.payment.handler;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ErrorResponse {
    private int status;
    private String message;
    
    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ErrorResponse))
			return false;
		ErrorResponse that = (ErrorResponse) o;
		return new EqualsBuilder().append(status, that.status)
				                  .append(message, that.message)
				                  .isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(status).append(message).toHashCode();
	}
	
    public static final class Builder {

        private final ErrorResponse object;

        public Builder() {
            object = new ErrorResponse();
        }

        public Builder withStatus(int value) {
            object.status = value;
            return this;
        }

        public Builder withMessage(String value) {
            object.message = value;
            return this;
        }

        public ErrorResponse build() {
            return object;
        }

    }

}