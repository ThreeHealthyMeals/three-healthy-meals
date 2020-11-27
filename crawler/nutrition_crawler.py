from urllib.request import urlopen
from urllib.request import HTTPError
from urllib.request import URLError
from bs4 import BeautifulSoup
from pprint import pprint
import requests
import urllib.parse
import re


mainUrl = 'https://www.fatsecret.kr/'

def searchMenu():
    menuName = input('검색어를 입력하세요 : ')
    
    return menuName

def removeTag(text_origin):
    text_origin = str(text_origin)
    text_removeTag = re.sub('<.+?>', '', text_origin, 0).strip()

    return text_removeTag

def open_url(url):
    try:
        html = urlopen(url)
    except HTTPError as e:
        return None
    except URLError as e:
        return None

def removeBrackets(text):
    text = text[1:len(text)-1]

    return text

def StringToList(string):
    string = string.split(', ')

    return string


# 메뉴 검색

baseUrl = 'https://www.fatsecret.kr/%EC%B9%BC%EB%A1%9C%EB%A6%AC-%EC%98%81%EC%96%91%EC%86%8C/search?q='
plusUrl = searchMenu()

url = baseUrl + urllib.parse.quote_plus(plusUrl)

open_url(url)

html = requests.get(url)

soup = BeautifulSoup(html.text, 'html.parser')

search_a_tag = soup.find('a', {'class':'prominent'})
search_link = search_a_tag.attrs['href']

# 검색어에 대한 영양정보

search_url = mainUrl + search_link

html = requests.get(search_url)
soup = BeautifulSoup(html.text, 'html.parser')


# 영양정보 크롤링 리스트

# 검색어에 대해 검색된 메뉴이름

title = soup.find('h1', {'style':'text-transform:none'})
title = removeTag(title)
print(title)

# 서빙 사이즈

serving_size = soup.find('div', {'class':'serving_size black serving_size_label'})
serving_size_amount = soup.find('div', {'class':'serving_size black serving_size_value'})

serving_size = removeTag(serving_size)
serving_size_amount = removeTag(serving_size_amount)

print(serving_size + serving_size_amount)

"""
# 영양소 명

nutrition_list = soup.find_all('div', {'class':'nutrient black left'})
nutrition_sublist = soup.find_all('div', {'class':'nutrient sub left'})
nutrition_list2 = soup.find_all('div', {'class':'nutrient left'})

nutrition_list = removeTag(nutrition_list)
nutrition_sublist = removeTag(nutrition_sublist)
nutrition_list2 = removeTag(nutrition_list2)

print(nutrition_list + nutrition_sublist + nutrition_list2)
"""

# 영양소 함유량

nutrition_list_amount = soup.find_all('div', {'class':'nutrient black right tRight'})
nutrition_sublist_amount = soup.find_all('div', {'class':'nutrient right tRight'})

nutrition_list_amount = removeTag(nutrition_list_amount)
nutrition_sublist_amount = removeTag(nutrition_sublist_amount)

nutrition_list_amount = removeBrackets(nutrition_list_amount)
nutrition_sublist_amount = removeBrackets(nutrition_sublist_amount)

# 영양소 함유랑 리스트 변환

amount_list = StringToList(nutrition_list_amount)
amount_sublist = StringToList(nutrition_sublist_amount)

amount_list.extend(amount_sublist)


# 딕셔너리형태 저장

nut_list = ['name', 'carbohydrate', 'protein', 'fat', 'calorie']
nut_amount_list = amount_list
nut_dict = {}

nut_dict = dict(zip(nut_list, nut_amount_list))

nut_dict['name']=title

print(nut_dict)