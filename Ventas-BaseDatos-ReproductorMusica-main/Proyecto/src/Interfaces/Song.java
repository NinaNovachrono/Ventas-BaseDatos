/*
    Sánchez Plazola José Abraham
    02/08/2021
    Clase Song utilizada en el reproductor de musica
*/
package Interfaces;

import java.net.URL;

public class Song {

    private String name;
    private URL url;

    public Song(String name, URL url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
