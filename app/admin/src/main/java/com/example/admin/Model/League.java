package com.example.admin.Model;

import com.google.firebase.firestore.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class League {
    @DocumentId
    private String id;
    private Country country;
    private String name;
    private String image;
    private int rating;
}
