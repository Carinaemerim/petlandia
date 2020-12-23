package br.edu.ifrs.canoas.webapp.helper;

import br.edu.ifrs.canoas.webapp.domain.*;

import java.lang.reflect.InvocationTargetException;

public class ScoreHelper {
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
