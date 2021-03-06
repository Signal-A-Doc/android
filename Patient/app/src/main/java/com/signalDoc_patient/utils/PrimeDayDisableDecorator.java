package com.signalDoc_patient.utils;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public class PrimeDayDisableDecorator implements DayViewDecorator {

    @Override public boolean shouldDecorate(final CalendarDay day) {
      return PRIME_TABLE[day.getDay()];
    }

    @Override public void decorate(final DayViewFacade view) {
      view.setDaysDisabled(true);
    }

    private static boolean[] PRIME_TABLE = {
        false,  // 0?
        false,
        true, // 2
        true, // 3
        false,
        true, // 5
        false,
        true, // 7
        false,
        false,
        false,
        true, // 11
        false,
        true, // 13
        false,
        false,
        false,
        true, // 17
        false,
        true, // 19
        false,
        false,
        false,
        true, // 23
        false,
        false,
        false,
        false,
        false,
        true, // 29
        false,
        true, // 31
        false,
        false,
        false, //PADDING
    };
  }