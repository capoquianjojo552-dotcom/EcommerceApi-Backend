package com.ws101.capoquian.banawis.EcommerceApi.model;

   import lombok.Data;
   import lombok.NoArgsConstructor;
   import lombok.AllArgsConstructor;

   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   public class Product {
       private Long id;
       private String name;
       private String description;
       private Double price;
       private String category;
       private Integer stockQuantity;
       private String imageUrl;
   }
