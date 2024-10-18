package com.bridge.studentmanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document
public class Address {
  //  address (Object containing street, city, state, and postal code)
    private String street;
    private String city;
    private String state;
    private long postalCode;

}
