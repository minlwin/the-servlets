package com.jdc.task.test;

import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;

import static org.hamcrest.Condition.*;

import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class HasRecordComponent<T> extends TypeSafeDiagnosingMatcher<T>{
	
	private final String componentName;
	private final Matcher<Object> valueMatcher;	
	
	public static <T> Matcher<T> hasRecordComponent(String componentName, Matcher<?> valueMatcher) {
		return new HasRecordComponent<>(componentName, valueMatcher);
	}

	@SuppressWarnings("unchecked")
	public HasRecordComponent(String componentName, Matcher<?> valueMatcher) {
		this.componentName = componentName;
		this.valueMatcher = (Matcher<Object>)valueMatcher;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("hasRecordComponent(").appendText(componentName).appendText(",")
			.appendDescriptionOf(valueMatcher)
			.appendText(")");
	}

	@Override
	protected boolean matchesSafely(T item, Description mismatchDescription) {
		return recordComponentOn(item, mismatchDescription)
				.and(WITH_READ_METHOD)
				.and(withValue(item))
				.matching(valueMatcher, "Record Component '%s'".formatted(componentName));
	}
	
	private Condition.Step<Method, Object> withValue(T bean) {
		return (method, mismatch) -> {
			try {
				return matched(method.invoke(bean), mismatch);
			} catch (Exception e) {
				mismatch.appendText(e.getMessage());
				return notMatched();
			}
		};
	}
	
	private Condition<RecordComponent> recordComponentOn(T item, Description mismatch) {
		var array = item.getClass().getRecordComponents();
		
		for(var comp : array) {
			if(comp.getName().equals(componentName)) {
				return matched(comp, mismatch);
			}
		}
		
		mismatch.appendText("There is no Record Component with name \"%s\".".formatted(componentName));
		return notMatched();
	}
	
	private static final Step<RecordComponent, Method> WITH_READ_METHOD = (prop, mismatch) ->  {
		var method = prop.getAccessor();
		if(method == null) {
			mismatch.appendText("Record Component \"%s\" is not readable.".formatted(prop.getName()));
			return notMatched();
		}
		
		return matched(method, mismatch);
	};

}
