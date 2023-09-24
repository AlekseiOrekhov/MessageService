### Привет, соискатель!

Мы рады, что ты проявил интерес к нашей компании и вакансии!

Для нас очень важны HARD-составляющие твоих навыков - чтобы рука об руку развивать проекты международного уровня. Поэтому предлагаем выполнить небольшое тестовое задание - которое позволит тебе понять вектор будущих задач, а нам - вглянуть на твою логику их решения.

### Итак, дано:
Есть сервис, который должен принимать List сообщения из топика RabbitMQ, сохранять их в БД, записывать информацию в метрики, а также реализовывать по АПИ возможность получить все сообщения или одно сообщение по ID и типу.

Но в данный момент сервис не закончен, и твоя задача состоит в том, чтобы его доработать

### А теперь - немного правил:

* Нельзя изменять модели данных.
* Нельзя изменять набор библиотек (pom.xml).

### Что необходимо сделать:

* Начать принимать сообщения из топика Rabbit `sms.data`. + 
* Реализовать MessageService и GetMessageUseCase. + 
* Используя CalculateService рассчитать количество сообщений без учёта СМС и вывести это в лог.
* Добавить кэширование запроса выдачи сообщений по типу. +
* Для выдачи всех сообщений сделать пагинацию (по страничную выдачу). +
* Раз в час получать из БД CustomMessageEntity и сохранять в .json файл. +
* Исправить/улучшить предоставленный код.
* Написать тестирование добавленного функционала используя H2 базу в памяти и замокав Rabbit. (минимум 1 тест).  + 

#### При получении сообщений необходимо:
* Провалидировать. +
* Сохранить в БД. +
* Рассчитать сколько сообщений пришло по каждому типу. +
* Сохранить расчёт в метрики, используя MetricService из добавленной библиотеки (library-for-test-1.0.jar). + 

Удачи в выполнении задания!

Результаты выполненного задания отправить в формате ссылки на гит или файла по адресу 
sedova_ekateina@keepcode.org или в Telegram @Recruiter_Keepcode.



Update:
Задача реализована, в дополнении добавлен producer для кафки и добавлен контроллер для отправки JSON с списком сообщений. Добавлены тесты с замоканым consumer и проверкой на то что сообщения сохраняются и в нужном количестве. Добавлена Postman коллекция для работы с API на локально запущенной машине
