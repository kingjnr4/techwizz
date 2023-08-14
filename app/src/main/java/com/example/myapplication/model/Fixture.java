package com.example.myapplication.model;

import com.google.firebase.firestore.DocumentId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fixture {
    @DocumentId
    private String id;
    private League league;
    private Team homeTeam;
    private Team awayTeam;
    private String date;
    private int homeScore=0;
    private int awayScore=0;
}
