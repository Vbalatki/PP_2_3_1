package web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private final UserDao dao;

    public UserServiceImpl(UserDao dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        logger.debug("Получение всех пользователей");
        List<User> users = dao.getAllUsers();
        logger.info("Полученные {} пользователи", users.size());
        return users;
    }

    @Override
    @Transactional
    public User getUserById(int id) {
        logger.debug("Пользователь с ID: {}", id);
        User user = dao.getUserById(id);
        if (user != null) {
            logger.info("Пользователь с ID: {} найден", id);
        } else {
            logger.warn("Пользователь с ID: {} не найден", id);
        }
        return user;
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        logger.debug("Сохранение пользователя: {}", user);
        dao.saveUser(user);
        logger.info("Успешное сохранение пользователя: {}", user);
    }

    @Override
    @Transactional
    public void updateUser(User updatedUser, int id) {
        logger.debug("Обновление пользователя с ID: {}", id);
        User user = getUserById(id);
        if (user != null) {
            user.setName(updatedUser.getName());
            user.setSurname(updatedUser.getSurname());
            user.setEmail(updatedUser.getEmail());
            dao.updateUser(user);
            logger.info("Пользователь с ID: {} обновлен успешно", id);
        } else {
            logger.warn("Пользователь с ID: {} не найден.", id);
        }
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        logger.debug("Удаление пользователя с ID: {}", id);
        dao.deleteUser(id);
        logger.info("Пользователь с ID: {} удален успешно", id);
    }
}