package com.liutong.website.entities.experiences;

import java.net.URL;

public class Company {
    private String name;
    private URL iconLink;
    private URL infoLink;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URL getIconLink() {
        return iconLink;
    }

    public void setIconLink(URL iconLink) {
        this.iconLink = iconLink;
    }

    public URL getInfoLink() {
        return infoLink;
    }

    public void setInfoLink(URL infoLink) {
        this.infoLink = infoLink;
    }
}
