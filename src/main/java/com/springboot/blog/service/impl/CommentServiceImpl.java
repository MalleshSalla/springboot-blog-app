package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;

@Service //It indicates that it will be auto detected during class path scanning and we can configure it has spring bean and we can inject it in another classes 
public class CommentServiceImpl implements CommentService
{
	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private ModelMapper mapper;
	
	public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository, ModelMapper mapper) {
	
		this.commentRepository = commentRepository;
		this.postRepository=postRepository;
		this.mapper=mapper;
	}


	public CommentDto createComment(long postId, CommentDto commentDto) 
	{
		Comment comment=mapToEntity(commentDto);
		
		//Retrieve post entity by id
		Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		
		//Set post to comment entity
		comment.setPost(post);
		
		//save comment entity in database
		Comment newComment=commentRepository.save(comment);
		return mapToDto(newComment);
	}

	
	public List<CommentDto> getCommentsByPostId(long postId)
	{
		//retrieve comments by postId
		//List<Comment> is entity type(Java object type)
		List<Comment> comments=commentRepository.findByPostId(postId);
		
		//Convert list of comment entites to list of comment Dto
		List<CommentDto> listCommentDto=comments.stream().map(comment->mapToDto(comment)).collect(Collectors.toList());
		return listCommentDto;
	}
	
	
	public CommentDto getCommentsById(Long postId, Long commentId)
	{
		//Retrieve post entity by id
		Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
	
		//Retrieve comment by id
		Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Post", "id", commentId));
		
		if(comment.getPost().getId()!=(post.getId()))
		{
			throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belongs to post");
		}
		return mapToDto(comment);
	}
	
	@Override
	public CommentDto updateComment(Long postId, long commentId, CommentDto commentRequest)
	{
		//Retrieve post entity by id
		Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
			
		//Retrieve comment entity by id
		Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Post", "id", commentId));
				
		//Checking weather postId and commentId are matching or not, if not matching throws exception
		if(comment.getPost().getId()!=(post.getId()))
		{
			throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belongs to post");
		}
		
		//Setting updated details in comment object
		comment.setName(commentRequest.getName());
		comment.setEmail(commentRequest.getEmail());
		comment.setBody(commentRequest.getBody());
		
		//Saving updated comment details in database
		Comment updatedComment=commentRepository.save(comment);
		return mapToDto(updatedComment);
	}
	
	public void deleteComment(Long postId, Long commentId) 
	{
		//Retrieve post entity by id
		Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
					
		//Retrieve comment entity by id
		Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Post", "id", commentId));
						
		//Checking weather postId and commentId are matching or not, if not matching throws exception
		if(comment.getPost().getId()!=(post.getId()))
		{
			throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belongs to post");
		}
		commentRepository.delete(comment);
	}

	
	private CommentDto mapToDto(Comment comment)
	{
		CommentDto commentDto=mapper.map(comment, CommentDto.class);
		/*CommentDto commentDto=new CommentDto();
		commentDto.setId(comment.getId());
		commentDto.setName(comment.getName());
		commentDto.setEmail(comment.getEmail());
		commentDto.setBody(comment.getBody());*/
		return commentDto;
	}
	
	private Comment mapToEntity(CommentDto commentDto)
	{
		Comment comment=mapper.map(commentDto, Comment.class);
		/*Comment comment=new Comment();
		comment.setId(commentDto.getId());
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());*/
		return comment;
	}


	

	





	


	
}
