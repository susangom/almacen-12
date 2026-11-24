package com.susan.almacen.controllers;

import com.susan.almacen.dto.productos.ProductoRequest;
import com.susan.almacen.dto.productos.ProductoResponse;
import com.susan.almacen.services.productos.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "API Productos", description = "Gestion de inventario de productos")
public class ProductoController {

    private final ProductoService productoService;
    @Operation(summary = "Listar productos", description = "Todos los filtros son opc")
    @ApiResponse(responseCode = "200", description = "Listado obtenido")

    public ResponseEntity<List<ProductoResponse>> listar(
            @Parameter(description = "Busqueda por nombre", example = "Laptop")
            @RequestParam(required = false) String nombre,

            @Parameter(description = "Busqueda por categoria", example = "Electrónico")
            @RequestParam(required = false) String categoria,

            @Parameter(description = "Busqueda por precio minimo", example = "1000")
            @RequestParam(required = false)BigDecimal precioMin,

            @Parameter(description = "Busqueda por precio maximo", example = "20000")
            @RequestParam(required = false) BigDecimal precioMax
    ){
        return ResponseEntity.ok(productoService.listar(nombre, categoria, precioMin, precioMax));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID")
    @ApiResponse(responseCode = "200", description = "Producto encontrado")
    @ApiResponse(responseCode = "404", description = "El producto no existe")
    public ResponseEntity<ProductoResponse> obtenerPorId(

            @Parameter(description = "ID del producto", example = "1")
            @PathVariable @Positive(message = "El ID  debe ser positivo") Long id
    ){
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo producto")
    @ApiResponse(responseCode = "201",  description = "Producto creado")

    public  ResponseEntity<ProductoResponse> registrar(
            @Valid @RequestBody ProductoRequest request
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.registrar(request));
    }

    @PostMapping("/{id}")
    @Operation(summary = "Actualizar un producto existente")
    @ApiResponse(responseCode = "200", description = "Producto actualizado")
    @ApiResponse(responseCode = "404", description = "El producto existe")
    public ResponseEntity<ProductoResponse> actualizar(
            @Parameter(description = "ID del producto", example = "1")
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id,

            @Valid @RequestBody ProductoRequest request
    ){
        return ResponseEntity.ok(productoService.actualizar(request, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto")
    @ApiResponse(responseCode = "204", description = "Producto eliminado")
    @ApiResponse(responseCode = "404", description = "El producto no existe")
    public ResponseEntity<ProductoResponse> eliminar(

            @Parameter(description = "ID del producto", example = "1")
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ){
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
