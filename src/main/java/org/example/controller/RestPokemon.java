package org.example.controller;

import org.example.model.Adestrador;
import org.example.model.Pokemon;
import org.example.service.PokemonService;
import org.example.util.JsonImporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(RestPokemon.MAPPING)
public class RestPokemon {

    public static final String MAPPING = "/mongodb/pokemon";

    @Autowired
    private PokemonService pokemonService;

    @Autowired
    private JsonImporter jsonImporter;

    @PostMapping("/gardar")
    public ResponseEntity<Pokemon> gardar(@RequestBody Pokemon pokemon) {
        pokemonService.crearPokemon(pokemon);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<Pokemon>> listarColeccion() {
        List<Pokemon> pokemons = pokemonService.buscarPokemons();
        return new ResponseEntity<>(pokemons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pokemon> buscarPorId(@PathVariable String id) {
        Pokemon pokemon = pokemonService.buscarPokemon(id);
        if (pokemon == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pokemon);
    }

    @GetMapping("/adestrador/{idAdestrador}")
    public ResponseEntity<List<Pokemon>> buscarPorAdestrador(@PathVariable String idAdestrador) {
        List<Pokemon> pokemons = pokemonService.buscarPokemonsPorAdestrador(idAdestrador);
        return new ResponseEntity<>(pokemons, HttpStatus.OK);
    }

    @GetMapping("/getAdestradorDePokemon/{id}")
    public ResponseEntity<Adestrador> getAdestradorDePokemon(@PathVariable String id) {
        Adestrador a = pokemonService.buscarAdestradorDePokemon(id);
        if (a == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(a);
    }

    @PostMapping("/importarDesdeJson")
    public ResponseEntity<String> importarDesdeJson(@RequestParam String filePath) {
        try {
            jsonImporter.importarPokemonsDesdeJson(filePath);
            return ResponseEntity.ok("Pokémons importados desde JSON correctamente");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al importar desde JSON: " + e.getMessage());
        }
    }

    @PostMapping("/insertarDirectamente")
    public ResponseEntity<String> insertarDirectamente() {
        jsonImporter.insertarDatosDirectamente();
        return ResponseEntity.ok("Datos insertados directamente correctamente");
    }
}