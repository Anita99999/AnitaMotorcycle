package com.anita.anitamotorcycle.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.anita.anitamotorcycle.helps.MotorHelper;
import com.anita.anitamotorcycle.helps.UserHelper;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Anita
 * @description:
 * @date : 2020/2/9 14:50
 */
public class MotorUtils {
    /**
     * 验证是否存在已显示摩托车
     * 即SharedPreferences中是否有保存motorID
     */
    public static boolean isExitMotor(Context context) {
        boolean result = false;
        SharedPreferences sp = context.getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
//        取出常量中的内容
        String motorID = sp.getString("motorID", "");
//        取出的内容不为空
        if (!TextUtils.isEmpty(motorID)) {
//            保存用户标记，在全局单例类UserHelp之中
            MotorHelper.getInstance().setCurrentMotorId(motorID);
            result = true;
        }
        return result;
    }

    /**
     * 利用SharedPreferences，保存用户的显示车辆（motorID）
     *
     * @param context
     * @param motorID
     * @return 是否保存成功
     */
    public static boolean saveMotor(Context context, String motorID) {
        SharedPreferences sp = context.getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
//        将motorID的值传入常量中
        editor.putString("motorID", motorID);
        boolean result = editor.commit();
        return result;
    }

    /**
     * 删除用户标记
     */
    public static boolean removeMotor(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("motorID");
        boolean result = editor.commit();
        MotorHelper.getInstance().setCurrentMotorId(null);

        return result;
    }
    /**
     * 计算两个日期之间相差的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int daysBetween(Date date1, Date date2){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static boolean VINValidate(String vinCode){
        int number = 0;
        int check = 0;
        String number9 = "";
        String checknumber = "";
        for (int i = 0; i < vinCode.length(); i++) {
            char string = vinCode.charAt(i);
            if(i==8){
                number9 = string+"";
            }
            number +=covervin(string)*coveri(i) ;
        }
        check = number%11;
        if(check == 10){
            checknumber = "X";
        }else{
            checknumber = check+"";
        }
        if(number9.equals(checknumber)){
            return true;
        }
        return false;
    }

    public static int coveri(int i){
        int number = -1;
        switch (i) {
            case 0: number = 8; break;
            case 1: number = 7; break;
            case 2: number = 6; break;
            case 3: number = 5; break;
            case 4: number = 4; break;
            case 5: number = 3; break;
            case 6: number = 2; break;
            case 7: number = 10; break;
            case 8: number = 0; break;
            case 9: number = 9; break;
            case 10: number = 8; break;
            case 11: number = 7; break;
            case 12: number = 6; break;
            case 13: number = 5; break;
            case 14: number = 4; break;
            case 15: number = 3; break;
            case 16: number = 2; break;
            default:break;
        }
        return number;
    }

    private static int covervin(char string){
        int number = -1;
        switch (string) {
            case 'A': number = 1; break;
            case 'B': number = 2; break;
            case 'C': number = 3; break;
            case 'D': number = 4; break;
            case 'E': number = 5; break;
            case 'F': number = 6; break;
            case 'G': number = 7; break;
            case 'H': number = 8; break;
            case 'J': number = 1; break;
            case 'K': number = 2; break;
            case 'L': number = 3; break;
            case 'M': number = 4; break;
            case 'N': number = 5; break;
            case 'P': number = 7; break;
            case 'R': number = 9; break;
            case 'S': number = 2; break;
            case 'T': number = 3; break;
            case 'U': number = 4; break;
            case 'V': number = 5; break;
            case 'W': number = 6; break;
            case 'X': number = 7; break;
            case 'Y': number = 8; break;
            case 'Z': number = 9; break;
            case '0': number = 0; break;
            case '1': number = 1; break;
            case '2': number = 2; break;
            case '3': number = 3; break;
            case '4': number = 4; break;
            case '5': number = 5; break;
            case '6': number = 6; break;
            case '7': number = 7; break;
            case '8': number = 8; break;
            case '9': number = 9; break;
            default: break;
        }
        return number;
    }
}
