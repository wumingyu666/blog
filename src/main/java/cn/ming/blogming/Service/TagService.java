package cn.ming.blogming.Service;

import cn.ming.blogming.domain.Tag;
import cn.ming.blogming.domain.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    Tag saveTag(Tag tag);
    Tag getTag(Long id);
    Page<Tag> listTag(Pageable pageable);
    Tag updateTag(Long id, Tag tag);
    void deleteTag(Long id);
    Tag findByName(String name);
    List<Tag>listTag();
    List<Tag>ListTag(String ids);
    List<Tag> listTagTop(Integer size);
}
