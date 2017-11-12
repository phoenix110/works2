package com.cernol.works.core.jmx;

import com.cernol.works.entity.Calendar;
import com.cernol.works.service.ToolsService;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.security.app.Authenticated;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static java.lang.Integer.valueOf;

@Component("works_CalendarsMBean")
public class Calendars implements CalendarsMBean {

    private Logger log = LoggerFactory.getLogger(Calendars.class);

    @Inject
    private Persistence persistence;

    @Inject
    private Metadata metadata;

    @Inject
    private ToolsService toolsService;


    @Override
    @Authenticated
    public String createOrUpdateCalendarsSince(Date sinceDate, Date untilDate) {

        try {

            List<Calendar> calendarList;

            LocalDate sinceLocalDate = toolsService.asLocalDate(sinceDate);
            LocalDate untilLocalDate = toolsService.asLocalDate(untilDate);

            DateTimeFormatter periodFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
            DateTimeFormatter year2Formatter = DateTimeFormatter.ofPattern("yy");

            try (Transaction tx = persistence.createTransaction()) {


                while (!sinceLocalDate.isAfter(untilLocalDate)) {
                    TypedQuery<Calendar> query = persistence.getEntityManager().createQuery(
                            "select e from works$Calendar e where ?1 between e.dtStart and e.dtEnd",
                            Calendar.class);

                    query.setParameter(1, toolsService.asDate(sinceLocalDate));

                    calendarList = query.getResultList();

                    if (calendarList.size() == 0) {

                        Calendar calendar = metadata.create(Calendar.class);

                        calendar.setTheDate(toolsService.asDate(sinceLocalDate));
                        calendar.setIsoDate(sinceLocalDate.toString());
                        calendar.setMonthName(sinceLocalDate.getMonth().toString());
                        calendar.setMonthNo(sinceLocalDate.getMonthValue());
                        calendar.setYear4(sinceLocalDate.getYear());
                        calendar.setYear2(valueOf(sinceLocalDate.format(year2Formatter)));
                        calendar.setPeriod(sinceLocalDate.format(periodFormatter));
                        calendar.setDtStart(toolsService.beginOfDay(toolsService.asDate(sinceLocalDate)));
                        calendar.setDtEnd(toolsService.endOfDay(toolsService.asDate(sinceLocalDate)));

                        persistence.getEntityManager().persist(calendar);


                    } else if (calendarList.size() == 1) {
                        Calendar calendar = calendarList.get(0);

                        calendar.setTheDate(toolsService.asDate(sinceLocalDate));
                        calendar.setIsoDate(sinceLocalDate.toString());
                        calendar.setMonthName(sinceLocalDate.getMonth().toString());
                        calendar.setMonthNo(sinceLocalDate.getMonthValue());
                        calendar.setYear4(sinceLocalDate.getYear());
                        calendar.setYear2(valueOf(sinceLocalDate.format(year2Formatter)));
                        calendar.setPeriod(sinceLocalDate.format(periodFormatter));
                        calendar.setDtStart(toolsService.beginOfDay(toolsService.asDate(sinceLocalDate)));
                        calendar.setDtEnd(toolsService.endOfDay(toolsService.asDate(sinceLocalDate)));

                    } else {
                        throw new IllegalStateException("More than one Calendar entry for " + sinceLocalDate.toString());
                    }

                    log.info("Calendar update for " + sinceLocalDate.toString());
                    sinceLocalDate = sinceLocalDate.plusDays(1);
                }

                tx.commit();
            }
            return ("Updated calendar entries between " + sinceDate.toString() + " and " + untilLocalDate.toString());
        } catch (Throwable e) {
            log.error("Error calculating usages", e);
            return ExceptionUtils.getFullStackTrace(e);
        }
    }

}
