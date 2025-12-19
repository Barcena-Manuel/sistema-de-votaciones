package com.mobydigital.segunda.evaluacion.exception;

public class CandidateNotExistException extends Exception {
    public CandidateNotExistException(Long id){
        super("The Candidate with the Id " + id + " was not found");
    }
}
