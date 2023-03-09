package com.springboot.blog.exception;

/*Two types of Exceptions occured in springboot application
 * 1.Data not found exception(Which are occured when we try to access data which is not present in database)
 * To print Data not found we declared resourceName variable.
 * 2.Field exceptions(Which are occured during validation). To print Field not found exception we declared fieldName,fieldValue variables
 *format() method returns the formatted string by a given locale, format, and argument. 
 *%s is a place holder which is used to pass the fields(resourceName,fieldName,fieldValue)dynamically
 *%s is dynamically replaced by mentioned 3 fields. Example ->Post not found with id:1 */
public class ResourceNotFoundException extends RuntimeException
{
	private String resourceName;
	private String fieldName;
	private long fieldValue;
	
	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s:'%s'",resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public long getFieldValue() {
		return fieldValue;
	}
	
	
}
