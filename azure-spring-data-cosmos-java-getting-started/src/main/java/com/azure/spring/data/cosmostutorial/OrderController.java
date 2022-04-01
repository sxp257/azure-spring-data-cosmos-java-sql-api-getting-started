package com.azure.spring.data.cosmostutorial;

import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderHeaderRepository orderHeaderRepository;
    private OrderLineRepository orderLineRepository;

    OrderController(OrderHeaderRepository orderHeaderRepository, OrderLineRepository orderLineRepository) {
        this.orderHeaderRepository = orderHeaderRepository;
        this.orderLineRepository = orderLineRepository;
    }

    @RequestMapping(value = "/orders/{id}",method = RequestMethod.GET
    ,produces = "application/json")
    @ResponseBody
    String getOrder(@PathVariable String id) {
        try {
            Iterator<OrderHeader> orderHeaderIterator = orderHeaderRepository.findByOrderId(id).iterator();
            Order order = new Order();
            orderHeaderIterator.forEachRemaining(header -> order.setOrderHeader(header));
            Iterator<OrderLine> orderLineIterator = orderLineRepository.findByOrderId(id).iterator();
            orderLineIterator.forEachRemaining(line -> order.setLines(line));
            Gson gsonLine = new GsonBuilder().setPrettyPrinting().create();
            String jsonLine = gsonLine.toJson(order);
            return jsonLine;
        } catch (Exception ex) {
            throw new OrderNotFoundException(id);
        }
    }

    /*@GetMapping("/orders/{id}")
    List<OrderHeader> getOrderById(@PathVariable String id) {
        try {
           
            return orderHeaderRepository.queryByOrderId(id);
        } catch (Exception ex) {
            throw new OrderNotFoundException(id);
        }
    }*/
   

}
