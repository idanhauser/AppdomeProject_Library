#   Appdome Home Assignment
Idan Hauser.

#If you want query a book that exists:
curl -v localhost:8080/api/v1/Library/books/{BookId}| json_pp

#for example:
curl -v localhost:8080/api/v1/Library/books/1| json_pp


#If you try and query a book that doesn't exist:

curl -v localhost:8080/api/v1/Library/books/{BookId}| json_pp
#for example:
curl -v localhost:8080/api/v1/Library/books/99| json_pp

#To create a new Book record we use the following command in a terminal:

curl -X POST localhost:8080/api/v1/Library/books -H 'Content-type:application/json' -d '{"name": "{BookName}", "author": "{AuthorName}", "numberOfPages":{NumberOfPages} }'| json_pp
#for example:
curl -X POST localhost:8080/api/v1/Library/books -H 'Content-type:application/json' -d '{"name": "AppDomeBook", "author": "AppdomeManager", "numberOfPages":199 }'| json_pp

#Then it stores newly created book and sends it back to us:
->  
{
"author" : "AppdomeManager",
"id" : 1,
"name" : "AppDomeBook",
"numberOfPages" : 199
}

#Finally, you can delete books like this:

curl -X DELETE localhost:8080/api/v1/Library/books/{BookId} | json_pp

#for example:
curl -X DELETE localhost:8080/api/v1/Library/books/1

#Now if we look again, it's gone

curl localhost:8080/api/v1/Library/books/1

-> Could not find book 1


# API METHODS:

###addBook
POST http://localhost:8080/api/v1/Library/books

###getBook
GET http://localhost:8080/api/v1/Library/books/{{id}}

###removeBook
DELETE http://localhost:8080/api/v1/Library/books/{{id}}
