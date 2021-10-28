package com.nguyenduc.controller;

import com.nguyenduc.model.blog.Blog;
import com.nguyenduc.model.blog.BlogForm;
import com.nguyenduc.model.Image;
import com.nguyenduc.model.blog.BlogResponse;
import com.nguyenduc.service.blog.IBlogService;
import com.nguyenduc.service.image.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private IBlogService blogService;

    @Autowired
    private IImageService imageService;

    @Value("${file-upload}")
    private String fileUpload;

    @GetMapping
    public ResponseEntity<Page<BlogResponse>> showHome(@RequestParam(name = "q", required = false) String q,
                                                       @RequestParam(value = "page", defaultValue = "0") int page) {
        int size = 5;
        Iterable<Blog> blogs;
        List<BlogResponse> blogResponses = new ArrayList<>();
        if (q == null || q.equals("")) {
            blogs = blogService.findAll();
            for (Blog b : blogs) {
                List<Image> images = imageService.findAllByBlog(b);
                BlogResponse blogResponse = new BlogResponse();
                blogResponse.setBlog(b);
                blogResponse.setImages(images);
                blogResponses.add(blogResponse);
            }
        } else {
            blogs = blogService.findAllByTitleContaining(q);
            for (Blog b : blogs) {
                List<Image> images = imageService.findAllByBlog(b);
                BlogResponse blogResponse = new BlogResponse();
                blogResponse.setBlog(b);
                blogResponse.setImages(images);
                blogResponses.add(blogResponse);
            }
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), blogResponses.size());
        Page<BlogResponse> blogResponses1;
        if(start > blogResponses.size()){
            blogResponses1 = new PageImpl<>(blogResponses, pageRequest, blogResponses.size());
        } else {
            blogResponses1 = new PageImpl<>(blogResponses.subList(start, end), pageRequest, blogResponses.size());
        }
        return new ResponseEntity<>(blogResponses1, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Blog> create(BlogForm blogForm, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasFieldErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<MultipartFile> multipartFiles = blogForm.getImages();
        Blog blog = new Blog();
        blog.setDate(new Date());
        if (blogForm.getId() != null) {
            blog.setId(blogForm.getId());
        }
        blog.setContent(blogForm.getContent());
        blog.setName(blogForm.getName());
        blog.setTitle(blogForm.getTitle());
        blog = blogService.save(blog);
        if (multipartFiles != null) {
            for (MultipartFile multipartFile : multipartFiles) {
                Image image = new Image();
                image.setBlog(blog);
                FileCopyUtils.copy(multipartFile.getBytes(), new File(fileUpload + multipartFile.getOriginalFilename()));
                image.setFile(multipartFile.getOriginalFilename());
                imageService.save(image);
            }
        } else {
            Image image = new Image();
            image.setBlog(blog);
            imageService.save(image);
        }
        return new ResponseEntity<>(blog, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Blog> blog = blogService.findById(id);
        if (!blog.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Image> images = imageService.findAllByBlog(blog.get());
        blogService.delete(id);
        if (images != null) {
            for (Image image : images) {
                imageService.delete(image.getId());
                new File(fileUpload + image.getFile()).delete();
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
