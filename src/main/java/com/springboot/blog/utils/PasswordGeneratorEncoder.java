package com.springboot.blog.utils;

import java.util.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGeneratorEncoder
{
	//This class is used to convert user passwords to encrypted format to store in database. If passwords not encrypted than spring will not 
	public static void main(String[] args)
	{
		PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
//		System.out.println(passwordEncoder.encode("mallesh"));
//		System.out.println(passwordEncoder.encode("admin"));
		System.out.println(passwordEncoder.encode("salla"));
		
		Date currentDate=new Date();
		Date expirationDate=new Date(currentDate.getTime()+604800000);
		System.out.println(expirationDate);
	}
}
