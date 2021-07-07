package com.bhp.playground;

import com.bhp.beans.Category;
import com.bhp.beans.Company;
import com.bhp.beans.Coupon;
import com.bhp.beans.Customer;
import com.bhp.dao.CompaniesDAO;
import com.bhp.dao.CouponsDAO;
import com.bhp.dao.CustomersDAO;
import com.bhp.db.DatabaseManager;
import com.bhp.dbdao.CompaniesDBDAO;
import com.bhp.dbdao.CouponDBDAO;
import com.bhp.dbdao.CustomersDBDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TestCRUDDB {
    public static void main(String[] args) {
        System.out.println("START");

        try {
            DatabaseManager.dropAndCreate();
            runCompaniesCRUD();
            runCustomersCRUD();
            runCouponsCRUD();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("END");
    }

    public static void runCompaniesCRUD() throws SQLException {
        System.out.println("==================== C O M P A N I E S =========================");
        System.out.println("--------------------> Add COMPANIES");
        CompaniesDAO companiesDAO = new CompaniesDBDAO();
        companiesDAO.add(new Company("bnp01", "bnhp@011", "12"));
        companiesDAO.add(new Company("bnp02", "bnhp@012", "13"));
        companiesDAO.add(new Company("bnp03", "bnhp@013", "14"));
        companiesDAO.add(new Company("bnp04", "bnhp@014", "15"));
        companiesDAO.add(new Company("bnp05", "bnhp@015", "16"));
        System.out.println("--------------------> Delete COMPANIES");
        companiesDAO.delete(1);
        companiesDAO.delete(2);
        System.out.println("--------------------> GET ALL COMPANIES");
        List<Company> companiesList = companiesDAO.getAll();
        for (Company c: companiesList) {
            System.out.println("Id: " + c.getId() + " Name: " + c.getName() + " email: " + c.getEmail() + " password: " +
                    c.getPassword());
        }
        System.out.println("--------------------> GET SINGLE COMPANY");
        int id = 3;
        Company company = companiesDAO.getOne(id);
        System.out.println("Id: " + id + " Name: " + company.getName() + " email: " + company.getEmail() + " password: " +
                company.getPassword());

        System.out.println("--------------------> UPDATE COMPANIES");
        companiesDAO.update(new Company(4, "bnp04", "bnp@0444444", "12345"));
        companiesDAO.update(new Company(5, "bnp055", "bnp@55555", "654321"));
        System.out.println("--------------------> GET ALL COMPANIES");
        List<Company> companiesUpdatedList = companiesDAO.getAll();
        for (Company c: companiesUpdatedList) {
            System.out.println("Id: " + c.getId() + " Name: " + c.getName() + " email: " + c.getEmail() + " password: " +
                    c.getPassword());
        }
    }

    public static void runCustomersCRUD() throws SQLException {
        System.out.println("==================== C U S T O M E R S =========================");
        System.out.println("--------------------> ADD CUSTOMERS");
        CustomersDAO customersDAO = new CustomersDBDAO();
        customersDAO.add(new Customer("Deyaa", "Taha", "deyaa@taha", "123"));
        customersDAO.add(new Customer("Amir", "Amer", "Amir@Amir", "321"));
        customersDAO.add(new Customer("Moshe", "Yani", "mosh@mosh", "325"));
        customersDAO.add(new Customer("Dvir", "Asi", "advir@asi", "852"));
        customersDAO.add(new Customer("Nedo", "Nad", "nedo@nad", "258"));
        System.out.println("--------------------> DELETE CUSTOMERS");
        customersDAO.delete(1);
        customersDAO.delete(2);
        System.out.println("--------------------> GET ALL CUSTOMERS");
        List<Customer> customersList = customersDAO.getAll();
        for (Customer c: customersList) {
            System.out.println("Id: " + c.getId() + " FirstName: " + c.getFirstName() + " LastName: " +
                    c.getLastName() + " email: " + c.getEmail() + " password: " + c.getPassword());
        }
        System.out.println("--------------------> GET SINGLE CUSTOMER");
        int id = 3;
        Customer customer = customersDAO.getOne(id);
        System.out.println("Id: " + customer.getId() + " FirstName: " + customer.getFirstName() + " LastName: " +
                customer.getLastName() + " email: " + customer.getEmail() + " password: " + customer.getPassword());

        System.out.println("--------------------> UPDATE CUSTOMERS");
        customersDAO.update(new Customer(4,"aaaa", "ccc", "aaa@aaa", "444"));
        customersDAO.update(new Customer(5,"bbbb", "ddd", "bbb@ccc", "555"));
        System.out.println("--------------------> GET ALL CUSTOMERS");
        List<Customer> customersUpdatedList = customersDAO.getAll();
        for (Customer c: customersUpdatedList) {
            System.out.println("Id: " + c.getId() + " FirstName: " + c.getFirstName() + " LastName: " +
                    c.getLastName() + " email: " + c.getEmail() + " password: " + c.getPassword());
        }
    }

    public static void runCouponsCRUD() throws SQLException {
        System.out.println("==================== C O U P O N S =========================");
        System.out.println("--------------------> ADD COUPONS");
        CouponsDAO couponsDAO = new CouponDBDAO();
        couponsDAO.add(new Coupon(3, Category.Food,"coupon01 title","coupon01 description",
                LocalDate.now(), LocalDate.now().plusDays(12), 200,
                99.99, "bla.com"));
        couponsDAO.add(new Coupon(3,Category.Electricity,"coupon02 title","coupon02 description",
                LocalDate.now(), LocalDate.now().plusDays(12), 200,
                80.99, "bla.com"));
        couponsDAO.add(new Coupon(3,Category.Restaurant,"coupon03 title","coupon03 description",
                LocalDate.now(), LocalDate.now().plusDays(12), 200,
                60.99, "bla.com"));
        couponsDAO.add(new Coupon(3,Category.Vacation,"coupon04 title","coupon04 description",
                LocalDate.now(), LocalDate.now().plusDays(12), 200,
                70.99, "bla.com"));
        couponsDAO.add(new Coupon(4,Category.Food,"coupon05 title","coupon05 description",
                LocalDate.now(), LocalDate.now().plusDays(12), 200,
                50.99, "bla.com"));

        System.out.println("--------------------> DELETE COUPONS");
        couponsDAO.delete(1);
        couponsDAO.delete(2);

        System.out.println("--------------------> GET ALL COUPONS");
        List<Coupon> couponsList = couponsDAO.getAll();
        for (Coupon c: couponsList) {
            System.out.println("Id: " + c.getId() + " CompanyId: " + c.getCompanyId() + " Category: " +
                    c.getCategory() + " Title: " + c.getTitle() + " Description: " + c.getDescription() +
                    " StartDate: " + c.getStartDate() + " EndDate: " + c.getEndDate() + " Amount:" + c.getAmount() +
                    " Price: " + c.getPrice() + " Image:" + c.getImage());
        }

        System.out.println("--------------------> GET SINGLE COUPONS");
        int id = 3;
        Coupon c = couponsDAO.getOne(id);
        System.out.println("Id: " + c.getId() + " CompanyId: " + c.getCompanyId() + " Category: " +
                c.getCategory() + " Title: " + c.getTitle() + " Description: " + c.getDescription() +
                " StartDate: " + c.getStartDate() + " EndDate: " + c.getEndDate() + " Amount:" + c.getAmount() +
                " Price: " + c.getPrice() + " Image:" + c.getImage());

        System.out.println("--------------------> UPDATE COUPONS");
        couponsDAO.add(new Coupon(3,4,Category.Restaurant,"coupon036666 title","coupon03554411 description",
                LocalDate.now(), LocalDate.now().plusDays(20), 150,
                60.99, "bla.com"));
        couponsDAO.add(new Coupon(4,5,Category.Vacation,"coupon0488522 title","coupon049999 description",
                LocalDate.now(), LocalDate.now().plusDays(20), 120,
                70.99, "bla.com"));

        System.out.println("--------------------> GET ALL COUPONS");
        List<Coupon> couponsUpdatedList = couponsDAO.getAll();
        for (Coupon coupon: couponsUpdatedList) {
            System.out.println("Id: " + coupon.getId() + " CompanyId: " + coupon.getCompanyId() + " Category: " +
                    coupon.getCategory() + " Title: " + coupon.getTitle() + " Description: " + coupon.getDescription() +
                    " StartDate: " + coupon.getStartDate() + " EndDate: " + coupon.getEndDate() + " Amount:" + coupon.getAmount() +
                    " Price: " + coupon.getPrice() + " Image:" + coupon.getImage());
        }
    }
}
