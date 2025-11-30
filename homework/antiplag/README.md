## Антиплагиат (Домашняя работа №3)

Набор микросервисов для загрузки работ, хранения файлов и построения отчетов о плагиате. Архитектура: шлюз (gateway) + File Storing Service (filestore) + File Analysis Service (analysis). Все сервисы — Spring Boot, `docker compose up`.

### Пользовательский сценарий
- Студент отправляет работу через `POST /api/works` (gateway) с файлом, `studentName`, `assignmentId`.
- Gateway сохраняет файл в File Storing Service, затем инициирует анализ в File Analysis Service.
- Преподаватель запрашивает отчеты по работе через `GET /api/reports?assignmentId=...` (gateway) и видит статус и флаг плагиата. Можно скачать файл `GET /api/files/{id}` и получить ссылку на облако слов `GET /api/reports/{id}/wordcloud`.

### Алгоритм выявления плагиата
- Для каждого файла вычисляется SHA-256 хэш содержимого.
- Плагиат фиксируется, если уже есть более ранняя сдача с тем же `assignmentId`, другим `studentName` и совпадающим хэшем содержимого.
- В отчете сохраняется источник подозрения (`plagiarismSource`) — автор и время первой найденной совпадающей работы.
- Если файл недоступен, отчет помечается как `FAILED` с текстом ошибки.

### API (gateway)
- `POST /api/works` — multipart: `file`, `studentName`, `assignmentId`. Возвращает метаданные файла и отчет.
- `GET /api/reports[?assignmentId=ID]` — список отчетов.
- `GET /api/reports/{id}` — отчет по id.
- `GET /api/reports/{id}/wordcloud` — JSON с полем `url` (быстрая ссылка на wordcloud от quickchart).
- `GET /api/files/{id}` — скачать исходный файл.

File Storing Service:
- `POST /files` — multipart `file`, сохраняет на диск, отдает метаданные.
- `GET /files/{id}` — скачать файл.

File Analysis Service:
- `POST /reports` — JSON `{fileId, assignmentId, studentName, submittedAt?}` запускает анализ.
- `GET /reports[?assignmentId=ID]`, `GET /reports/{id}`, `GET /reports/{id}/wordcloud`.

### Контейнеризация
- `docker compose up --build` в `homework/antiplag` поднимает три контейнера (порты 8080/8081/8082, общий том `./data/files`).

## Архитектура и взаимодействие
- Gateway проксирует клиентские запросы, не хранит файлов/отчетов локально.
- Filestore кладет бинарники на диск (`/data/files` по умолчанию) и держит метаданные в памяти.
- Analysis запрашивает файлы по HTTP из Filestore, вычисляет хэш и ищет совпадения среди ранее сохраненных отчетов (метаданные в памяти).
- Для word cloud сервис формирует ссылку на публичный API quickchart на основе частот слов документа. Реальный вызов внешнего API не выполняется.

## Локальный запуск без Docker
- `./gradlew :filestore:bootRun`, `./gradlew :analysis:bootRun`, `./gradlew :gateway:bootRun` (порты 8081/8082/8080).

## Замечания
- Хранилища сервисов — in-memory; после рестарта метаданные и отчеты обнуляются, файлы на диске остаются. 
- Простая эвристика плагиата опирается только на точное совпадение содержимого.
