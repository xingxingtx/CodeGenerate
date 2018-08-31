package code.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import code.model.User;
import code.mapper.UserMapper;
import code.service.IUserService;


@Service
public class UserServiceImpl implements IUserService {

@Autowired
private UserMapper userMapper;

@Override
public void insert(User user) {

userMapper.insert(user);
}

@Override
public void delete(User user){

userMapper.delete(user);
}

@Override
public void update(User user) {
this.userMapper.update(user);
}


@Override
public User select(User user) {
return this.userMapper.select(user);
}

}