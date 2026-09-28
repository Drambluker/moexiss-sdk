# moexiss-sdk

Java 21 SDK для [MOEX ISS API](https://iss.moex.com/iss/reference/), не
зависящий от Spring Framework. Проект распространяется по лицензии
[Apache License 2.0](LICENSE); сведения об авторстве приведены в
[NOTICE](NOTICE).

## Подключение

Пакет публикуется в GitHub Packages. Добавьте репозиторий и зависимость:

```xml
<repository>
  <id>github</id>
  <url>https://maven.pkg.github.com/Drambluker/moexiss-sdk</url>
</repository>

<dependency>
  <groupId>org.vlaskin.moex</groupId>
  <artifactId>moex-iss</artifactId>
  <version>1.1.0</version>
</dependency>
```

Для загрузки из GitHub Packages настройте сервер `github` в
`~/.m2/settings.xml`, используя имя пользователя GitHub и PAT с правом
`read:packages`. Не сохраняйте PAT в проекте. Стабильное имя Java-модуля —
`org.vlaskin.moexiss`.

## Использование

`MoexClient` предоставляет сервисы инструментов, справочников, торговых систем
и статистики, а также исторических данных:

```java
MoexClient client = new MoexClient();

ListSecurityParams params = new ListSecurityParams();
params.setQuery("SBER");
List<SecurityResponse> securities = client.getSecurities().getList(params);
```

Основные поля доступны через предметные getters, например
`security.getCode()` и `security.getLotSize()`. Остальные поля читаются с
проверкой типа:

```java
Double last = marketData.get(MarketDataResponse.Fields.LAST, Double.class);
```

### Исторические данные

```java
HistoryQuery query = new HistoryQuery("stock", "shares", "TQBR", "SBER",
    LocalDate.of(2025, 9, 22), LocalDate.of(2025, 9, 26), 0);
HistoryPage<SecurityHistoryRow> page = client.getHistory().getSecurityHistory(query);
```

Типы запросов и результатов находятся в `org.vlaskin.moexiss.service.history`.
Методы читают одну страницу; если `page.complete()` равно `false`, следующий
`start` равен `page.start() + page.rows().size()`. История индексов доступна
через `getIndexHistory`. Некорректные таблицы и курсоры отклоняются; отсутствующая
цена остаётся `null`, а не заменяется нулём.

`getCorporateActions` возвращает справочник дроблений, не полное покрытие всех
корпоративных событий. `getDistributions` возвращает `UNSUPPORTED`: достоверный
полный источник выплат пока не подтверждён. Отсутствие данных не означает,
что событий или выплат не было. Методика индекса и доходность SDK не вычисляются.

Стандартный HTTP-транспорт использует тайм-ауты подключения и ответа 10 и 30
секунд. Другие значения или собственный транспорт можно передать в конструктор
`MoexClient`. Ошибки HTTP представлены `MoexHttpException`, остальные сетевые
ошибки и некорректные ответы — `IOException`.

Гарантии Semantic Versioning распространяются на клиент, транспорт, сервисы,
параметры и возвращаемые entity-классы. Типы и пакеты, помеченные
`@InternalApi`, не входят в публичный контракт.

## Проверки и документация

```bash
./mvnw clean verify
./mvnw clean verify -Plive-contract-tests
./mvnw clean verify site
```

Обычные тесты используют локальные снимки ответов MOEX. Профиль
`live-contract-tests` обращается к реальному API и требует сетевого доступа к
`iss.moex.com`.

- [Maven Site и Javadoc](https://drambluker.github.io/moexiss-sdk/)
- [Выпуск, удаление и восстановление версий](RELEASING.md)

Локальный сайт после `./mvnw clean verify site` — `target/site/ru/index.html`.
