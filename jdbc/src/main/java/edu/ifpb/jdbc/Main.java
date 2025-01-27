package edu.ifpb.jdbc;

public class Main {
    public static void main(String[] args) {
        Repository repository = new Repository();

        User newUser = new User("Maria Silva", "maria.silva@email.com");
        repository.save(newUser);

        User foundUser = repository.getByName("Maria Silva");
        if (foundUser != null) {
            System.out.println("ID: " + foundUser.getId());
            System.out.println("Name: " + foundUser.getName());
            System.out.println("Email: " + foundUser.getEmail());
        }

        if (foundUser != null) {
            foundUser.setName("Maria Clara Silva");
            foundUser.setEmail("maria.clara@email.com");
            repository.update(foundUser.getId(), foundUser);
        }

        if (foundUser != null) {
            repository.delete(foundUser.getId());
        }
    }
}
