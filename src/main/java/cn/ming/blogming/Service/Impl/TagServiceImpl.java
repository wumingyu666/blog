package cn.ming.blogming.Service.Impl;

import cn.ming.blogming.Execption.NotFoundExecption;
import cn.ming.blogming.Service.TagService;
import cn.ming.blogming.dao.Tagdao;
import cn.ming.blogming.domain.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private Tagdao tagdao;
    @Override
    public Tag saveTag(Tag tag) {
        return tagdao.save(tag);
    }

    @Override
    public List<Tag> ListTag(String ids) {
        return tagdao.findAllById(convertToList(ids));
    }
    private List<Long>convertToList(String ids){
        List<Long>list=new ArrayList<>();
        if(!"".equals(ids)&&ids!=null){
            String[] split = ids.split(",");
            for(int i=0;i<split.length;++i){
                list.add(new Long(split[i]));
            }
        }
        return list;
    }
    @Override
    public Tag getTag(Long id) {
        return tagdao.getOne(id);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagdao.findAll(pageable);
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort=new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable=new PageRequest(0,size,sort);
        return tagdao.findTop(pageable);
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag tag1 = tagdao.getOne(id);
        if(tag1==null) throw new NotFoundExecption("该标签不存在");
        BeanUtils.copyProperties(tag,tag1);
        return tagdao.save(tag1);
    }

    @Override
    public void deleteTag(Long id) {
        tagdao.deleteById(id);
    }

    @Override
    public Tag findByName(String name) {
        return tagdao.findByName(name);
    }

    @Override
    public List<Tag> listTag() {
        return tagdao.findAll();
    }
}
