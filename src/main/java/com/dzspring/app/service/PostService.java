package com.dzspring.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dzspring.app.entity.Post;

@Service("postService")
public interface PostService {
	List<Post> getHeadlineWithCategory(String category);
}
