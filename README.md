# playwright-example

Небольшой фреймворк для UI-тестов на Java: Maven, TestNG, Playwright и Lombok. Страницы описаны через Page Object.

Первый тест открывает [Google](https://www.google.com/) и проверяет логотип, поле поиска и кнопки Google Search и I'm Feeling Lucky.

## Структура

```
src/main/java/com/example/framework
  config/     чтение config.properties и переопределение system properties
  core/       запуск браузера Playwright
  pages/      BasePage и page object'ы
src/main/resources/config.properties
src/test/java  базовый класс теста, listener скриншота и сами тесты
src/test/resources/testng.xml
```

## Требования

- Java 21
- Maven 3.9+

Если `channel` пустой, при первом запуске Playwright скачивает свой браузер с `cdn.playwright.dev`. Когда `channel` задан, например `channel=chrome`, используется уже установленный браузер и эта загрузка пропускается. `args=--disable-quic` отключает HTTP/3: без этого переход на Google часто падает с `net::ERR_SOCKET_NOT_CONNECTED`. Если сокет всё же оборвался, страница повторяет переход.

## Запуск

```sh
mvn test
```

Полезные параметры:

```sh
mvn test -Dheadless=false
mvn test -Dbrowser=firefox
mvn test -Dchannel=chrome
mvn test -DbrowserArgs=--no-sandbox
mvn test -DbaseUrl=https://www.google.com/?hl=en
```

`browser` принимает `chromium`, `firefox` или `webkit`. `channel` — имя установленного браузера, например `chrome`. Пустое значение запускает браузер, который поставляет Playwright.

При падении теста скриншот пишется в `target/screenshots`.
