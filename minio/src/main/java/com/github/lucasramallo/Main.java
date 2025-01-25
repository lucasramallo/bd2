package com.github.lucasramallo;

public class Main {
    public static void main(String[] args) {
        ObjectStorageService objectStorageService = new ObjectStorageService();
//        objectStorageService.upload("bd2", "file.pdf", "src/main/resources/teste.pdf");
        objectStorageService.dowloadObject("bd2", "file.pdf", "src/main/resources/downloads/");
    }
}