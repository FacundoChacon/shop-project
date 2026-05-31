package com.example.demo.specification;
import com.example.demo.model.Producto;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductoSpecification {

    public static Specification<Producto> nombreContiene(String nombre){
        return (root, query, cb) ->
                nombre == null
                        ? null
                        : cb.like(
                        cb.lower(root.get("nombre")),
                        "%" + nombre.toLowerCase() + "%"
                );
    }

    public static Specification<Producto> categoriaId(Long categoriaId){
        return (root, query, cb) ->
                categoriaId == null
                        ? null
                        : cb.equal(
                        root.get("categoria").get("id"),
                        categoriaId
                );
    }
    public static Specification<Producto> precioMayorIgual(BigDecimal min){
        return (root, query, cb) ->
                min == null
                        ? null
                        : cb.greaterThanOrEqualTo(
                        root.get("precio"),
                        min
                );
    }

    public static Specification<Producto> precioMenorIgual(BigDecimal max){
        return (root, query, cb) ->
                max == null
                        ? null
                        : cb.lessThanOrEqualTo(
                        root.get("precio"),
                        max
                );
    }

    public static Specification<Producto> stockMayorIgual(Integer stock){
        return (root, query, cb) ->
                stock == null
                        ? null
                        : cb.greaterThanOrEqualTo(
                        root.get("stock"),
                        stock
                );
    }
}

