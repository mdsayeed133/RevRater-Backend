package com.revature.services;

import com.revature.models.Employee;
import com.revature.models.User;
import com.revature.repositories.EmployeeRepository;
import com.revature.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    private EmployeeRepository employeeRepository;


    @Autowired
    public UserService(UserRepository userRepository, EmployeeRepository employeeRepository) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
    }

    public Optional<User> findByCredentials(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
    public User save(User user) {
        return userRepository.save(user);
    }


    public List<Employee> getAllFollowing(int userId){
        User user = userRepository.findById(userId).orElse(null);
        if(user != null) return user.getFollowedEmployees();
        return null;
    }

    public Optional<User> getUserById(int id){
        return userRepository.findById(id);
    }
    public boolean updatePassword(int userId, String newPassword){
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){return false;}
        user.setPassword(newPassword);
        userRepository.save(user);
        return true;
    }

    public List<User> getUserByName(String search){
        Set<User> allResults= new HashSet<>();
        List<User> firstResults = userRepository.findByFirstNameContainingIgnoreCase(search).orElse(null);
        List<User> lastResults = userRepository.findByLastNameContainingIgnoreCase(search).orElse(null);
        if (firstResults!=null)allResults.addAll(firstResults);
        if(lastResults!=null)allResults.addAll(lastResults);
        return new ArrayList<>(allResults);
    }


    public boolean unFollow(int userId, int employeeId){
        User user = userRepository.findById(userId).orElse(null);
        Employee emp = employeeRepository.findById(employeeId).orElse(null);
        if(user==null||emp==null) return false;
        ArrayList<Employee> list = new ArrayList<>(user.getFollowedEmployees());
        if(list.contains(emp)) {
            list.remove(emp);
            user.setFollowedEmployees(list);
            userRepository.save(user);
            return true;
        }return false;
    }

    public boolean follow(int userId, int employeeId){
        User user = userRepository.findById(userId).orElse(null);
        Employee emp = employeeRepository.findById(employeeId).orElse(null);
        if(user==null||emp==null) return false;
        ArrayList<Employee> list = new ArrayList<>(user.getFollowedEmployees());
        if(!list.contains(emp)) {
            list.add(emp);
            user.setFollowedEmployees(list);
            userRepository.save(user);
            return true;
        }return false;
    }

}
