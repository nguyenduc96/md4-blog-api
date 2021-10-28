package com.nguyenduc.service.blog;

import com.nguyenduc.model.blog.Blog;
import com.nguyenduc.repository.IBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlogService implements IBlogService{
    @Autowired
    private IBlogRepository blogRepository;

    @Override
    public Iterable<Blog> findAll() {
        return blogRepository.findAll();
    }

    @Override
    public Iterable<Blog> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return blogRepository.findAll(pageRequest);
    }

    @Override
    public Optional<Blog> findById(Long id) {
        return blogRepository.findById(id);
    }

    @Override
    public Blog save(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public void delete(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Iterable<Blog> findAllByTitleContaining(String keyword) {
        return blogRepository.findAllByTitleContaining(keyword);
    }
}
