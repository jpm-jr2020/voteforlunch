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
>
> Each restaurant provides new menu each day.

# 1. Объекты, используемые при обмене данными с приложением

Обмен данными с приложением производится в формате JSON.

Идентификаторы (целые положительные числа) всех объектов (даже разных типов) уникальны в пределах приложения.

Строковые значения (наименование, адрес) не могут содержать только пробельные символы.

## 1.1. Restaurant, ресторан

  * Long id: идентификатор ресторана
  * String name: наименование ресторана (от 2 до 100 символов)
  * String address: адрес ресторана (от 5 до 100 символов)
	
Объекты Restaurant не могут иметь одинаковую комбинацию полей name и address. 	

## 1.2. DishTo, блюдо в меню ресторана

  * Long id: идентификатор блюда
  * String name: наименование блюда (от 2 до 100 символов)
  * Integer price: цена блюда (в минимальных долях валютной единицы (копейки, центы и т.д.), неотрицательное целое число)
	
Объекты DishTo, входящие в состав одного меню одного и того же ресторана, не могут иметь одинаковое поле name. 	

## 1.3. RestaurantTo, ресторан с меню на текущий день

  * Long id: идентификатор ресторана
  * String name: наименование ресторана (от 2 до 100 символов)
  * String address: адрес ресторана (от 5 до 100 символов)
  * List<DishTo> todayMenu: перечень блюд в меню ресторана на текущий день, отсортированный по наименованию блюд
  * Boolean voted: true, если текущий пользователь проголосовал за меню данного ресторана на текущий день
	
## 1.4. MenuTo, меню ресторана на перечень дат

  * Restaurant restaurant: ресторан
  * Map<LocalDate, List<DishTo>>: меню ресторана на перечень дат, по одному меню (перечню блюд) на дату, отсортированные по убыванию дат 
  
## 1.5. Vote, голос текущего пользователя за меню ресторана на дату

  * LocalDateTime dateTime: дата и время голосования
  * Restaurant restaurant: ресторан, за который проголосовал текущий пользователь

## 1.6. VoteTo, голос текущего пользователя за меню ресторана на дату, с меню ресторана на эту дату

  * LocalDateTime dateTime: дата и время голосования
  * RestaurantTo restaurantTo: ресторан (с меню на дату голосования), за который проголосовал текущий пользователь

# 2. REST API для обычного пользователя
## 2.1. Получить список всех ресторанов и их меню на сегодня.

#### `URL /restaurants`

### Запрос
  * Метод: GET
  
### Ответ
  * HTTP код ответа: 200
  * Данные: объекты RestaurantTo
######
    [    
      { 
        "id":Long,
        "name":String,
        "address":String,
        "todayMenu":
        [
          {
            "id":Long,
            "name":String,
            "price":Integer
          }
          ...
        ],
        "voted":Boolean
      }
      ...
    ]
	

### Пример curl
##### `curl -s http://localhost:8080/voteforlunch/restaurants --user inga@gmail.com:passInga`

## 2.2. Получить ресторан по его id и его меню на сегодня.

#### `URL /restaurants/{id}`

### Запрос
  * Метод: GET
  * Параметр URL: Long id

### Ответ
  * HTTP код ответа: 200
  * Данные: объект RestaurantTo    
######
    { 
      "id":Long,
      "name":String,
      "address":String,
      "todayMenu":
      [
        {
          "id":Long,
          "name":String,
          "price":Integer
        }
        ...
      ],
      "voted":Boolean
    }
	
### Ошибки
  * Не найден ресторан, или в меню ресторана нет блюд
    * HTTP код ответа: 422
	* Сообщение: Data not found
  * Некорректное значение id
    * HTTP код ответа: 422
	* Сообщение: Validation error

### Пример curl
##### `curl -s http://localhost:8080/voteforlunch/restaurants --user inga@gmail.com:passInga`

## 2.3. Проголосовать за ресторан по его restaurantId.

#### `URL /votes`

### Запрос
  * Метод: POST
  * Параметр URL: Long restaurantId

### Ответ
  * HTTP код ответа: 201
  * Данные: объект VoteTo
