package com.azure.spring.data.cosmostutorial;

public class OrderNotFoundException extends RuntimeException {
  OrderNotFoundException(String orderId) {
    super("Could not find employee " + orderId);
  }
}
