package com.nguyenduc.model.blog;

import com.nguyenduc.model.Image;

import java.util.Date;
import java.util.List;

public class BlogResponse {
    private Blog blog;
    private List<Image> images;

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
