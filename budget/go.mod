module service

go 1.16

replace lib => ../lib

require (
	github.com/360EntSecGroup-Skylar/excelize/v2 v2.3.2
	github.com/alecthomas/template v0.0.0-20190718012654-fb15b899a751
	github.com/gin-gonic/gin v1.7.1
	github.com/swaggo/gin-swagger v1.3.0
	github.com/swaggo/swag v1.7.0
	go.mongodb.org/mongo-driver v1.5.1
	lib v0.0.0-00010101000000-000000000000
)
