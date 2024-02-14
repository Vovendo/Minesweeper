# Spring Boot "Minesweeper" Example Project

This is a sample Java / Maven / Docker / Spring Boot (version 3.0.0) application that allows you to play the minesweeper game

## How to Run

This application is packaged as a jar. 

1. Make sure you are using JDK 17 and Maven 3.x
2. You can build the project by running ```mvn clean package```
3. After successful creation, you can start the service using this method:
```
        src/docker/docker-compose up
```
## About the Service
### Endpoints

At the moment, the service has only two endpoint:

#### **POST** http://localhost:8080/minesweeper/new

##### Request Body

```json
{
  "width": 7,
  "height": 6,
  "mines_count": 5
}
```
#### **POST** http://localhost:8080/minesweeper/turn

##### Request Body

```json
{
  "game_id": "b8d984c5-bb8f-481b-82da-00234c5d68a9",
  "col": 0,
  "row": 3
}
```
### Example Requests

#### Create New Game

##### **POST** http://localhost:8080/minesweeper/new

###### Request Body

```json
{
  "width": 7,
  "height": 6,
  "mines_count": 5
}
```

###### Response Body

```json
{
  "game_id": "e086b97d-624a-48f7-9f5f-e235d84c2be2",
  "width": 7,
  "height": 6,
  "mines_count": 5,
  "completed": false,
  "field_state": [
    [
      " ",
      " ",
      " ",
      " ",
      " ",
      " ",
      " "
    ],
    [
      " ",
      " ",
      " ",
      " ",
      " ",
      " ",
      " "
    ],
    [
      " ",
      " ",
      " ",
      " ",
      " ",
      " ",
      " "
    ],
    [
      " ",
      " ",
      " ",
      " ",
      " ",
      " ",
      " "
    ],
    [
      " ",
      " ",
      " ",
      " ",
      " ",
      " ",
      " "
    ],
    [
      " ",
      " ",
      " ",
      " ",
      " ",
      " ",
      " "
    ]
  ]
}
```

#### Make A Turn

##### **POST** http://localhost:8080/minesweeper/turn

###### Request Body

```json
{
  "game_id": "e086b97d-624a-48f7-9f5f-e235d84c2be2",
  "col": 0,
  "row": 3
}
```

###### Response Body

```json
{
  "game_id": "e086b97d-624a-48f7-9f5f-e235d84c2be2",
  "width": 7,
  "height": 6,
  "mines_count": 5,
  "completed": false,
  "field_state": [
    [
      " ",
      " ",
      " ",
      " ",
      " ",
      " ",
      " "
    ],
    [
      " ",
      " ",
      " ",
      " ",
      " ",
      " ",
      " "
    ],
    [
      " ",
      " ",
      " ",
      " ",
      " ",
      " ",
      " "
    ],
    [
      "2",
      " ",
      " ",
      " ",
      " ",
      " ",
      " "
    ],
    [
      " ",
      " ",
      " ",
      " ",
      " ",
      " ",
      " "
    ],
    [
      " ",
      " ",
      " ",
      " ",
      " ",
      " ",
      " "
    ]
  ]
}
```

