from urllib.request import urlopen
from bs4 import BeautifulSoup
from pprint import pprint
import requests
import urllib.parse
import re

mainUrl = 'https://www.fatsecret.kr/'

# 메뉴 검색

def searchMenu():
    menuName = input('검색어를 입력하세요 : ')
    
    return menuName

baseUrl = 'https://www.fatsecret.kr/%EC%B9%BC%EB%A1%9C%EB%A6%AC-%EC%98%81%EC%96%91%EC%86%8C/search?q='
plusUrl = searchMenu()

url = baseUrl + urllib.parse.quote_plus(plusUrl)

html = requests.get(url)
soup = BeautifulSoup(html.text, 'html.parser')


search_a_tag = soup.find('a', {'class':'prominent'})
search_link = search_a_tag.attrs['href']

# 검색어에 대한 영양정보

search_url = mainUrl + search_link

html = requests.get(search_url)
soup = BeautifulSoup(html.text, 'html.parser')

"""
# 영양정보 전체 크롤링

title = soup.find('h1', {'style':'text-transform:none'})
print(title)

nutrition = soup.find('div', {'class':'nutrition_facts international'})

pprint(nutrition)
"""

# 영양정보 크롤링 리스트

def removeTag(text_origin):
    text_origin = str(text_origin)
    text_removeTag = re.sub('<.+?>', '', text_origin, 0).strip()

    return text_removeTag


nutrition_list = soup.find_all('div', {'class':'nutrient black left'})
nutrition_sublist = soup.find_all('div', {'class':'nutrient sub left'})

nutrition_list = removeTag(nutrition_list)
nutrition_sublist = removeTag(nutrition_sublist)

pprint(nutrition_list + nutrition_sublist)

nutrition_list_amount = soup.find_all('div', {'class':'nutrient black right tRight'})
nutrition_sublist_amount = soup.find_all('div', {'class':'nutrient right tRight'})

nutrition_list_amount = removeTag(nutrition_list_amount)
nutrition_sublist_amount = removeTag(nutrition_sublist_amount)

pprint(nutrition_list_amount + nutrition_sublist_amount)

"""
div serachNoResult 예외처리
dictionary형태
"""