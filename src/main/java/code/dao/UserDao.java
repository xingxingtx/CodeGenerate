package code.dao;

import code.model.User;

public interface UserDao {

void insert(User user);

void delete(User user);

User select(User user);

void update(User user);



}