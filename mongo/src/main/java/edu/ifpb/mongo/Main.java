package edu.ifpb.mongo;

public class Main {
    public static void main(String[] args) {
        PostService postService = new PostService();
        postService.savePost("lucasramallo", "Hoje levei uma queda no centro da cidade, quase quebrei o celular! KKK");
        postService.savePost("lucasramallo", "Terminei o projeto de React Native, estou muito feliz com o resultado!");
        postService.savePost("lucasramallo", "Alguém sabe onde posso comprar um bom fone de ouvido por menos de 200 reais?");
        postService.savePost("lucasramallo", "Estou tentando aprender mais sobre MongoDB. Alguma dica de bons tutoriais?");
        postService.savePost("lucasramallo", "Acabei de assistir o último episódio de The Mandalorian. Que final incrível!");

        postService.deletePostById("67951be77477b3710b59a4ae");

        postService.editPostById("67951d57df9e8a5ef4a4ebd2", "Estou na chuva");

        postService.getPostsByUsername("lucasramallo");
    }
}