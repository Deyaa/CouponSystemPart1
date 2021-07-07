package com.bhp.playground;

import com.bhp.beans.Category;
import com.bhp.beans.Company;
import com.bhp.beans.Coupon;
import com.bhp.beans.Customer;
import com.bhp.dao.CompaniesDAO;
import com.bhp.dao.CouponsDAO;
import com.bhp.dao.CustomersDAO;
import com.bhp.db.ConnectionPool;
import com.bhp.db.DatabaseManager;
import com.bhp.dbdao.CompaniesDBDAO;
import com.bhp.dbdao.CouponDBDAO;
import com.bhp.dbdao.CustomersDBDAO;
import com.bhp.exceptions.CouponSystemException;
import com.bhp.facades.AdminFacade;
import com.bhp.facades.CompanyFacade;
import com.bhp.facades.CustomerFacade;
import com.bhp.job.CouponExpiredDailyTask;
import com.bhp.security.ClientType;
import com.bhp.security.LoginManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Test {

    public static void testAll() {
        System.out.println("START");

        try {
            DatabaseManager.dropAndCreate();
            //1. Running daily task
            System.out.println("==================== RUN DAILY JOB =============================");
            CouponExpiredDailyTask couponExpiredDailyTask = new CouponExpiredDailyTask();
            Thread thread = new Thread(couponExpiredDailyTask);
            //only run until this main finished
            thread.setDaemon(true);
            thread.start();

            LoginManager loginManager = LoginManager.getInstance();

            //2. Test Administrator login and logic
            System.out.println("==================== A D M I N I S T R A T O R ===================");
            AdminFacade adminFacade = (AdminFacade) loginManager.login("admin@admin.com", "admin", ClientType.Administrator);
            System.out.println("====================> ADD COMPANIES");
            adminFacade.addCompany(new Company("Apple", "Apple@test.mail", "pass01"));
            adminFacade.addCompany(new Company("Samsung", "Samsung@test.mail", "pass02"));
            adminFacade.addCompany(new Company("TaT", "TaT@test.mail", "pass03"));
            adminFacade.addCompany(new Company("XFONE", "XFONE@test.mail", "pass04"));
            adminFacade.addCompany(new Company("MOBILTY", "MOBILTY@test.mail", "pass05"));
            adminFacade.addCompany(new Company("mobi", "mobi@test.mail", "pass06"));
            System.out.println(adminFacade.getAllCompanies());

            System.out.println("====================> UPDATE COMPANY");
            Company companyTest = adminFacade.getOneCompany(1);
            companyTest.setPassword("passApple");
            companyTest.setName("Appleeeeee");
            adminFacade.updateCompany(companyTest);
            System.out.println(adminFacade.getOneCompany(companyTest.getId()));
            System.out.println("====================> DELETE COMPANY");
            adminFacade.deleteCompany(6);
            System.out.println(adminFacade.getAllCompanies());
            System.out.println("====================> GET ALL COMPANIES");
            System.out.println(adminFacade.getAllCompanies());
            System.out.println("====================> GET SINGLE COMPANY");
            System.out.println(adminFacade.getOneCompany(companyTest.getId()));
            System.out.println("====================> ADD CUSTOMERS");
            adminFacade.addCustomer(new Customer("Deyaa", "Taha",
                    "deyaa@mail", "dey"));
            adminFacade.addCustomer(new Customer("Moshe", "Mosh",
                    "moshe@mail", "mosh"));
            adminFacade.addCustomer(new Customer("Roni", "Ron",
                    "roni@mail", "ron"));
            adminFacade.addCustomer(new Customer("Avi", "Or",
                    "avi@mail", "avi"));
            adminFacade.addCustomer(new Customer("David", "Dov",
                    "david@mail", "dav"));
            adminFacade.addCustomer(new Customer("Saba", "Sabaa",
                    "saba@mail", "sab"));
            System.out.println(adminFacade.getAllCustomers());

            System.out.println("====================> UPDATE CUSTOMER");
            Customer customerTest = adminFacade.getOneCustomer(1);
            customerTest.setFirstName("DeyaaDeyaaTaha");
            adminFacade.updateCustomer(customerTest);
            System.out.println(adminFacade.getOneCustomer(customerTest.getId()));
            System.out.println("====================> DELETE CUSTOMER");
            adminFacade.deleteCustomer(6);
            System.out.println(adminFacade.getAllCustomers());
            System.out.println("====================> GET ALL CUSTOMERS");
            System.out.println(adminFacade.getAllCustomers());
            System.out.println("====================> GET SINGLE CUSTOMER");
            System.out.println(adminFacade.getOneCustomer(customerTest.getId()));

            //3. Test Company login and logic
            System.out.println("==================== C O M P A N Y =============================");
            CompanyFacade companyFacade = (CompanyFacade) loginManager.login("Apple@test.mail", "passApple", ClientType.Company);
            System.out.println("====================> ADD COUPONS");
            Coupon couponTest = new Coupon(companyTest.getId(), Category.Restaurant, "coupon 01", "coupon 01 description",
                    LocalDate.now(), LocalDate.now().plusDays(15), 10, 100, "coupon01.com");
            companyFacade.addCoupon(couponTest);
            companyFacade.addCoupon(new Coupon(companyTest.getId(), Category.Electricity, "coupon02 title", "coupon02 description",
                    LocalDate.now(), LocalDate.now().plusDays(10), 15, 90, "coupon02.com"));
            companyFacade.addCoupon(new Coupon(companyTest.getId(), Category.Restaurant, "coupon03 title", "coupon03 description",
                    LocalDate.now(), LocalDate.now().plusDays(12), 20, 60, "coupon03.com"));
            companyFacade.addCoupon(new Coupon(companyTest.getId(), Category.Vacation, "coupon04 title", "coupon04 description",
                    LocalDate.now(), LocalDate.now().plusDays(14), 5, 70, "coupon04.com"));
            companyFacade.addCoupon(new Coupon(companyTest.getId(), Category.Food, "coupon05 title", "coupon05 description",
                    LocalDate.now(), LocalDate.now().plusDays(18), 25, 50, "coupon05.com"));
            companyFacade.addCoupon(new Coupon(companyTest.getId(), Category.Food, "coupon06 title", "coupon06 description",
                    LocalDate.now(), LocalDate.now().minusDays(1), 25, 50, "coupon06.com"));
            companyFacade.addCoupon(new Coupon(companyTest.getId(), Category.Food, "coupon07 title", "coupon07 description",
                    LocalDate.now(), LocalDate.now().minusDays(1), 25, 50, "coupon07.com"));
            companyFacade.addCoupon(new Coupon(companyTest.getId(), Category.Food, "coupon08 title", "coupon08 description",
                    LocalDate.now(), LocalDate.now().minusDays(1), 25, 50, "coupon08.com"));

            Thread.sleep(5000);

            System.out.println("====================> UPDATE COUPON");
            couponTest.setId(1);
            couponTest.setAmount(250);
            couponTest.setImage("http://www.coupon01.com");
            companyFacade.updateCoupon(couponTest);
            System.out.println("====================> DELETE COUPON");
            companyFacade.deleteCoupon(5);
            System.out.println("====================> GET COMPANY COUPONS BY COMPANY ID");
            //companyFacade.setCompanyId(companyTest.getId());
            System.out.println(companyFacade.getCompanyCoupons());
            System.out.println("====================> GET COMPANY COUPONS BY CATEGORY ID");
            System.out.println(companyFacade.getCompanyCoupons(Category.Electricity));
            System.out.println("====================> GET COMPANY COUPONS BY MAX RPICE");
            System.out.println(companyFacade.getCompanyCoupons(70));
            System.out.println("====================> GET COMPANY COUPON DETAILS");
            System.out.println(companyFacade.getCompanyDetails(couponTest));

            //4. Test Customer login and logic
            System.out.println("==================== C U S T O M E R ===========================");
            CustomerFacade customerFacade = (CustomerFacade) loginManager.login("deyaa@mail", "dey", ClientType.Customer);
            System.out.println("====================> PURCHASE COUPON");
            customerFacade.purchaseCoupon(couponTest);
            System.out.println("====================> GET Coupons purchased by customer");
            System.out.println(customerFacade.getCustomerCoupons());
            System.out.println("====================> GET Coupons purchased by customer from specific category");
            System.out.println(customerFacade.getCustomerCoupons(Category.Restaurant));
            System.out.println("====================> GET Coupons purchased by customer BY MAX PRICE");
            System.out.println(customerFacade.getCustomerCoupons(200));
            System.out.println("====================> GET CUSTOMER DETAILS");
            System.out.println(customerFacade.getCustomerDetails());

            //5. Stop daily task
            System.out.println("==================== STOP DAILY JOB ============================");
            couponExpiredDailyTask.stop(true);
            //6. Clode all db connections
            System.out.println("==================== CLOSE DB CONNECTIONS ======================");
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connectionPool.closeAllConnections();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("END");
    }
}
