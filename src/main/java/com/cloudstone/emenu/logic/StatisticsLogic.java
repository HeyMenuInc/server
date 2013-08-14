
package com.cloudstone.emenu.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.data.Bill;
import com.cloudstone.emenu.data.DailyStat;
import com.cloudstone.emenu.exception.BadRequestError;
import com.cloudstone.emenu.exception.ServerError;
import com.cloudstone.emenu.storage.db.IStatDb;
import com.cloudstone.emenu.util.UnitUtils;

@Component
public class StatisticsLogic extends BaseLogic {

    @Autowired
    private IStatDb statDb;

    @Autowired
    private OrderLogic orderLogic;

    @Autowired
    private TableLogic tableLogic;

    public DailyStat getDailyStat(long time) {
        if (time <= 0)
            throw new BadRequestError();
        // long offset = Calendar.getInstance().getTimeZone().getRawOffset();

        long requestDay = (long) (time / UnitUtils.DAY);

        DailyStat dailyStat = null;
        long currentDay = System.currentTimeMillis();
        if (requestDay == currentDay) {
            dailyStat = computeDailyStat(time);
            DailyStat oldStat = statDb.get(requestDay);
            if (oldStat != null) {
                dailyStat.setUpdateTime(System.currentTimeMillis());
                statDb.update(dailyStat);
            } else {
                dailyStat.setCreatedTime(System.currentTimeMillis());
                dailyStat.setUpdateTime(System.currentTimeMillis());
                statDb.add(dailyStat);
            }
        } else {
            dailyStat = statDb.get(requestDay);
            if (dailyStat == null) {
                dailyStat = computeDailyStat(time);
                dailyStat.setCreatedTime(System.currentTimeMillis());
                dailyStat.setUpdateTime(System.currentTimeMillis());
                statDb.add(dailyStat);
            }
        }

        return dailyStat;
    }

    private DailyStat computeDailyStat(long time) {

        DailyStat dailyStat = new DailyStat();
        // TIME
        dailyStat.setTime(System.currentTimeMillis() / UnitUtils.DAY);
        // INCOME
        List<Bill> bills = orderLogic.getDailyBills(time);
        double income = 0;
        for (Bill bill : bills) {
            income += bill.getCost();
        }
        dailyStat.setIncome(income);
        // TABLECOUNT
        dailyStat.setTableCount(bills.size());
        // CUSTOMERCOUNT
        int customers = 0;
        for (Bill bill : bills) {
            customers += bill.getOrder().getCustomerNumber();
        }
        dailyStat.setCustomerCount(customers);
        // TABLERATE
        int totalTables = tableLogic.getAll().size();
        double rate = 0;
        if (0 != totalTables) {
            rate = (double) bills.size() / (double) totalTables;
        } else {
            // throw new ServerError("Total tables is zero");
        }
        dailyStat.setTableRate(rate);
        return dailyStat;
    }

}
