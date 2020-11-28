from bs4 import BeautifulSoup
import requests
import urllib.parse


base_url = 'https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query='
img_url = 'https://search.naver.com/search.naver?where=image&section=post&query='
rest_img_url ='&res_fr=0&res_to=0&sm=tab_opt&face=0&color=0&ccl=0&nso=so%3Ar%2Ca%3Aall%2Cp%3Aall&datetype=0&startdate=0&enddate=0&start=1'


def price_str_to_int(str):
    temp = ""
    for i in range(0, len(str)):
        if str[i].isdigit():
            temp += str[i]
    if temp == '':
        return 0
    else:
        ret = int(temp)
        return ret


def get_menu_info(location, restaurant):
    try:
        data = {}
        url = base_url + urllib.parse.quote_plus(location + '+' + restaurant)
        req = requests.get(url)

        html = req.text
        soup = BeautifulSoup(html, 'html.parser')
        rep_menu = soup.find('div', {'class': 'menu'})
        price = soup.find('em', {'class': 'price'})
        desc = soup.find('span', {'class': 'category'})

        if rep_menu == None or price == None or desc == None:
            temp = soup.find_all('span', {'class': '_3Ru_R'})

            if temp != []:
                text = str(temp[1].text).split(" ")
                data["name"] = text[0]
                data['price'] = price_str_to_int(text[1])
                desc = soup.find('span', {'class' : '_1EJFy'})
                time = soup.find_all('div', {'class' : '_1qN5M'})

                data['description'] = desc.text

        else:
            data["name"] = rep_menu.text
            data["price"] = price_str_to_int(price.text)
            data["description"] = desc.text

        url = img_url + urllib.parse.quote_plus(location + '+' + restaurant) + rest_img_url
        req = requests.get(url)
        html = req.text
        soup = BeautifulSoup(html, 'html.parser')
        img = soup.find_all(class_='_img')

        if img != []:
            data["imgUrl"] = img[0]['data-source']

        return data

    except Exception as e:
        print(e)
        return None

