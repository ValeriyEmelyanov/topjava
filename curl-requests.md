curl -X GET \
  http://localhost:8080/topjava/rest/meals \
  -H 'cache-control: no-cache'
  
  curl -X GET \
    http://localhost:8080/topjava/rest/meals/100002 \
    -H 'cache-control: no-cache'
    
   curl -X DELETE \
     http://localhost:8080/topjava/rest/meals/100003 \
     -H 'cache-control: no-cache'
    
   curl -X POST \
     http://localhost:8080/topjava/rest/meals \
     -H 'Content-Type: application/json' \
     -H 'cache-control: no-cache' \
     -d '{
       "id": null,
       "dateTime": "2019-05-30T10:00:00",
       "description": "Завтрак",
       "calories": 512,
       "user": null
   }'
   
   curl -X PUT \
     http://localhost:8080/topjava/rest/meals/100002 \
     -H 'Content-Type: application/json' \
     -H 'cache-control: no-cache' \
     -d '{
       "id": 100002,
       "dateTime": "2015-05-30T10:05:00",
       "description": "Завтрак",
       "calories": 496,
       "user": null
   }'
   
   curl -X GET \
     'http://localhost:8080/topjava/rest/meals/between?startDate=2015-05-30&startTime=&endDate=2015-05-31&endTime=' \
     -H 'cache-control: no-cache'
    
   curl -X GET \
     'http://localhost:8080/topjava/rest/meals/between?startDate=&startTime=08:00&endDate=&endTime=11:00' \
     -H 'cache-control: no-cache'