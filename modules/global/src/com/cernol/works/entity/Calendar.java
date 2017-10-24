package com.cernol.works.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|isoDate")
@Table(name = "WORKS_CALENDAR")
@Entity(name = "works$Calendar")
public class Calendar extends StandardEntity {
    private static final long serialVersionUID = -3367204030935530177L;

    @Temporal(TemporalType.DATE)
    @Column(name = "THE_DATE", nullable = false, unique = true)
    protected Date theDate;

    @Column(name = "ISO_DATE", nullable = false)
    protected String isoDate;

    @Column(name = "MONTH_NAME", nullable = false)
    protected String monthName;

    @Column(name = "MONTH_NO", nullable = false)
    protected Integer monthNo;

    @Column(name = "YEAR4", nullable = false)
    protected Integer year4;

    @Column(name = "YEAR2", nullable = false)
    protected Integer year2;

    @Column(name = "PERIOD", nullable = false)
    protected String period;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_START", nullable = false)
    protected Date dtStart;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_END", nullable = false)
    protected Date dtEnd;

    public void setDtStart(Date dtStart) {
        this.dtStart = dtStart;
    }

    public Date getDtStart() {
        return dtStart;
    }

    public void setDtEnd(Date dtEnd) {
        this.dtEnd = dtEnd;
    }

    public Date getDtEnd() {
        return dtEnd;
    }


    public void setTheDate(Date theDate) {
        this.theDate = theDate;
    }

    public Date getTheDate() {
        return theDate;
    }

    public void setIsoDate(String isoDate) {
        this.isoDate = isoDate;
    }

    public String getIsoDate() {
        return isoDate;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthNo(Integer monthNo) {
        this.monthNo = monthNo;
    }

    public Integer getMonthNo() {
        return monthNo;
    }

    public void setYear4(Integer year4) {
        this.year4 = year4;
    }

    public Integer getYear4() {
        return year4;
    }

    public void setYear2(Integer year2) {
        this.year2 = year2;
    }

    public Integer getYear2() {
        return year2;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPeriod() {
        return period;
    }


}