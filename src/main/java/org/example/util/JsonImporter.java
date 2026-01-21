package org.example.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Adestrador;
import org.example.model.Pokemon;
import org.example.repository.AdestradorRepository;
import org.example.repository.PokemonRepository;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class JsonImporter {

    private final PokemonRepository pokemonRepository;
    private final AdestradorRepository adestradorRepository;
    private final ObjectMapper objectMapper;

    public JsonImporter(PokemonRepository pokemonRepository,
                        AdestradorRepository adestradorRepository) {
        this.pokemonRepository = pokemonRepository;
        this.adestradorRepository = adestradorRepository;
        this.objectMapper = new ObjectMapper();
    }

    // Método para importar Adestradores desde JSON
    public void importarAdestradoresDesdeJson(String filePath) throws IOException {
        File file = new File(filePath);
        List<Adestrador> adestradores = objectMapper.readValue(
                file,
                new TypeReference<List<Adestrador>>() {}
        );

        adestradorRepository.saveAll(adestradores);
        System.out.println("Adestradores importados: " + adestradores.size());
    }

    // Método para importar Pokémons desde JSON
    public void importarPokemonsDesdeJson(String filePath) throws IOException {
        File file = new File(filePath);
        List<Pokemon> pokemons = objectMapper.readValue(
                file,
                new TypeReference<List<Pokemon>>() {}
        );

        pokemonRepository.saveAll(pokemons);
        System.out.println("Pokémons importados: " + pokemons.size());
    }

    // Método para inserir datos directamente
    public void insertarDatosDirectamente() {
        // Crear adestradores
        Adestrador ash = new Adestrador();
        ash.setNome("Ash Ketchum");
        ash.setIdade(10);
        ash.setCidade("Pallet Town");
        adestradorRepository.save(ash);

        Adestrador misty = new Adestrador();
        misty.setNome("Misty");
        misty.setIdade(12);
        misty.setCidade("Ciudad Celeste");
        adestradorRepository.save(misty);

        // Crear pokémons para Ash
        Pokemon pikachu = new Pokemon();
        pikachu.setNome("Pikachu");
        pikachu.setTipo(List.of("Eléctrico"));
        pikachu.setNivel(25);
        pikachu.setHabilidades(List.of("Impactrueno", "Rayo", "Velocidad Extrema"));
        pikachu.setIdAdestrador(ash.getId());
        pokemonRepository.save(pikachu);

        Pokemon charizard = new Pokemon();
        charizard.setNome("Charizard");
        charizard.setTipo(List.of("Fuego", "Volador"));
        charizard.setNivel(36);
        charizard.setHabilidades(List.of("Lanzallamas", "Vuelo", "Garra Dragón"));
        charizard.setIdAdestrador(ash.getId());
        pokemonRepository.save(charizard);

        // Crear pokémons para Misty
        Pokemon starmie = new Pokemon();
        starmie.setNome("Starmie");
        starmie.setTipo(List.of("Agua", "Psíquico"));
        starmie.setNivel(28);
        starmie.setHabilidades(List.of("Hidrobomba", "Psíquico", "Rayo Burbuja"));
        starmie.setIdAdestrador(misty.getId());
        pokemonRepository.save(starmie);

        System.out.println("Datos insertados directamente");
    }
}