package com.nguyenduc.service.blog;

import com.nguyenduc.model.blog.Blog;
import com.nguyenduc.service.IGeneralService;
import org.springframework.data.domain.Page;

public interface IBlogService extends IGeneralService<Blog> {
    Iterable<Blog> findAllByTitleContaining(String keyword);
}
