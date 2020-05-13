# VoteForLunch

##### веб-приложение для проведения голосования, в каком ресторане обедать

## Техническое задание для приложения

> Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.
> The task is:
> Build a voting system for deciding where to have lunch.
> * 2 types of users: admin and regular users
> * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
> * Menu changes each day (admins do the updates)
> * Users can vote on which restaurant they want to have lunch at
> * Only one vote counted per user
> * If user votes again the same day:
>   * If it is before 11:00 we asume that he changed his mind.
>   * If it is after 11:00 then it is too late, vote can't be changed
    Each restaurant provides new menu each day.

Приложение предоставляет REST API, функциями которого могут пользоваться только зарегистрированные пользователи: обычные пользователи и администраторы. 

Используется HTTP basic аутентификация.

Обмен данными с приложением производится в формате JSON.

# 1. API для обычного пользователя
## 1.1. Получить список всех ресторанов и их меню на сегодня.

#### `URL /restaurants`

### Запрос
  * Метод: GET
  
### Ответ
  * HTTP код ответа: 200
  * Данные: объекты RestaurantTo
> ###### [    
>> ###### { 
>>> ###### "id":Long,
>>> ###### "name":String,
>>> ###### "address":String,
>>> ###### "todayMenu":
>>> ###### [
>>>> ###### {
>>>>> ###### "id":Long,
>>>>> ###### "name":String,
>>>>> ###### "price":Integer
>>>> ###### }
>>>> ###### ...
>>> ###### ],
>>> ###### "voted":Boolean
>> ###### }
>> ###### ...
> ###### ]

### Пример curl
##### `curl -s http://localhost:8080/voteforlunch/restaurants --user inga@gmail.com:passInga`

## 1.2. Получить ресторан по его id и его меню на сегодня.

#### `URL /restaurants/{id}`

### Запрос
  * Метод: GET
  * Параметр URL: Long id

### Ответ
  * HTTP код ответа: 200
  * Данные: объект RestaurantTo    
> ###### { 
>> ###### "id":Long,
>> ###### "name":String,
>> ###### "address":String,
>> ###### "todayMenu":
>> ###### [
>>> ###### {
>>>> ###### "id":Long,
>>>> ###### "name":String,
>>>> ###### "price":Integer
>>> ###### }
>>> ###### ...
>> ###### ],
>> ###### "voted":Boolean
> ###### }

### Пример curl
##### `curl -s http://localhost:8080/voteforlunch/restaurants --user inga@gmail.com:passInga`

## 1.3. Проголосовать за ресторан по его restaurantId.

#### `URL /votes`

### Запрос
  * Метод: POST
  * Параметр URL: Long restaurantId

### Ответ
  * HTTP код ответа: 201
  * Данные: объект VoteTo
> ###### {
>> ###### "dateTime":LocalDateTime,
>> ###### "restaurantTo":
>> ###### {
>>> ###### "id":Long,
>>> ###### "name":String,
>>> ###### "address":String,
>>> ###### "todayMenu":
>>> ###### [
>>>> ###### {
>>>>> ###### "id":Long,
>>>>> ###### "name":String,
>>>>> ###### "price":Integer
>>>> ###### }
>>>> ###### ...
>>> ###### ],
>>> ###### "voted":Boolean
>> ###### }
> ###### }

### Пример curl
##### `curl -X POST -i -s http://localhost:8080/voteforlunch/votes?restaurantId=100004 --user inga@gmail.com:passInga`

## 1.4. Получить историю своих голосований.

#### `URL /votes`

### Запрос
  * Метод: GET

### Ответ
  * HTTP код ответа: 200
  * Данные: объекты Vote
> ###### [
>> ###### {
>>> ###### "dateTime":LocalDateTime,
>>> ###### "restaurant":
>>> ###### {
>>>> ###### "id":Long,
>>>> ###### "name":String,
>>>> ###### "address":String
>>> ###### }
>> ###### }
>> ###### ...
> ###### ]

### Пример curl
##### `curl -s http://localhost:8080/voteforlunch/votes --user inga@gmail.com:passInga`

## 1.5. Получить историю своих голосований в интервале дат от startDate до endDate включительно.

#### `URL /votes/filter`

### Запрос
  * Метод: GET
  * Параметры URL (необязательные): LocalDate startDate, LocalDate endDate

### Ответ
  * HTTP код ответа: 200
  * Данные: объекты Vote
> ###### [
>> ###### {
>>> ###### "dateTime":LocalDateTime,
>>> ###### "restaurant":
>>> ###### {
>>>> ###### "id":Long,
>>>> ###### "name":String,
>>>> ###### "address":String
>>> ###### }
>> ###### }
>> ###### ...
> ###### ]

