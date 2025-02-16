package org.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

public class Main {
    public static void main(String[] args) {
        String geoserverUrl = "http://localhost:8080/geoserver/joaolucas/wms";

        String params = "?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetMap"
                + "&FORMAT=image/jpeg"
                + "&TRANSPARENT=true"
                + "&STYLES="
                + "&LAYERS=joaolucas:PB_Municipios_2023"
                + "&EXCEPTIONS=application/vnd.ogc.se_inimage"
                + "&SRS=EPSG:4674"
                + "&WIDTH=768&HEIGHT=440"
                + "&BBOX=-36.021766662597656,-7.0827484130859375,-35.758094787597656,-6.9316864013671875";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(geoserverUrl + params);

            try (CloseableHttpResponse response = httpClient.execute(request);
                 InputStream inputStream = response.getEntity().getContent();
                 FileOutputStream fileOutputStream = new FileOutputStream(new File("esperanca_mapa.jpg"))) {

                if (response.getCode() == 200) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, bytesRead);
                    }
                    System.out.println("Imagem salva como 'esperanca_mapa.jpg'.");
                } else {
                    System.out.println("Erro ao obter imagem: " + response.getCode());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
