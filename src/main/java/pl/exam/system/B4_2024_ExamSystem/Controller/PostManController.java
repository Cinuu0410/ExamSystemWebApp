package pl.exam.system.B4_2024_ExamSystem.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.exam.system.B4_2024_ExamSystem.Model.User;
import pl.exam.system.B4_2024_ExamSystem.Service.UserService;

import java.util.List;

@RestController
public class PostManController {

    @Autowired
    private UserService service;

    @GetMapping("/userslist")
    public List<User> getAllCustomers(){
        return service.getAllUsers();
    }

    @PostMapping("/update")
    public void updateUser(@RequestBody User user){
        service.saveUser(user);
    }

    @PostMapping("/delete")
    public void deleteUser(@RequestBody Long id){
        service.deleteUser(id);
    }

}
