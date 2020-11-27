import requests


SAFE_RESTAURANT_API_HOST = 'https://openapi.gg.go.kr/SafetyRestrntInfo?Type=json&KEY=2837dbd5f36c4daa88c8c1c8612788d5&pSize=1000'
FAMOUS_RESTAURANT_API_HOST = 'https://openapi.gg.go.kr/PlaceThatDoATasteyFoodSt?Type=json&KEY=c962ba57009c4694930738b381f0d589&pSize=1000'
WEB_SERVER_HOST = 'http://localhost:8080/api/'


def safe_restaurant_req(method):
    if method == 'GET':
        return requests.get(SAFE_RESTAURANT_API_HOST)


def famous_restaurant_req(method):
    if method == 'GET':
        return requests.get(FAMOUS_RESTAURANT_API_HOST)


def post_restaurant_req(payload):
    API_URL = WEB_SERVER_HOST + 'restaurants/_bulk'
    return requests.post(API_URL, data=payload)


def get_restaurant():
    API_URL = WEB_SERVER_HOST + 'restaurants'
    return requests.get(API_URL)


def register_restaurant_menu(payload):
    API_URL = WEB_SERVER_HOST + 'menus'
    return requests.post(API_URL, data=payload)
