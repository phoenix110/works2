package com.cernol.works.entity;

import javax.persistence.Entity;
import javax.persistence.Column;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import java.util.List;
import javax.persistence.OneToMany;
import com.haulmont.cuba.core.entity.annotation.Listeners;

@Listeners("works_SalesOrderEntityListener")
@NamePattern("%s %s|documentNo,description")
@Entity(name = "works$SalesOrder")
public class SalesOrder extends Order {
    private static final long serialVersionUID = 4697506411283964946L;

    @Column(name = "INVOICE_NO")
    protected String invoiceNo;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "salesOrder")
    protected List<SalesOrderContainer> salesOrderContainers;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "salesOrder")
    protected List<SalesOrderRawMaterial> salesOrderRawMaterials;

    public void setSalesOrderContainers(List<SalesOrderContainer> salesOrderContainers) {
        this.salesOrderContainers = salesOrderContainers;
    }

    public List<SalesOrderContainer> getSalesOrderContainers() {
        return salesOrderContainers;
    }

    public void setSalesOrderRawMaterials(List<SalesOrderRawMaterial> salesOrderRawMaterials) {
        this.salesOrderRawMaterials = salesOrderRawMaterials;
    }

    public List<SalesOrderRawMaterial> getSalesOrderRawMaterials() {
        return salesOrderRawMaterials;
    }


    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }


}