### Пример curl
##### `curl -s http://localhost:8080/voteforlunch/votes/filter?startDate=2020-05-13&endDate=2020-05-14 --user inga@gmail.com:passInga`

## 1.6. Получить свое голосование за дату date.

#### `URL /votes/{date}`

### Запрос
  * Метод: GET
  * Параметр URL: LocalDate date

### Ответ
  * HTTP код ответа: 200
  * Данные: объект VoteTo
> ###### {
>> ###### "dateTime":LocalDateTime,
>> ###### "restaurantTo":
>> ###### {
>>> ###### "id":Long,
>>> ###### "name":String,
>>> ###### "address":String,
>>> ###### "todayMenu":
>>> ###### [
>>>> ###### {
>>>>> ###### "id":Long,
>>>>> ###### "name":String,
>>>>> ###### "price":Integer
>>>> ###### }
>>>> ###### ...
>>> ###### ],
>>> ###### "voted":Boolean
>> ######  }
> ###### }

### Пример curl
##### `curl -s http://localhost:8080/voteforlunch/votes/2020-05-13 --user inga@gmail.com:passInga`

# 2. API для администратора

Помимо нижеуказанных функций, администратору доступны все функции API обычного пользователя.

## 2.1. Получить список всех ресторанов.

#### `URL /admin/restaurants`

### Запрос
  * Метод: GET

### Ответ
  * HTTP код ответа: 200
  * Данные: объекты Restaurant
######
    [
      {
        "id":Long,
        "name":String,
        "address":String
      }
      ...
    ]

### Пример curl
##### `curl -s http://localhost:8080/voteforlunch/admin/restaurants --user inga@gmail.com:passInga`

## 2.2. Получить ресторан по его id.

#### `URL /admin/restaurants/{id}`

### Запрос
  * Метод: GET
  * Параметр URL: Long id

### Ответ
  * HTTP код ответа: 200
  * Данные: объект Restaurant
######
    {
      "id":Long,
      "name":String,
      "address":String
    }

### Пример curl
##### `curl -s http://localhost:8080/voteforlunch/admin/restaurants/100004 --user inga@gmail.com:passInga`

## 2.3. Создать ресторан.

#### `URL /admin/restaurants`

### Запрос
  * Метод: POST
  * Данные: объект Restaurant (без поля id)
######
    {
      "name":String,
      "address":String
    }

### Ответ
  * HTTP код ответа: 201
  * Данные: объект Restaurant
######
    {
      "id":Long,
      "name":String,
      "address":String
    }

### Пример curl
##### `curl -X POST -i -d '{"name":"New restaurant", "address":"Moscow, ul. Tenistaya, 1"}' -H "Content-Type: application/json; charset=UTF-8" -s http://localhost:8080/voteforlunch/admin/restaurants --user inga@gmail.com:passInga`

## 2.4. Обновить ресторан по его id.

#### `URL /admin/restaurants/{id}`

### Запрос
  * Метод: PUT
  * Параметр URL Long id
  * Данные: объект Restaurant
######
    {
      "id":Long,
      "name":String,
      "address":String
    }

### Ответ
  * HTTP код ответа: 204

### Пример curl
##### `curl -X PUT -i -d '{"id":"100004", "name":"New restaurant", "address":"Moscow, ul. Tenistaya, 2"}' -H "Content-Type: application/json; charset=UTF-8" -s http://localhost:8080/voteforlunch/admin/restaurants/100004 --user inga@gmail.com:passInga`

### 2.5. Удалить ресторан по его id.

#### `URL /admin/restaurants/{id}`

### Запрос
  * Метод: DELETE
  * Параметр URL: Long id

### Ответ
  * HTTP код ответа: 204

### Пример curl
##### `curl -X DELETE -s http://localhost:8080/voteforlunch/admin/restaurants/100004 --user inga@gmail.com:passInga`

## 2.6. Получить список меню ресторана по его restaurantId.

#### `URL /admin/restaurants/{restaurantId}/menu`

### Запрос
  * Метод: GET
  * Параметр URL: Long restaurantId

### Ответ
  * HTTP код ответа: 200

  * Данные: объект MenuTo
###### 
    {
      "restaurant":
      {
        "id":Long,
        "name":String,
         "address":String
      },
      "menus":
      {
        LocalDate:
        [
          {
            "id":Long,
            "name":String,
            "price":Long
          }
          ...
        ]
      }
    }

### Пример curl
##### `curl -s http://localhost:8080/voteforlunch/admin/restaurants/100004/menu --user inga@gmail.com:passInga`

