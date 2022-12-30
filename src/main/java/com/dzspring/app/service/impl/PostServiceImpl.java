package com.dzspring.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzspring.app.entity.Post;
import com.dzspring.app.repository.PostRepository;
import com.dzspring.app.service.PostService;

@Service("postServiceImpl")
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;
	
	@Override
	public List<Post> getHeadlineWithCategory(String category) {
		return postRepository.findByCategoryLimit10(category);
	}

}
