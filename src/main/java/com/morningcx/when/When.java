package com.morningcx.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * When
 *
 * @author MorningStar
 * @date 2022/10/3
 */
public class When<T> implements Predicate<T> {
    private List<Branch> branches = new ArrayList<>();
    private int branchIndex = 0;
    private int predicateIndex = 0;


    @SafeVarargs
    public When(Predicate<? super T>... predicates) {
        Arrays.stream(predicates).forEach(this::and);
    }


    @Override
    public When<T> and(Predicate<? super T> and) {
        Branch branch = getCurrentBranch();
        List<Predicate<T>> predicates = branch.getPredicates();
        // 新条件
        if (predicateIndex == predicates.size()) {
            predicates.add(and::test);
        } else {
            // 原条件
            predicates.set(predicateIndex, predicates.get(predicateIndex).and(and));
        }
        return this;
    }

    @Override
    public When<T> or(Predicate<? super T> or) {
        ++predicateIndex;
        return and(or);
    }

//
//    /**
//     * @param then
//     * @return
//     */
//    public When<T> then(Predicate<? super T> then) {
//        getCurrentBranch().setThen(then::test);
//        ++branchIndex;
//        return this;
//    }


    /**
     * @param then
     * @return
     */
    public When<T> then(When<? super T> then) {
        getCurrentBranch().setThen(then::test);
        ++branchIndex;
        return this;
    }

    /**
     * @param then
     * @return
     */
    public When<T> then(Consumer<? super T> then) {
        getCurrentBranch().setThen(t -> {
            then.accept(t);
            return true;
        });
        ++branchIndex;
        return this;
    }

    @Override
    public When<T> negate() {
        return new When<>(t -> !test(t));
    }

    @Override
    public boolean test(T t) {
        if (branches.isEmpty()) {
            return true;
        }
        for (Branch branch : branches) {
            List<Predicate<T>> predicates = branch.getPredicates();
            Predicate<T> then = branch.getThen();
            if (predicates.isEmpty()) {
                return then == null || then.test(t);
            } else {
                for (Predicate<T> predicate : predicates) {
                    if (predicate.test(t)) {
                        return then == null || then.test(t);
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取当前分支
     *
     * @return
     */
    private Branch getCurrentBranch() {
        // 新分支
        if (branchIndex == branches.size()) {
            Branch branch = new Branch();
            branches.add(branch);
            predicateIndex = 0;
            return branch;
        } else {
            // 原分支
            return branches.get(branchIndex);
        }
    }


    private class Branch {
        private List<Predicate<T>> predicates = new ArrayList<>();
        private Predicate<T> then;

        public List<Predicate<T>> getPredicates() {
            return predicates;
        }

        public void setPredicates(List<Predicate<T>> predicates) {
            this.predicates = predicates;
        }

        public Predicate<T> getThen() {
            return then;
        }

        public void setThen(Predicate<T> then) {
            this.then = then;
        }
    }
}
