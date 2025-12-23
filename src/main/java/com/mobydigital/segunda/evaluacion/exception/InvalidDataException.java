package com.mobydigital.segunda.evaluacion.exception;

public class InvalidDataException extends Exception{
    public InvalidDataException(){
        super("The Object contains invalid data");
    }
}
