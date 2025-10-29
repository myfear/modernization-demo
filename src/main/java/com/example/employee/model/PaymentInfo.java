package com.example.employee.model;

import com.example.employee.model.enums.PaymentMethod;
import java.util.Objects;

public class PaymentInfo {
    private final String accountNumber;
    private final String routingNumber;
    private final String bankName;
    private final PaymentMethod paymentMethod;
    
    public PaymentInfo(String accountNumber, String routingNumber, 
                      String bankName, PaymentMethod paymentMethod) {
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
        this.bankName = bankName;
        this.paymentMethod = paymentMethod;
    }
    
    public String getAccountNumber() { return accountNumber; }
    public String getRoutingNumber() { return routingNumber; }
    public String getBankName() { return bankName; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    
    // Security issue: exposes sensitive data in logs
    @Override
    public String toString() {
        return "PaymentInfo{accountNumber='" + accountNumber + "', bank='" + bankName + "'}";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentInfo that = (PaymentInfo) o;
        return Objects.equals(accountNumber, that.accountNumber) &&
               Objects.equals(routingNumber, that.routingNumber) &&
               Objects.equals(bankName, that.bankName) &&
               paymentMethod == that.paymentMethod;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, routingNumber, bankName, paymentMethod);
    }
}

// Made with Bob
