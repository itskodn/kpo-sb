## Домашняя работа №4 — Orders & Payments

Три Spring Boot сервиса (Java 17) + фронтенд:
- `orders-service` — создание заказа, список/статус, outbox → Kafka `orders.created`, inbox + WebSocket push статусов.
- `payments-service` — кошельки, пополнение, списание; inbox/outbox, идемпотентная оплата, публикует `payments.result`.
- `api-gateway` — роутинг `/api/orders/**` на Orders и `/api/wallets/**` на Payments.
- `frontend` — простое веб-приложение (порт 3000) для работы с API и WebSocket подпиской.

## Запуск
1. В корне `homework/gazon-shop` собрать: `./gradlew clean build`
2. Поднять инфраструктуру и сервисы: `docker compose up --build`
3. Порты: gateway `8080`, frontend `3000`, orders `8181` (внутри сети сервис слушает 8081), payments `8082`, Kafka `9092`, Postgres: `orders-db` `5433`, `payments-db` `5544`.
4. Healthchecks добавлены для Kafka и БД, сервисы стартуют после их готовности.

## Переменные окружения
- `KAFKA_BOOTSTRAP_SERVERS` (по умолчанию `localhost:9092`)
- `ORDERS_DB_URL`, `ORDERS_DB_USER`, `ORDERS_DB_PASSWORD`
- `PAYMENTS_DB_URL`, `PAYMENTS_DB_USER`, `PAYMENTS_DB_PASSWORD`

## REST API (через gateway, заголовок `X-User-Id` обязателен)
### Payments (порт 8080 → 8082)
- `POST /api/wallets` — создать кошелек.
- `POST /api/wallets/top-up` — `{ "amount": 500 }`, пополнить.
- `GET /api/wallets/balance` — баланс.

### Orders (порт 8080 → 8081)
- `POST /api/orders` — `{ "amount": 500 }`, создать заказ и запустить оплату.
- `GET /api/orders` — список заказов пользователя.
- `GET /api/orders/{id}` — статус.

### WebSocket
- Endpoint: `ws://localhost:8181/ws`
- Топик: `/topic/orders/{orderId}` — OrderStatusMessage (orderId, status, updatedAt).
Фронтенд автоматически подписывается после создания заказа.

## Swagger / OpenAPI
- Orders: http://localhost:8181/swagger-ui.html
- Payments: http://localhost:8082/swagger-ui.html

## События
- `orders.created` — `OrderCreatedEvent` (orderId, userId, amount, eventId, createdAt).
- `payments.result` — `PaymentResultEvent` (status SUCCESS/FAILED, sourceEventId, orderId, reason, createdAt).

## Docker-образы
Multi-stage сборка для всех сервисов (`Dockerfile` в каждом модуле). `docker-compose.yml` поднимает Kafka, два Postgres, gateway, оба сервиса и фронтенд.
