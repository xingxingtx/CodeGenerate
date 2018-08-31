package code.mapper;



import code.model.User;


public interface UserMapper {

void insert(User user);

void delete(User user);

void update(User user);

User select(User user);

}