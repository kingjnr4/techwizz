package com.example.admin.Model;

import com.example.admin.Model.User;
import com.google.type.DateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    private String message;
    private User user;
    private String createdAt = new SimpleDateFormat("d MMM, yyyy, h:mm a", Locale.getDefault()).format(new Date());
}