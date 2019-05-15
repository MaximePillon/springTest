package springproject.project.service;

import springproject.project.model.Image;
import springproject.project.model.Role;
import springproject.project.model.User;
import springproject.project.repository.UserRepository;
import springproject.project.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service("userService")
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        return userRepository.findById(id);
    }

    public List<User> findByPartialEmail(String email) {
        return userRepository.findAllByEmailContaining(email);
    }

    public void validate(User user) { userRepository.saveAndFlush(user); }

    public void saveUser(User user) {
        Logger logger = Logger.getInstance();

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);

        logger.logCreate("user with username " + user.getUsername());
    }

    public void deleteImage(User user, Image image) {
        user.getImages().remove(image);

        userRepository.saveAndFlush(user);
    }

    public void updateUser(User user, User newDetails) {
        user.setEmail(newDetails.getEmail());
        user.setFirstName(newDetails.getFirstName());
        user.setLastName(newDetails.getLastName());
        user.setCity(newDetails.getCity());

        userRepository.saveAndFlush(user);
    }

    public List<String> findEmailWithPackage(String name) {
        List<User> users = userRepository.findByBundle(name);
        List<String> emails = new ArrayList<>();

        for (User user : users) { emails.add(user.getEmail()); }
        return emails;
    }

    public String analysis() {
        String output = "Begining of the User analysis:\n";

        output += "- " + userRepository.count() + " user have an account\n";
        output += "- " + userRepository.findByActive(1).size() + " are active";

        return output;
    }
}