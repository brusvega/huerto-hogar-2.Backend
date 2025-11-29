package com.example.BackEndHuerto.controller;

import com.example.BackEndHuerto.model.Producto;
import com.example.BackEndHuerto.service.ProductoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Producto> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Producto obtener(@PathVariable Long id) {
        return service.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    // -----------------------------------------
    //  CREAR PRODUCTO CON IMAGEN
    // -----------------------------------------
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Producto crear(
            @RequestParam("nombre") String nombre,
            @RequestParam("precio") String precio,
            @RequestParam("stock") Integer stock,
            @RequestParam(value = "descripcion", required = false) String descripcion,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen

    ) throws IOException {

        String imagenUrl = null;

        if (imagen != null && !imagen.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + imagen.getOriginalFilename();
            Path path = Paths.get("uploads/" + fileName);
            Files.write(path, imagen.getBytes());

            imagenUrl = "http://localhost:8080/uploads/" + fileName;
        }

        Producto p = new Producto();
        p.setNombre(nombre);
        p.setDescripcion(descripcion);
        p.setPrecio(new java.math.BigDecimal(precio));
        p.setStock(stock);
        p.setImagenUrl(imagenUrl);

        return service.guardar(p);
    }

    // -----------------------------------------
    //  ACTUALIZAR PRODUCTO (CON O SIN IMAGEN)
    // -----------------------------------------
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Producto actualizar(
            @PathVariable Long id,
            @RequestParam("nombre") String nombre,
            @RequestParam("precio") String precio,
            @RequestParam("stock") Integer stock,
            @RequestParam(value = "descripcion", required = false) String descripcion,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen
    ) throws IOException {

        Producto p = service.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        p.setNombre(nombre);
        p.setDescripcion(descripcion);
        p.setPrecio(new java.math.BigDecimal(precio));
        p.setStock(stock);

        if (imagen != null && !imagen.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + imagen.getOriginalFilename();
            Path path = Paths.get("uploads/" + fileName);
            Files.write(path, imagen.getBytes());

            p.setImagenUrl("http://localhost:8080/uploads/" + fileName);
        }

        return service.guardar(p);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
