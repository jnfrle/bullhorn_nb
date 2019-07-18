package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    MessageRepository messageRepository;

    @RequestMapping("/")
    public String showMessages(Model model){
        model.addAttribute("messages", messageRepository.findAll());
        return "home";
    }

    @GetMapping("/add")
    public String newMessage(Model model){
        Bullhorn bull = new Bullhorn();
        model.addAttribute("bullhorn", bull);
        return "messageForm";
    }

    @PostMapping("/process")
    public String processMessage(@Valid Bullhorn bullhorn, Model model, BindingResult result){
        if(result.hasErrors()){
            return "messageForm";
        }
        model.addAttribute("bullhorn", bullhorn);
        messageRepository.save(bullhorn);
        return "redirect:/";
    }

    @RequestMapping("/update/{id}")
    public String updateMessage(@PathVariable("id") long id, Model model){
        model.addAttribute("bullhorn", messageRepository.findById(id).get());
        return "messageForm";
    }

    @RequestMapping("/detail/{id}")
    public String viewMessage(@PathVariable("id") long id, Model model){
        model.addAttribute("message", messageRepository.findById(id).get());
        return "viewMessage";
    }

    @RequestMapping("/delete/{id}")
    public String deleteMessage(@PathVariable("id") long id){
        messageRepository.deleteById(id);
        return "redirect:/";
    }

}
