# urlshortener with Java Spring Boot

This project was build in VSCODE

### How to set VSCODE to run Java projects
* [Youtube setup tutorial](https://www.youtube.com/watch?v=BB0gZFpukJU&t=4s)

### Prerequisites
* Java (JDK 17 +)
* Spring Boot
* Maven
* H2 database
* Your preferred IDE

### Run project locally
1.- Clone this repo to your local machine
```
git clone https://github.com/paubedac/urlshortener
```

2.- Import project to your IDE

3.- Build project in your IDE

4.- Run project on your IDE pressing the "Run" button

5.- The api will be running on [http://localhost:8080](http://localhost:8080)

### Endpoints
* GET /api/url
Show list of current urls available in the database

* GET /api/url/{shortLink}
Get url data related to shortLink

* GET /api/redirect/{shortLink}
Redirects to the url related to the shortLink

* POST /api/generate
Create url record in database using the provided url and generating a shortLink of 8 characters

Payload example
```json
{
    "url": "https://www.dummylink.com"
}
```

* POST /api/update
Update the url record related to the shortLink

Payload example
```json
{
    "shortLink": "00000000",
    "url": "https://www.dummylink.com",
    "isEnabled": true
}
```

