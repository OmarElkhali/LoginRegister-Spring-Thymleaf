package com.bmt.Project.controller;

import com.bmt.Project.model.User;
import com.bmt.Project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;


@Controller
public class AuthController {
    @Autowired
    private UserRepository userRepository;


    private static final String REDIRECT_REGISTER = "redirect:/register";
    private static final String REDIRECT_LOGIN = "redirect:/login";

    @GetMapping("/")
    public String index() {
        return "index"; // Correspond à index.html
    }

    @GetMapping("/register")
    public String showRegisterPage(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("user", new User()); // Ajout d'un objet vide pour la liaison du formulaire
        model.addAttribute("error", error);
        return "register"; // Correspond à register.html
    }

    @PostMapping("/register")
    public String registerUser(@Validated @ModelAttribute("user") User user, BindingResult result, Model model) {
        // Si des erreurs de validation existent, on renvoie vers la page d'inscription
        if (result.hasErrors()) {
            return "register";
        }

        // Vérification si l'utilisateur existe déjà
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return REDIRECT_REGISTER + "?error=exists"; // Redirection si l'email existe déjà
        }

        // Sauvegarde de l'utilisateur
        userRepository.save(user);

        return REDIRECT_LOGIN + "?success=registered"; // Redirection vers la page de login après inscription
    }

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(required = false) String error, @RequestParam(required = false) String success, Model model) {
        model.addAttribute("error", error);
        model.addAttribute("success", success);
        return "login"; // Correspond à login.html
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password, Model model) {
        // Recherche de l'utilisateur par email
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            model.addAttribute("name", user.get().getName());
            return "users"; // Correspond à users.html
        }
        return REDIRECT_LOGIN + "?error=invalid"; // Redirection en cas de connexion invalide
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userRepository.findAll();  // Récupère tous les utilisateurs
        System.out.println("Users found: " + users.size());  // Affiche le nombre d'utilisateurs dans la console
        model.addAttribute("users", users);  // Ajoute la liste des utilisateurs au modèle
        return "users";  // Retourne le nom de la vue Thymeleaf (users.html)
    }

    // Gestion des exceptions générales
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("errorMessage", "Une erreur inattendue s'est produite : " + e.getMessage());
        return "error"; // Correspond à error.html
    }
}
