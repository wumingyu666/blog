package cn.ming.blogming.Service;

import cn.ming.blogming.domain.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    Type saveType(Type type);
    Type getType(Long id);
    Page<Type> listType(Pageable pageable);
    List<Type> listTypeTop(Integer size);
    Type updateType(Long id,Type type);
    void deleteType(Long id);
    Type findByName(String name);
    List<Type>listType();
}
