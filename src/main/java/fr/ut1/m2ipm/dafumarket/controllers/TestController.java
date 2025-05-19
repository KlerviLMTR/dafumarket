package fr.ut1.m2ipm.dafumarket.controllers;

import fr.ut1.m2ipm.dafumarket.dto.PropositionProduitDTO;
import fr.ut1.m2ipm.dafumarket.services.PropositionProduitService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public String testEndpoint() {
        return "Dafu market is working!!! toto ";
    }




}