package ru.sergeich.diploma.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sergeich.diploma.domain.Bouquet;
import ru.sergeich.diploma.services.BouquetService;

import java.util.List;

@Controller
public class ShopController {
    private final BouquetService bouquetService;

    public ShopController(BouquetService bouquetService) {
        this.bouquetService = bouquetService;
    }

    @GetMapping("/shop")
    public String getShop(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }

        List<Bouquet> bouquets = bouquetService.getAllBouquets();
        model.addAttribute("bouquets", bouquets);
        return "shop";
    }
}
