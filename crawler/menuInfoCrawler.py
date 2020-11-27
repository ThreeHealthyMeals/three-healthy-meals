from bs4 import BeautifulSoup
import requests
import urllib.parse

data = {}
base_url = 'https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query='


def get_menu_info(location, restaurant):

    url = base_url + urllib.parse.quote_plus(location + '+' + restaurant)
    req = requests.get(url)

    html = req.text
    soup = BeautifulSoup(html, 'html.parser')
    rep_menu = soup.find('div', {'class': 'menu'})
    price = soup.find('em', {'class': 'price'})
    desc = soup.find('span', {'class': 'category'})

    if rep_menu == None or price == None or desc == None:
        try:
            temp = soup.find_all('span', {'class': '_3Ru_R'})
            if temp is None:
                raise Exception
            text = str(temp[1].text).split(" ")
            data["name"] = text[0]
            data['price'] = text[1]

            desc = soup.find('span', {'class': '_1EJFy'})
            data['description'] = desc.text
        except Exception as e:
            print(e)
            return None
    else:
        data["name"] = rep_menu.text
        data["price"] = price.text
        data["description"] = desc.text

    return data
