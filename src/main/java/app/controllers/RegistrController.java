package app.controllers;

import app.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import app.service.impl.ServiceImpl;

@RestController
@RequestMapping("registr")
public class RegistrController {


    @Autowired
    ServiceImpl userService;

    @Autowired
    BCryptPasswordEncoder encoder;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView addUser(@ModelAttribute String error, ModelAndView modelAndView, String email,
                                String username, String password, String password_confirm) {
        modelAndView.addObject("username",username);
        modelAndView.addObject("email",email);

        if (userService.findUserByEmail(email)!= null) {
            modelAndView.addObject("error", "This email is already in use");
            modelAndView.setViewName("/registr");
            return modelAndView;
        }

        if (userService.findUserByName(username)!= null) {
            modelAndView.addObject("error", "This login already exists");
            modelAndView.setViewName("/registr");
            return modelAndView;
        }
        if (username.isEmpty() || password.isEmpty() || password_confirm.isEmpty()){
            modelAndView.addObject("error", "Fill in all the fields");
            modelAndView.setViewName("/registr");
            return modelAndView;
        }

        if (!password.equals(password_confirm)){
            modelAndView.addObject("error", "Passwords do not match");
            modelAndView.setViewName("/registr");
            return modelAndView;
        }


        userService.addBaseUser(new User(email, username, encoder.encode(password)));
        modelAndView.setViewName("/login");
        return  modelAndView;
    }

    @RequestMapping( method = RequestMethod.GET)
    public ModelAndView getUserForm(ModelAndView modelAndView)
    {
        modelAndView.addObject("error", "");
        modelAndView.setViewName("/registr");
        return  modelAndView;
    }
}
