package org.maz.testdemo.controller;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.mockito.MockedStatic;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.withSettings;

/**
 * Idea is we going to use this filter which will then be injected within the bean when the test is running.
 * Within this filter there will be the code to mock the static class which will be running on the same thread
 * as the ones handling the request.
 */

@Component
public class TestStaticFilter extends OncePerRequestFilter {

	/**
	 * Need ensure the variable is thread safe so that the update and read is properly done, because at least 2 thread will be accessing this concurrently
	 * 1, the test and another the one that handles the request
	 */
	private AtomicReference<String> mockedNonParam = new AtomicReference<>(StringUtils.EMPTY);
	private AtomicReference<String> mockedParam = new AtomicReference<>(StringUtils.EMPTY);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try(MockedStatic<StaticMethod> mockedStaticMethod = mockStatic(StaticMethod.class, withSettings().verboseLogging())){
			mockedStaticMethod.when(StaticMethod::returnStaticValue).thenReturn(getMockNonParamValue());
			mockedStaticMethod.when(() -> StaticMethod.buildValue(any())).thenAnswer(answer -> getMockParamValue() + answer.getArgument(0));

			filterChain.doFilter(request, response);
		}
	}

	public void updateTheNonParamMockValue(final String mockValue){
		mockedNonParam.getAndSet(mockValue);
	}

	public String getMockNonParamValue() {
		return mockedNonParam.getAcquire();
	}

	public void updateTheParamMockValue(final String mockValue){
		mockedParam.getAndSet(mockValue);
	}

	public String getMockParamValue() {
		return mockedParam.getAcquire();
	}
}
