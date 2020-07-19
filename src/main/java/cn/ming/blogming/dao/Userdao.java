package cn.ming.blogming.dao;

import cn.ming.blogming.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Userdao extends JpaRepository<User,Long> {
    public User findByUsernameAndPassword(String username,String password);
}
