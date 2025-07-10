package com.itheima.consultant.service;

import com.itheima.consultant.mapper.ReservationMapper;
import com.itheima.consultant.pojo.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    @Autowired
    private ReservationMapper reservationMapper;

    //1.添加预约信息的方法

    /**
     * 添加预约信息
     * @param reservation
     */
    public void insert(Reservation reservation) {
        reservationMapper.insert(reservation);
    }

    /**
     * 根据手机号查询预约信息
     * @param phone
     * @return
     */
    //2.查询预约信息的方法(根据手机号查询)
    public Reservation findByPhone(String phone) {
        return reservationMapper.findByPhone(phone);
    }
}
