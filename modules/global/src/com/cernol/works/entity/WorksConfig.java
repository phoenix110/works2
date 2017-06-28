package com.cernol.works.entity;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.defaults.Default;
import com.haulmont.cuba.core.config.defaults.DefaultInteger;


/**
 * Created by skopp on 27/06/2017.
 */
@Source(type = SourceType.DATABASE)
public interface WorksConfig extends Config {

    @Property("works.orderOverhead")
    @DefaultInteger(0)
    Integer getOrderOverhead();

    @Property("works.decantingOverhead")
    @DefaultInteger(0)
    Integer getDecantingOverhead();

}
