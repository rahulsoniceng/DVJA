package com.appsecco.dvja.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class RedirectAction extends BaseController {

    // Allowlist of permitted redirect paths/URLs (relative paths or trusted absolute URLs)
    private static final List<String> ALLOWED_URLS = Arrays.asList(
        "/home",
        "/login",
        "/dashboard",
        "/profile"
    );

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String execute() {
        String target = getUrl();
        if (!StringUtils.isEmpty(target) && isAllowed(target)) {
            return "redirect";
        }
        return renderText("Missing or invalid url");
    }

    private boolean isAllowed(String target) {
        // Only allow relative paths (no scheme/host) or explicitly allowlisted values
        try {
            URI uri = new URI(target);
            // Reject any URL that specifies a scheme or host (absolute URLs)
            if (uri.getScheme() != null || uri.getHost() != null) {
                return false;
            }
        } catch (URISyntaxException e) {
            return false;
        }
        // Additionally restrict to known allowlisted relative paths
        return ALLOWED_URLS.contains(target);
    }

}