###### 
    {
      "dateTime":LocalDateTime,
      "restaurantTo":
      {
        "id":Long,
        "name":String,
        "address":String,
        "todayMenu":
        [
          {
            "id":Long,
            "name":String,
            "price":Integer
          }
          ...
        ],
        "voted":Boolean
      }
    }

### Ошибки
  * Не найден ресторан, или в меню ресторана на сегодня нет блюд
    * HTTP код ответа: 422
	* Сообщение: Data not found
  * Некорректное значение restaurantId
    * HTTP код ответа: 422
	* Сообщение: Validation error
  * Отсутствует параметр restaurantId
    * HTTP код ответа: 400
	* Сообщение: Wrong request
  * Попытка заново проголосовать после 11:00
    * HTTP код ответа: 400
	* Сообщение: Wrong request
	
### Пример curl
##### `curl -X POST -i -s http://localhost:8080/voteforlunch/votes?restaurantId=100004 --user inga@gmail.com:passInga`

## 2.4. Получить историю своих голосований.

#### `URL /votes`

### Запрос
  * Метод: GET

### Ответ
  * HTTP код ответа: 200
  * Данные: объекты Vote
###### 
    [
      {
        "dateTime":LocalDateTime,
        "restaurant":
        {
          "id":Long,
          "name":String,
          "address":String
        }
      }
      ...
    ]

### Пример curl
##### `curl -s http://localhost:8080/voteforlunch/votes --user inga@gmail.com:passInga`

## 2.5. Получить историю своих голосований в интервале дат от startDate до endDate включительно.

#### `URL /votes/filter`

### Запрос
  * Метод: GET
  * Параметры URL (необязательные): LocalDate startDate, LocalDate endDate

### Ответ
  * HTTP код ответа: 200
  * Данные: объекты Vote
###### 
    [
      {
        "dateTime":LocalDateTime,
        "restaurant":
        {
          "id":Long,
          "name":String,
          "address":String
        }
      }
      ...
    ]

### Ошибки
  * Некорректные значения startDate и/или endDate
    * HTTP код ответа: 422
	* Сообщение: Validation error
	
### Пример curl
##### `curl -s http://localhost:8080/voteforlunch/votes/filter?startDate=2020-05-13&endDate=2020-05-14 --user inga@gmail.com:passInga`

## 2.6. Получить свое голосование за дату date.

#### `URL /votes/{date}`

### Запрос
  * Метод: GET
  * Параметр URL: LocalDate date

### Ответ
  * HTTP код ответа: 200
  * Данные: объект VoteTo
###### 
    {
      "dateTime":LocalDateTime,
      "restaurantTo":
      {
        "id":Long,
        "name":String,
        "address":String,
        "todayMenu":
        [
          {
            "id":Long,
            "name":String,
            "price":Integer
          }
          ...
        ],
        "voted":Boolean
      }
    }

### Ошибки
  * Пользователь не голосовал в указанную дату
    * HTTP код ответа: 422
	* Сообщение: Data not found
  * Некорректное значение date
    * HTTP код ответа: 422
	* Сообщение: Validation error
	
### Пример curl
##### `curl -s http://localhost:8080/voteforlunch/votes/2020-05-13 --user inga@gmail.com:passInga`

# 3. REST API для администратора

Помимо нижеуказанных функций, администратору доступны все функции API обычного пользователя.

## 3.1. Получить список всех ресторанов.

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

## 3.2. Получить ресторан по его id.

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

### Ошибки
  * Не найден ресторан
    * HTTP код ответа: 422
	* Сообщение: Data not found
  * Некорректное значение id
    * HTTP код ответа: 422
	* Сообщение: Validation error
	
### Пример curl
##### `curl -s http://localhost:8080/voteforlunch/admin/restaurants/100004 --user inga@gmail.com:passInga`

## 3.3. Создать ресторан.

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
	
### Ошибки
  * Не найден ресторан
    * HTTP код ответа: 422
	* Сообщение: Data not found
  * Некорректное значение id
    * HTTP код ответа: 422
	* Сообщение: Validation error
  * Некорректные поля name и/или address
    * HTTP код ответа: 422
	* Сообщение: Validation error
  * Присутствует поле id в объекте запроса
    * HTTP код ответа: 422
	* Сообщение: Validation error
  * Ошибка в формате объекта запроса
    * HTTP код ответа: 422
	* Сообщение: Validation error
  * Попытка создания дубликата ресторана
    * HTTP код ответа: 409
	* Сообщение: Validation error

