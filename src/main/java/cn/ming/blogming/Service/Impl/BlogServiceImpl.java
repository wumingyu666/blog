package cn.ming.blogming.Service.Impl;

import cn.ming.blogming.Execption.NotFoundExecption;
import cn.ming.blogming.Service.BlogService;
import cn.ming.blogming.dao.Blogdao;
import cn.ming.blogming.domain.Blog;
import cn.ming.blogming.domain.BlogQuery;
import cn.ming.blogming.domain.Type;
import cn.ming.blogming.utils.MarkdownUtils;
import cn.ming.blogming.utils.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private Blogdao blogdao;
    @Override
    public Blog getBlog(Long id) {
        return blogdao.getOne(id);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogdao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate>predicates=new ArrayList<>();
                if(!"".equals(blog.getTitle()) && blog.getTitle()!=null){
                    predicates.add(criteriaBuilder.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                }
                if(blog.getTypeId()!=null){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
                if(blog.isRecommend()){
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }
    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogdao.getOne(id);
        if(blog==null){
            throw new NotFoundExecption("博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtml(content));
        blogdao.updateViews(id);
        return b;
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort=new Sort(Sort.Direction.DESC,"updateTime");
        Pageable pageable=new PageRequest(0,size,sort);
        return blogdao.findTop(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogdao.findByQuery(query,pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogdao.findAll(pageable);
    }
    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if(blog.getId()==null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else{
            blog.setUpdateTime(new Date());
        }

        return blogdao.save(blog);
    }

    @Override
    public Long countBlog() {
        return blogdao.count();
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String>years=blogdao.findGroupYear();
        Map<String, List<Blog>> map=new HashMap<>();
        for(String year:years){
            map.put(year,blogdao.findByYear(year));
        }
        return map;
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog blog1 = blogdao.getOne(id);
        if (blog1 == null) throw new NotFoundExecption("该博客不存在");
        BeanUtils.copyProperties(blog,blog1, MyBeanUtils.getNullPropertyNames(blog));
        blog1.setUpdateTime(new Date());
        return blogdao.save(blog1);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogdao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogdao.deleteById(id);
    }
}
