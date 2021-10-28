package com.nguyenduc.repository;

import com.nguyenduc.model.blog.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBlogRepository extends JpaRepository<Blog, Long> {
    Iterable<Blog> findAllByTitleContaining(String keyword);
}
