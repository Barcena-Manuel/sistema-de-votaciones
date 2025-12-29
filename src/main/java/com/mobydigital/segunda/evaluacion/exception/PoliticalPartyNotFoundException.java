package com.mobydigital.segunda.evaluacion.exception;

public class PoliticalPartyNotFoundException extends Exception{
    public PoliticalPartyNotFoundException(Long id){
        super("The Political Party with the Id " + id + " was not found");
    }
}
