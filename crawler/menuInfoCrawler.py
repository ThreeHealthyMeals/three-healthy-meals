from selenium import webdriver
from bs4 import BeautifulSoup
import requests
import urllib.parse
import re
import json
import os

## DB에서 받을 정보
location = ["고양시", "고양시"]
res_name = ["야구장농원", "청정바지락칼국수"]

data = {}
base_url = 'https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query='


for i in range(len(location)):
    url = base_url + urllib.parse.quote_plus(location[i] + '+' + res_name[i])
    req = requests.get(url)

    html = req.text
    soup = BeautifulSoup(html, 'html.parser')
    rep_menu = soup.find('div', {'class': 'menu'})
    price = soup.find('em', {'class' : 'price'})
    desc = soup.find('span', {'class' : 'category'})

    if (rep_menu == None or price == None or desc == None):
        temp = soup.find_all('span', {'class': '_3Ru_R'})
        text = str(temp[1].text).split(" ")
        data["name"] = text[0]
        data['price'] = text[1]

        desc = soup.find('span', {'class' : '_1EJFy'})
        data['description'] = desc.text
    else:
        data["name"] = rep_menu.text
        data["price"] = price.text
        data["description"] = desc.text

    print(data)
