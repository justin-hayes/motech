package org.motechproject.scheduletracking.api.domain;

import org.joda.time.MutablePeriod;
import org.joda.time.Period;
import org.joda.time.ReadWritablePeriod;
import org.joda.time.format.PeriodFormatterBuilder;
import org.joda.time.format.PeriodParser;
import org.motechproject.scheduletracking.api.domain.exception.InvalidScheduleDefinitionException;
import org.motechproject.scheduletracking.api.domain.json.AlertRecord;
import org.motechproject.scheduletracking.api.domain.json.MilestoneRecord;
import org.motechproject.scheduletracking.api.domain.json.ScheduleRecord;
import org.motechproject.scheduletracking.api.domain.json.ScheduleWindowsRecord;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduleFactory {

    PeriodParser yearParser = new PeriodFormatterBuilder()
        .appendYears()
        .appendSuffix(" year", " years")
        .toParser();
    PeriodParser monthParser = new PeriodFormatterBuilder()
        .appendMonths()
        .appendSuffix(" month", " months")
        .toParser();
    PeriodParser weekParser = new PeriodFormatterBuilder()
        .appendWeeks()
        .appendSuffix(" week", " weeks")
        .toParser();
    PeriodParser dayParser = new PeriodFormatterBuilder()
        .appendDays()
        .appendSuffix(" day", " days")
        .toParser();

    public Schedule build(ScheduleRecord scheduleRecord) {
        Schedule schedule = new Schedule(scheduleRecord.name());
        int alertIndex = 0;
        for (MilestoneRecord milestoneRecord : scheduleRecord.milestoneRecords()) {
            ScheduleWindowsRecord windowsRecord = milestoneRecord.scheduleWindowsRecord();
            List<String> earliestValue = windowsRecord.earliest();
            List<String> dueValue = windowsRecord.due();
            if (dueValue.isEmpty())
                dueValue = earliestValue;
            List<String> lateValue = windowsRecord.late();
            if (lateValue.isEmpty())
                lateValue = dueValue;
            List<String> maxValue = windowsRecord.max();
            if (maxValue.isEmpty())
                maxValue = lateValue;

            Period earliest = getWindowPeriod(earliestValue);
            Period due = getWindowPeriod(dueValue).minus(earliest);
            Period late = getWindowPeriod(lateValue).minus(earliest.plus(due));
            Period max = getWindowPeriod(maxValue).minus(earliest.plus(due).plus(late));

            Milestone milestone = new Milestone(milestoneRecord.name(), earliest, due, late, max);
            milestone.setData(milestoneRecord.data());
            for (AlertRecord alertRecord : milestoneRecord.alerts()) {
                String offset = alertRecord.offset();
                if (offset == null || offset.isEmpty())
                    throw new InvalidScheduleDefinitionException("alert needs an offset parameter.");
                milestone.addAlert(WindowName.valueOf(alertRecord.window()), new Alert(parse(offset), parse(alertRecord.interval()), Integer.parseInt(alertRecord.count()), alertIndex++));
            }
            schedule.addMilestones(milestone);

            schedule.isBasedOnAbsoluteWindows(scheduleRecord.hasAbsoluteWindows());
        }
        return schedule;
    }

    private Period getWindowPeriod(List<String> readableValues) {
        ReadWritablePeriod period = new MutablePeriod();
        for (String s : readableValues)
            period.add(parse(s));
        return period.toPeriod();
    }

    private Period parse(String s) {
        ReadWritablePeriod period = new MutablePeriod();
        if (yearParser.parseInto(period, s, 0, null) > 0)
            return period.toPeriod();
        if (monthParser.parseInto(period, s, 0, null) > 0)
            return period.toPeriod();
        if (weekParser.parseInto(period, s, 0, null) > 0)
            return period.toPeriod();
        if (dayParser.parseInto(period, s, 0, null) > 0)
            return period.toPeriod();
        return period.toPeriod();
    }
}
