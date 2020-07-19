package cn.ming.blogming.Service;

import cn.ming.blogming.domain.User;

public interface UserService {
    public User checkUser(String username,String password);
}
