package com.cn.lx.dao;

import com.cn.lx.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

    OrderMaster findByOrderId(String orderId);

    /**
     * 根据买家openid获取买家订单
     * @param openid
     * @param pageable
     * @return
     */
    Page<OrderMaster> findByBuyerOpenid(String openid , Pageable pageable);
}
