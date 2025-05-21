package fr.ut1.m2ipm.dafumarket.controllers;
import fr.ut1.m2ipm.dafumarket.dto.RayonDTO;
import fr.ut1.m2ipm.dafumarket.services.RayonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Controlleur principal permettant de récupérer des informations liées aux rayons
 */
@RestController
@RequestMapping("/api/rayons")
public class RayonController {

    private final RayonService rayonService;

    public RayonController(RayonService rayonService) {
        this.rayonService = rayonService;
    }

    /**
     * Recupere et renvoie la liste des rayons disponibles en drive ainsi que leurs découpages en catégories
     * @return List<RayonDTO>
     */
    @GetMapping
    public List<RayonDTO> getTousLesRayons() {
        return this.rayonService.getAllRayons();
    }


    /**
     * Recupere et renvoie le rayon correspondant à l'id fourni s'il existe
     * @return RayonDTO
     */
    @GetMapping("/{idRayon}")
    public RayonDTO getTousLesRayons(@PathVariable int idRayon) {
        return this.rayonService.getRayonById(idRayon);
    }

}