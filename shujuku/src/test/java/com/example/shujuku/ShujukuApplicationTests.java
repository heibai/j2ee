package com.example.shujuku;

import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;

@SpringBootTest
class ShujukuApplicationTests {
    String b = "2023-12-22 00:00:00";
    LocalDateTime a = LocalDateTime.parse(b);

}
