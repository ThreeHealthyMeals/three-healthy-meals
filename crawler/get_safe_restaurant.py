import re
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
    exist_restaurants = get_restaurant()
    already_registered_rest = json.loads(get_restaurant().text)
    for restaurant in restaurants:
        check = True
        for dic in already_registered_rest:
            if restaurant['name'] == dic['name']:
                check = False
        if check:
            body.append(
                {
                    "name": restaurant['name'],
                    "address": restaurant['address'],
                    "phoneNumber": restaurant['phoneNumber'],
                    "latitude": restaurant['latitude'],
                    "longitude": restaurant['longitude'],
                    "coronaSafe": restaurant['coronaSafe']
                }
            )

    ids = re.sub('[\[\]]', '', post_restaurant_req(body).text)
    if ids == '':
        print('Scheduler end')
        exit(0)

    restaurant_ids = list(ids.split(','))
    exist_restaurants = json.loads(exist_restaurants.content.decode('utf8').replace("'", '"'))


    #TODO: 현우, 찬혁 크롤러 부분 가져와서 api call
    for idx, restaurant_id in enumerate(restaurant_ids):
        location = ''.join(body[idx]['address'].split(' ')[:2])
        menu = get_menu_info(location, body[idx]['name'])
        if menu:
            print('registered...')
            restaurant_menu = {'restaurantId': restaurant_id, **menu}
            register_restaurant_menu(restaurant_menu)
        else:
            #TODO: delete no menu
            pass
