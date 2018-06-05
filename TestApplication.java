package demo.mytest;

import java.math.BigDecimal;
import java.util.HashMap;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import demo.beans.*;
import demo.repo.*;
import demo.service.*;

public class TestApplication {
    
	WalletService walletService;
	WalletRepo walletRepo;
	Customer customer;

    @Before
    public  void setUp() {
     walletRepo = new WalletRepoImpl(new HashMap<String,Customer>());
	 walletService = new WalletServiceImpl(walletRepo);
	 customer = new Customer();
	}
        
    @Test
    public void TestCreateAccount()
    {
    	String name= "Bharati";
    	String mobile= "9898989898";
    	walletService = new WalletServiceImpl(walletRepo);
    	BigDecimal amount = new BigDecimal(12000.00);
    	customer.setName(name);
    	customer.setMobileNo(mobile);
    	walletService.createAccount(name, mobile, amount);
    	Assert.assertEquals(name,customer.getName());
    	Assert.assertEquals(mobile,customer.getMobileNo());
    	Assert.assertEquals(amount,customer.getWallet().getBalance());
    }
    
    @Test(expected = NullPointerException.class)
    public void TestCreateAccount_Name_Null()
    {
    	walletService = new WalletServiceImpl(walletRepo);
    	String name= null;
    	String mobile = null;
    	BigDecimal amount = null;
    	walletService.createAccount(name, mobile, amount);
    	Assert.assertEquals(name,customer.getName());
    	Assert.assertEquals(mobile,customer.getMobileNo());
    	Assert.assertEquals(amount,customer.getWallet().getBalance());
    	//Or...
    	Assert.assertNull(customer.getName());
    	Assert.assertNull(customer.getMobileNo());
    	Assert.assertNull(customer.getWallet().getBalance());
    }
    
    
    
    @Test
    public void TestShowBalance()
    {
    	walletService = new WalletServiceImpl(walletRepo);
    	String mobileNo= "9898989898";
    	Customer cust = walletRepo.findOne(mobileNo);
    	if(cust.getMobileNo()!=null)
    	walletService.showBalance(cust.getMobileNo());    	
    	Assert.assertEquals(mobileNo,customer.getMobileNo());    	
    }

    @Test(expected = NullPointerException.class)
    public void TestShowBalance_Null_Value()
    {
    	String mobileNo= null;
    	customer.setMobileNo(mobileNo);
    	walletService = new WalletServiceImpl(walletRepo);   
    	if(customer.getMobileNo().equals(null))
    	walletService.showBalance(mobileNo);    	
    	Assert.assertNull(customer.getMobileNo());    	
    }
    
    
    @Test
    public void TestFundTransfer()
    {
    	walletService = new WalletServiceImpl(walletRepo);
    	String source_MobileNo = "9890909090";
    	String trg_MobileNo = "9898989898";
    	BigDecimal amount = new BigDecimal(1500.00);
    	Customer cust_Src_Mobile, cust_Target_Mobile;
    	
    	cust_Src_Mobile = walletRepo.findOne(source_MobileNo);
    	cust_Target_Mobile = walletRepo.findOne(trg_MobileNo);
    	
    	if(cust_Src_Mobile != null && trg_MobileNo != null)
    	walletService.fundTransfer(cust_Src_Mobile.toString(),cust_Target_Mobile.toString() ,amount);	
    	Assert.assertEquals(cust_Src_Mobile,customer.getMobileNo());
    	Assert.assertEquals(cust_Target_Mobile,customer.getMobileNo());
    	Assert.assertEquals(amount,customer.getWallet().getBalance());
    }
    
    @SuppressWarnings("null")
	@Test(expected = NullPointerException.class)
    public void TestFundTransfer_Null_Value()
    {
    	walletService = new WalletServiceImpl(walletRepo);
    	String source_MobileNo =null;
    	String trg_MobileNo = null;
    	BigDecimal amount = new BigDecimal(1500.00);
    	
    	Customer cust_Src_Mobile = walletRepo.findOne(source_MobileNo);
    	Customer cust_Target_Mobile = walletRepo.findOne(trg_MobileNo);
    	
    	if(cust_Src_Mobile.equals(null) && trg_MobileNo.equals(null))
    	walletService.fundTransfer(cust_Src_Mobile.toString(),cust_Target_Mobile.toString() ,amount);	
    	//Assert.assertEquals(cust_Src_Mobile,customer.getMobileNo());
    	//Assert.assertEquals(cust_Target_Mobile,customer.getMobileNo());
    	Assert.assertEquals(amount,customer.getWallet().getBalance());
    	Assert.assertNull(customer.getMobileNo());  //Just Added
    }
    
    
    @Test
    public void TestDepositAmount()
    {
    	walletService = new WalletServiceImpl(walletRepo);
    	String mobileNo= "9988998899";
    	BigDecimal amount = new BigDecimal(2500.00);
    	Customer cust = walletRepo.findOne(mobileNo);
    	if(cust.getMobileNo()!=null)
    	walletService.depositAmount(cust.getMobileNo(), amount);
    	Assert.assertEquals(mobileNo,customer.getMobileNo());    	
    }
    
    @Test(expected = NullPointerException.class)
    public void TestDepositAmount_Null_Value()
    {
    	walletService = new WalletServiceImpl(walletRepo);
    	String mobileNo= null;
    	BigDecimal amount = new BigDecimal(0);
    	Customer cust = walletRepo.findOne(mobileNo);
    	if(cust.getMobileNo().equals(null))
    	walletService.depositAmount(cust.getMobileNo(), amount);
    	Assert.assertNull(customer.getMobileNo());
    	Assert.assertEquals(0,amount);
    }
    
    @Test
    public void TestDepositAmount_Validation()
    {
    	walletService = new WalletServiceImpl(walletRepo);
    	String mobileNo= "9899889988";
    	BigDecimal amount = new BigDecimal(2500.00);
    	Customer cust = walletRepo.findOne(mobileNo);
    	
    	if(cust.getMobileNo()!=null && mobileNo.matches("\\d{10}") && amount.byteValue()>1000)
    	walletService.depositAmount(cust.getMobileNo(), amount);
    	Assert.assertEquals(mobileNo, customer.getMobileNo());
    	Assert.assertEquals(amount, customer.getWallet().getBalance());
    }
    
    @After
    public  void tearDown() {
     walletRepo = null;
	 walletService = null;
	 customer = null;
	}
    
    
}