### Пример curl
##### `curl -X POST -i -d '{"name":"New restaurant", "address":"Moscow, ul. Tenistaya, 1"}' -H "Content-Type: application/json; charset=UTF-8" -s http://localhost:8080/voteforlunch/admin/restaurants --user inga@gmail.com:passInga`

## 3.4. Обновить ресторан по его id.

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

### Ошибки
  * Не найден ресторан
    * HTTP код ответа: 422
	* Сообщение: Data not found
  * Некорректное значение id
    * HTTP код ответа: 422
	* Сообщение: Validation error
  * Некорректные поля name и/или address
    * HTTP код ответа: 422
	* Сообщение: Validation error
  * Отличаются значения id в объекте запроса и в параметре URL
    * HTTP код ответа: 422
	* Сообщение: Validation error
  * Ошибка в формате объекта запроса
    * HTTP код ответа: 422
	* Сообщение: Validation error
  * Попытка создания дубликата ресторана
    * HTTP код ответа: 409
	* Сообщение: Validation error
	
### Пример curl
##### `curl -X PUT -i -d '{"id":"100004", "name":"New restaurant", "address":"Moscow, ul. Tenistaya, 2"}' -H "Content-Type: application/json; charset=UTF-8" -s http://localhost:8080/voteforlunch/admin/restaurants/100004 --user inga@gmail.com:passInga`

### 3.5. Удалить ресторан по его id.

#### `URL /admin/restaurants/{id}`

### Запрос
  * Метод: DELETE
  * Параметр URL: Long id

### Ответ
  * HTTP код ответа: 204

### Ошибки
  * Не найден ресторан
    * HTTP код ответа: 422
	* Сообщение: Data not found
  * Некорректное значение id
    * HTTP код ответа: 422
	* Сообщение: Validation error
	
### Пример curl
##### `curl -X DELETE -s http://localhost:8080/voteforlunch/admin/restaurants/100004 --user inga@gmail.com:passInga`

## 3.6. Получить список меню ресторана по его restaurantId.

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

### Ошибки
  * Не найден ресторан
    * HTTP код ответа: 422
	* Сообщение: Data not found
  * Некорректное значение id
    * HTTP код ответа: 422
	* Сообщение: Validation error
	
### Пример curl
##### `curl -s http://localhost:8080/voteforlunch/admin/restaurants/100004/menu --user inga@gmail.com:passInga`

## 3.7. Получить список меню ресторана по его restaurantId в интервале дат от startDate до endDate включительно.

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

### Ошибки
  * Не найден ресторан
    * HTTP код ответа: 422
	* Сообщение: Data not found
  * Некорректные значения id и/или startDate и/или endDate
    * HTTP код ответа: 422
	* Сообщение: Validation error
	
### Пример curl
##### `curl -s http://localhost:8080/voteforlunch/admin/restaurants/100004/menu/filter?startDate=2020-05-12&endDate=2020-05-14 --user inga@gmail.com:passInga`

## 3.8. Получить меню ресторана по его restaurantId на дату date.

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

### Ошибки
  * Не найден ресторан
    * HTTP код ответа: 422
	* Сообщение: Data not found
  * Некорректные значения id и/или date
    * HTTP код ответа: 422
	* Сообщение: Validation error
	
### Пример curl
##### `curl -s http://localhost:8080/voteforlunch/admin/restaurants/100004/menu/2020-05-13 --user inga@gmail.com:passInga`

## 3.9. Получить блюдо по его id из меню ресторана по его restaurantId на дату date.

#### `URL /admin/restaurants/{restaurantId}/menu/{date}/dishes/{id}`

### Запрос
  * Метод: GET
  * Параметры URL: Long restaurantId, LocalDate date, Long id

### Ответ
  * HTTP код ответа: 200
  * Данные: объект DishTo
###### 
    {
      "id":Long,
      "name":String,
      "price":Integer
    }

