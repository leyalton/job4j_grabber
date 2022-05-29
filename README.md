# Проект - "Агрегатор вакансий"
____

#### Описание.
Система запускается по расписанию - раз в минуту.  Период запуска указывается в настройках - app.properties. 
Тестовым сайтом является - career.habr.com. С ним будет идти работа. Программа считывает все вакансии относящиеся к Java и записывает их в базу.
Доступ к интерфейсу осуществляется через REST API. Приложение собирается в jar.

####Расширение.
- В проект можно добавить новые сайты без изменения кода.
- В проекте можно сделать параллельный парсинг сайтов.