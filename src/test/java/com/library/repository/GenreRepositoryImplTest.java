package com.library.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.library.model.Genre;

@ExtendWith(MockitoExtension.class)
public class GenreRepositoryImplTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private GenreRepositoryImpl genreRepositoryImpl;

    private Genre genreTest;
    private List<Genre> genreListTest;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }
}
