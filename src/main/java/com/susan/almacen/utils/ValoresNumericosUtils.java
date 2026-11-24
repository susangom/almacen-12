package com.susan.almacen.utils;

import com.susan.almacen.exceptions.DatoInvalidoException;

import java.math.BigDecimal;

public class ValoresNumericosUtils {

    //Para que cualquier numero pueda tener cualquier valor (double, entero etc)
    //N extend Number es para eso (Limite superior)

    public static <N extends Number> void validarNumeroRequerido(N numero, String mensaje){
        if (numero == null)
            throw new DatoInvalidoException(mensaje);
    }

    public static void validarEnteroPositivo(Integer numero, String mensaje){

        validarNumeroRequerido(numero, mensaje);

        if (numero <= 0)
            throw new DatoInvalidoException(mensaje);
    }

    public static void validarBigDecimalPositivo(BigDecimal numero, String mensaje){
        validarNumeroRequerido(numero, mensaje);

        if(numero.compareTo(BigDecimal.ZERO) <= 0)
            throw new DatoInvalidoException(mensaje);
    }
}



