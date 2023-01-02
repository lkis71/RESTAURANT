package com.restaurant.trace.hellotrace.threadlocal;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalServiceTest {
    
    private ThreadLocalService service = new ThreadLocalService();

    @Test
    void field() {
        log.info("main start");
        Runnable userA = () -> {
            service.logic("userA");
        };
        Runnable userB = () -> {
            service.logic("userB");
        };

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");

        threadA.start();
        // sleep(2000); //동시성 문제 발생x
        sleep(100); //동시성 문제 발생o
        threadB.start();

        sleep(3000);
        log.info("exit");
    }

    private void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
