package com.github.fangming.springboot.h2;

import com.google.common.base.MoreObjects;
import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Function;

public class TestLamanda {

    Consumer<Human> f1 = (Human human) -> System.out.println(human.getId());

    Function<Human, String> f2 = Human::toString;

    @Test
    public void testFunctionInterface(){
        Human h1 = new Human(100);
        f1.accept(h1);
        System.out.println(f2.apply(h1));
    }


    class Human {

        private int id ;

        public Human(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                .add("id", id)
                .toString();
        }
    }
}
