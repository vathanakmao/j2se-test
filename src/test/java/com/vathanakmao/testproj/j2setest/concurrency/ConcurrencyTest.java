package com.vathanakmao.testproj.j2setest.concurrency;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

public class ConcurrencyTest {

    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    @Test(expected = AssertionError.class)
    public void testToSeeWhenCallableIsProcessed() throws Exception {

        Future<Boolean> future = executorService.submit(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                throw new Exception();
            }
        });

        assertFalse(true);

        // The Exception is never thrown because the Callable.call() is invoked by Future.get(), not the submit() method,
        // and this line is unreachable.
        future.get();

    }

    @Test
    public void executeCallableTaskUsingNewThread() throws Exception {

        Future<Boolean> future = executorService.submit(new Callable<Boolean>() {
            public Boolean call() throws Exception {

                final ArrayList<Boolean> result = new ArrayList<Boolean>();
                result.add(false); // false

                // Thread
                new Thread() {

                    @Override
                    public void run() {
                        result.set(0, true); // true
                    }
                }.start();

                Thread.sleep(1000); // to wait for the above thread to finish

                return result.get(0); // true

            }
        });

        boolean result = future.get();
        assertTrue(result); // true
    }

    @Test
    public void executeCallableTaskUsingNewThread_2() throws Exception {

        Future<Boolean> future = executorService.submit(new Callable<Boolean>() {
            public Boolean call() throws Exception {

                final List<Boolean> result = new ArrayList<Boolean>();
                result.add(false); // false

                // Thread
                new Thread() {

                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000); // sleep one second
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        result.set(0, true); // true
                    }
                }.start();

                // returns before the above thread finishes so the first element of the result list is not reset to true yet.
                return result.get(0); // false

            }
        });

        boolean result = future.get();
        assertFalse(result); // false
    }

    @Test
    public void executeCallableTaskUsingHttpClient() throws Exception {

        Future<Boolean> future = executorService.submit(new CallableTaskUsingHttpClient());

        boolean result = false;

        result = future.get(); // wait until the HttpClient execution finishes so it means that the HttpClient doesn't create a
                               // new thread

        assertTrue(result);
    }

}
