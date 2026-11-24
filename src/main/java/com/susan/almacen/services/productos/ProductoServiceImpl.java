package com.susan.almacen.services.productos;

import com.susan.almacen.dto.productos.ProductoRequest;
import com.susan.almacen.dto.productos.ProductoResponse;
import com.susan.almacen.entities.Producto;
import com.susan.almacen.enums.Categoria;
import com.susan.almacen.exceptions.RecursoNoEncontradoException;
import com.susan.almacen.mappers.ProductoMapper;
import com.susan.almacen.repositories.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j

public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    private final ProductoMapper productoMapper;

    @Override
    public List<ProductoResponse> listar(
            String nombre, String categoria,
            BigDecimal precioMin, BigDecimal precioMax) {

        log.info("Listando todods los productos...");
        return productoRepository.findAll().stream()
                //.map(producto -> productoMapper.entidadAResponse(p)).toList();
                .map(productoMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponse obtenerPorId(Long id) {
        return null;
    }

    @Override
    public ProductoResponse registrar(ProductoRequest request) {

        log.info("Registrando el producto...");
        Producto producto = productoMapper.requestAEntidad(
                request,
                Categoria.obtenerCategoriaPorDescripcion(request.categoria().trim()));
        productoRepository.saveAndFlush(producto);

        log.info("Nuevo producto {} registrado", producto.getNombre());

        return productoMapper.entidadAResponse(producto);
    }

    @Override
    public ProductoResponse actualizar(ProductoRequest request, Long id) {

        Producto producto = obtenerProductoException(id);
        log.info("Actualizando el producto {}", id);

        producto.actualizar(
                request.nombre().trim(),
                Categoria.obtenerCategoriaPorDescripcion(request.categoria().trim()),
                request.precio(),
                request.cantidad());

        productoRepository.saveAndFlush(producto);

        log.info("Producto con id {} actulizado", id);

        return productoMapper.entidadAResponse(producto);

    }

    @Override
    public void eliminar(Long id) {
        Producto producto = obtenerProductoException(id);

        log.info("Eliminando el producto {}", id);

        productoRepository.delete(producto);
        productoRepository.flush();

    }
    private Producto obtenerProductoException(Long id){
        log.info("Buscando el producto con id: {}", id);

        return productoRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException(
                        "Producto no encontrado: " + id));
    }
}
