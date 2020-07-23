package br.edu.ifrs.canoas.webapp.helper;


import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Optional;

public final class URIHelper {

    public static String buildPath(URI uri) {
        String query = uri.getRawQuery();

        if(query == null) {
            return uri.getPath();
        }

        return uri.getPath() + '?' + query;
    }

    /**
     * Get referrer path from header
     * @param request The request object
     * @param defaultUri The path in case the referrer header is not available.
     *                   This should always be passed
     * @return The path to a route on our application
     */
    public static String getReferrerURI(HttpServletRequest request, String defaultUri) {
        String uriString = Optional.ofNullable(request.getHeader("Referer"))
                .orElse(defaultUri);

        URI uri = URI.create(uriString);

        return buildPath(uri);
    }


}

