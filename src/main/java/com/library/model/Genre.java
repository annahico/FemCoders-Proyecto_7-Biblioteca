package com.library.model;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class Genre {
private int id;
private String name;
@Builder.Default
private List <Book> books = new ArrayList<>();
}





    
    