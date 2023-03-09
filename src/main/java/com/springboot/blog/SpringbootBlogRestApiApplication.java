package com.springboot.blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootBlogRestApiApplication {

	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}
	/*Steps to add ModelMapper
	 * 1.Add ModelMapper maven dependency in pom.xml
	 * 2.Define ModelMapper bean in spring configuration(in main method class)
	 * 3.Inject ModelMapper(create object of ModelPaper) in service class and use ModelMapper
	 * */
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}

}