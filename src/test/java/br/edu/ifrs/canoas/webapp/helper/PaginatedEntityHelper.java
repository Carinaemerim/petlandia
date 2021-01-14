package br.edu.ifrs.canoas.webapp.helper;

import br.edu.ifrs.canoas.webapp.domain.PaginatedEntity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class PaginatedEntityHelper {
    /**
     * Cria paginated entity para um objeto dinamico.
     *
     * //TODO: Caso necessário fazer seedMethod retornar um array de entity
     * //TODO: ao invés de chamar ele pageLength vezes. Útil caso se deseje dados
     * //TODO: mais randomicos no set
     *
     * @param entity O objeto class da entidade que se deseja criar a lista
     * @param seedMethod O método que retorna a entidade. Chamado pageLength vezes
     * @param seedParameters Parametros para seedMethod
     * @param size O tamanho da lista
     * @param pageLength O comprimento de página da lista
     * @param <T> O Tipo da entidade que se deseja criar a lista
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static <T> PaginatedEntity<T> createPaginatedEntity(
            Class<T> entity,
            Method seedMethod,
            Object[] seedParameters,
            int size,
            int pageLength
    ) throws InvocationTargetException, IllegalAccessException {
        ArrayList<T> dataList = new ArrayList<>();

        if (! seedMethod.getReturnType().equals(entity)) {
            String message = String.format(
                    "Trying to create list of %s, but seedMethod function returns %s",
                    entity.toString(),
                    seedMethod.getReturnType().toString()
            );
            throw new IllegalArgumentException(message);
        }

        for (int i = 0; i < size; i++) {
            T element = (T) seedMethod.invoke(null, seedParameters);
            dataList.add(element);
        }

        return PaginatedEntity.<T>builder()
                .data(dataList)
                .currentPage(1)
                .totalResults(size)
                .pageLength(pageLength)
                .build();
    }
}
