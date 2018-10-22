package com.github.fangming.springboot.jdbc.transaction;

import com.google.common.collect.Maps;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.Map;

public interface STransactionDefinition {


    public enum TransactionPropagation{
        PROPAGATION_REQUIRED,
        PROPAGATION_SUPPORTS,
        PROPAGATION_MANDATORY,
        PROPAGATION_REQUIRES_NEW,
        PROPAGATION_NOT_SUPPORTED,
        PROPAGATION_NEVER
    }

    public enum TransactionIsolationLevel {
        ISOLATION_DEFAULT(-1),
        ISOLATION_NONE(0),
        ISOLATION_READ_UNCOMMITTED(1),
        ISOLATION_READ_COMMITTED(2),
        ISOLATION_REPEATABLE_READ(4),
        ISOLATION_SERIALIZABLE(8);

        private final int value;

        static Map<Integer, TransactionIsolationLevel> dataMap = Maps.newHashMap();
        static  {
            dataMap = Maps.uniqueIndex(Arrays.asList(TransactionIsolationLevel.values())
                , TransactionIsolationLevel::getValue);
        }

        TransactionIsolationLevel(int value){
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static TransactionIsolationLevel getByLevel(int value){
            return dataMap.get(value);
        }
    }

    int TIMEOUT_DEFAULT = -1;

    TransactionPropagation getPropagationBehavior();

    TransactionIsolationLevel getIsolationLevel();

    int getTimeout();

    boolean isReadOnly();

    @Nullable
    String getName();
}
