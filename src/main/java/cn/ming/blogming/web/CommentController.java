package cn.ming.blogming.web;

import cn.ming.blogming.Service.BlogService;
import cn.ming.blogming.Service.CommentService;
import cn.ming.blogming.domain.Comment;
import cn.ming.blogming.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;
    @Value("${comment.avator}")
    private String avator;
    //刷新评论区
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog::commentList";
    }
    //提交评论
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        Long blogId=comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user=(User) session.getAttribute("user");
        if (user != null) {
            comment.setAvator(user.getAvator());
            comment.setAdminComment(true);
        }else{
            comment.setAvator(avator);
            comment.setAdminComment(false);
        }
        commentService.savecomment(comment);
        return "redirect:/comments/"+blogId;
     }
}
