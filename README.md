## Тестовое задание
***
### Сборка и запуск
#### Командная строка
````
git clone https://github.com/vensyyy91/ComfortSoftTest.git
````
````
cd ComfortSoftTest
````
````
mvn spring-boot:run
````
***

#### IntelliJ IDEA
````
File -> New -> Project from Version Control...
URL: https://github.com/vensyyy91/ComfortSoftTest.git
Directory: {your_path}
Clone
Trust Project
This Window / New Window
````
````
Run -> Run 'ComfortSoftTestApplication' / Shift + F10
````
***
### Swagger
````
http://localhost:8090/swagger-ui/index.html
````
***
_В директории_ __excel__ _находится пример принимаемого файла, а в директории_ __postman__ _- коллекция с запросом. В параметре_ __path__ _нужно либо указать путь до проекта вместо_ __{your_path}__ _(если хотите использовать файл-пример из директории_ __excel__ _), либо полностью заменить значение на ваш путь до файла (если хотите использовать свой файл)._  
*Пример:* 
````
C://Users//User//IdeaProjects//ComfortSoftTest//excel//test.xlsx
````
