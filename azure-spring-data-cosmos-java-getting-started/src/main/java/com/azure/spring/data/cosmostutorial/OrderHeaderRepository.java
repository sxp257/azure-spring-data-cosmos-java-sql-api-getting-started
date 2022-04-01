package com.azure.spring.data.cosmostutorial;

import java.util.List;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.azure.spring.data.cosmos.repository.Query;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderHeaderRepository extends CosmosRepository<OrderHeader, String> {

    @Query("select * from c where c.order_id = @order_id")
    Iterable<OrderHeader> findByOrderId(@Param("order_id") String order_id);

    @Query("select * from c where c.id = @id")
    Page<JsonNode> findOrderById(@Param("id") String id, Pageable pageable);

    @Query("select * from c where c.order_id = @order_id")
    List<OrderHeader> queryByOrderId(@Param("order_id") String order_id);
}
