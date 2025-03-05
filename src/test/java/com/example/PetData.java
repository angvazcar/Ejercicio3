package com.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Cuenta cuántas veces aparece cada nombre de mascota y obtiene
 * la lista de mascotas vendidas desde la API en formato {id, name}.
 */
public class PetData {

    private static final String BASE_URL = "https://petstore.swagger.io/v2";

    public static Map<String, Integer> countPetNames(List<Map<String, Object>> pets) {
        Map<String, Integer> petNamesCount = new HashMap<>();
        if (pets == null || pets.isEmpty()) {
            throw new IllegalArgumentException("La lista de mascotas no puede estar vacía o ser nula.");
        }
        for (Map<String, Object> pet : pets) {
            String name = (String) pet.get("name");
            if (name != null) {
                petNamesCount.put(name, petNamesCount.getOrDefault(name, 0) + 1);
            }
        }
        return petNamesCount;
    }

    /**
     * Obtiene la lista de mascotas vendidas desde la API en un formato estructurado.
     * @return Lista de mapas con claves "id" y "name".
     */
    public List<Map<String, Object>> getSoldPets() {
        List<Map<String, Object>> soldPets = new ArrayList<>();
        try {
            HttpResponse<kong.unirest.JsonNode> response = Unirest.get(BASE_URL + "/pet/findByStatus")
                    .queryString("status", "sold")
                    .header("Accept", "application/json")
                    .asJson();

            if (response.getStatus() != 200) {
                throw new RuntimeException("Error al obtener mascotas vendidas: Código " + response.getStatus());
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody().toString());

            for (JsonNode petNode : rootNode) {
                if (petNode.has("id") && petNode.has("name")) {
                    Map<String, Object> petMap = new HashMap<>();
                    petMap.put("id", petNode.get("id").asInt()); // Convertir id a int
                    petMap.put("name", petNode.get("name").asText());
                    soldPets.add(petMap);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar la respuesta de la API", e);
        }
        return soldPets;
    }
}