### Ошибки
  * Не найдено блюдо в меню ресторана на дату
    * HTTP код ответа: 422
	* Сообщение: Data not found
  * Некорректные значения restaurantId и/или id и/или date
    * HTTP код ответа: 422
	* Сообщение: Validation error
	
### Пример curl
##### `curl -s http://localhost:8080/voteforlunch/admin/restaurants/100004/menu/2020-05-13/dishes/100015 --user inga@gmail.com:passInga`

## 3.10. Добавить блюдо в меню ресторана по его restaurantId на дату date.

#### `URL /admin/restaurants/{restaurantId}/menu/{date}/dishes`

### Запрос
  * Метод: POST
  * Параметры URL: Long restaurantId, LocalDate date
  * Данные: объект DishTo (без поля id)
###### 
    {
      "name":String,
      "price":Integer
    }

### Ответ
  * HTTP код ответа: 201
  * Данные: объект DishTo
###### 
    {
      "id":Long,
      "name":String,
      "price":Integer
    }

### Ошибки
  * Не найден ресторан
    * HTTP код ответа: 422
	* Сообщение: Data not found
  * Некорректные значения restaurantId и/или date
    * HTTP код ответа: 422
	* Сообщение: Validation error
  * Некорректные поля name и/или price
    * HTTP код ответа: 422
	* Сообщение: Validation error
  * Присутствует поле id в объекте запроса
    * HTTP код ответа: 422
	* Сообщение: Validation error
  * Ошибка в формате объекта запроса
    * HTTP код ответа: 422
	* Сообщение: Validation error
  * Попытка создания дубликата блюда
    * HTTP код ответа: 409
	* Сообщение: Validation error
	
### Пример curl
##### `curl -X POST -i -d '{"name":"New dish", "price": 12300}' -H "Content-Type: application/json; charset=UTF-8" -s http://localhost:8080/voteforlunch/admin/restaurants/100004/menu/2020-05-13/dishes --user inga@gmail.com:passInga`

## 3.11. Обновить блюдо по его id в меню ресторана по его restaurantId на дату date.

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

### Ошибки
  * Не найдено блюдо в меню ресторана на дату
    * HTTP код ответа: 422
	* Сообщение: Data not found
  * Некорректные значения restaurantId и/или date и/или id
    * HTTP код ответа: 422
	* Сообщение: Validation error
  * Некорректные поля name и/или price
    * HTTP код ответа: 422
	* Сообщение: Validation error
  * Отличаются значения id в объекте запроса и в параметре URL
    * HTTP код ответа: 422
	* Сообщение: Validation error
  * Ошибка в формате объекта запроса
    * HTTP код ответа: 422
	* Сообщение: Validation error
  * Попытка создания дубликата блюда
    * HTTP код ответа: 409
	* Сообщение: Validation error
	
### Пример curl
##### `curl -X PUT -i -d '{"id": "100031", "name":"New dish", "price": 23400}' -H "Content-Type: application/json; charset=UTF-8" -s http://localhost:8080/voteforlunch/admin/restaurants/100004/menu/2020-05-13/dishes/100031 --user inga@gmail.com:passInga`

## 3.12. Удалить блюдо по его id из меню ресторана по его restaurantId на дату date.

#### `URL /admin/restaurants/{restaurantId}/menu/{date}/dishes/{id}`

### Запрос
  * Метод: DELETE
  * Параметры URL: Long restaurantId, LocalDate date, Long id

### Ответ
  * HTTP код ответа: 204

### Ошибки
  * Не найдено блюдо в меню ресторана на дату
    * HTTP код ответа: 422
	* Сообщение: Data not found
  * Некорректные значения restaurantId и/или date и/или id
    * HTTP код ответа: 422
	* Сообщение: Validation error
	
### Пример curl
##### `curl -X DELETE -s http://localhost:8080/voteforlunch/admin/restaurants/100004/menu/2020-05-13/dishes/100031 --user inga@gmail.com:passInga`

# 4. Аутентификация

Функциями REST API приложения могут пользоваться только зарегистрированные пользователи: обычные пользователи и администраторы. 

Используется HTTP basic аутентификация.

