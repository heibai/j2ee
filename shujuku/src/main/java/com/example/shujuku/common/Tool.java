package com.example.shujuku.common;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

public class Tool {

    @SuppressWarnings("rawtypes")
    public static boolean isPresent(Object obj) {
        if (null == obj) {
            return false;
        }
        else if (obj instanceof CharSequence) {
            // 字符串比较特殊，如果是空字符串也不行
            return isNotBlank((CharSequence) obj);
        }
        return true;
    }

    public static boolean isBlank(CharSequence str) {
        int length;
        if (str != null && (length = str.length()) != 0) {
            for(int i = 0; i < length; ++i) {
                if (!isBlankChar(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(CharSequence str) {
        return !isBlank(str);
    }

    public static boolean isBlankChar(char c) {
        return isBlankChar((int)c);
    }

    public static boolean isBlankChar(int c) {
        return Character.isWhitespace(c) || Character.isSpaceChar(c) || c == 65279 || c == 8234 || c == 0;
    }
}
