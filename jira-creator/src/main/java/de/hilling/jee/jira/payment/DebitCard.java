package de.hilling.jee.jira.payment;

@Debit
public class DebitCard implements PaymentService {
    @Override
    public void pay(int i) {
        System.err.println("done");
    }
}
