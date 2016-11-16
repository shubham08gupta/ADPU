package com.cmos.adpu;

/**
 * Created by Pankaj on 03/10/2015.
 */
public class PrintableText {
    
    String convert(String data, Integer left, Integer right)
    {
        String str1 = data;
        String str2 = "";
        if (right != 0)
        {
            if (data.split("\\.").length == 2)
            {
                str1 = data.split("\\.")[0];
                str2 = "." + data.split("\\.")[1];

                for (int i = 0;i < right - data.split("\\.")[1].length(); i++)
                {
                    str2 = str2 + "0";
                }
                str2 = str2.substring(0,3);
            }
            else {
                str2 = ".";
                for(int i = 0; i < right; i++)
                    str2 = str2 + "0";
            }
            for(int j = 0; j < left - data.split("\\.")[0].length(); j++)
                str1 = " " + str1;
        }
        else
            for(int j = 0; j < left - data.length(); j++)
                str1 = " " + str1;
        return str1 + str2;
    }
}
