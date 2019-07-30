/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c482;

/**
 *
 * @author A Suzuki
 */
public class InputCheck {
    public static int inputIntChk(String str){
        int i;
        try{
            i = Integer.parseInt(str);
        } catch (NumberFormatException e){
            i = 0;
        }
        return i;
    }
    public static double inputDoubleChk(String str){
        double d;
        try{
            d = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            d = 0.0;
        }
        return d;
    }
}
