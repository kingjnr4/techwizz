package com.example.admin.Model;

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
public class Admin {
    @DocumentId
    private String id;
    private String username;
    private String password;
}
