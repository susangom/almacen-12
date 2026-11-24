package com.susan.almacen.utils;

import com.susan.almacen.entities.Producto;
import com.susan.almacen.enums.Categoria;
import com.susan.almacen.repositories.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor

public class DatosIniciales implements CommandLineRunner {

    private final ProductoRepository productoRepository;

    @Override
    public void run(String... args) throws Exception {

        if (productoRepository.count() == 0 ){
            productoRepository.saveAll(List.of(
                    new Producto(
                            null,
                            "Laptop Gamer",
                            Categoria.ELECTRONICA,
                            BigDecimal.valueOf(15000),
                            10),

                    new Producto(
                            null,
                            "Celular",
                            Categoria.ELECTRONICA,
                            BigDecimal.valueOf(5300),
                            15),

                    new Producto(
                            null,
                            "Laptop",
                            Categoria.ELECTRONICA,
                            BigDecimal.valueOf(4000),
                            3)
            ));
        }

        log.info("Productos de prueba cargados correctamente");

    }
}
