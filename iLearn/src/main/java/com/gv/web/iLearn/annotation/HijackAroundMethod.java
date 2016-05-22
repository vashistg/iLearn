package com.gv.web.iLearn.annotation;
import java.util.Arrays;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class HijackAroundMethod implements MethodInterceptor{

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println("Method name : "
				+ invocation.getMethod().getName());
		System.out.println("Method arguments : "
				+ Arrays.toString(invocation.getArguments()));

		// same with MethodBeforeAdvice
		System.out.println("HijackAroundMethod : Before method hijacked!");

		try {
			// proceed to original method call
			Object result = invocation.proceed();

			// same with AfterReturningAdvice
			System.out.println("HijackAroundMethod : After method hijacked!");

			return result;

		} catch (IllegalArgumentException e) {
			// same with ThrowsAdvice
			System.out.println("HijackAroundMethod : Throw exception hijacked!");
			throw e;
		}
	}

}
