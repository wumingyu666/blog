package cn.ming.blogming.Service.Impl;

import cn.ming.blogming.Execption.NotFoundExecption;
import cn.ming.blogming.Service.TypeService;
import cn.ming.blogming.dao.Typedao;
import cn.ming.blogming.domain.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private Typedao typedao;
    @Transactional
    @Override
    public Type saveType(Type type) {
        Type save = typedao.save(type);
        return save;
    }
    @Transactional
    @Override
    public Type getType(Long id) {
       return typedao.getOne(id);
    }
    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typedao.findAll(pageable);
    }
    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type one = typedao.getOne(id);
        if(one==null){
            throw new NotFoundExecption("不存在该分类");
        }
        BeanUtils.copyProperties(type,one);
        return typedao.save(one);

    }

    @Override
    public List<Type> listType() {
        return typedao.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort=new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable=new PageRequest(0,size,sort);
        return typedao.findTop(pageable);
    }

    @Override
    public Type findByName(String name) {
        return typedao.findByName(name);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typedao.deleteById(id);
    }
}
