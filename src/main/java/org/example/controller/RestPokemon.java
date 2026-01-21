package org.example.controller;

import org.example.model.Pokemon;
import org.example.model.Adestrador;
import org.example.service.PokemonService;
import org.example.service.JsonImporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public ResponseEntity<Pokemon> gardarDocumento(@RequestBody Pokemon pokemon) {
        pokemonService.crearPokemon(pokemon);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<Pokemon>> listarColeccion() {
        List<Pokemon> pokemons = pokemonService.buscarPokemons();
        return new ResponseEntity<>(pokemons, HttpStatus.OK);
    }

    @DeleteMapping("/borrarTodos")
    public ResponseEntity<Pokemon> borrarColeccion() {
        pokemonService.borrarTodos();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/importar")
    public ResponseEntity<String> importar(@RequestParam String rutaArchivo) {
        try {
            jsonImporter.importarPokemonsDesdeJson(rutaArchivo);
            return ResponseEntity.ok("Pokémons importados correctamente desde: " + rutaArchivo);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al importar desde " + rutaArchivo + ": " + e.getMessage());
        }
    }

    @GetMapping(value = "/exportar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> exportarPokemon() throws JsonProcessingException {
        List<Pokemon> pokemons = pokemonService.buscarPokemons();

        ObjectMapper mapper = new ObjectMapper();
        byte[] jsonBytes = mapper.writeValueAsBytes(pokemons);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentDisposition(
                ContentDisposition.attachment()
                        .filename("Pokemons.json")
                        .build()
        );

        return new ResponseEntity<>(jsonBytes, headers, HttpStatus.OK);
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

    @PostMapping("/insertarDirectamente")
    public ResponseEntity<String> insertarDirectamente() {
        jsonImporter.insertarDatosDirectamente();
        return ResponseEntity.ok("Datos insertados directamente correctamente");
    }
}