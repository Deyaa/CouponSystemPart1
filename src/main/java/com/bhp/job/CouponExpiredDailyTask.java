package com.bhp.job;

import com.bhp.beans.Coupon;
import com.bhp.dao.CouponsDAO;
import com.bhp.dbdao.CouponDBDAO;

import java.sql.SQLException;
import java.util.List;

public class CouponExpiredDailyTask implements Runnable{

    private CouponsDAO couponsDAO;
    private boolean quit;

    public CouponExpiredDailyTask(){
        this.couponsDAO = new CouponDBDAO();
        this.quit = false;
    }
    @Override
    public void run() {

        while(!quit){

            try {
                System.out.println("====================> Start job removing expired coupons");
                List<Coupon> expiredCoupons = this.couponsDAO.getExpiredCoupons();
                System.out.println("Before removing expired coupons: " + this.couponsDAO.getAll().size());
                for (Coupon c : expiredCoupons) {
                    this.couponsDAO.delete(c.getId());
                    this.couponsDAO.deleteCustomerVsCouponByCouponId(c.getId());
                }
                System.out.println("After removing expired coupons: " + this.couponsDAO.getAll().size());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            try {
                //Thread.sleep(1000*60*60*24);
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void stop(Boolean quit) {
        this.quit = quit;
    }
}
