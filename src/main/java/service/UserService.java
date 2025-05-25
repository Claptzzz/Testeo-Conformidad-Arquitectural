package service;

import dao.UserRepository;

public class UserService {
    private final UserRepository repository = new UserRepository();

    public void registerUser(String name) {
        repository.save(name);
    }

    public void getUserInfo(String name) {
        System.out.println("User info for: " + name);
    }
}