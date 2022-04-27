package domain;

public class Entity {
    // este o clasa abstracta => pentru ca avem id-ul comun
    protected  String id;

    public Entity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id='" + id + '\'' +
                '}';
    }
}
