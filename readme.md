#When the app starts, we can immediately interrogate it.
$ curl -v localhost:8080/api/v1/Library/books| json_pp

#If you try and query a book that doesn’t exist…
$ curl -v localhost:8080/api/v1/Library/books/99


#To create a new Book record we use the following command in a terminal—the $ 
#at the beginning signifies that what follows it is a terminal command:

curl -X POST localhost:8080/api/v1/Library/books -H 'Content-type:application/json' -d '{"name": "Samwise Gamgee", "author": "gardener", "numberOfPages":70 }'

#Then it stores newly created book and sends it back to us

#You can update the book. Let’s change his author.

$ curl -X PUT localhost:8080/api/v1/Library/books/3 -H 'Content-type:application/json' -d '{"name": "Samwise Gamgee", "author": "ring bearer"}'
#And we can see the change reflected in the output.

#Finally, you can delete users like this:

$ curl -X DELETE localhost:8080/api/v1/Library/books/3

# Now if we look again, it's gone
$ curl localhost:8080/api/v1/Library/books/3
Could not find book 3

