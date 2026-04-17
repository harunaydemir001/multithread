package com.harun.my_project.multithread;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

@AllArgsConstructor
public class _28_SearchOccurrenceTask extends RecursiveTask<Integer> {

    int[] arr;
    int start;
    int end;
    int searchElement;


    @Override
    protected Integer compute() {
        int size = end - start + 1;
        if (size > 50) {
            int mid = (start + end) / 2;
            _28_SearchOccurrenceTask task1 = new _28_SearchOccurrenceTask(arr, start, mid, searchElement);
            _28_SearchOccurrenceTask task2 = new _28_SearchOccurrenceTask(arr, mid + 1, end, searchElement);

            task1.fork();
            task2.fork();

            return task1.join() + task2.join();
        } else {
            return search();
        }
    }

    private Integer search() {
        int count = 0;
        for (int i = start; i <= end; i++) {
            if (arr[i] == searchElement) {
                count++;
            }
        }
        return count;
    }

}

class FJPDemo {
    public static void main(String[] args) {
        int[] arr = new int[100];
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(10) + 1;
        }
        int searchElement = random.nextInt(10) + 1;

        try (ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors())) {
            _28_SearchOccurrenceTask task = new _28_SearchOccurrenceTask(arr, 0, arr.length, searchElement);
            Integer occurence = pool.invoke(task);
            System.out.println("Array is : " + Arrays.toString(arr));
            System.out.printf("%d found %d times", searchElement, occurence);
        }
    }
}

/**
 *Kod Analizi: SearchOccurrenceTask
 * Bu kod, bir dizide belirli bir sayının kaç kez geçtiğini Fork/Join kullanarak bulan bir program.
 *
 * 🔍 Ne Yapıyor? (Özet)
 *
 * 100 elemanlı rastgele dizi oluşturur (1-10 arası sayılar)
 * Rastgele bir aranacak sayı seçer
 * Fork/Join Pool ile dizide o sayıyı arar ve sayar
 *
 *
 * 📦 Sınıf Analizi
 * SearchOccurrenceTask (RecursiveTask<Integer>)
 * javaint[] arr;        // Aranacak dizi
 * int start;        // Başlangıç indeksi
 * int end;          // Bitiş indeksi
 * int searchElement; // Aranacak sayı
 * java@Override
 * protected Integer compute() {
 *     return search(); // görevi çalıştır
 * }
 *
 * private Integer search() {
 *     int count = 0;
 *     for (int i = start; i < end; i++) {  // start'tan end'e kadar gez
 *         if (arr[i] == searchElement) {    // eşleşme varsa
 *             count++;                      // say
 *         }
 *     }
 *     return count; // kaç tane bulduğunu döndür
 * }
 * ```
 *
 * ---
 *
 * ### `FJPDemo` - Main Akışı
 * ```
 * 1. int[100] → rastgele 1-10 arası sayılarla doldur
 * 2. searchElement → 1-10 arası rastgele bir hedef seç
 * 3. ForkJoinPool → CPU çekirdek sayısı kadar thread aç
 * 4. Task oluştur → tüm diziyi (0 → 99) tara
 * 5. pool.invoke(task) → görevi çalıştır, sonucu bekle
 * 6. Sonucu yazdır
 *
 * ⚠️ Kritik Bug Var!
 * java// ❌ YANLIŞ - son eleman dahil edilmiyor!
 * SearchOccurrenceTask task = new SearchOccurrenceTask(arr, 0, arr.length - 1, searchElement);
 * //                                                            ↑           ↑
 * //                                                            0          99  ← 99. index atlanıyor!
 *
 * // search() içinde:
 * for (int i = start; i < end; i++)  // i < 99 → 99. index HİÇ KONTROL EDİLMİYOR
 * java// ✅ DOĞRUSU
 * new SearchOccurrenceTask(arr, 0, arr.length, searchElement);
 * // VEYA
 * for (int i = start; i <= end; i++)  // <= olmalı
 *
 * 🤔 Fork/Join Doğru Kullanılmış mı?
 * Hayır. Bu kodda Fork/Join'in asıl gücü kullanılmıyor çünkü:
 * DurumBu KodDoğru KullanımTask sayısı1 taskDizi bölünüp N taskParalellik❌ Yok✅ VarWork Stealing❌ Çalışmıyor✅ Aktif
 * ✅ Doğru Fork/Join Kullanımı:
 * java@Override
 * protected Integer compute() {
 *     // Dizi küçükse direkt ara
 *     if ((end - start) <= 10) {
 *         return search();
 *     }
 *
 *     // Büyükse ikiye böl
 *     int mid = (start + end) / 2;
 *
 *     SearchOccurrenceTask leftTask  = new SearchOccurrenceTask(arr, start, mid, searchElement);
 *     SearchOccurrenceTask rightTask = new SearchOccurrenceTask(arr, mid, end, searchElement);
 *
 *     leftTask.fork();              // sol yarıyı paralel başlat
 *     int rightResult = rightTask.compute(); // sağ yarıyı bu thread yap
 *     int leftResult  = leftTask.join();     // sol yarının bitmesini bekle
 *
 *     return leftResult + rightResult;       // ikisini topla
 * }
 * ```
 *
 * ---
 *
 * ## 📊 Doğru Çalışma Akışı (Düzeltilmiş)
 * ```
 * Dizi [0...99]
 *         │
 *    ┌────┴────┐
 * [0..49]   [50..99]     ← fork()
 *    │           │
 * [0..24] [25..49] [50..74] [75..99]  ← tekrar fork()
 *    │       │       │        │
 *   say     say     say      say
 *    └───────┴───────┴────────┘
 *               toplam → sonuç
 *
 * 💡 Özet
 * AçıklamaAmaçDizide sayı saymakBugSon eleman atlanıyor (arr.length - 1)EksikGerçek fork/join (böl-fethet) yokDüzeltmecompute() içinde recursive bölme yapılmalı
 */

