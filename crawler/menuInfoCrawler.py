from selenium import webdriver
from bs4 import BeautifulSoup
import json
import os

## python파일의 위치
BASE_DIR = os.path.dirname(os.path.abspath(__file__))

## PC에 따른 경로 지정 필요
driver = webdriver.Chrome('/Users/yey66/Downloads/chromedriver_win32/chromedriver')

## 암묵적으로 웹 자원 로드를 위해 3초까지 기다려 준다.
driver.implicitly_wait(3)

## url에 접근한다.

## DB에서 받을 정보 
location = ["고양시", "고양시"]
res_name = ["청정바지락칼국수", "야구장농원"]
data = {}

for i in range(len(location)):
    driver.get('https://www.naver.com')
    driver.find_element_by_name('query').send_keys(location[i] + ' ' + res_name[i]);

    driver.find_element_by_xpath('//*[@id="sform"]/fieldset/button').click()

    html = driver.page_source
    soup = BeautifulSoup(html, 'html.parser')

    rep_menu = soup.select('#place_main_ct > div > section:nth-child(1) > div > div.ct_box_area > div.bizinfo_area > div > div:nth-child(4) > div > ul > li:nth-child(1) > div > div > div > span')
    price = soup.select('#place_main_ct > div > section:nth-child(1) > div > div.ct_box_area > div.bizinfo_area > div > div:nth-child(4) > div > ul > li:nth-child(1) > div > em')



    if (rep_menu == [] or price == []):
        temp = soup.select('#loc-main-section-root > div > div > div:nth-child(2) > div._3cNc1 > div._7BCw9 > div:nth-child(4) > div > span:nth-child(1)')
        for tm in temp:
            str = str(tm.text).split(" ")
            print(str[0])
            print(str[1] + "원")
            data[str[0]] = str[1] + "원"
    else:
        print(rep_menu[0].text)
        print(price[0].text)
        data[rep_menu[0].text] = price[0].text

with open(os.path.join(BASE_DIR, 'result.json'), 'w+') as json_file:
    json.dump(data, json_file)

