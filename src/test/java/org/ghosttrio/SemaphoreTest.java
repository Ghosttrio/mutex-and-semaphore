package org.ghosttrio;


import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;


public class SemaphoreTest {

    private static final Semaphore semaphore = new Semaphore(3);

    public void accessResource(int threadId) {
        try {
            semaphore.acquire();
            Thread.sleep(1000);
            System.out.println("ID : " + threadId + " 세마포어 얻음");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
            System.out.println("ID : " + threadId + " 세마포어 반납");
        }
    }

    @Test
    public void testSemaphore() throws InterruptedException {
        // 최대 3개의 스레드만 자원에 접근할 수 있도록 설정

        // 스레드를 동시에 실행시키기 위한 ExecutorService
        ExecutorService executorService = Executors.newFixedThreadPool(5); // 5개의 스레드를 생성

        // 5개의 스레드를 실행하여 자원에 접근 시도
        for (int i = 1; i <= 5; i++) {
            final int threadId = i;
            executorService.submit(() -> accessResource(threadId));
        }

        // 모든 작업이 끝날 때까지 기다림
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            Thread.sleep(100); // 대기
        }

    }
}
