package cn.ming.blogming.Service;

import cn.ming.blogming.domain.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);
    Comment savecomment(Comment comment);
}
