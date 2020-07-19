package cn.ming.blogming.Service;

import cn.ming.blogming.domain.Blog;
import cn.ming.blogming.domain.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog getBlog(Long id);
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
    Page<Blog> listBlog(Pageable pageable);
    Blog saveBlog(Blog blog);
    Blog updateBlog(Long id,Blog blog);
    void deleteBlog(Long id);
    List<Blog> listRecommendBlogTop(Integer size);
    Page<Blog> listBlog(String query,Pageable pageable);
    Blog getAndConvert(Long id);
    Page<Blog> listBlog(Long tagId,Pageable pageable);
    Map<String,List<Blog>>archiveBlog();
    Long countBlog();
}
