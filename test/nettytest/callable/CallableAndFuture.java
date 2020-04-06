/**
 * @author YC 04/06/2020
 */

package nettytest.callable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableAndFuture {
    public static void main(String[] args) throws Exception {
        ExecutorService es = Executors.newCachedThreadPool();
        Future<Long> f = es.submit(new MyTask());
        long l = f.get();
        System.out.println(f.toString());
        es.shutdown();
    }
}
