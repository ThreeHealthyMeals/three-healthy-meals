from urllib.request import urlopen
from bs4 import BeautifulSoup
from pprint import pprint
import requests
import urllib.parse

# 예시 영양성분
# 이부분을 검색결과 크롤링으로 변경 예정

url = "https://www.fatsecret.kr/%EC%B9%BC%EB%A1%9C%EB%A6%AC-%EC%98%81%EC%96%91%EC%86%8C/%EC%9D%BC%EB%B0%98%EB%AA%85/%ED%98%BC%ED%95%A9-%EA%B2%AC%EA%B3%BC%EB%A5%98?portionid=52042&portionamount=100.000"

html = requests.get(url)

soup = BeautifulSoup(html.text, 'html.parser', from_encoding='utf-8')


# 영양정보 전체 크롤링

nutrition = soup.find('div', {'class':'nutrition_facts international'})

pprint(nutrition)


# 영양정보 크롤링 리스트

nutrition_list = soup.find_all('div', {'class':'nutrient black left'})
nutrition_sublist = soup.find_all('div', {'class':'nutrient sub left'})

pprint(nutrition_list + nutrition_sublist)


""" 
주소는 https://www.fatsecret.kr/칼로리-영양소/search?q=김치찌개 식으로 표현
    search?q="음식이름" url만 변경시 접근 가능

음식은
https://www.fatsecret.kr/칼로리-영양소/음식/감자칩

영양성분은
https://www.fatsecret.kr/칼로리-영양소/일반명/감자칩?portionid=22241&portionamount=1.000

https://www.foodsafetykorea.go.kr/

"""