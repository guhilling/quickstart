package de.hilling.jee.jira;

import de.hilling.jee.jira.payment.Credit;
import de.hilling.jee.jira.payment.Debit;
import de.hilling.jee.jira.payment.PaymentService;
import de.hilling.junit.cdi.CdiTestJunitExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.enterprise.inject.Instance;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import java.lang.annotation.Annotation;

@ExtendWith(CdiTestJunitExtension.class)
class DynamicResolverTest {

    @Inject
    private Instance<PaymentService> paymentServices;

    Annotation creditAnnotation = new AnnotationLiteral<Credit> (){
    };
    Annotation debitAnnotation = new AnnotationLiteral<Debit> (){
    };

    @Test
    void selectCreditService() {
        Assertions.assertThrows(RuntimeException.class, () -> paymentServices.select(creditAnnotation)
                                                                             .get()
                                                                             .pay(4), "no money");
    }
    @Test
    void selectDebitService() {
        paymentServices.select(debitAnnotation)
                       .get()
                       .pay(4);
    }
}