### Ошибки
  * Попытка использования API неаутентифицированным пользователем
    * HTTP код ответа: 401
   * Попытка использования API администратора обычным пользователем
     * HTTP код ответа: 403
  
# 5. Кэширование

##### 5.1. Кэшируются результаты запросов администратора на получение ресторанов и списков ресторанов (пункты API: 2.1. и 2.2.), на 1 час, до 100 запросов.

##### 5.2. Кэшируются результаты запросов администратора на получение меню ресторанов на все даты и на определенную дату (пункты API: 3.6. и 3.8.), а также на получение блюд из меню ресторанов (пункт API: 3.9.), на 10 минут, до 1000 запросов.

##### 5.3. Кэшируются результаты запросов пользователя на получение ресторанов и списков ресторанов с меню на сегодня (пункты API: 2.1. и 2.2.), на 10 минут, до 5000 запросов.

##### 5.4. Кэшируются результаты запросов пользователя на получение всей истории своих голосований (пункт API: 2.4.), на 10 минут, до 1000 запросов.

Очистка кэшей 5.2. и 5.3. производится при создании, обновлении и удалении блюд меню (пункты API: 3.3., 3.4. и 3.5.).

Очистка кэшей 5.1., 5.3. и 5.4. производится при голосовании пользователя (пункт API: 2.3.).

Очистка всех кэшей производится при создании, обновлении и удалении ресторанов (пункты API: 3.10., 3.11. и 3.12.).

# 6. Пример сценария работы приложения

Сегодня 12 мая 2020 г.

### 6.1. Пользователь получает список ресторанов с меню на сегодня
##### `curl -s http://localhost:8080/voteforlunch/restaurants --user petr@yandex.ru:petr`

### 6.2. Пользователь голосует за ресторан
##### `curl -X POST -i -s http://localhost:8080/voteforlunch/votes?restaurantId=100004 --user petr@yandex.ru:petr`

### 6.3. Администратор получает список ресторанов
##### `curl -s http://localhost:8080/voteforlunch/admin/restaurants --user inga@gmail.com:passInga`

### 6.4. Администратор создает новый ресторан (ресторян получает id 100050)
##### `curl -X POST -i -d '{"name":"New restaurant", "address":"Moscow, ul. Tenistaya, 1"}' -H "Content-Type: application/json; charset=UTF-8" -s http://localhost:8080/voteforlunch/admin/restaurants --user inga@gmail.com:passInga`

### 6.5. Администратор добавляет блюда в меню созданного ресторана на сегодня
##### `curl -X POST -i -d '{"name":"New dish", "price": 12300}' -H "Content-Type: application/json; charset=UTF-8" -s http://localhost:8080/voteforlunch/admin/restaurants/100050/menu/2020-05-12/dishes --user inga@gmail.com:passInga`
##### `curl -X POST -i -d '{"name":"New desert", "price": 23400}' -H "Content-Type: application/json; charset=UTF-8" -s http://localhost:8080/voteforlunch/admin/restaurants/100050/menu/2020-05-12/dishes --user inga@gmail.com:passInga`

### 6.6. Администратор получает меню созданного ресторана на сегодня
##### `curl -s http://localhost:8080/voteforlunch/admin/restaurants/100050/menu/2020-05-12 --user inga@gmail.com:passInga`

### 6.7. Пользователь получает список ресторанов с меню на сегодня
##### `curl -s http://localhost:8080/voteforlunch/restaurants --user petr@yandex.ru:petr`

### 6.8. Пользователь меняет свой голос в пользу нового ресторана
##### `curl -X POST -i -s http://localhost:8080/voteforlunch/votes?restaurantId=100050 --user petr@yandex.ru:petr`

### 6.9. Пользователь получает историю своих голосований
##### `curl -s http://localhost:8080/voteforlunch/votes --user petr@yandex.ru:petr`

### 6.10. Администратор получает список ресторанов с меню на сегодня 
##### `curl -s http://localhost:8080/voteforlunch/restaurants --user inga@gmail.com:passInga`

### 6.11. Администратор голосует за новый ресторан
##### `curl -X POST -i -s http://localhost:8080/voteforlunch/votes?restaurantId=100050 --user inga@gmail.com:passInga`
