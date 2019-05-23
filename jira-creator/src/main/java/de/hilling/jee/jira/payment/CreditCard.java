package de.hilling.jee.jira.payment;

@Credit
public class CreditCard implements PaymentService {
    @Override
    public void pay(int i) {
        throw new RuntimeException("no money");
    }
}
