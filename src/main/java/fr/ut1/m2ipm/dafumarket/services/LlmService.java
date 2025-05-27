package fr.ut1.m2ipm.dafumarket.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ut1.m2ipm.dafumarket.dao.ClientDAO;
import fr.ut1.m2ipm.dafumarket.dto.ProduitDTO;
import fr.ut1.m2ipm.dafumarket.models.Liste;
import fr.ut1.m2ipm.dafumarket.models.PostIt;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LlmService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String agentId = "ag:359ab99b:20250522:untitled-agent:0d2e13d1";
    private final String apiUrl = "https://api.mistral.ai/v1/agents/completions";
    private final String apiKey = "W4gjIutIAxTtxm3VbjMwDhuZrsgkvs9H";
    private final ClientDAO clientDAO;

    public LlmService(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public Map<String, Object> traiterRecetteAvecLLM(String phrase, List<ProduitDTO> tousLesProduits, Liste listeCourses, PostIt postIt) {
        Map<String, Object> etape1 = envoyerPromptEtExtraireIngredients(phrase);

        String message1 = etape1.get("reponseUtilisateur").toString();

        List<String> ingredients = (List<String>) etape1.get("ingredients");
        if (ingredients == null || ingredients.isEmpty()) {
            Map<String, Object> etape2 = Map.of(
                    "reponseUtilisateur", "Aucun ingrédient détecté dans la demande.",
                    "produitsSelectionnes", List.of(),
                    "promptEnvoye", "",
                    "reponseBrute", ""
            );
            return Map.of("etape1", etape1, "etape2", etape2);
        }

        Map<String, List<ProduitDTO>> propositions = trouverProduitsSimilaires(tousLesProduits, ingredients);

        List<Map<String, Object>> propositionJson = propositions.entrySet().stream()
                .filter(e -> !e.getValue().isEmpty())
                .map(e -> Map.of(
                        "ingredient", e.getKey(),
                        "produitsProposes", e.getValue().stream()
                                .map(p -> Map.of("idProduit", p.getIdProduit(), "nom", p.getNom()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Map<String, Object> etape2 = envoyerProduitsPourValidation(Map.of("propositions", propositionJson), listeCourses, postIt);

        String message2 = etape2.get("reponseUtilisateur").toString();
        String reponseBrute = etape2.get("reponseBrute").toString();

        // Mettre à jour le postit
        this.clientDAO.updatePostItLLM(postIt, message1 + "\n" + message2);

        return Map.of("etape1", etape1, "etape2", etape2);
    }

    public Map<String, Object> envoyerPromptEtExtraireIngredients(String prompt) {
        try {
            Map<String, Object> messageMap = Map.of("role", "user", "content", prompt);
            Map<String, Object> body = Map.of("agent_id", agentId, "messages", List.of(messageMap));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(body), headers);
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);
            return extraireReponseEtIngredients(response.getBody(), prompt);
        } catch (Exception e) {
            System.out.println("Erreur appel Mistral : " + e.getMessage());
            return Map.of(
                    "reponseUtilisateur", "Erreur Mistral",
                    "ingredients", Collections.emptyList(),
                    "promptEnvoye", prompt,
                    "reponseBrute", ""
            );
        }
    }

    public Map<String, Object> envoyerProduitsPourValidation(Map<String, Object> jsonEnvoye, Liste listeCourses, PostIt postIt) {
        try {
            String contentJson = mapper.writeValueAsString(jsonEnvoye);
            Map<String, Object> message = Map.of("role", "user", "content", contentJson);
            Map<String, Object> body = Map.of("agent_id", agentId, "messages", List.of(message));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(body), headers);
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);

            return extraireProduitsSelectionnesDepuisMistral(response.getBody(), contentJson, listeCourses, postIt);
        } catch (Exception e) {
            System.out.println("Erreur envoi validation Mistral : " + e.getMessage());
            return Map.of(
                    "reponseUtilisateur", "Erreur Mistral",
                    "produitsSelectionnes", Collections.emptyList(),
                    "promptEnvoye", jsonEnvoye,
                    "reponseBrute", ""
            );
        }
    }

    private Map<String, Object> extraireReponseEtIngredients(String jsonReponseMistral, String promptEnvoye) throws Exception {
        JsonNode racine = mapper.readTree(jsonReponseMistral);
        String contentJsonString = racine.path("choices").get(0).path("message").path("content").asText();
        JsonNode contentJson = mapper.readTree(contentJsonString);

        String reponseUtilisateur = contentJson.path("reponseUtilisateur").asText();
        List<String> ingredients = new ArrayList<>();
        for (JsonNode node : contentJson.path("ingredients")) {
            ingredients.add(node.asText());

        }

        return Map.of(
                "reponseUtilisateur", reponseUtilisateur,
                "ingredients", ingredients,
                "promptEnvoye", promptEnvoye,
                "reponseBrute", contentJsonString
        );
    }

    private Map<String, Object> extraireProduitsSelectionnesDepuisMistral(String jsonReponseMistral, String promptEnvoye, Liste listeCourses, PostIt postit) throws Exception {

        JsonNode racine = mapper.readTree(jsonReponseMistral);
        String contentJsonString = racine.path("choices").get(0).path("message").path("content").asText();
        JsonNode contentJson = mapper.readTree(contentJsonString);

        String reponseUtilisateur = contentJson.path("reponseUtilisateur").asText();
        List<Map<String, Integer>> produits = new ArrayList<>();
        for (JsonNode p : contentJson.path("produitsSelectionnes")) {
            // Ici: ajouter les produits à la liste de courses
            this.clientDAO.ajouterOuMettreAJourElementListeLLM(listeCourses, p.path("idProduit").asInt(), p.path("quantite").asInt());


            produits.add(Map.of(
                    "idProduit", p.path("idProduit").asInt(),
                    "quantite", p.path("quantite").asInt()
            ));


        }

        return Map.of(
                "reponseUtilisateur", reponseUtilisateur,
                "produitsSelectionnes", produits,
                "promptEnvoye", promptEnvoye,
                "reponseBrute", contentJsonString
        );
    }


    public void ajouterProduitListe(int idProduit, int idListe) {

    }


    public Map<String, List<ProduitDTO>> trouverProduitsSimilaires(List<ProduitDTO> produits, List<String> ingredients) {
        Map<String, List<ProduitDTO>> resultats = new LinkedHashMap<>();
        for (String ingredient : ingredients) {
            String ingredientNormalise = normaliserTexte(ingredient);
            List<ProduitDTO> correspondants = produits.stream()
                    .filter(p -> estProduitSimilaire(p, ingredientNormalise))
                    .limit(10)
                    .collect(Collectors.toList());
            resultats.put(ingredient, correspondants);
        }
        return resultats;
    }

    private boolean estProduitSimilaire(ProduitDTO produit, String ingredient) {
        String nomProduit = normaliserTexte(produit.getNom());

        String[] motsIngredient = ingredient.split(" ");
        String[] motsProduit = nomProduit.split(" ");

        int seuilSimilarite = 70;
        int correspondances = 0;

        for (String motIng : motsIngredient) {
            for (String motProd : motsProduit) {
                if (calculerSimilarite(motIng, motProd) >= seuilSimilarite) {
                    correspondances++;
                    break;
                }
            }
        }

        return correspondances > 0;
    }

    private int calculerSimilarite(String mot1, String mot2) {
        LevenshteinDistance distance = LevenshteinDistance.getDefaultInstance();
        int maxLen = Math.max(mot1.length(), mot2.length());
        if (maxLen == 0) return 100;
        int dist = distance.apply(mot1, mot2);
        return (int) ((1.0 - (double) dist / maxLen) * 100);
    }

    private String normaliserTexte(String texte) {
        return texte == null ? "" : texte.toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")
                .replaceAll("\\s+", " ")
                .trim();
    }
}
