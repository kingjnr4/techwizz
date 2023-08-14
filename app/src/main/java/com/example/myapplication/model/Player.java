package com.example.myapplication.model;

import com.google.firebase.firestore.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    @DocumentId
    private  String id;
    private String firstname;
    private String lastname;
    private String bio;
    private int goals;
    private int matches;
    private int age;
    private int height;
    private int weight;
    private String image;
    private Team team;
    private String position;
    public String getFullName() {
        return firstname+lastname;
    }
    public String getSpacedFullName(){return firstname+" "+lastname;}
}
