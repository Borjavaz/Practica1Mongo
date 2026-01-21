package org.example.service;

import org.example.model.Adestrador;
import org.example.model.Pokemon;
import org.example.repository.AdestradorRepository;
import org.example.repository.PokemonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PokemonService {

    private final PokemonRepository pokemonRepo;
    private final AdestradorRepository adestradorRepo;

    public PokemonService(PokemonRepository pokemonRepo, AdestradorRepository adestradorRepo) {
        this.pokemonRepo = pokemonRepo;
        this.adestradorRepo = adestradorRepo;
    }

    public void crearPokemon(Pokemon p) {
        pokemonRepo.save(p);
    }

    public Pokemon buscarPokemon(String id) {
        return pokemonRepo.findById(id).orElse(null);
    }

    public List<Pokemon> buscarPokemons() {
        return pokemonRepo.findAll();
    }

    public List<Pokemon> buscarPokemonsPorAdestrador(String idAdestrador) {
        return pokemonRepo.findByIdAdestrador(idAdestrador);
    }

    public Adestrador buscarAdestradorDePokemon(String idPokemon) {
        Pokemon pokemon = buscarPokemon(idPokemon);
        if (pokemon == null || pokemon.getIdAdestrador() == null) return null;

        return adestradorRepo.findById(pokemon.getIdAdestrador()).orElse(null);
    }
}