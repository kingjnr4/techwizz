package com.example.myapplication.model;

import com.google.firebase.firestore.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @DocumentId
    private String id;
    private String uid;
    private String username;
    private String email;
    @Nullable
    private String phone;
    @Nullable
    private String picture;
}
