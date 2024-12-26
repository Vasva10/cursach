package org.example.library.dto;

import lombok.Data;

@Data
public class CopyDTO {
    private Long bookId;
    private String inventoryNumber;
    private String status;
}
