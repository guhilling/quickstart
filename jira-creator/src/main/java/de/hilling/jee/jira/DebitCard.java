package de.hilling.jee.jira;

@Debit
public class DebitCard implements PaymentService {
    @Override
    public void pay(int i) {
        System.err.println("done");
    }
}
