package com.example.admin.Model;

import com.google.firebase.firestore.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
