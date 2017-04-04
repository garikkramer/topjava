package ru.javawebinar.topjava;

import ru.javawebinar.topjava.util.DateTimeUtil;

/**
 * User: gkislin
 * Date: 05.08.2015
 *
 * @link http://caloriesmng.herokuapp.com/
 * @link https://github.com/JavaOPs/topjava
 */
public class Main {
    public static void main(String[] args) {
//        System.out.format("Hello Topjava Enterprise!");
        System.out.println(DateTimeUtil.getDate("2017-04-02"));
        System.out.println(DateTimeUtil.getTime("20:12"));
    }
}
