package org.maz.testdemo.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MainControllerTest {

	private final String MOCK_VALUE_NON_PARAM = "YOUARE_BEING_MOCKED";
	private final String MOCK_VALUE_PARAM = "YOUARE_BEING_MOCKED_PARAM_";
	private final String MOCK_PARAM = "mock_param";

	@Autowired
	private TestRestTemplate testRestTemplate;
	@Autowired
	private TestStaticFilter testStaticFilter;

	@Test
	@DisplayName("Given endpoint of root without any parameter, when call, return the predefined static value from the mock of the static")
	void testCallWithoutParameter() {
		testStaticFilter.updateTheNonParamMockValue(MOCK_VALUE_NON_PARAM);

		final var response = testRestTemplate.getForEntity("/", String.class);
		assertThat(response).isNotNull();
		assertThat(response.getBody()).isEqualTo(MOCK_VALUE_NON_PARAM);
	}

	@Test
	@DisplayName("Given endpoint of root with any parameter, when call, return the predefined static value from the mock of the static")
	void testCallWithParameter() {
		testStaticFilter.updateTheParamMockValue(MOCK_VALUE_PARAM);

		final var response = testRestTemplate.getForEntity("/?param="+MOCK_PARAM, String.class);
		assertThat(response).isNotNull();
		assertThat(response.getBody()).isEqualTo(MOCK_VALUE_PARAM+MOCK_PARAM);
	}
}
