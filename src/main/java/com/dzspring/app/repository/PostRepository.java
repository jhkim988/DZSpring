package com.dzspring.app.repository;

import java.util.List;

import com.dzspring.app.entity.Post;

public interface PostRepository {
	List<Post> findByCategoryLimit10(String category);
}
