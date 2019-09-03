package com.m.gis.springboot.common.utils;

import com.m.gis.springboot.common.exception.GisIllegalParameterCommonException;
import com.m.gis.springboot.common.exception.GisIndexOutOfBoundsCommonException;
import com.m.gis.springboot.common.exception.GisNullParameterCommonException;

/**
 * @Title: Preconditions
 * @Package: springboot.common.utils
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/7
 * @Version: V1.0
 */
public class Preconditions {
    protected Preconditions() {
    }

    /**
     * @Description: check Argument
     * @Author: monkjavaer
     * @Data: 16:43 2018/6/7
     * @Param: [expression]
     * @Throws GisIllegalParameterCommonException
     * @Return void
     */
    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new GisIllegalParameterCommonException();
        }
    }

    /**
     * @Description: check Argument, if false, throw GisIllegalParameterCommonException with message
     * @Author: monkjavaer
     * @Data: 16:44 2018/6/7
     * @Param: [expression, errorMessage]
     * @Throws GisIllegalParameterCommonException
     * @Return void
     */
    public static void checkArgument(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new GisIllegalParameterCommonException(String.valueOf(errorMessage));
        }
    }

    /**
     * @Description: check Argument, if false, throw GisIllegalParameterCommonException with format message
     * @Author: monkjavaer
     * @Data: 16:46 2018/6/7
     * @Param: [expression, errorMessageTemplate, errorMessageArgs]
     * @Throws GisIllegalParameterCommonException
     * @Return void
     */
    public static void checkArgument(boolean expression, String errorMessageTemplate, Object... errorMessageArgs) {
        if (!expression) {
            throw new GisIllegalParameterCommonException(format(errorMessageTemplate, errorMessageArgs));
        }
    }

    /**
     * @Description: check Argument is null or not, if null, throw GisNullParameterCommonException.
     * @Author: monkjavaer
     * @Data: 16:47 2018/6/7
     * @Param: [reference]
     * @Throws GisNullParameterCommonException
     * @Return T
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new GisNullParameterCommonException();
        }
        return reference;
    }

    /**
     * @Description: check Argument is null or not, if null, throw GisNullParameterCommonException with message
     * @Author: monkjavaer
     * @Data: 17:03 2018/6/7
     * @Param: [reference, errorMessage]
     * @Throws GisNullParameterCommonException
     * @Return T
     */
    public static <T> T checkNotNull(T reference, Object errorMessage) {
        if (reference == null) {
            throw new GisNullParameterCommonException(String.valueOf(errorMessage));
        }
        return reference;
    }

    /**
     * @Description: check Argument is null or not, if null, throw GisNullParameterCommonException with format message.
     * @Author: monkjavaer
     * @Data: 17:03 2018/6/7
     * @Param: [reference, errorMessageTemplate, errorMessageArgs]
     * @Throws GisNullParameterCommonException
     * @Return T
     */
    public static <T> T checkNotNull(T reference, String errorMessageTemplate, Object... errorMessageArgs) {
        if (reference == null) {
            throw new GisNullParameterCommonException(format(errorMessageTemplate, errorMessageArgs));
        }
        return reference;
    }


    /**
     * @Description: check element index, if out of bounds, throw exceptions.
     * @Author: monkjavaer
     * @Data: 17:04 2018/6/7
     * @Param: [index, size]
     * @Throws [GisIndexOutOfBoundsCommonException, GisIllegalParameterCommonException]
     * @Return int
     */
    public static int checkElementIndex(int index, int size) {
        return checkElementIndex(index, size, "index");
    }

    /**
     * @Description: check element index, if out of bounds, throw exceptions with message
     * @Author: monkjavaer
     * @Data: 17:04 2018/6/7
     * @Param: [index, size, desc]
     * @Throws [GisIndexOutOfBoundsCommonException, GisIllegalParameterCommonException]
     * @Return int
     */
    public static int checkElementIndex(int index, int size, String desc) {
        // Carefully optimized for execution by hotspot (explanatory commentabove)
        if (index < 0 || index >= size) {
            throw new GisIndexOutOfBoundsCommonException(badElementIndex(index, size, desc));
        }
        return index;
    }

    /**
     * @Description: bad element index, returns format message.
     * @Author: monkjavaer
     * @Data: 17:05 2018/6/7
     * @Param: [index, size, desc]
     * @Throws GisIllegalParameterCommonException
     * @Return java.lang.String
     */
    private static String badElementIndex(int index, int size, String desc) {
        if (index < 0) {
            return format("%s (%s) must not be negative", desc, index);
        } else if (size < 0) {
            throw new GisIllegalParameterCommonException("negative size: " + size);
        } else { // index >= size
            return format("%s (%s) must be less than size (%s)", desc, index, size);
        }
    }

    /**
     * @Description: check position index, if out of index, throw exception
     * @Author: monkjavaer
     * @Data: 17:19 2018/6/7
     * @Param: [index, size]
     * @Throws [GisIndexOutOfBoundsCommonException, GisIllegalParameterCommonException]
     * @Return int
     */
    public static int checkPositionIndex(int index, int size) {
        return checkPositionIndex(index, size, "index");
    }

    /**
     * @Description: check position index, if out of index, throw exception with message.
     * @Author: monkjavaer
     * @Data: 17:19 2018/6/7
     * @Param: [index, size, desc]
     * @Throws [GisIndexOutOfBoundsCommonException, GisIllegalParameterCommonException]
     * @Return int
     */
    public static int checkPositionIndex(int index, int size, String desc) {
        if (index < 0 || index > size) {
            throw new GisIndexOutOfBoundsCommonException(badPositionIndex(index, size, desc));
        }
        return index;
    }

    /**
     * @Description: bad position index handler, returns exception message.
     * @Author: monkjavaer
     * @Data: 17:21 2018/6/7
     * @Param: [index, size, desc]
     * @Throws GisIllegalParameterCommonException
     * @Return java.lang.String
     */
    private static String badPositionIndex(int index, int size, String desc) {
        if (index < 0) {
            return format("%s (%s) must not be negative", desc, index);
        } else if (size < 0) {
            throw new GisIllegalParameterCommonException("negative size: " + size);
        } else { // index > size
            return format("%s (%s) must not be greater than size (%s)", desc, index, size);
        }
    }

    /**
     * @Description: check position index, if out of index, throw exception.
     * @Author: monkjavaer
     * @Data: 17:22 2018/6/7
     * @Param: [start, end, size]
     * @Throws [GisIndexOutOfBoundsCommonException, GisIllegalParameterCommonException]
     * @Return void
     */
    public static void checkPositionIndexes(int start, int end, int size) {
        if (start < 0 || end < start || end > size) {
            throw new GisIndexOutOfBoundsCommonException(badPositionIndexes(start, end, size));
        }
    }

    /**
     * @Description: bad position index handler, returns exception message.
     * @Author: monkjavaer
     * @Data: 17:23 2018/6/7
     * @Param: [start, end, size]
     * @Throws GisIllegalParameterCommonException
     * @Return java.lang.String
     */
    private static String badPositionIndexes(int start, int end, int size) {
        if (start < 0 || start > size) {
            return badPositionIndex(start, size, "start index");
        }
        if (end < 0 || end > size) {
            return badPositionIndex(end, size, "end index");
        }
        // end < start
        return format("end index (%s) must not be less than start index(%s)", end, start);
    }


    /**
     * @Description: format message with template.
     * @Author: monkjavaer
     * @Data: 17:18 2018/6/7
     * @Param: [template, args]
     * @Throws
     * @Return java.lang.String
     */
    private static String format(String template, Object... args) {
        StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
        int templateStart = 0;
        int i = 0;
        while (i < args.length) {
            int placeholderStart = template.indexOf("%s", templateStart);
            if (placeholderStart == -1) {
                break;
            }
            builder.append(template.substring(templateStart, placeholderStart));
            builder.append(args[i++]);
            templateStart = placeholderStart + 2;
        }
        builder.append(template.substring(templateStart));
        // if we run out of placeholders, append the extra args in square braces
        if (i < args.length) {
            builder.append(" [");
            builder.append(args[i++]);
            while (i < args.length) {
                builder.append(", ");
                builder.append(args[i++]);
            }
            builder.append("]");
        }
        return builder.toString();
    }
}