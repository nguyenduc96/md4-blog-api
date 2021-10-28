package com.nguyenduc.service.image;

import com.nguyenduc.model.blog.Blog;
import com.nguyenduc.model.Image;
import com.nguyenduc.service.IGeneralService;

import java.util.List;

public interface IImageService extends IGeneralService<Image> {
    List<Image> findAllByBlog(Blog blog);
}
