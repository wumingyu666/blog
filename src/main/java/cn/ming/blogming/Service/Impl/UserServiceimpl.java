package cn.ming.blogming.Service.Impl;

import cn.ming.blogming.Service.UserService;
import cn.ming.blogming.dao.Userdao;
import cn.ming.blogming.domain.User;
import cn.ming.blogming.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceimpl implements UserService {
    @Autowired
    private Userdao userdao;
    @Override
    public User checkUser(String username, String password) {
        return userdao.findByUsernameAndPassword(username, MD5Utils.code(password));
    }
}
