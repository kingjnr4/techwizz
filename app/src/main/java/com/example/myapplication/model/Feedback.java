package com.example.myapplication.model;

import com.google.firebase.firestore.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    @DocumentId
    private String id;
    private String message;
    private User user;
    private String createdAt = new SimpleDateFormat("d MMM, yyyy, h:mm a", Locale.getDefault()).format(new Date());
}