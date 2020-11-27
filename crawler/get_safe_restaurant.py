import json
from apis import *
from menuInfoCrawler import get_menu_info
from nutrition_crawler import get_nutrition

SAFE_RESTAURANT_API_HOST = 'https://openapi.gg.go.kr/SafetyRestrntInfo?Type=json&KEY=2837dbd5f36c4daa88c8c1c8612788d5&pSize=1000'
FAMOUS_RESTAURANT_API_HOST = 'https://openapi.gg.go.kr/PlaceThatDoATasteyFoodSt?Type=json&KEY=c962ba57009c4694930738b381f0d589&pSize=1000'
WEB_SERVER_HOST = 'http://localhost:8080/api/'


def register_restaurant():
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

    restaurants = []
    for rest in restaurant_data:
        restaurants.append(
            {
                "name": rest['BIZPLC_NM'],
                "address": rest['REFINE_ROADNM_ADDR'],
                "phoneNumber": rest['TELNO'],
                "latitude": rest['REFINE_WGS84_LAT'],
                "longitude": rest['REFINE_WGS84_LOGT'],
                "coronaSafe": True
            }
        )

    # db 비교 없는것만 추가.
    body = []
    exist_restaurants = get_restaurant().text
    for restaurant in restaurants:
        for k, v in restaurant.items():
            if k not in exist_restaurants:
                body.append(
                    {
                        "name": restaurant['BIZPLC_NM'],
                        "address": restaurant['REFINE_ROADNM_ADDR'],
                        "phoneNumber": restaurant['TELNO'],
                        "latitude": restaurant['REFINE_WGS84_LAT'],
                        "longitude": restaurant['REFINE_WGS84_LOGT'],
                        "coronaSafe": True
                    }
                )

    restaurant_ids = post_restaurant_req(body)

    # TODO: 현우, 찬혁 크롤러 부분 가져와서 api call
    for restaurant_id in restaurant_ids:
        for restaurant in exist_restaurants:
            if restaurant['id'] == restaurant:
                location = restaurant['address'].split('')[:1]
                menu = get_menu_info(location, restaurant['name'])
                if menu:
                    restaurant_menu = {'restaurantId': restaurant_id}.update(menu)
                    register_restaurant_menu(restaurant_menu)
