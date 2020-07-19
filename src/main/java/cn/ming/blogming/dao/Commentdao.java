package cn.ming.blogming.dao;

import cn.ming.blogming.domain.Blog;
import cn.ming.blogming.domain.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Commentdao extends JpaRepository<Comment,Long> {

    List<Comment> findByBlogIdAndParentCommentNull(Long BlogId, Sort sort);
}
