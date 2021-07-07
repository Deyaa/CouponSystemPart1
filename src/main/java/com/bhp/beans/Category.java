package com.bhp.beans;

import java.util.Arrays;
import java.util.List;

public enum Category {
    Food,
    Electricity,
    Restaurant,
    Vacation;

    public final int value = 1 + ordinal();
}
