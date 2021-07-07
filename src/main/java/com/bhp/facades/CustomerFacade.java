package com.bhp.facades;

import com.bhp.beans.Category;
import com.bhp.beans.Coupon;
import com.bhp.beans.Customer;
import com.bhp.exceptions.CouponSystemException;
import com.bhp.exceptions.ErrorMessage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerFacade extends ClientFacade{

    private Customer customer;
    private int customerId;

    public CustomerFacade() throws SQLException {
        super();
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean login(String email, String password) throws SQLException {
        if(this.customerDAO.isCustomerExists(email, password)){
            setCustomerId(this.customerDAO.getCustomerIdByEmailAndPassword(email, password));
            this.customer = this.customerDAO.getOne(getCustomerId());
            return true;
        }
        return false;
    }

    public void purchaseCoupon(Coupon coupon) throws SQLException, CouponSystemException {
        if (coupon.getAmount() <= 0){
            throw new CouponSystemException(ErrorMessage.COUPON_AMOUNT_ZERO);
        }
        if(coupon.getEndDate().isBefore(LocalDate.now())){
            throw new CouponSystemException(ErrorMessage.COUPON_EXPIRED);
        }
        if(this.customerCouponDAO.isPurchase(coupon.getId())) {
            throw new CouponSystemException(ErrorMessage.COUPON_DOUBLE_PURCHASE);
        }

        this.customerCouponDAO.addCouponPurchase(getCustomerId(), coupon.getId());
        //Lower the amount of coupons
        coupon.setAmount(coupon.getAmount() - 1) ;
        this.couponsDAO.update(coupon);
        //Add the new coupon to customer list purchase
        this.customer.getCoupons().add(coupon);
    }
    //Coupons purchased by customer
    public List<Coupon> getCustomerCoupons(){
        return this.customer.getCoupons();
    }
    //Coupons purchased by customer from specific category
    public List<Coupon> getCustomerCoupons(Category category){
        List<Coupon> coupons = new ArrayList<>();
        for (Coupon coupon : getCustomerCoupons()) {
            if (coupon.getCategory().value == category.value) {
                coupons.add(coupon);
            }
        }
        return coupons;
    }

    public List<Coupon> getCustomerCoupons(double maxPrice){
        List<Coupon> coupons = new ArrayList<>();
        for (Coupon coupon : getCustomerCoupons()) {
            if (coupon.getPrice() < maxPrice) {
                coupons.add(coupon);
            }
        }
        return coupons;
    }

    public Customer getCustomerDetails() throws SQLException {
        return this.customerDAO.getOne(getCustomerId());
    }
}
