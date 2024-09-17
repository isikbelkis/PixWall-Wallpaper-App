## Pix Wall - Wallpaper Uygulaması
Pix Wall, Unsplash API kullanarak yüksek kaliteli duvar kağıtlarını listeleyen ve kullanıcıların bu resimlere detaylı olarak erişebilmesini sağlayan bir mobil uygulamadır. 
Kullanıcılar listelenen resimlere tıklayarak detay sayfasına yönlendirilir. Detay sayfasında, kullanıcılar resmi kaydedebilir, indirebilir veya cihazlarının arka planı olarak ayarlayabilirler.

## Kullanılan Teknolojiler
1-Unsplash API: Yüksek kaliteli görselleri listelemek ve detayları almak için kullanıldı.
2-Firebase: Kullanıcı kimlik doğrulama ve verilerin Firestore'a kaydedilmesi için kullanıldı.
3-Firestore: Kullanıcı verilerini, favorileri ve seçilen kategorileri saklamak için kullanıldı.
4-ViewPager2: Uygulama içindeki sayfalar arasında geçiş yapmak için kullanıldı.
5-Chip Navigation Bar: Kategorilere hızlı erişim sağlamak için GitHub'dan çekilen kütüphane kullanıldı.
6-Retrofit: API ile network isteklerini gerçekleştirmek için kullanıldı.
7-Glide: Görsellerin hızlı ve verimli bir şekilde yüklenmesi için kullanıldı.
8-Navigation Component: Fragmentlar arası geçiş ve veri iletimi sağlamak için kullanıldı.
9-Coroutines: Asenkron işlemleri UI thread'ini kilitlemeden gerçekleştirmek için kullanıldı.
10-Wallpaper Manager: Kullanıcıların bir resmi doğrudan cihazlarının arka planı olarak ayarlayabilmesi için kullanıldı.
11-Download Manager: Kullanıcıların detay sayfasında resmi indirip cihazlarına kaydedebilmeleri için kullanıldı.
12-Firebase Authentication: Kullanıcı giriş işlemleri için kullanıldı.
13-Firestore: Kullanıcıların seçtiği kategorileri ve favorileri saklamak için kullanıldı.

## Uygulama Özellikleri
-Listeleme ve Detay Sayfası: Ana ekranda, Unsplash API’dan çekilen duvar kağıtları listelenir. Herhangi bir resme tıklanıldığında detay sayfasına geçilir.
-Kaydetme ve İndirme: Kullanıcılar, detay sayfasında görüntülenen resmi cihazlarına kaydedebilir veya Download Manager kullanarak indirebilirler.
-Arka Plan Yapma: Wallpaper Manager sayesinde, kullanıcılar bir tıklamayla resmi doğrudan cihazlarının arka planı olarak ayarlayabilirler.
-Kullanıcı Girişi ve Kategoriler: Kullanıcılar giriş yaptıktan sonra, kategorileri seçebilirler. Bu kategoriler Firestore'da saklanır ve ana sayfada listelenir. Kullanıcının seçtiği kategorilere göre içerikler görüntülenir.
-Profil Sayfası: Kullanıcının kaydettiği veriler, profil sayfasında gösterilir.
-Asenkron Veri Yönetimi: Uygulama, API'dan gelen verileri verimli ve performanslı bir şekilde işlemek için Koin ve Coroutines ile asenkron veri yönetimi sağlar.

## Kütüphaneler
Firebase, Retrofit, Glide, Koin, Navigation Component, Coroutines
OkHttp, Gson, ViewPager2, Chip Navigation Bar, BlurView

## UI Tasarım

| ----------------- | ------------------------------------------------------------------ |
| <img src="./img/1.png" width="50%" height="50%">  | <img src="./img/2.png" width="50%" height="50%"> |
