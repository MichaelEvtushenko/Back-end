package com.netcraker.services.impl;

import com.netcraker.model.Genre;
import com.netcraker.repositories.GenreRepository;
import com.netcraker.services.GenreService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GenreServiceImp implements GenreService {

    private final @NonNull GenreRepository genreRepository;

    @Override
    public List<Genre> getGenres() {
        return genreRepository.getAll();
    }
}