package com.susan.almacen.mappers;

import com.susan.almacen.dto.productos.ProductoRequest;
import com.susan.almacen.dto.productos.ProductoResponse;
import com.susan.almacen.entities.Producto;
import com.susan.almacen.enums.Categoria;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public Producto requestAEntidad(ProductoRequest request, Categoria categoria) {

        return request == null
                ? null
                : Producto.crear(
                        request.nombre(),
                        categoria,
                        request.precio(),
                        request.cantidad());

    }

    public ProductoResponse entidadAResponse(Producto producto) {
        return producto == null
                ? null
                : new ProductoResponse(
                        producto.getId(),
                        producto.getNombre(),
                        producto.getCategoria().getDescripcion(),
                        producto.getPrecio(),
                        producto.getCantidad());
    }

}
