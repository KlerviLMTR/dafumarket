package fr.ut1.m2ipm.dafumarket.services;


import fr.ut1.m2ipm.dafumarket.dto.LoginUserDTO;
import fr.ut1.m2ipm.dafumarket.dto.RegisterUserDTO;
import fr.ut1.m2ipm.dafumarket.models.Client;
import fr.ut1.m2ipm.dafumarket.models.Compte;
import fr.ut1.m2ipm.dafumarket.repositories.ClientRepository;
import fr.ut1.m2ipm.dafumarket.repositories.CompteRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final CompteRepository compteRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final ClientRepository clientRepository;

    public AuthenticationService(
            CompteRepository compteRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            ClientRepository clientRepository) {
        this.authenticationManager = authenticationManager;
        this.compteRepository = compteRepository;
        this.passwordEncoder = passwordEncoder;
        this.clientRepository = clientRepository;
    }

    public Client signup(RegisterUserDTO input) {
        Compte compte = new Compte();
        compte.setLogin(input.getEmail());
        compte.setPassword(passwordEncoder.encode(input.getPassword()));

        Compte c = compteRepository.save(compte);

        Client client = new Client();
        client.setNom(input.getNom());
        client.setPrenom(input.getPrenom());
        client.setCp(input.getCp());
        client.setAdresse(input.getAdresse());
        client.setVille(input.getVille());
        client.setNumero(input.getTelephone());

        client.setCompte(c);

        return clientRepository.save(client);
    }

    public Compte authenticateClient(LoginUserDTO input) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        Optional<Compte> authCompte = compteRepository.findById(input.getEmail());
        if (authCompte.isPresent()) {
            return authCompte.get();
        }
        throw new Exception("Not authorized");

    }
}
