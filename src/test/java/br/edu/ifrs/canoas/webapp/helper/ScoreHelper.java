package br.edu.ifrs.canoas.webapp.helper;

import br.edu.ifrs.canoas.webapp.domain.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreHelper {
    public static Map<String, List<? extends Score>> createScoresMap() {
        List<AnimalType> animalTypes = ScoreHelper.createScoreAsList("DOG", 2.0, AnimalType.class);
        List<AnimalCastrated> animalCastrateds = ScoreHelper.createScoreAsList("YES", 2.0, AnimalCastrated.class);
        List<AnimalGender> animalGenders = ScoreHelper.createScoreAsList("FEMALE", 2.0, AnimalGender.class);
        List<AnimalAge> animalAges = ScoreHelper.createScoreAsList("PUPPY", 2.0, AnimalAge.class);
        List<AnimalColor> animalColors = ScoreHelper.createScoreAsList("WHITE", 2.0, AnimalColor.class);
        List<AnimalSize> animalSizes = ScoreHelper.createScoreAsList("MEDIUM", 2.0, AnimalSize.class);

        Map<String, List<? extends Score>> scores = new HashMap<>();

        scores.put("animalTypes", animalTypes);
        scores.put("animalCastrateds", animalCastrateds);
        scores.put("animalGenders", animalGenders);
        scores.put("animalAges", animalAges);
        scores.put("animalColors", animalColors);
        scores.put("animalSizes", animalSizes);

        return scores;
    }

    public static <T extends Score> List<T> createScoreAsList(String name, double weight, Class<T> scoreType) {
        return Collections.singletonList(createScore(name, weight, scoreType));
    }

    public static <T extends Score> T createScore(String name, double weight, Class<T> scoreType) {
        try {
            T score = scoreType.getConstructor().newInstance();
            score.setLabel(name);
            score.setName(name);
            score.setWeight(weight);

            return score;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