## 2.7. Получить список меню ресторана по его restaurantId в интервале дат от startDate до endDate включительно.

#### `URL /admin/restaurants/{restaurantId}/menu/filter`

### Запрос
  * Метод: GET
  * Параметр URL: Long restaurantId
  * Параметры URL (необязательные): LocalDate startDate, LocalDate endDate

### Ответ
  * HTTP код ответа: 200
  * Данные: объект MenuTo
###### 
    {
      "restaurant":
      {
        "id":Long,
        "name":String,
         "address":String
      },
      "menus":
      {
        LocalDate:
        [
          {
            "id":Long,
            "name":String,
            "price":Long
          }
          ...
        ]
      }
    }

### Пример curl
##### `curl -s http://localhost:8080/voteforlunch/admin/restaurants/100004/menu/filter?startDate=2020-05-12&endDate=2020-05-14 --user inga@gmail.com:passInga`

## 2.8. Получить меню ресторана по его restaurantId на дату date.

#### `URL /admin/restaurants/{restaurantId}/menu/{date}`

### Запрос
  * Метод: GET
  * Параметры URL: Long restaurantId, LocalDate date

### Ответ
  * HTTP код ответа: 200
  * Данные: объект MenuTo
###### 
    {
      "restaurant":
      {
        "id":Long,
        "name":String,
         "address":String
      },
      "menus":
      {
        LocalDate:
        [
          {
            "id":Long,
            "name":String,
            "price":Long
          }
          ...
        ]
      }
    }

### Пример curl
##### `curl -s http://localhost:8080/voteforlunch/admin/restaurants/100004/menu/2020-05-13 --user inga@gmail.com:passInga`

## 2.9. Получить блюдо по его id из меню ресторана по его restaurantId на дату date.

#### `URL /admin/restaurants/{restaurantId}/menu/{date}/dishes/{id}`

### Запрос
  * Метод: GET
  * Параметры URL: Long restaurantId, LocalDate date, Long id

### Ответ
  * HTTP код ответа: 200
  * Данные: объект DishTo
> ###### {
>> ###### "id":Long,
>> ###### "name":String,
>> ###### "price":Integer
> ###### }

### Пример curl
##### `curl -s http://localhost:8080/voteforlunch/admin/restaurants/100004/menu/2020-05-13/dishes/100015 --user inga@gmail.com:passInga`

## 2.10. Добавить блюдо в меню ресторана по его restaurantId на дату date.

#### `URL /admin/restaurants/{restaurantId}/menu/{date}/dishes`

### Запрос
  * Метод: POST
  * Параметры URL: Long restaurantId, LocalDate date
  * Данные: объект DishTo (без поля id)
> ###### {
>> ###### "name":String,
>> ###### "price":Integer
> ###### }

### Ответ
  * HTTP код ответа: 201
  * Данные: объект DishTo
> ###### {
>> ###### "id":Long,
>> ###### "name":String,
>> ###### "price":Integer
> ###### }

### Пример curl
##### `curl -X POST -i -d '{"name":"New dish", "price": 12300}' -H "Content-Type: application/json; charset=UTF-8" -s http://localhost:8080/voteforlunch/admin/restaurants/100004/menu/2020-05-13/dishes --user inga@gmail.com:passInga`

## 2.11. Обновить блюдо по его id в меню ресторана по его restaurantId на дату date.

#### `URL /admin/restaurants/{restaurantId}/menu/{date}/dishes/{id}`

### Запрос
  * Метод: PUT
  * Параметры URL: Long restaurantId, LocalDate date, Long id
  * Данные: объект DishTo
###### 
    {
    "id":Long,
    "name":String,
    "price":Integer
    }

### Пример curl
##### `curl -X PUT -i -d '{"id": "100031", "name":"New dish", "price": 23400}' -H "Content-Type: application/json; charset=UTF-8" -s http://localhost:8080/voteforlunch/admin/restaurants/100004/menu/2020-05-13/dishes/100031 --user inga@gmail.com:passInga`

## 2.12. Удалить блюдо по его id из меню ресторана по его restaurantId на дату date.

#### `URL /admin/restaurants/{restaurantId}/menu/{date}/dishes/{id}`

### Запрос
  * Метод: DELETE
  * Параметры URL: Long restaurantId, LocalDate date, Long id

### Ответ
  * HTTP код ответа: 204

### Пример curl
##### `curl -X DELETE -s http://localhost:8080/voteforlunch/admin/restaurants/100004/menu/2020-05-13/dishes/100031 --user inga@gmail.com:passInga`

