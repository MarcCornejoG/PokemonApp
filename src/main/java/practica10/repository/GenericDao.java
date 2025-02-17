package practica10.repository;

import practica10.exceptions.ExcepcionGestorPokedex;
import practica10.exceptions.PokemonDuplicado;

import java.util.List;

public interface GenericDao <T,ID extends Number> {

        List<T> getAll() throws ExcepcionGestorPokedex;
        T getById(ID id) throws ExcepcionGestorPokedex;
        ID create(T t) throws ExcepcionGestorPokedex, PokemonDuplicado;
        T update(T t) throws ExcepcionGestorPokedex;
        void delete(ID id) throws ExcepcionGestorPokedex;
        void deleteAll()throws ExcepcionGestorPokedex;



}