/**
 * Güncellenmiş SearchOccurrenceTask Analizi
 * Bu sefer kod gerçek Fork/Join implementasyonu! Önceki versiyonun tüm eksikleri giderilmiş.
 *
 * 🎯 Ne Yapıyor?
 * 1.000.000 elemanlı dizide bir sayıyı, diziyi ikiye böle böle paralel olarak arar.
 *
 * 🔑 compute() - Asıl Mantık
 * java@Override
 * protected Integer compute() {
 *     int size = end - start + 1;  // parçanın büyüklüğü
 *
 *     if (size > 50) {
 *         // 🔀 FORK: Parça büyükse ikiye böl
 *         int mid = (start + end) / 2;
 *
 *         SearchOccurrenceTask task1 = new SearchOccurrenceTask(arr, start,   mid, searchElement);
 *         SearchOccurrenceTask task2 = new SearchOccurrenceTask(arr, mid + 1, end, searchElement);
 *
 *         task1.fork();  // task1'i paralel başlat
 *         task2.fork();  // task2'yi paralel başlat
 *
 *         return task1.join()   // task1 bitsin bekle
 *              + task2.join();  // task2 bitsin bekle
 *     } else {
 *         // ✅ Parça küçükse direkt ara
 *         return search();
 *     }
 * }
 * ```
 *
 * ---
 *
 * ## 📊 Çalışma Akışı (Görsel)
 * ```
 * [0 ────────────────── 999.999]   size=1.000.000 > 50 → FORK
 *           │
 *     ┌─────┴─────┐
 * [0..499.999] [500.000..999.999]  size=500.000 > 50 → FORK
 *     │               │
 *   ┌─┴─┐           ┌─┴─┐
 * [0..  ] [...  ] [...  ] [...  ]  → tekrar FORK
 *     │
 *    ...
 *     │
 * [x .. x+49]   size=50 → search() ✅ direkt say
 *
 * ⚖️ task1.fork() + task2.fork() Kullanımı
 * Bu kodda her iki task da fork'lanıyor. Bu çalışır ama küçük bir verimsizlik var:
 * java// Mevcut kod ✅ Çalışır ama:
 * task1.fork();   // thread pool'a gönder
 * task2.fork();   // thread pool'a gönder
 * return task1.join() + task2.join();  // ikisini de bekle
 * // Mevcut thread boşa bekliyor!
 *
 * // Daha iyi yaklaşım 🚀
 * task1.fork();                    // task1'i pool'a gönder
 * int result2 = task2.compute();   // task2'yi bu thread'de çalıştır (boşa bekleme!)
 * int result1 = task1.join();      // task1 bitsin
 * return result1 + result2;
 *
 * 🔍 search() - Küçük Parçayı Tara
 * javaprivate Integer search() {
 *     int count = 0;
 *     for (int i = start; i <= end; i++) {  // ✅ <= end (önceki bug düzeltilmiş!)
 *         if (arr[i] == searchElement) {
 *             count++;
 *         }
 *     }
 *     return count;
 * }
 *
 * 🏊 ForkJoinPool Kurulumu
 * java// CPU kaç çekirdekliyse o kadar thread aç
 * ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
 * //  örn: 8 çekirdekli CPU → 8 thread
 *
 * SearchOccurrenceTask task = new SearchOccurrenceTask(arr, 0, arr.length - 1, searchElement);
 * //                                                        ↑         ↑
 * //                                                        0      999.999  ✅ doğru
 *
 * Integer occurence = pool.invoke(task);  // tamamlanana kadar bekle
 * ```
 *
 * ---
 *
 * ## 📈 Kaç Task Oluşuyor?
 * ```
 * 1.000.000 eleman, eşik = 50
 *
 * Her seviyede ikiye bölünüyor:
 * Seviye 0:  1 task      (1.000.000)
 * Seviye 1:  2 task      (500.000'er)
 * Seviye 2:  4 task      (250.000'er)
 * ...
 * Seviye 14: ~20.000 task (50'şer) → search() çalışır
 *
 * Toplam ≈ 40.000 task oluşur
 *
 * ✅ Önceki Versiyonla Karşılaştırma
 * ÖzellikÖnceki KodBu KodFork/Join❌ Yok✅ VarBöl-Fethet❌ Yok✅ RecursiveSon eleman❌ Atlanıyor✅ <= endDizi boyutu1001.000.000Paralellik❌ Tek thread✅ Tüm çekirdeklerPerformansDüşük🚀 Yüksek
 *
 * 💡 Özet
 *
 * 1.000.000 elemanlı dizi, eşik değeri 50 olacak şekilde recursive olarak ikiye bölünür. Her parça 50'ye düşünce search() ile sayılır, sonuçlar join() ile toplanır. Tüm CPU çekirdekleri paralel çalışır → çok hızlı!
 */