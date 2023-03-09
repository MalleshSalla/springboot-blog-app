package com.springboot.blog.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse 
{
	//This class is used to send Paging response to the client
	
	//This field consisting of all blog data
	private List<PostDto> content;
	
	private int pageNo;
	private int pageSize;
	private long totalElements;
	private long totalPages;
	private boolean last;
	
}
