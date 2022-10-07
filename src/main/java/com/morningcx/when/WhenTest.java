package com.morningcx.when;

/**
 * WhenTest
 *
 * @author MorningStar
 * @date 2022/10/6
 */
public class WhenTest {
    public static void main(String[] args) {
//        if (print("a", false) || print("b", false) && print("c", false) || print("d", true)) {
//            if (print("a", false) || print("b", false) && print("c", false) || print("d", true)) {
//                System.out.println("111111111");
//            }
//        }

        if (print("a", false) || print("b", true) && print("c", false) || print("d", false)) {
            System.out.println("111111111");
        }






        System.out.println("=============================================");

        When<Role> then2 = when().and(t -> false).or(t -> false);
        boolean test1 = then2.test(new Role());
        System.out.println(test1);


        System.out.println("=============================================");



        When<Role> then = when()
                .or(r -> print("a", false))
                .or(r -> print("b", false))
                .and(r -> print("c", false))
                .or(r -> print("d", true))
                .then(when()
                        .or(r -> print("a", false))
                        .or(r -> print("b", false))
                        .and(r -> print("c", false))
                        .or(r -> print("d", true))
                        .then(when()
                                .or(r -> print("a", false))
                                .or(r -> print("b", true))
                                .and(r -> print("c", false))
                                .or(r -> print("d", true))
                                .then(r -> System.out.println("111111111"))));
        boolean test = then.test(new Role());
        System.out.println(test);

        System.out.println("=============================================");

        When<Role> then1 = when().then(role -> System.out.println("11111111111111111"));

        System.out.println(then1.test(null));

    }

    private static boolean print(String word, boolean b) {
        System.out.println(word);
        return b;
    }


    private static When<Role> when() {
        return new When<>();
    }
}