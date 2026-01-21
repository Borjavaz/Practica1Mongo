package org.example.service;

import org.example.model.Pokemon;
import org.example.model.Adestrador;
import org.example.repository.PokemonRepository;
import org.example.repository.AdestradorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class JsonImporter {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private AdestradorRepository adestradorRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void importarPokemonsDesdeJson(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("El archivo no existe en la ruta: " + filePath);
        }

        Pokemon[] pokemons = objectMapper.readValue(file, Pokemon[].class);
        pokemonRepository.saveAll(Arrays.asList(pokemons));
    }

    public void importarAdestradoresDesdeJson(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("El archivo no existe en la ruta: " + filePath);
        }

        Adestrador[] adestradores = objectMapper.readValue(file, Adestrador[].class);
        adestradorRepository.saveAll(Arrays.asList(adestradores));
    }

    public void insertarDatosDirectamente() {
        Adestrador brock = new Adestrador();
        brock.setNome("Brock");
        brock.setIdade(15);
        brock.setCidade("Ciudad Plateada");
        adestradorRepository.save(brock);

        Adestrador gary = new Adestrador();
        gary.setNome("Gary Oak");
        gary.setIdade(11);
        gary.setCidade("Pallet Town");
        adestradorRepository.save(gary);

        Pokemon bulbasaur = new Pokemon();
        bulbasaur.setNome("Bulbasaur");
        bulbasaur.setTipo(Arrays.asList("Planta", "Veneno"));
        bulbasaur.setNivel(15);
        bulbasaur.setHabilidades(Arrays.asList("Latigazo", "Drenadoras", "Rayo Solar"));
        bulbasaur.setIdAdestrador(brock.getId());
        pokemonRepository.save(bulbasaur);

        Pokemon squirtle = new Pokemon();
        squirtle.setNome("Squirtle");
        squirtle.setTipo(Arrays.asList("Agua"));
        squirtle.setNivel(18);
        squirtle.setHabilidades(Arrays.asList("Pistola Agua", "Cabezazo", "Refugio"));
        squirtle.setIdAdestrador(gary.getId());
        pokemonRepository.save(squirtle);
    }
}