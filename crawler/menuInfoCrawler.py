from selenium import webdriver
from bs4 import BeautifulSoup
import requests
import urllib.parse
import re
import json
import os

## DB에서 받을 정보
location = ["고양시", "고양시", "고양시"]
res_name = ["야구장농원", "청정바지락칼국수", "해원복집"]


base_url = 'https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query='
img_url = 'https://search.naver.com/search.naver?where=image&sm=tab_jum&query='

def price_str_to_int (str):
    temp = ""
    for i in range(0, len(str)):
        if str[i].isdigit():
            temp += str[i]

    ret = int(temp)
    return ret

for i in range(len(location)):
    data = {}
    url = base_url + urllib.parse.quote_plus(location[i] + '+' + res_name[i])
    req = requests.get(url)

    html = req.text
    soup = BeautifulSoup(html, 'html.parser')
    rep_menu = soup.find('div', {'class': 'menu'})
    price = soup.find('em', {'class' : 'price'})
    desc = soup.find('span', {'class' : 'category'})
    time = soup.find('div', {'class' : 'biztime'})

    if (rep_menu == None or price == None or desc == None):
        temp = soup.find_all('span', {'class': '_3Ru_R'})

        if (temp != []):
            text = str(temp[1].text).split(" ")
            data["name"] = text[0]
            data['price'] = price_str_to_int(text[1])
            #print(int(text[1]))
            desc = soup.find('span', {'class' : '_1EJFy'})
            time = soup.find_all('div', {'class' : '_1qN5M'})

            data['description'] = desc.text
            data['time'] = time[2].text
    else:
        data["name"] = rep_menu.text
        data["price"] = price_str_to_int(price.text)
        data["description"] = desc.text
        data["time"] = time.text

    url = img_url + urllib.parse.quote_plus(location[i] + '+' + res_name[i])
    req = requests.get(url)
    html = req.text
    soup = BeautifulSoup(html, 'html.parser')
    img = soup.find_all(class_='_img')

    if (img != []):
        data["imgUrl"] = img[0]['data-source']

    print(data)
