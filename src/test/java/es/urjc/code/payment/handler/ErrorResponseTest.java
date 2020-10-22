package es.urjc.code.payment.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import es.urjc.code.payment.handler.ErrorResponse;

class ErrorResponseTest {

	private static final String DEFAULT_MESSAGE = "Default message";
	private static final Integer DEFAULT_STATUS = 500;

	@Test
	void givenValidDataWhenBuildThenReturnErrorResponse() {
		final ErrorResponse response = new ErrorResponse.Builder().withStatus(DEFAULT_STATUS)
				.withMessage(DEFAULT_MESSAGE).build();
		assertEquals(DEFAULT_STATUS, response.getStatus());
		assertEquals(DEFAULT_MESSAGE, response.getMessage());
	}

	@Test
	void shouldBeEqualsAndSymmetric() {
		final ErrorResponse response = new ErrorResponse.Builder().withStatus(DEFAULT_STATUS)
				.withMessage(DEFAULT_MESSAGE).build();
		final ErrorResponse otherResponse = new ErrorResponse.Builder().withStatus(DEFAULT_STATUS)
				.withMessage(DEFAULT_MESSAGE).build();

		assertTrue(response.equals(otherResponse) && otherResponse.equals(response));
		assertEquals(response.hashCode(),otherResponse.hashCode());
	}
}
