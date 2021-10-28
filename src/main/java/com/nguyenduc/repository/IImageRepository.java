package com.nguyenduc.repository;

import com.nguyenduc.model.blog.Blog;
import com.nguyenduc.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByBlog(Blog blog);
}
