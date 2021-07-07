package com.bhp.facades;

import com.bhp.beans.Category;
import com.bhp.beans.Company;
import com.bhp.beans.Coupon;
import com.bhp.exceptions.CouponSystemException;
import com.bhp.exceptions.ErrorMessage;

import java.sql.SQLException;
import java.util.List;

public class CompanyFacade extends ClientFacade{

    private int companyId;

    public CompanyFacade() {
        super();
    }

    public int getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean login(String email, String password) throws SQLException {
        if(this.companiesDAO.isCompanyExists(email, password)){
            setCompanyId(this.companiesDAO.getCompanyIdByEmailAndPassword(email, password));
            return true;
        }
        return false;
    }

    public void addCoupon(Coupon coupon) throws SQLException, CouponSystemException {
        if(this.couponsDAO.isCompanyCouponTitleExist(coupon.getCompanyId(), coupon.getTitle())){
            throw new CouponSystemException(ErrorMessage.COUPON_TILE_EXISTS_FOR_COMPANY);
        }
        this.couponsDAO.add(coupon);
    }

    public void updateCoupon(Coupon coupon) throws SQLException, CouponSystemException {
        if(!this.companiesDAO.isCompanyIdExist(coupon.getCompanyId())){
            throw new CouponSystemException(ErrorMessage.UPDATE_COMPANY_ID_NOT_EXISTS);
        }
        if(!this.couponsDAO.isCouponIdExist(coupon.getId())){
            throw new CouponSystemException(ErrorMessage.COUPON_ID_NOT_EXISTS);
        }
        this.couponsDAO.update(coupon);
    }

    public void deleteCoupon(int couponId) throws SQLException {
        this.couponsDAO.deleteCustomerVsCouponByCouponId(couponId);
        this.couponsDAO.delete(couponId);
    }

    public List<Coupon> getCompanyCoupons() throws SQLException {
        return this.couponsDAO.getAllByCompanyId(getCompanyId());
    }

    public List<Coupon> getCompanyCoupons(Category category) throws SQLException {
        return this.couponsDAO.getAllByCompanyAndCategoryId(getCompanyId(),category.value);
    }

    public List<Coupon> getCompanyCoupons(double maxPrice) throws SQLException {
        return this.couponsDAO.getAllByCompanyAndPrice(getCompanyId(), maxPrice);
    }

    public Company getCompanyDetails(Coupon coupon) throws SQLException {
        Company company = this.companiesDAO.getOne(coupon.getCompanyId());
        company.setCoupons(getCompanyCoupons());
        return company;
    }
}
