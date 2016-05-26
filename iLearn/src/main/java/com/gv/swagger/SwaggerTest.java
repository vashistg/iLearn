package com.gv.swagger;

import java.lang.reflect.Method;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wordnik.swagger.annotations.ApiOperation;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import scala.collection.mutable.StringBuilder;

public class SwaggerTest {
	public static void main(String[] args) {
		new SwaggerTest().scanForSwaggerAnnotations("com.gv.web.iLearn.controller");
	}
	
	public void scanForSwaggerAnnotations(String basePackage){
		new FastClasspathScanner(new String[] { basePackage })
		.matchClassesWithAnnotation(Controller.class, c -> {
			for (Method m : c.getMethods()) {
				/**
				 * This works for all the rest controllers and web socket controllers.
				 */
				RequestMapping reqAnnotation = m.getAnnotation(RequestMapping.class);
				MessageMapping msgAnnotation = m.getAnnotation(MessageMapping.class);
				if (reqAnnotation != null || msgAnnotation != null) {
					StringBuilder builder = new StringBuilder();
					
					/**
					 * This code needs to be repeated for all the annotations that we want to put.
					 */
					ApiOperation operation = m.getAnnotation(ApiOperation.class);
					if (operation == null) {
						builder.append("@ApiOperatiion ");
					}
					/**
					 * repeat ends
					 */
					
					if (builder.toString() != null && builder.toString().length() > 0) {
						System.out.println(
							"Controller ::" + c.getName() ) ;
						System.out.println("  Method :: " + m.getName() + " ");
						System.out.println("  Missing Annotations ");
						System.out.println("  " +builder.toString());
					}
				}
			}
		}).scan();
	}
		
}
