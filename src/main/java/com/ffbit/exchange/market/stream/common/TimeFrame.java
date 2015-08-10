package com.ffbit.exchange.market.stream.common;

import java.time.DayOfWeek;
import java.time.OffsetDateTime;

public enum TimeFrame {
    M1 {
        @Override
        public OffsetDateTime adjust(OffsetDateTime dateTime) {
            return dateTime.withNano(0).withSecond(0);
        }
    },
    M2 {
        @Override
        public OffsetDateTime adjust(OffsetDateTime dateTime) {
            return M1.adjust(dateTime)
                    .withMinute(scale(dateTime.getMinute(), 2));
        }
    },
    M3 {
        @Override
        public OffsetDateTime adjust(OffsetDateTime dateTime) {
            return M1.adjust(dateTime)
                    .withMinute(scale(dateTime.getMinute(), 3));
        }
    },
    M4 {
        @Override
        public OffsetDateTime adjust(OffsetDateTime dateTime) {
            return M1.adjust(dateTime)
                    .withMinute(scale(dateTime.getMinute(), 4));
        }
    },
    M5 {
        @Override
        public OffsetDateTime adjust(OffsetDateTime dateTime) {
            return M1.adjust(dateTime)
                    .withMinute(scale(dateTime.getMinute(), 5));
        }
    },
    M6 {
        @Override
        public OffsetDateTime adjust(OffsetDateTime dateTime) {
            return M1.adjust(dateTime)
                    .withMinute(scale(dateTime.getMinute(), 6));
        }
    },
    M10 {
        @Override
        public OffsetDateTime adjust(OffsetDateTime dateTime) {
            return M1.adjust(dateTime)
                    .withMinute(scale(dateTime.getMinute(), 10));
        }
    },
    M15 {
        @Override
        public OffsetDateTime adjust(OffsetDateTime dateTime) {
            return M1.adjust(dateTime)
                    .withMinute(scale(dateTime.getMinute(), 15));
        }
    },
    M30 {
        @Override
        public OffsetDateTime adjust(OffsetDateTime dateTime) {
            return M1.adjust(dateTime)
                    .withMinute(scale(dateTime.getMinute(), 30));
        }
    },
    H1 {
        @Override
        public OffsetDateTime adjust(OffsetDateTime dateTime) {
            return M1.adjust(dateTime).withMinute(0);
        }
    },
    H4 {
        @Override
        public OffsetDateTime adjust(OffsetDateTime dateTime) {
            return H1.adjust(dateTime)
                    .withHour(scale(dateTime.getHour(), 4));
        }
    },
    D1 {
        @Override
        public OffsetDateTime adjust(OffsetDateTime dateTime) {
            return H1.adjust(dateTime).withHour(0);
        }
    },
    W1 {
        @Override
        public OffsetDateTime adjust(OffsetDateTime dateTime) {
            return D1.adjust(dateTime).with(DayOfWeek.MONDAY);
        }
    },
    MN {
        @Override
        public OffsetDateTime adjust(OffsetDateTime dateTime) {
            return D1.adjust(dateTime).withDayOfMonth(1);
        }
    };

    private static int scale(int value, int factor) {
        return (value / factor) * factor;
    }

    public abstract OffsetDateTime adjust(OffsetDateTime dateTime);

}
