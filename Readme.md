# Readme

# GPT-Clone

## Proje Amacı

Bu proje, kullanıcıların doğal dil işleme (NLP) tabanlı GPT modelini kullanarak metin üretmelerini sağlayan bir uygulamadır. Kullanıcılar, belirli parametrelerle özelleştirilebilen metin istekleri gönderir ve uygulama, OpenAI API'si üzerinden cevaplar alır. Yanıtlar, uygulamanın backend'ine kaydedilir ve kullanıcıya görsel olarak sunulur. Ayrıca, proje, DALL-E API'si ile entegre edilerek görsel üretimi de destekler. Kullanıcılar, metin ve görsel çıktılarıyla etkileşime girerek, sorulara dayalı içerik oluşturma deneyimini geliştirirler. Proje, model seçimi, `top_p`, `max_tokens`, `temperature`, ve `role` parametreleri gibi özelleştirilebilir ayarlarla kullanıcılara daha fazla kontrol sunar. Frontend, HTML, HTMX, ve Tailwind CSS ile tasarlanmış, backend ise Spring Boot kullanılarak geliştirilmiştir. Proje, Docker ve Docker Compose kullanılarak dağıtıma hazırlanmıştır.

![Adsız.png](https://github.com/sinanbalibey/Gptclone/blob/main/img/Adsz.png)

## Projenin Çalıştırılması

Projenin gereksinimleri sadece docker yüklü bir makine.

Bir dizinde “docker-compose.yml” isimli bir dosya oluşturun ve içine aşağıdakileri yapıştırın.

```yaml
services:
  gptclone-app:
    image: sibacode/gptclone
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://gptclone-db:3306/gpt_db
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: 123456
      GPT_API_KEY: sk-....API-KEY
    depends_on:
      - gptclone-db
    networks:
      - gpt_network

  gptclone-db:
    image: mysql:8.0
    restart: always
    ports:
      - "3306:3306"
    environment:
      MYSQL_ROOT_PASSWORD: 123456
      MYSQL_DATABASE: gpt_db
      MYSQL_USER: root
      MYSQL_PASSWORD: 123456
    volumes:
      - db_data:/var/lib/mysql
    networks:
      - gpt_network

volumes:
  db_data:

networks:
  gpt_network:
    driver: bridge
```

“GPT_API_KEY” değişkenini kendi API_KEY’inizle değiştirin.

Compose dosyasının bulunduğu dizine konsol aracılığıyla erişin ve şu komutu yazın:

```bash
docker-compose up -d 
```

Gerekli indirmeleri yaptıktan sonra projeye [http://localhost:8080](http://localhost:8080/) adresinden erişebilirsiniz.

Projeyi kapatmak için:

```bash
docker-compose down
```

!Projeyi kapattıktan sonra volume dosyanızı silmezseniz verileriniz silinmez.

!Not: Projenin Çalışması için 8080 ve 3306 portlarının boşta olması lazım.

!Not: Gpt-Api Key için [https://openai.com/index/openai-api/](https://openai.com/index/openai-api/) adresinden 5 dolar karşılığında alabilirsiniz.

### Sonuç

Bu proje, OpenAI'nin GPT ve DALL-E API'lerini entegre ederek kullanıcıların metin ve görsel içerik oluşturma süreçlerini kolaylaştıran bir platform sağlamaktadır. Kullanıcılar, uygulama aracılığıyla belirli parametrelerle metin talepleri göndererek yanıtlar alabilir ve bu yanıtlar görsel formatta da sunulabilir. Proje, kullanıcılara etkili bir deneyim sunarken, arka planda güçlü bir Spring Boot uygulamasıyla yönetilen API entegrasyonları sağlar. Docker ve Docker Compose kullanılarak uygulamanın taşınabilirliği ve ölçeklenebilirliği sağlanmıştır. Uygulama, metin ve görsel içerik oluşturma konusunda kullanıcı dostu bir arayüz sunmakta ve yapay zeka tabanlı çözümlerle dijital içerik üretiminde verimliliği artırmayı hedeflemektedir. Sonuç olarak, bu proje, yapay zeka tabanlı içerik oluşturma araçlarının entegrasyonu ile kullanıcıların daha hızlı ve verimli bir şekilde içerik üretmelerine olanak tanımaktadır.

![dalle.PNG](https://github.com/sinanbalibey/Gptclone/blob/main/img/dalle.png)

![gpt.PNG](https://github.com/sinanbalibey/Gptclone/blob/main/img/gpt.png)

![gpt1.PNG](https://github.com/sinanbalibey/Gptclone/blob/main/img/gpt1.png)

![gpt4.PNG](https://github.com/sinanbalibey/Gptclone/blob/main/img/gpt4.png)

![gpt3.png](https://github.com/sinanbalibey/Gptclone/blob/main/img/gpt3.png)

![gpt2.png](https://github.com/sinanbalibey/Gptclone/blob/main/img/gpt2.png)

![gpt5.PNG](https://github.com/sinanbalibey/Gptclone/blob/main/img/gpt5.png)

![gpt6.PNG](https://github.com/sinanbalibey/Gptclone/blob/main/img/gpt6.png)
