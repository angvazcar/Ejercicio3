package com.example;

/**Lista las mascotas con id y nombre. Devuelve {id,nombre}
 **/

public class Pet {
    private final int id;
    private final String name;

    public Pet(int id, String name) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID de la mascota debe ser un número positivo.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la mascota no puede estar vacío o ser nulo.");
        }
        this.id = id;
        this.name = name;
    }

    public int getId() {return id;}

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "{id: " + id + ", name: \"" + name + "\"}";
    }

}