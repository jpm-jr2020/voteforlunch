package com.herokuapp.voteforlunch.web;

import com.herokuapp.voteforlunch.to.DishTo;

import static com.herokuapp.voteforlunch.model.AbstractEntity.START_SEQ;

public class DishTestData {
    public static TestMatcher<DishTo> DISH_MATCHER = TestMatcher.usingFieldsComparator(DishTo.class);
    public static TestMatcher<DishTo> DISH_TO_MATCHER = TestMatcher.usingFieldsComparator(DishTo.class);

    public static final long DISH_HI_YESTERDAY1_ID = START_SEQ + 37;
    public static final long DISH_HI_YESTERDAY2_ID = START_SEQ + 38;
    public static final long DISH_HI_TODAY1_ID = START_SEQ + 39;
    public static final long DISH_HI_TODAY2_ID = START_SEQ + 40;
    public static final long DISH_HI_TODAY3_ID = START_SEQ + 41;
    public static final long DISH_HI_TOMORROW1_ID = START_SEQ + 42;
    public static final long DISH_HI_TOMORROW2_ID = START_SEQ + 43;

    public static final DishTo DISH_HI_YESTERDAY1 = new DishTo(DISH_HI_YESTERDAY1_ID, "Водка", 14000);
    public static final DishTo DISH_HI_YESTERDAY2 = new DishTo(DISH_HI_YESTERDAY2_ID, "Хинкали", 5000);
    public static final DishTo DISH_HI_TODAY1 = new DishTo(DISH_HI_TODAY1_ID, "Компот", 6000);
    public static final DishTo DISH_HI_TODAY2 = new DishTo(DISH_HI_TODAY2_ID, "Хачапури", 35000);
    public static final DishTo DISH_HI_TODAY3 = new DishTo(DISH_HI_TODAY3_ID, "Сациви", 12000);
    public static final DishTo DISH_HI_TOMORROW1 = new DishTo(DISH_HI_TOMORROW1_ID, "Вино", 15000);
    public static final DishTo DISH_HI_TOMORROW2 = new DishTo(DISH_HI_TOMORROW2_ID, "Пицца-дзе", 40000);

    public static DishTo getNew() {
        return new DishTo(null, "Новое блюдо", 12345);
    }

    public static DishTo getNewWithId(long id) {
        return new DishTo(id, "Новое блюдо", 12345);
    }

    public static DishTo getNewWithInvalidName() {
        return new DishTo(null, "A", 12345);
    }

    public static DishTo getNewWithInvalidPrice() {
        return new DishTo(null, "Новое блюдо", -1);
    }

    public static DishTo getNewDuplicated() {
        return new DishTo(null, DISH_HI_TOMORROW1.getName(), 12345);
    }

    public static DishTo getUpdated() {
        return new DishTo(DISH_HI_TODAY2_ID, "Хачапури с сыром", 37000);
    }

    public static DishTo getUpdatedWithInvalidName() {
        return new DishTo(DISH_HI_TODAY2_ID, "A", 37000);
    }

    public static DishTo getUpdatedWithInvalidPrice() {
        return new DishTo(DISH_HI_TODAY2_ID, "Хачапури с сыром", -1);
    }

    public static DishTo getUpdatedWithDifferentId() {
        return new DishTo(1L, "Хачапури с сыром", 37000);
    }

    public static DishTo getUpdatedWithNullId() {
        return new DishTo(null, "Хачапури с сыром", 37000);
    }

    public static DishTo getUpdatedDuplicated() {
        return new DishTo(DISH_HI_TODAY2_ID, DISH_HI_TODAY1.getName(), 12345);
    }

}
