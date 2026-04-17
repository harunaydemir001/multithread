package com.harun.my_project.multithread;

import java.util.concurrent.atomic.AtomicInteger;

public class _27_AtomicVariable33 {
    private static int count = 0;
    private static final AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                //count++;
                counter.incrementAndGet();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                //count++;
                counter.incrementAndGet();
            }
        });
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Count value is " + counter);
    }
}
/**
 * Atomic Variable (Java)
 * Read-Modify-Write Cycle Nedir?
 * Çok thread'li programlarda bir değişkeni değiştirmek 3 adımdan oluşur:
 * 1. READ   → Değeri bellekten oku
 * 2. MODIFY → Değeri değiştir
 * 3. WRITE  → Yeni değeri belleğe yaz
 * Problem: İki thread aynı anda bu adımları yaparsa Race Condition oluşur ve veri bozulur.
 *
 * Atomic Variable Nedir?
 * Bu 3 adımı tek bir atomik işlem olarak gerçekleştiren değişken türleridir. Yani bir thread işlemi bitirmeden başka bir thread araya giremez. synchronized kullanmaya gerek kalmaz.
 *
 * Türleri
 * TürAçıklamaAtomicIntegerint için thread-safe sayaçAtomicBooleanboolean için thread-safe flagAtomicLonglong için thread-safe sayaç
 *
 * Temel Operasyonlar
 * javaAtomicInteger sayi = new AtomicInteger(0);
 *
 * sayi.get()                    // Değeri oku → 0
 * sayi.set(5)                   // Değeri yaz → 5
 *
 * sayi.compareAndSet(5, 10)     // Eğer değer 5 ise 10 yap (CAS)
 *
 * sayi.getAndIncrement()        // Önce döndür, sonra artır (i++)
 * sayi.incrementAndGet()        // Önce artır, sonra döndür (++i)
 *
 * sayi.getAndDecrement()        // Önce döndür, sonra azalt (i--)
 * sayi.decrementAndGet()        // Önce azalt, sonra döndür (--i)
 *
 * Neden Kullanılır?
 * java// ❌ Thread-safe DEĞİL
 * int counter = 0;
 * counter++;  // Race condition riski!
 *
 * // ✅ Thread-safe
 * AtomicInteger counter = new AtomicInteger(0);
 * counter.incrementAndGet();  // Güvenli!
 * compareAndSet() özellikle lock-free algoritmalar için çok önemlidir — beklenen değer hâlâ oradaysa güncelle, yoksa tekrar dene.
 */