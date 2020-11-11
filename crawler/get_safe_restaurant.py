import requests
import json
SAFE_RESTAURANT_API_HOST = 'https://openapi.gg.go.kr/SafetyRestrntInfo?Type=json&KEY=2837dbd5f36c4daa88c8c1c8612788d5&pSize=1000'
FAMOUS_RESTAURANT_API_HOST = 'https://openapi.gg.go.kr/PlaceThatDoATasteyFoodSt?Type=json&KEY=c962ba57009c4694930738b381f0d589&pSize=1000'


def safe_restaurant_req(method):
    if method == 'GET':
        return requests.get(SAFE_RESTAURANT_API_HOST)


def famous_restaurant_req(method):
    if method == 'GET':
        return requests.get(FAMOUS_RESTAURANT_API_HOST)


safe_restaurant_response = safe_restaurant_req('GET')
famous_restaurant_response = famous_restaurant_req('GET')

safe_restaurant_data = json.loads(safe_restaurant_response.text)
famous_restaurant_data = json.loads(famous_restaurant_response.text)

restaurant_data = []

for safe_restaurant in safe_restaurant_data['SafetyRestrntInfo'][1]['row']:
    for famous_restaurant in famous_restaurant_data['PlaceThatDoATasteyFoodSt'][1]['row']:
        if safe_restaurant['BIZPLC_NM'] == famous_restaurant['RESTRT_NM']:
            restaurant_data.append(safe_restaurant)
            break

for rest in restaurant_data:
    print(rest['BIZPLC_NM'])