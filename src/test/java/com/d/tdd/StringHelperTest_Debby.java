package com.d.tdd;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class StringHelperTest_Debby {


    //"ABCD" => "BCD", "AACD"=> "CD", "BACD"=>"BCD", "AAAA" => "AA", "MNAA"=>"MNAA"
    //Red
    //Green
    //Refactor
    @Test
    void test_ () {
    StringHelper helper = new StringHelper();
    String actual = helper.replaceAInFirst2Positions("ABCD");
    assertEquals("BCD", actual);
    }

}
