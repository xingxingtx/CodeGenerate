package code.service;




import code.model.User;


public interface IUserService {

void insert(User user);

void delete(User user);

User select(User user);

void update(User user);


}