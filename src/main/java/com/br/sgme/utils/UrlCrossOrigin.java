package com.br.sgme.utils;

import org.springframework.beans.factory.annotation.Value;

public class UrlCrossOrigin {

    @Value("${cross.origin.url}")
    private String urlCrossOrigin;

    public String getUrlCrossOrigin() {
        return urlCrossOrigin;
    }

}
