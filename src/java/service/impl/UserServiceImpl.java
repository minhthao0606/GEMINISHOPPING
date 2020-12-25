package service.impl;

import java.io.File;
import java.util.List;

import dao.UserDao;
import model.User;
import service.UserService;
import dao.impl.UserDaoImpl;

public class UserServiceImpl implements UserService {

    UserDao userDao = (UserDao) new UserDaoImpl();

    @Override
    public boolean register(String username, String password, String email) {
        if (userDao.checkExistUsername(username)) {
            return false;
        }
        userDao.insert(new User(email, username, password));
        return true;
    }

    @Override
    public void insert(User user) {
        userDao.insert(user);
    }

    @Override
    public List<User> search(String username) {
        return userDao.search(username);
    }

    @Override
    public void edit(User newUser) {
        User oldUser = userDao.get(newUser.getId());

        oldUser.setEmail(newUser.getEmail());
        oldUser.setUsername(newUser.getUsername());
        oldUser.setPassword(newUser.getPassword());
        oldUser.setRoleId(newUser.getRoleId());
        if (newUser.getAvatar() != null) { // add new avartar
            String fileName = oldUser.getAvatar();
            final String dir = "D:\\Code\\JavaWeb\\GEMINISHOPPING\\image";
            File file = new File(dir + "/" + fileName);
            if (file.exists()) {
                file.delete();
            }
            oldUser.setAvatar(newUser.getAvatar());
        }
        userDao.edit(oldUser);
    }

    @Override
    public User get(int id) {
        return userDao.get(id);
    }

    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

    @Override
    public User get(String username) {
        return userDao.get(username);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User login(String username, String password) {
        User user = this.get(username);
        if (user != null && password.equals(user.getPassword())) { // account is exist
            return user;
        }
        return null;
    }

    public boolean checkExistEmail(String email) {
        return userDao.checkExistEmail(email);
    }

    public boolean checkExistUsername(String username) {
        return userDao.checkExistUsername(username);
    }

    @Override
    public void active(int id) {
        userDao.active(id);
    }
}
