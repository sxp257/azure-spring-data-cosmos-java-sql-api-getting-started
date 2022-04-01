// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.spring.data.cosmostutorial;

import com.azure.spring.data.cosmos.core.query.CosmosPageRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Iterator;

@SpringBootApplication
@ComponentScan(basePackageClasses = OrderController.class)
public class SampleApplication implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(SampleApplication.class);

    @Autowired
    private OrderHeaderRepository orderHeaderRepository;

    @Autowired
    private OrderLineRepository orderLineRepository;



    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }

    public void run(String... var1) {

        String order_id = "OE0211101A794337";
        Iterator<OrderHeader> orderHeaderIterator = orderHeaderRepository.findByOrderId(order_id).iterator();
        Order order = new Order();
        
        if (orderHeaderIterator.hasNext()) {
            
            OrderHeader orderHeader = orderHeaderIterator.next();
            logger.info("orderHeader order id : {}", orderHeader.getOrder_id());
            order.setOrderHeader(orderHeader);
        }

        logger.info("Query Line using orderLineRepository");

        Iterator<OrderLine> orderLineIterator = orderLineRepository.findByOrderId(order_id).iterator();
        orderLineIterator.forEachRemaining(line -> order.setLines(line));
        Gson gsonLine = new GsonBuilder().setPrettyPrinting().create();
        String jsonLine = gsonLine.toJson(order);
        logger.info(String.format("First query result: orderdetail with (/id) = (%s, %s)",
                order.getOrderHeader().getId(), jsonLine));

        logger.info("Done.");
    }
}
