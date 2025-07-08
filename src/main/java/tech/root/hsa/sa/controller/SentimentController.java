package tech.root.hsa.sa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tech.root.hsa.sa.entities.Sentiment;
import tech.root.hsa.sa.enums.TypeSentiment;
import tech.root.hsa.sa.service.SentimentService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "sentiment", produces = APPLICATION_JSON_VALUE)
public class SentimentController {

    private SentimentService sentimentService;

    public SentimentController(SentimentService sentimentService) {
        this.sentimentService = sentimentService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createSentiment(@RequestBody Sentiment sentiment) {
        if (
                sentiment.getTexte().contains("pas") || sentiment.getTexte().contains("mauvais")
        ){
            sentiment.setType(TypeSentiment.NEGATIF);
        }else{
            sentiment.setType(TypeSentiment.POSITIF);
        }
        this.sentimentService.saveSentiment(sentiment);

    }

    @GetMapping
    public @ResponseBody List<Sentiment> search(
            @RequestParam(required = false) Integer typeValue,
            @RequestParam(required = false, defaultValue = "-1") Integer clientId
    ) {
        TypeSentiment type = null;

        // Logique de conversion de l'entier (0 ou 1) en TypeSentiment
        if (typeValue != null) {
            if (typeValue == 0) {
                type = TypeSentiment.POSITIF;
            } else if (typeValue == 1) {
                type = TypeSentiment.NEGATIF;
            } else {
                throw new IllegalArgumentException("La valeur du paramètre 'type' doit être 0 pour POSITIF ou 1 pour NEGATIF.");
            }
        }

        return this.sentimentService.search(type, clientId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "{id}")
    public void deleteSentiment(@PathVariable int id) {
        this.sentimentService.deleteSentimentById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
    public void updateSentiment(@PathVariable int id, @RequestBody Sentiment sentiment) {
        sentimentService.updateSentiment(id, sentiment);
    }


}
