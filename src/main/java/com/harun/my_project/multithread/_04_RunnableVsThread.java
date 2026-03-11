package com.harun.my_project.multithread;

public class _04_RunnableVsThread {
    /**
     *
     * 1)Thread extend edersen, başka bir sınıftan kalıtım alamazsın. Runnable implement edersen sınıfın özgür kalır:
     *
     * . Aynı görevi birden fazla thread'de çalıştırabilirsin
     * Runnable task = new MyTask();
     *
     * // Aynı task nesnesi, 3 farklı thread'de
     * new Thread(task).start();
     * new Thread(task).start();
     * new Thread(task).start();
     */
}
