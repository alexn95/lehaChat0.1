package app.controllers;

import app.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("home")
public class HomeController {

    @Autowired
    ServiceImpl service;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getRolesGet()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", "");
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView postMessenger(@ModelAttribute String error, String password, String password_confirm) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", "");
        if (password.isEmpty() || password_confirm.isEmpty()){
            modelAndView.addObject("error", "Fill in all the fields");
            modelAndView.setViewName("/home");
            return modelAndView;
        }

        if (!password.equals(password_confirm)){
            modelAndView.addObject("error", "Passwords do not match");
            modelAndView.setViewName("/home");
            return modelAndView;
        }
        service.changePassword(password);
        modelAndView.addObject("error", "Password was changed");
        modelAndView.setViewName("home");
        return modelAndView;
    }

}
