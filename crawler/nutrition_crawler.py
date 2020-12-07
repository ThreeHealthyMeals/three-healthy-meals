from re import search
from urllib.request import urlopen
from urllib.error import HTTPError, URLError
from bs4 import BeautifulSoup
import requests
import urllib.parse
import re

mainUrl = 'https://www.fatsecret.kr/'


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

def open_a_tag(url):
    try:
        html = requests.get(url)
        soup = BeautifulSoup(html.text, 'html.parser')
        search_a_tag = soup.find('a', {'class': 'prominent'})
        search_link = search_a_tag.attrs['href']

        return search_link
    except AttributeError as e:
        return None

def open_a_tag_alternative(url, food):
    try:
        html = requests.get(url)
        soup = BeautifulSoup(html.text, 'html.parser')        
        search_a_tag_href = soup.find_all('a', {'class': 'prominent'})
        # 페이지 모든 태그 리스트
        a_tag_link = []
        for i in search_a_tag_href:
            href = i.attrs['href']
            a_tag_link.append(href)

        # 다른 메뉴명 리스트
        search_a_tags = soup.find_all('a', {'class': 'prominent'})
        a_tag_text = removeTag(search_a_tags)
        a_tag_text = removeBrackets(a_tag_text)
        a_tag_text_list = StringToList(a_tag_text)

        i = 0
        # 메뉴명 일치 확인
        for i in range(0, len(a_tag_text_list)):
            correct = a_tag_text_list[i].endswith(food)
            if(correct == True):
                break

        return a_tag_link[i]
    except AttributeError as e:
        return None
        
def ModifyMenuname(str_origin):
    str_modify = str_origin[len(str_origin)-2:len(str_origin)]
    return str_modify

def removeBrackets(text):
    text = text[1:len(text) - 1]

    return text


def StringToList(string):
    string = string.split(', ')

    return string


def removeUnit(text_origin):
    text_origin = re.sub(' kcal', '', text_origin, 0).strip()
    text_origin = re.sub('mg', '', text_origin, 0).strip()
    text_origin = re.sub(' kJ', '', text_origin, 0).strip()
    text_origin = re.sub('g', '', text_origin, 0).strip()
    text_removeUnit = text_origin

    return text_removeUnit


# 메뉴 검색

def get_nutrition(food):
    baseUrl = 'https://www.fatsecret.kr/%EC%B9%BC%EB%A1%9C%EB%A6%AC-%EC%98%81%EC%96%91%EC%86%8C/search?q='
    url = baseUrl + urllib.parse.quote_plus(food)

    open_url(url)
    
    # 검색어에 대한 영양정보

    search_link = open_a_tag(url)

    # 검색어에 대한 검색결과 없을 시 대안 탐색
    if(search_link == None):
        food = ModifyMenuname(food)
        url = baseUrl + urllib.parse.quote_plus(food)
        search_link = open_a_tag_alternative(url, food)
    
    search_url = mainUrl + search_link

    html = requests.get(search_url)
    soup = BeautifulSoup(html.text, 'html.parser')

    # 영양정보 크롤링 리스트

    # 검색어에 대해 검색된 메뉴이름

    title = soup.find('h1', {'style': 'text-transform:none'})
    title = removeTag(title)

    # 서빙 사이즈

    serving_size = soup.find('div', {'class': 'serving_size black serving_size_label'})
    serving_size_amount = soup.find('div', {'class': 'serving_size black serving_size_value'})

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

    nutrition_list_amount = soup.find_all('div', {'class': 'nutrient black right tRight'})
    nutrition_sublist_amount = soup.find_all('div', {'class': 'nutrient right tRight'})

    nutrition_list_amount = removeTag(nutrition_list_amount)
    nutrition_sublist_amount = removeTag(nutrition_sublist_amount)

    nutrition_list_amount = removeBrackets(nutrition_list_amount)
    nutrition_sublist_amount = removeBrackets(nutrition_sublist_amount)

    # 영양소 함유랑 리스트 변환

    nutrition_list_amount = removeUnit(nutrition_list_amount)
    nutrition_sublist_amount = removeUnit(nutrition_sublist_amount)

    amount_list = StringToList(nutrition_list_amount)
    amount_sublist = StringToList(nutrition_sublist_amount)

    amount_list.extend(amount_sublist)

    # 딕셔너리형태 저장

    nut_list = ['name', 'carbohydrate', 'protein', 'fat', 'calorie']
    nut_amount_list = amount_list

    nut_dict = dict(zip(nut_list, nut_amount_list))

    nut_dict['name'] = title
    return nut_dict
