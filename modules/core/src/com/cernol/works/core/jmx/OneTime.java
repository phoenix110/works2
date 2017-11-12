package com.cernol.works.core.jmx;

import com.cernol.works.entity.Lable;
import com.cernol.works.entity.Product;
import com.cernol.works.entity.ProductContainer;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.security.app.Authenticated;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Component("works_OneTimeMBean")
public class OneTime implements OneTimeMBean {

    private Logger log = LoggerFactory.getLogger(OneTime.class);

    @Inject
    private Persistence persistence;

    @Override
    @Authenticated
    public String updateAllDefaultLabels() {
        try {

            List<Product> productList;
            List<ProductContainer> productContainerList;

            try (Transaction tx = persistence.createTransaction()) {

                TypedQuery<Product> productTypedQuery = persistence.getEntityManager().createQuery(
                        "select p from works$Product p " +
                                "where p.deleteTs is null",
                        Product.class);

                productList = productTypedQuery.getResultList();

                for (Product product : productList) {
                    productContainerList = product.getContainers();

                    for (ProductContainer productContainer : productContainerList) {
                        if (product.getIsCorrosive()) {
                            productContainer.setCorrosiveLabel(getLabel("LCorrosive"));
                        }

                        if (product.getIsFlammable()) {
                            productContainer.setFlammableLabel(getLabel("LFlammable"));
                        }

                        if (product.getIsPoisonous()) {
                            productContainer.setPoisonousLabel(getLabel("LPoisonous"));
                        }

                        if (product.getKeepAway()) {
                            productContainer.setKeepAwayLabel(getLabel("LKeepAway"));
                        }

                        productContainer.setProductLabel(getLabel("LProduct"));
                    }

                }

                tx.commit();
            }

            return ("Updated default labels");
        }
        catch (Throwable e) {
            log.error("Error updating default labels ", e);
            return ExceptionUtils.getFullStackTrace(e);
        }
    }

    private Lable getLabel(String labelCode) {

        TypedQuery<Lable> lableTypedQuery = persistence.getEntityManager().createQuery(
                "select l from works$Lable l where l.code = ?1", Lable.class);

        lableTypedQuery.setParameter(1, labelCode);

        Lable label = lableTypedQuery.getFirstResult();

        return label;
    }
}
