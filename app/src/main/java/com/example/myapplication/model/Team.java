package com.example.myapplication.model;

import com.google.firebase.firestore.DocumentId;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @DocumentId
    private String id;
    private String name;
    private String shortName;
    private String managerName;
    private String image;
    private ArrayList<League> leagues;
    private int rating;
}
